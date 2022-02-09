package com.rostami.onlineservice.controller;

import com.rostami.onlineservice.controller.api.filter.AdSpecification;
import com.rostami.onlineservice.dto.api.ResponseResult;
import com.rostami.onlineservice.dto.api.filter.AdFilter;
import com.rostami.onlineservice.dto.in.create.AdCreateParam;
import com.rostami.onlineservice.dto.in.update.AdUpdateParam;
import com.rostami.onlineservice.dto.out.CreateUpdateResult;
import com.rostami.onlineservice.dto.out.single.AdFindResult;
import com.rostami.onlineservice.model.Ad;
import com.rostami.onlineservice.service.AdService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/ads")
@RequiredArgsConstructor
public class AdController {
    private final AdService adService;

    @PostMapping("/create")
    @PreAuthorize("hasAnyRole('CUSTOMER', 'ADMIN')")
    public ResponseEntity<ResponseResult<CreateUpdateResult>> create(@Validated @RequestBody AdCreateParam param){
        CreateUpdateResult result = adService.save(param);
        return ResponseEntity.ok(ResponseResult.<CreateUpdateResult>builder()
                .code(0).message("Ad Successfully Created.").data(result).build());
    }

    @PutMapping("/update")
    @PreAuthorize("hasAnyRole('CUSTOMER', 'ADMIN')")
    public ResponseEntity<ResponseResult<CreateUpdateResult>> update(@Validated @RequestBody AdUpdateParam param){
        Ad fetchedEntity = adService.getForUpdate(param.getId());
        Ad updatedEntity = param.convertToDomain(fetchedEntity);
        CreateUpdateResult result = adService.update(updatedEntity);
        return ResponseEntity.ok(ResponseResult.<CreateUpdateResult>
                builder().code(0).message("Ad Successfully Updated.").data(result).build());
    }

    @DeleteMapping("/delete/{id}")
    @PreAuthorize("hasAnyRole('CUSTOMER', 'ADMIN')")
    public ResponseEntity<ResponseResult<CreateUpdateResult>> delete(@Validated @PathVariable Long id){
        adService.delete(id);
        return ResponseEntity.ok(ResponseResult.<CreateUpdateResult>
                builder().code(0).message("Ad Successfully Deleted.")
                .data(CreateUpdateResult.builder().success(true).id(id).build()).build());
    }

    @GetMapping("/load/{id}")
    @PreAuthorize("hasAnyRole('CUSTOMER', 'EXPERT', 'ADMIN')")
    public ResponseEntity<ResponseResult<AdFindResult>> read(@Validated @PathVariable Long id){
        AdFindResult result = (AdFindResult) adService.get(id);
        return ResponseEntity.ok(ResponseResult.<AdFindResult>builder()
                .code(0)
                .message("Ad Successfully Loaded.")
                .data(result)
                .build());
    }

    @GetMapping("/all")
    @PreAuthorize("hasAnyRole('CUSTOMER', 'EXPERT', 'ADMIN')")
    public ResponseEntity<ResponseResult<List<AdFindResult>>> readAll(@RequestParam Integer page){
        Pageable pageable = PageRequest.of(page, 5);
        List<AdFindResult> results = adService.findAll(pageable).stream().map(adOutDto -> (AdFindResult) adOutDto).toList();
        return ResponseEntity.ok(ResponseResult.<List<AdFindResult>>builder()
                .code(0)
                .message("All Ads Loaded Successfully.")
                .data(results)
                .build());
    }

    @GetMapping("/filter")
    @PreAuthorize("hasAnyRole('ADMIN')")
    public ResponseEntity<ResponseResult<List<AdFindResult>>> filter(@RequestBody AdFilter filter, @RequestParam Integer page){
        Pageable pageable = PageRequest.of(page, 5);
        Specification<Ad> specification = new AdSpecification().getAds(filter);
        List<AdFindResult> result = adService.findAll(specification, pageable)
                .stream().map(ad -> (AdFindResult) ad).toList();
        return ResponseEntity.ok(ResponseResult.<List<AdFindResult>>builder()
                .code(0)
                .message("Successfully Load All Ads Based On Filter.")
                .data(result)
                .build());
    }

    @PutMapping("/chooseExpert")
    @PreAuthorize("hasAnyRole('CUSTOMER', 'ADMIN')")
    public ResponseEntity<ResponseResult<CreateUpdateResult>> chooseExpert(@RequestParam Long adId, @RequestParam Long expertId){
        CreateUpdateResult res = adService.chooseExpert(adId, expertId);
        return ResponseEntity.ok(ResponseResult.<CreateUpdateResult>builder()
                .code(0)
                .message("Successfully Chose Expert For Ad")
                .data(res)
                .build());
    }

    @GetMapping("/loadAllAdsOfCustomer/{id}")
    @PreAuthorize("hasAnyRole('CUSTOMER', 'EXPERT', 'ADMIN')")
    public ResponseEntity<ResponseResult<List<AdFindResult>>> loadAllAdsOfCustomer(@PathVariable Long id, @RequestParam Integer page){
        Pageable pageable = PageRequest.of(page, 5);
        List<AdFindResult> allAdsOfCustomer = adService.findAllAdsOfCustomer(id, pageable);
        return ResponseEntity.ok(ResponseResult.<List<AdFindResult>>builder()
                .code(0)
                .message("Successfully Load All Related Ads.")
                .data(allAdsOfCustomer)
                .build());
    }

    @GetMapping("/findRelatedAdsToExpertSubServ/{id}")
    @PreAuthorize("hasAnyRole('CUSTOMER', 'EXPERT', 'ADMIN')")
    public ResponseEntity<ResponseResult<List<AdFindResult>>> findRelatedAds(@PathVariable Long id, @RequestParam Integer page){
        Pageable pageable = PageRequest.of(page, 5);
        List<AdFindResult> result = adService.findAdsRelatedToExpertSubServ(id, pageable);
        return ResponseEntity.ok(ResponseResult.<List<AdFindResult>>builder()
                .code(0)
                .message("Successfully Load All Related Ads.")
                .data(result)
                .build());
    }

    @PutMapping("/setAdToDone/{adId}")
    @PreAuthorize("hasAnyRole('CUSTOMER', 'ADMIN')")
    public ResponseEntity<ResponseResult<CreateUpdateResult>> setAdToDone(@PathVariable Long adId){
        CreateUpdateResult result = adService.setAdToDone(adId);
        return ResponseEntity.ok(ResponseResult.<CreateUpdateResult>builder()
                .code(0)
                .message("Successfully Updated Ad To Done")
                .data(result)
                .build());
    }
}
