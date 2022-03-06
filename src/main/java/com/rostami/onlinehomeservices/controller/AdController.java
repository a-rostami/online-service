package com.rostami.onlinehomeservices.controller;

import com.rostami.onlinehomeservices.controller.api.filter.AdSpecification;
import com.rostami.onlinehomeservices.dto.api.ResponseResult;
import com.rostami.onlinehomeservices.dto.api.filter.AdFilter;
import com.rostami.onlinehomeservices.dto.in.create.AdCreateParam;
import com.rostami.onlinehomeservices.dto.in.update.AdUpdateParam;
import com.rostami.onlinehomeservices.dto.out.CreateUpdateResult;
import com.rostami.onlinehomeservices.dto.out.single.AdFindResult;
import com.rostami.onlinehomeservices.model.Ad;
import com.rostami.onlinehomeservices.service.AdService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.Set;
import java.util.stream.Collectors;

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
        CreateUpdateResult result = adService.update(param, param.getId());
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
        AdFindResult result = (AdFindResult) adService.findById(id);
        return ResponseEntity.ok(ResponseResult.<AdFindResult>builder()
                .code(0)
                .message("Ad Successfully Loaded.")
                .data(result)
                .build());
    }

    @GetMapping("/all")
    @PreAuthorize("hasAnyRole('CUSTOMER', 'EXPERT', 'ADMIN')")
    public ResponseEntity<ResponseResult<Set<AdFindResult>>> readAll(@RequestParam Integer page){
        Pageable pageable = PageRequest.of(page, 5);
        Set<AdFindResult> results = adService.findAll(pageable).stream().map(adOutDto -> (AdFindResult) adOutDto).collect(Collectors.toSet());
        return ResponseEntity.ok(ResponseResult.<Set<AdFindResult>>builder()
                .code(0)
                .message("All Ads Loaded Successfully.")
                .data(results)
                .build());
    }

    @GetMapping("/filter")
    @PreAuthorize("hasAnyRole('ADMIN')")
    public ResponseEntity<ResponseResult<Set<AdFindResult>>> filter(@RequestBody AdFilter filter, @RequestParam Integer page){
        Pageable pageable = PageRequest.of(page, 5);
        Specification<Ad> specification = new AdSpecification().getAds(filter);
        Set<AdFindResult> result = adService.findAll(specification, pageable)
                .stream().map(ad -> (AdFindResult) ad).collect(Collectors.toSet());
        return ResponseEntity.ok(ResponseResult.<Set<AdFindResult>>builder()
                .code(0)
                .message("Successfully Load All Ads Based On Filter.")
                .data(result)
                .build());
    }

    @PutMapping("/choose-expert")
    @PreAuthorize("hasAnyRole('CUSTOMER', 'ADMIN')")
    public ResponseEntity<ResponseResult<CreateUpdateResult>> chooseExpert(@RequestParam Long adId, @RequestParam Long expertId){
        CreateUpdateResult res = adService.chooseExpert(adId, expertId);
        return ResponseEntity.ok(ResponseResult.<CreateUpdateResult>builder()
                .code(0)
                .message("Successfully Chose Expert For Ad")
                .data(res)
                .build());
    }

    @GetMapping("/load-all-ads-of-customer/{id}")
    @PreAuthorize("hasAnyRole('CUSTOMER', 'EXPERT', 'ADMIN')")
    public ResponseEntity<ResponseResult<Set<AdFindResult>>> loadAllAdsOfCustomer(@PathVariable Long id, @RequestParam Integer page){
        Pageable pageable = PageRequest.of(page, 5);
        Set<AdFindResult> allAdsOfCustomer = adService.findAllAdsOfCustomer(id, pageable);
        return ResponseEntity.ok(ResponseResult.<Set<AdFindResult>>builder()
                .code(0)
                .message("Successfully Load All Related Ads.")
                .data(allAdsOfCustomer)
                .build());
    }

    @GetMapping("/find-related-ads-to-expert-subServ/{id}")
    @PreAuthorize("hasAnyRole('CUSTOMER', 'EXPERT', 'ADMIN')")
    public ResponseEntity<ResponseResult<Set<AdFindResult>>> findRelatedAds(@PathVariable Long id, @RequestParam Integer page){
        Pageable pageable = PageRequest.of(page, 5);
        Set<AdFindResult> result = adService.findAdsRelatedToExpertSubServ(id, pageable);
        return ResponseEntity.ok(ResponseResult.<Set<AdFindResult>>builder()
                .code(0)
                .message("Successfully Load All Related Ads.")
                .data(result)
                .build());
    }

    @PutMapping("/set-ad-to-done/{adId}")
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
