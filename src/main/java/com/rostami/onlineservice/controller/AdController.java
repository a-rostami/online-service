package com.rostami.onlineservice.controller;

import com.rostami.onlineservice.dto.api.ResponseResult;
import com.rostami.onlineservice.dto.in.create.AdCreateParam;
import com.rostami.onlineservice.dto.out.CreateUpdateResult;
import com.rostami.onlineservice.dto.out.single.AdFindResult;
import com.rostami.onlineservice.service.AdService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/ads")
@RequiredArgsConstructor
public class AdController {
    private final AdService adService;

    @PostMapping("/create")
    public ResponseEntity<ResponseResult<CreateUpdateResult>> save(@Validated @RequestBody AdCreateParam param){
        CreateUpdateResult result = adService.saveOrUpdate(param);
        return ResponseEntity.ok(ResponseResult.<CreateUpdateResult>builder()
                .code(0).message("Ad Successfully Created.").data(result).build());
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
    public ResponseEntity<ResponseResult<List<AdFindResult>>> readAll(){
        List<AdFindResult> results = adService.list()
                .stream().map((baseOutDto) -> (AdFindResult) baseOutDto).collect(Collectors.toList());
        return ResponseEntity.ok(ResponseResult.<List<AdFindResult>>builder()
                .code(0)
                .message("All Ads Loaded Successfully.")
                .data(results)
                .build());
    }
}
