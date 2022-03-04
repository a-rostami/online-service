package com.rostami.onlinehomeservices.controller;

import com.rostami.onlinehomeservices.dto.api.ResponseResult;
import com.rostami.onlinehomeservices.dto.in.create.CreditCreateParam;
import com.rostami.onlinehomeservices.dto.in.update.CreditUpdateParam;
import com.rostami.onlinehomeservices.dto.out.CreateUpdateResult;
import com.rostami.onlinehomeservices.dto.out.single.CreditFindResult;
import com.rostami.onlinehomeservices.service.CreditService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.Set;
import java.util.stream.Collectors;

@RequestMapping("/credits")
@RequiredArgsConstructor
@RestController
public class CreditController {
    private final CreditService creditService;

    @PostMapping("/create")
    @PreAuthorize("hasAnyRole('ADMIN')")
    public ResponseEntity<ResponseResult<CreateUpdateResult>> save(@Validated @RequestBody CreditCreateParam param){
        CreateUpdateResult result = creditService.save(param);
        return ResponseEntity.ok(ResponseResult.<CreateUpdateResult>builder()
                .code(0).message("Credit Successfully Created.").data(result).build());
    }

    @PutMapping("/update")
    @PreAuthorize("hasAnyRole('ADMIN')")
    public ResponseEntity<ResponseResult<CreateUpdateResult>> update(@Validated @RequestBody CreditUpdateParam param){
        CreateUpdateResult result = creditService.update(param, param.getId());
        return ResponseEntity.ok(ResponseResult.<CreateUpdateResult>
                builder().code(0).message("Credit Successfully Updated.").data(result).build());
    }

    @DeleteMapping("/delete/{id}")
    @PreAuthorize("hasAnyRole('ADMIN')")
    public ResponseEntity<ResponseResult<CreateUpdateResult>> delete(@Validated @PathVariable Long id){
        creditService.delete(id);
        return ResponseEntity.ok(ResponseResult.<CreateUpdateResult>
                        builder().code(0).message("Credit Successfully Deleted.")
                .data(CreateUpdateResult.builder().success(true).id(id).build()).build());
    }

    @GetMapping("/load/{id}")
    @PreAuthorize("hasAnyRole('CUSTOMER', 'EXPERT', 'ADMIN')")
    public ResponseEntity<ResponseResult<CreditFindResult>> read(@Validated @PathVariable Long id){
        CreditFindResult result = (CreditFindResult) creditService.findById(id);
        return ResponseEntity.ok(ResponseResult.<CreditFindResult>builder()
                .code(0)
                .message("Credit Successfully Loaded.")
                .data(result)
                .build());
    }

    @GetMapping("/all")
    @PreAuthorize("hasAnyRole('ADMIN')")
    public ResponseEntity<ResponseResult<Set<CreditFindResult>>> readAll(@RequestParam Integer page){
        Pageable pageable = PageRequest.of(page, 5);
        Set<CreditFindResult> results = creditService.findAll(pageable)
                .stream().map(creditOutDto -> (CreditFindResult) creditOutDto).collect(Collectors.toSet());
        return ResponseEntity.ok(ResponseResult.<Set<CreditFindResult>>builder()
                .code(0)
                .message("All Credits Loaded Successfully.")
                .data(results)
                .build());
    }
}
