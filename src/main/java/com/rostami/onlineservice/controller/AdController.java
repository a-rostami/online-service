package com.rostami.onlineservice.controller;

import com.rostami.onlineservice.controller.api.filter.AdSpecification;
import com.rostami.onlineservice.dto.api.ResponseResult;
import com.rostami.onlineservice.dto.api.filter.AdFilter;
import com.rostami.onlineservice.dto.in.create.AdCreateParam;
import com.rostami.onlineservice.dto.in.update.AdUpdateParam;
import com.rostami.onlineservice.dto.out.CreateUpdateResult;
import com.rostami.onlineservice.dto.out.single.AdFindResult;
import com.rostami.onlineservice.dto.out.single.MainServFindResult;
import com.rostami.onlineservice.entity.Ad;
import com.rostami.onlineservice.entity.MainServ;
import com.rostami.onlineservice.service.AdService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@RestController
@RequestMapping("/ads")
@RequiredArgsConstructor
public class AdController {
    private final AdService adService;

    @PostMapping("/create")
    public ResponseEntity<ResponseResult<CreateUpdateResult>> create(@Validated @RequestBody AdCreateParam param){
        CreateUpdateResult result = adService.saveOrUpdate(param);
        return ResponseEntity.ok(ResponseResult.<CreateUpdateResult>builder()
                .code(0).message("Ad Successfully Created.").data(result).build());
    }

    @PutMapping("/update")
    public ResponseEntity<ResponseResult<CreateUpdateResult>> update(@Validated @RequestBody AdUpdateParam param){
        CreateUpdateResult result = adService.saveOrUpdate(param);
        return ResponseEntity.ok(ResponseResult.<CreateUpdateResult>
                builder().code(0).message("Ad Successfully Updated.").data(result).build());
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<ResponseResult<CreateUpdateResult>> delete(@Validated @PathVariable Long id){
        adService.delete(id);
        return ResponseEntity.ok(ResponseResult.<CreateUpdateResult>
                builder().code(0).message("Ad Successfully Deleted.")
                .data(CreateUpdateResult.builder().success(true).id(id).build()).build());
    }

    @GetMapping("/load/{id}")
    public ResponseEntity<ResponseResult<AdFindResult>> read(@Validated @PathVariable Long id){
        AdFindResult result = (AdFindResult) adService.get(id);
        return ResponseEntity.ok(ResponseResult.<AdFindResult>builder()
                .code(0)
                .message("Ad Successfully Loaded.")
                .data(result)
                .build());
    }

    @GetMapping("/all")
    public ResponseEntity<ResponseResult<List<AdFindResult>>> readAll(@RequestParam Integer page){
        Pageable pageable = PageRequest.of(page, 5);
        Stream<Ad> stream = adService.findAll(pageable).get();
        List<AdFindResult> results =
                stream.map(ad -> AdFindResult.builder().build().convertToDto(ad)).collect(Collectors.toList());
        return ResponseEntity.ok(ResponseResult.<List<AdFindResult>>builder()
                .code(0)
                .message("All Ads Loaded Successfully.")
                .data(results)
                .build());
    }

    @GetMapping("/filter")
    public ResponseEntity<ResponseResult<List<AdFindResult>>> filter(@RequestBody AdFilter filter, @RequestParam Integer page){
        Pageable pageable = PageRequest.of(page, 5);
        Specification<Ad> specification = new AdSpecification().getAds(filter);
        List<Ad> all = adService.findAll(specification, pageable);
        List<AdFindResult> result = all.stream().map(ad -> AdFindResult.builder().build().convertToDto(ad)).toList();
        return ResponseEntity.ok(ResponseResult.<List<AdFindResult>>builder()
                .code(0)
                .message("Successfully Load All Ads Based On Filter.")
                .data(result)
                .build());
    }

    @PutMapping("/chooseExpert")
    public ResponseEntity<ResponseResult<CreateUpdateResult>> chooseExpert(@RequestParam Long adId, @RequestParam Long expertId){
        CreateUpdateResult res = adService.chooseExpert(adId, expertId);
        return ResponseEntity.ok(ResponseResult.<CreateUpdateResult>builder()
                .code(0)
                .message("Successfully Chose Expert For Ad")
                .data(res)
                .build());
    }

    @GetMapping("/loadAllAdsOfCustomer/{id}")
    public ResponseEntity<ResponseResult<List<AdFindResult>>> loadAllAdsOfCustomer(@PathVariable Long id, @RequestParam Integer page){
        Pageable pageable = PageRequest.of(page, 5);
        List<AdFindResult> allAdsOfCustomer = adService.findAllAdsOfCustomer(id, pageable);
        return ResponseEntity.ok(ResponseResult.<List<AdFindResult>>builder()
                .code(0)
                .message("Successfully Load All Related Ads.")
                .data(allAdsOfCustomer)
                .build());
    }
}
