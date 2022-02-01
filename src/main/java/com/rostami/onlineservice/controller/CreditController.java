package com.rostami.onlineservice.controller;

import com.rostami.onlineservice.dto.api.ResponseResult;
import com.rostami.onlineservice.dto.in.create.CreditCreateParam;
import com.rostami.onlineservice.dto.in.update.CreditUpdateParam;
import com.rostami.onlineservice.dto.out.CreateUpdateResult;
import com.rostami.onlineservice.dto.out.single.CreditFindResult;
import com.rostami.onlineservice.service.CreditService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

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

    @PutMapping("/update")
    public ResponseEntity<ResponseResult<CreateUpdateResult>> update(@Validated @RequestBody CreditUpdateParam param){
        CreateUpdateResult result = creditService.saveOrUpdate(param);
        return ResponseEntity.ok(ResponseResult.<CreateUpdateResult>
                builder().code(0).message("Credit Successfully Updated.").data(result).build());
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<ResponseResult<CreateUpdateResult>> delete(@Validated @PathVariable Long id){
        creditService.delete(id);
        return ResponseEntity.ok(ResponseResult.<CreateUpdateResult>
                        builder().code(0).message("Credit Successfully Deleted.")
                .data(CreateUpdateResult.builder().success(true).id(id).build()).build());
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
    public ResponseEntity<ResponseResult<List<CreditFindResult>>> readAll(@RequestParam Integer page){
        Pageable pageable = PageRequest.of(page, 5);
        List<CreditFindResult> results = creditService.findAll(pageable)
                .stream().map(creditOutDto -> (CreditFindResult) creditOutDto).toList();
        return ResponseEntity.ok(ResponseResult.<List<CreditFindResult>>builder()
                .code(0)
                .message("All Credits Loaded Successfully.")
                .data(results)
                .build());
    }
}
