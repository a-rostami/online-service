package com.rostami.onlineservice.controller;

import com.rostami.onlineservice.dto.api.ResponseResult;
import com.rostami.onlineservice.dto.in.create.OfferCreateParam;
import com.rostami.onlineservice.dto.in.update.OfferUpdateParam;
import com.rostami.onlineservice.dto.out.CreateUpdateResult;
import com.rostami.onlineservice.dto.out.single.OfferFindResult;
import com.rostami.onlineservice.service.OfferService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequiredArgsConstructor
@RequestMapping("/offers")
@RestController
public class OfferController {
    private final OfferService offerService;

    @PostMapping("/create")
    @PreAuthorize("hasAnyRole('EXPERT')")
    public ResponseEntity<ResponseResult<CreateUpdateResult>> create(@Validated @RequestBody OfferCreateParam param){
        CreateUpdateResult result = offerService.saveOrUpdate(param);
        return ResponseEntity.ok(ResponseResult.<CreateUpdateResult>builder()
                .code(0)
                .message("Offer Successfully Created.")
                .data(result)
                .build());
    }

    @PutMapping("/update")
    @PreAuthorize("hasAnyRole('ADMIN', 'EXPERT')")
    public ResponseEntity<ResponseResult<CreateUpdateResult>> update(@Validated @RequestBody OfferUpdateParam param){
        CreateUpdateResult result = offerService.saveOrUpdate(param);
        return ResponseEntity.ok(ResponseResult.<CreateUpdateResult>
                builder().code(0).message("Offer Successfully Updated.").data(result).build());
    }

    @DeleteMapping("/delete/{id}")
    @PreAuthorize("hasAnyRole('ADMIN', 'EXPERT')")
    public ResponseEntity<ResponseResult<CreateUpdateResult>> delete(@Validated @PathVariable Long id){
        offerService.delete(id);
        return ResponseEntity.ok(ResponseResult.<CreateUpdateResult>
                        builder().code(0).message("Offer Successfully Deleted.")
                .data(CreateUpdateResult.builder().success(true).id(id).build()).build());
    }

    @GetMapping("/load/{id}")
    @PreAuthorize("hasAnyRole('ADMIN', 'EXPERT', 'CUSTOMER')")
    public ResponseEntity<ResponseResult<OfferFindResult>> read(@Validated @PathVariable Long id){
        OfferFindResult result = (OfferFindResult) offerService.get(id);
        return ResponseEntity.ok(ResponseResult.<OfferFindResult>builder()
                .code(0)
                .message("Successfully Found Offer.")
                .data(result).build());
    }

    @GetMapping("/all")
    @PreAuthorize("hasAnyRole('ADMIN')")
    public ResponseEntity<ResponseResult<List<OfferFindResult>>> readAll(@RequestParam Integer page){
        Pageable pageable = PageRequest.of(page, 5);
        List<OfferFindResult> results = offerService.findAll(pageable)
                .stream().map(offerOutDto -> (OfferFindResult) offerOutDto).toList();
        return ResponseEntity.ok(ResponseResult.<List<OfferFindResult>>builder()
                .code(0)
                .message("Successfully Found All Offers.")
                .data(results)
                .build());
    }

    @GetMapping("/loadAllOffersOfExpert/{id}")
    @PreAuthorize("hasAnyRole('ADMIN', 'EXPERT')")
    public ResponseEntity<ResponseResult<List<OfferFindResult>>> loadAllAdsOfCustomer(@PathVariable Long id, @RequestParam Integer page){
        Pageable pageable = PageRequest.of(page, 5);
        List<OfferFindResult> allOffersOfExpert = offerService.loadAllOffersOfExpert(id, pageable);
        return ResponseEntity.ok(ResponseResult.<List<OfferFindResult>>builder()
                .code(0)
                .message("Successfully Load All Related Offers.")
                .data(allOffersOfExpert)
                .build());
    }

    @GetMapping("/orderOffersByPrice/{adId}")
    @PreAuthorize("hasAnyRole('ADMIN')")
    public ResponseEntity<ResponseResult<List<OfferFindResult>>> orderOffersByPrice(@PathVariable Long adId, @RequestParam Integer page){
        Pageable pageable = PageRequest.of(page, 5, Sort.by("price"));
        List<OfferFindResult> results = offerService.orderOffersByPrice(adId, pageable);
        return ResponseEntity.ok(ResponseResult.<List<OfferFindResult>>builder()
                .code(0)
                .message("Successfully Load All Sorted Offers By Price.")
                .data(results)
                .build());
    }
}
