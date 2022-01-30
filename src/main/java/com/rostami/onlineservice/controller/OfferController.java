package com.rostami.onlineservice.controller;

import com.rostami.onlineservice.dto.api.ResponseResult;
import com.rostami.onlineservice.dto.in.create.OfferCreateParam;
import com.rostami.onlineservice.dto.in.update.OfferUpdateParam;
import com.rostami.onlineservice.dto.out.CreateUpdateResult;
import com.rostami.onlineservice.dto.out.single.MainServFindResult;
import com.rostami.onlineservice.dto.out.single.OfferFindResult;
import com.rostami.onlineservice.entity.MainServ;
import com.rostami.onlineservice.entity.Offer;
import com.rostami.onlineservice.service.OfferService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@RequiredArgsConstructor
@RequestMapping("/offers")
@RestController
public class OfferController {
    private final OfferService offerService;

    @PostMapping("/create")
    public ResponseEntity<ResponseResult<CreateUpdateResult>> create(@Validated @RequestBody OfferCreateParam param){
        CreateUpdateResult result = offerService.saveOrUpdate(param);
        return ResponseEntity.ok(ResponseResult.<CreateUpdateResult>builder()
                .code(0)
                .message("Offer Successfully Created.")
                .data(result)
                .build());
    }

    @PutMapping("/update")
    public ResponseEntity<ResponseResult<CreateUpdateResult>> update(@Validated @RequestBody OfferUpdateParam param){
        CreateUpdateResult result = offerService.saveOrUpdate(param);
        return ResponseEntity.ok(ResponseResult.<CreateUpdateResult>
                builder().code(0).message("Offer Successfully Updated.").data(result).build());
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<ResponseResult<CreateUpdateResult>> delete(@Validated @PathVariable Long id){
        offerService.delete(id);
        return ResponseEntity.ok(ResponseResult.<CreateUpdateResult>
                        builder().code(0).message("Offer Successfully Deleted.")
                .data(CreateUpdateResult.builder().success(true).id(id).build()).build());
    }

    @GetMapping("/load/{id}")
    public ResponseEntity<ResponseResult<OfferFindResult>> read(@Validated @PathVariable Long id){
        OfferFindResult result = (OfferFindResult) offerService.get(id);
        return ResponseEntity.ok(ResponseResult.<OfferFindResult>builder()
                .code(0)
                .message("Successfully Found Offer.")
                .data(result).build());
    }

    @GetMapping("/all")
    public ResponseEntity<ResponseResult<List<OfferFindResult>>> readAll(@RequestParam Integer page){
        Pageable pageable = PageRequest.of(page, 5);
        Stream<Offer> stream = offerService.findAll(pageable).get();
        List<OfferFindResult> results =
                stream.map(offer -> OfferFindResult.builder().build().convertToDto(offer)).collect(Collectors.toList());
        return ResponseEntity.ok(ResponseResult.<List<OfferFindResult>>builder()
                .code(0)
                .message("Successfully Found All Offers.")
                .data(results)
                .build());
    }

    @GetMapping("/loadAllOffersOfExpert/{id}")
    public ResponseEntity<ResponseResult<List<OfferFindResult>>> loadAllAdsOfCustomer(@PathVariable Long id, @RequestParam Integer page){
        Pageable pageable = PageRequest.of(page, 5);
        List<OfferFindResult> allOffersOfExpert = offerService.loadAllOffersOfExpert(id, pageable);
        return ResponseEntity.ok(ResponseResult.<List<OfferFindResult>>builder()
                .code(0)
                .message("Successfully Load All Related Offers.")
                .data(allOffersOfExpert)
                .build());
    }
}
