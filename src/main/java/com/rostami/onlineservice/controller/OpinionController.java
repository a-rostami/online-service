package com.rostami.onlineservice.controller;

import com.rostami.onlineservice.dto.api.ResponseResult;
import com.rostami.onlineservice.dto.in.create.OpinionCreateParam;
import com.rostami.onlineservice.dto.in.update.OpinionUpdateParam;
import com.rostami.onlineservice.dto.out.CreateUpdateResult;
import com.rostami.onlineservice.dto.out.single.OpinionFindResult;
import com.rostami.onlineservice.service.OpinionService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequestMapping("/opinions")
@RequiredArgsConstructor
@RestController
public class OpinionController {
    private final OpinionService opinionService;

    @PostMapping("/create")
    public ResponseEntity<ResponseResult<CreateUpdateResult>> create(@Validated @RequestBody OpinionCreateParam param){
        CreateUpdateResult result = opinionService.submitOpinion(param);
        return ResponseEntity.ok(ResponseResult.<CreateUpdateResult>builder()
                .code(0)
                .message("Opinion Successfully Created.")
                .data(result)
                .build());
    }

    @PutMapping("/update")
    public ResponseEntity<ResponseResult<CreateUpdateResult>> update(@Validated @RequestBody OpinionUpdateParam param){
        CreateUpdateResult result = opinionService.saveOrUpdate(param);
        return ResponseEntity.ok(ResponseResult.<CreateUpdateResult>
                builder().code(0).message("Opinion Successfully Updated.").data(result).build());
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<ResponseResult<CreateUpdateResult>> delete(@Validated @PathVariable Long id){
        opinionService.delete(id);
        return ResponseEntity.ok(ResponseResult.<CreateUpdateResult>
                        builder().code(0).message("Opinion Successfully Deleted.")
                .data(CreateUpdateResult.builder().success(true).id(id).build()).build());
    }

    @GetMapping("/load/{id}")
    public ResponseEntity<ResponseResult<OpinionFindResult>> read(@Validated @PathVariable Long id){
        OpinionFindResult result = (OpinionFindResult) opinionService.get(id);
        return ResponseEntity.ok(ResponseResult.<OpinionFindResult>builder()
                .code(0)
                .message("Successfully Found Opinion.")
                .data(result).build());
    }

    @GetMapping("/all")
    public ResponseEntity<ResponseResult<List<OpinionFindResult>>> readAll(@RequestParam Integer page){
        Pageable pageable = PageRequest.of(page, 5);
        List<OpinionFindResult> results = opinionService.findAll(pageable)
                .stream().map(opinionOutDto -> (OpinionFindResult) opinionOutDto).toList();
        return ResponseEntity.ok(ResponseResult.<List<OpinionFindResult>>builder()
                .code(0)
                .message("Successfully Found All Opinions.")
                .data(results)
                .build());
    }
}
