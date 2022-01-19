package com.rostami.onlineservice.controller;

import com.rostami.onlineservice.dto.api.ResponseResult;
import com.rostami.onlineservice.dto.in.create.CreditCreateParam;
import com.rostami.onlineservice.dto.out.CreateUpdateResult;
import com.rostami.onlineservice.dto.out.single.CreditFindResult;
import com.rostami.onlineservice.service.CreditService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RequestMapping("/credits")
@RequiredArgsConstructor
@RestController
public class CreditController {
    private final CreditService creditService;

    @PostMapping("/create")
    public ResponseEntity<ResponseResult<CreateUpdateResult>> save(@Validated @RequestBody CreditCreateParam param){
        CreateUpdateResult result = creditService.saveOrUpdate(param);
        return ResponseEntity.ok(ResponseResult.<CreateUpdateResult>builder()
                .code(0).message("Credit Successfully Created.").data(result).build());
    }

    @GetMapping("/load/{id}")
    public ResponseEntity<ResponseResult<CreditFindResult>> read(@Validated @PathVariable Long id){
        CreditFindResult result = (CreditFindResult) creditService.get(id);
        return ResponseEntity.ok(ResponseResult.<CreditFindResult>builder()
                .code(0)
                .message("Credit Successfully Loaded.")
                .data(result)
                .build());
    }

    @GetMapping("/all")
    public ResponseEntity<ResponseResult<List<CreditFindResult>>> readAll(){
        List<CreditFindResult> results = creditService.list()
                .stream().map((baseOutDto) -> (CreditFindResult) baseOutDto).collect(Collectors.toList());
        return ResponseEntity.ok(ResponseResult.<List<CreditFindResult>>builder()
                .code(0)
                .message("All Credits Loaded Successfully.")
                .data(results)
                .build());
    }
}
