package com.rostami.onlinehomeservices.controller;

import com.rostami.onlinehomeservices.dto.api.ResponseResult;
import com.rostami.onlinehomeservices.dto.in.create.OpinionCreateParam;
import com.rostami.onlinehomeservices.dto.in.update.OpinionUpdateParam;
import com.rostami.onlinehomeservices.dto.out.CreateUpdateResult;
import com.rostami.onlinehomeservices.dto.out.single.OpinionFindResult;
import com.rostami.onlinehomeservices.model.Expert;
import com.rostami.onlinehomeservices.service.OpinionService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.Set;
import java.util.stream.Collectors;

@RequestMapping("/opinions")
@RequiredArgsConstructor
@RestController
public class OpinionController {
    private final OpinionService opinionService;

    @PostMapping("/create")
    @PreAuthorize("hasAnyRole('CUSTOMER', 'ADMIN')")
    public ResponseEntity<ResponseResult<CreateUpdateResult>> create(@Validated @RequestBody OpinionCreateParam param){
        CreateUpdateResult result = opinionService.submitOpinion(param);
        return ResponseEntity.ok(ResponseResult.<CreateUpdateResult>builder()
                .code(0)
                .message("Opinion Successfully Created.")
                .data(result)
                .build());
    }

    @PutMapping("/update")
    @PreAuthorize("hasAnyRole('ADMIN', 'CUSTOMER')")
    public ResponseEntity<ResponseResult<CreateUpdateResult>> update(@Validated @RequestBody OpinionUpdateParam param){
        CreateUpdateResult result = opinionService.update(param, param.getId());
        return ResponseEntity.ok(ResponseResult.<CreateUpdateResult>
                builder().code(0).message("Opinion Successfully Updated.").data(result).build());
    }

    @DeleteMapping("/delete/{id}")
    @PreAuthorize("hasAnyRole('ADMIN')")
    public ResponseEntity<ResponseResult<CreateUpdateResult>> delete(@Validated @PathVariable Long id){
        opinionService.delete(id);
        return ResponseEntity.ok(ResponseResult.<CreateUpdateResult>
                        builder().code(0).message("Opinion Successfully Deleted.")
                .data(CreateUpdateResult.builder().success(true).id(id).build()).build());
    }

    @GetMapping("/load/{id}")
    @PreAuthorize("hasAnyRole('ADMIN')")
    public ResponseEntity<ResponseResult<OpinionFindResult>> read(@Validated @PathVariable Long id){
        OpinionFindResult result = (OpinionFindResult) opinionService.findById(id);
        return ResponseEntity.ok(ResponseResult.<OpinionFindResult>builder()
                .code(0)
                .message("Successfully Found Opinion.")
                .data(result).build());
    }

    @GetMapping("/all")
    @PreAuthorize("hasAnyRole('ADMIN', 'EXPERT', 'CUSTOMER')")
    public ResponseEntity<ResponseResult<Set<OpinionFindResult>>> readAll(@RequestParam Integer page, @RequestParam Long expertId){
        Expert expert = Expert.builder().id(expertId).build();
        Pageable pageable = PageRequest.of(page, 5);
        Set<OpinionFindResult> results = opinionService.findAll((root, query, cb) ->
                        cb.equal(root.get("expert"), expert), pageable)
                .stream().map(opinionOutDto -> (OpinionFindResult) opinionOutDto).collect(Collectors.toSet());
        return ResponseEntity.ok(ResponseResult.<Set<OpinionFindResult>>builder()
                .code(0)
                .message("Successfully Found All Opinions.")
                .data(results)
                .build());
    }
}
