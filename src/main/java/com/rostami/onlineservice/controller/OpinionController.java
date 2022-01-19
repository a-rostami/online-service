package com.rostami.onlineservice.controller;

import com.rostami.onlineservice.dto.api.ResponseResult;
import com.rostami.onlineservice.dto.in.create.OpinionCreateParam;
import com.rostami.onlineservice.dto.out.CreateUpdateResult;
import com.rostami.onlineservice.dto.out.single.OpinionFindResult;
import com.rostami.onlineservice.service.OpinionService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RequestMapping("/opinions")
@RequiredArgsConstructor
@RestController
public class OpinionController {
    private final OpinionService opinionService;

    @PostMapping("/create")
    public ResponseEntity<ResponseResult<CreateUpdateResult>> create(@Validated @RequestBody OpinionCreateParam param){
        CreateUpdateResult result = opinionService.saveOrUpdate(param);
        return ResponseEntity.ok(ResponseResult.<CreateUpdateResult>builder()
                .code(0)
                .message("Opinion Successfully Created.")
                .data(result)
                .build());
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
    public ResponseEntity<ResponseResult<List<OpinionFindResult>>> readAll(){
        List<OpinionFindResult> results = opinionService.list().stream()
                .map((baseOutDto -> (OpinionFindResult) baseOutDto))
                .collect(Collectors.toList());
        return ResponseEntity.ok(ResponseResult.<List<OpinionFindResult>>builder()
                .code(0)
                .message("Successfully Found All Opinions.")
                .data(results)
                .build());
    }
}
