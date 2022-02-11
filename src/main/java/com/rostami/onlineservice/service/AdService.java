package com.rostami.onlineservice.service;

import com.rostami.onlineservice.dto.out.CreateUpdateResult;
import com.rostami.onlineservice.dto.out.single.AdFindResult;
import com.rostami.onlineservice.dto.out.single.ExpertFindResult;
import com.rostami.onlineservice.model.Ad;
import com.rostami.onlineservice.model.Customer;
import com.rostami.onlineservice.model.Expert;
import com.rostami.onlineservice.model.SubServ;
import com.rostami.onlineservice.model.enums.AdStatus;
import com.rostami.onlineservice.exception.EntityLoadException;
import com.rostami.onlineservice.repository.AdRepository;
import com.rostami.onlineservice.service.base.BaseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.PostConstruct;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import static com.rostami.onlineservice.util.ExceptionMessages.ENTITY_ID_LOAD_MESSAGE;

@Service
public class AdService extends BaseService<Ad, Long, AdFindResult> {
    private final AdRepository repository;
    private final ExpertService expertService;

    @Autowired
    public AdService(AdRepository repository, @Lazy ExpertService expertService) {
        this.repository = repository;
        this.expertService = expertService;
    }

    @PostConstruct
    public void init() {
        setRepository(repository);
        setBaseOutDto(AdFindResult.builder().build());
    }

    @Transactional
    public Set<AdFindResult> findAllAdsOfCustomer(Long customerId, Pageable pageable){
        Customer customer = Customer.builder().id(customerId).build();
        return findAll((root, query, cb) -> cb.equal(root.get("customer"), customer), pageable)
                .stream().map(ad -> (AdFindResult) ad).collect(Collectors.toSet());
    }

    @Transactional
    public Set<AdFindResult> findAdsRelatedToExpertSubServ(Long expertId, Pageable pageable){
        ExpertFindResult expert = (ExpertFindResult) expertService.get(expertId);
        List<SubServ> subServs = expert.getSubServFindResults()
                .stream().map(subServFindResult -> SubServ.builder()
                        .id(subServFindResult.getId())
                        .build()).collect(Collectors.toList());

        return repository.findAll((root, query, cb) -> root.get("subServ").in(subServs), pageable)
                .stream().map(ad -> new AdFindResult().convertToDto(ad)).collect(Collectors.toSet());
    }

    @Transactional
    public CreateUpdateResult chooseExpert(Long adId, Long expertId){
        Ad ad = repository.findById(adId).orElseThrow(() -> new EntityLoadException(ENTITY_ID_LOAD_MESSAGE));
        ad.setChosenExpert(Expert.builder().id(expertId).build());
        ad.setStatus(AdStatus.STARTED);
        return CreateUpdateResult.builder().id(adId).success(true).build();
    }

    @Transactional
    public CreateUpdateResult setAdToDone(Long adId){
        Ad ad = repository.findById(adId).orElseThrow(() -> new EntityLoadException(ENTITY_ID_LOAD_MESSAGE));
        ad.setStatus(AdStatus.DONE);

        Expert chosenExpert = ad.getChosenExpert();
        LocalTime completionTime = ad.getCompletionTime().toLocalTime();
        LocalDateTime now = LocalDateTime.now();
        int delay =  now.getHour() - completionTime.getHour();

        Ad saved = repository.save(ad);
        decreaseExpertPoint(chosenExpert, delay);

        return CreateUpdateResult.builder().id(saved.getId()).success(true).build();
    }

    private void decreaseExpertPoint(Expert expert, int delayByHour){
        if (delayByHour < 1 || expert == null || expert.getId() == null) return;

        Double expertAveragePoint = expert.getAveragePoint();

        // 5% for every 1-Hour delay
        int percentage = 5 * delayByHour / 100;
        double newAveragePoint = expertAveragePoint - (expertAveragePoint * percentage);

        expertService.updateAveragePoint(newAveragePoint, expert.getId());
    }

}