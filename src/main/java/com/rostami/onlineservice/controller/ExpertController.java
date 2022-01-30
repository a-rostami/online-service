package com.rostami.onlineservice.controller;
import com.rostami.onlineservice.controller.api.filter.ExpertSpecification;
import com.rostami.onlineservice.dto.api.ResponseResult;
import com.rostami.onlineservice.dto.api.filter.ExpertFilter;
import com.rostami.onlineservice.dto.in.create.ExpertCreateParam;
import com.rostami.onlineservice.dto.in.update.ExpertUpdateParam;
import com.rostami.onlineservice.dto.in.update.SubServUpdateParam;
import com.rostami.onlineservice.dto.in.update.api.DepositParam;
import com.rostami.onlineservice.dto.out.CreateUpdateResult;
import com.rostami.onlineservice.dto.out.single.AdFindResult;
import com.rostami.onlineservice.dto.out.single.CreditFindResult;
import com.rostami.onlineservice.dto.out.single.CustomerFindResult;
import com.rostami.onlineservice.dto.out.single.ExpertFindResult;
import com.rostami.onlineservice.entity.Customer;
import com.rostami.onlineservice.entity.Expert;
import com.rostami.onlineservice.service.ExpertService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@RestController
@RequestMapping("/experts")
@RequiredArgsConstructor
public class ExpertController {
    private final ExpertService expertService;

    @PostMapping("/create")
    public ResponseEntity<ResponseResult<CreateUpdateResult>> create(@Validated @ModelAttribute ExpertCreateParam param){
        CreateUpdateResult result = expertService.saveOrUpdate(param);
        return ResponseEntity.ok(ResponseResult.<CreateUpdateResult>builder()
                .code(0)
                .message("Expert Successfully Created.")
                .data(result)
                .build());
    }

    @PutMapping("/update")
    public ResponseEntity<ResponseResult<CreateUpdateResult>> update(@Validated @RequestBody ExpertUpdateParam param){
        CreateUpdateResult result = expertService.saveOrUpdate(param);
        return ResponseEntity.ok(ResponseResult.<CreateUpdateResult>
                builder().code(0).message("Expert Successfully Updated.").data(result).build());
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<ResponseResult<CreateUpdateResult>> delete(@Validated @PathVariable Long id){
        expertService.delete(id);
        return ResponseEntity.ok(ResponseResult.<CreateUpdateResult>
                        builder().code(0).message("Expert Successfully Deleted.")
                .data(CreateUpdateResult.builder().success(true).id(id).build()).build());
    }

    @GetMapping("/load/{id}")
    public ResponseEntity<ResponseResult<ExpertFindResult>> read(@Validated @PathVariable Long id){
        ExpertFindResult result = (ExpertFindResult) expertService.get(id);
        return ResponseEntity.ok(ResponseResult.<ExpertFindResult>builder()
                .code(0)
                .message("Successfully Found Expert.")
                .data(result).build());
    }

    @GetMapping("/all")
    public ResponseEntity<ResponseResult<List<ExpertFindResult>>> readAll(@RequestParam Integer page){
        Pageable pageable = PageRequest.of(page, 5);
        Stream<Expert> stream = expertService.findAll(pageable).get();
        List<ExpertFindResult> results =
                stream.map(expert -> ExpertFindResult.builder().build().convertToDto(expert)).collect(Collectors.toList());
        return ResponseEntity.ok(ResponseResult.<List<ExpertFindResult>>builder()
                .code(0)
                .message("Successfully Found All Experts.")
                .data(results)
                .build());
    }

    @GetMapping("/credit/{id}")
    public ResponseEntity<ResponseResult<CreditFindResult>> loadCredit(@PathVariable Long id){
        CreditFindResult creditFindResult = expertService.loadCredit(id);
        return ResponseEntity.ok(ResponseResult.<CreditFindResult>builder()
                .code(0)
                .message("Successfully Load Credit.")
                .data(creditFindResult)
                .build());
    }

    @PutMapping("/deposit")
    public ResponseEntity<ResponseResult<CreateUpdateResult>> depositToCredit(@RequestBody DepositParam param){
        CreateUpdateResult result = expertService.depositToCredit(param.getUserId(), param.getAmount());
        return ResponseEntity.ok(ResponseResult.<CreateUpdateResult>builder()
                .code(0)
                .message("Successfully Deposit To Credit")
                .data(result)
                .build());
    }

    @GetMapping("/findRelatedAds/{id}")
    public ResponseEntity<ResponseResult<List<AdFindResult>>> findRelatedAds(@PathVariable Long id, @RequestParam Integer page){
        Pageable pageable = PageRequest.of(page, 5);
        List<AdFindResult> result = expertService.findAdsRelatedToExpertSubServ(id, pageable);
        return ResponseEntity.ok(ResponseResult.<List<AdFindResult>>builder()
                .code(0)
                .message("Successfully Load All Related Ads.")
                .data(result)
                .build());
    }

    @PostMapping("/addSubServ")
    public ResponseEntity<ResponseResult<CreateUpdateResult>> chooseExpert(@RequestParam Long expertId, @RequestParam Long subServId){
        CreateUpdateResult result = expertService.addSubServ(expertId, subServId);
        return ResponseEntity.ok(ResponseResult.<CreateUpdateResult>builder()
                .code(0)
                .message("SubServ Successfully Added To Expert SubServs.")
                .data(result)
                .build());
    }

    @GetMapping("/filter")
    public ResponseEntity<ResponseResult<List<ExpertFindResult>>> getCustomers(@RequestBody ExpertFilter filter){
        List<Expert> all = expertService.findAll(new ExpertSpecification().getUsers(filter));
        List<ExpertFindResult> results =
                all.stream().map(p -> ExpertFindResult.builder().build().convertToDto(p)).collect(Collectors.toList());
        return ResponseEntity.ok(ResponseResult.<List<ExpertFindResult>>builder()
                .code(0)
                .message("Successfully Load Experts Based Filters.")
                .data(results)
                .build());
    }

    @GetMapping("/countOfDoneAds/{expertId}")
    public ResponseEntity<ResponseResult<Long>> countOfDoneAds(@PathVariable Long expertId){
        long count = expertService.getNumberOfDoneAds(expertId);
        return ResponseEntity.ok(ResponseResult.<Long>builder()
                .code(0)
                .message("Successfully Load Number Of Done Ads By Given Expert.")
                .data(count)
                .build());
    }
}