package com.rostami.onlineservice.controller;
import com.rostami.onlineservice.dto.api.ResponseResult;
import com.rostami.onlineservice.dto.in.create.ExpertCreateParam;
import com.rostami.onlineservice.dto.in.update.AdUpdateParam;
import com.rostami.onlineservice.dto.in.update.ExpertUpdateParam;
import com.rostami.onlineservice.dto.out.CreateUpdateResult;
import com.rostami.onlineservice.dto.out.single.ExpertFindResult;
import com.rostami.onlineservice.service.ExpertService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/experts")
@RequiredArgsConstructor
public class ExpertController {
    private final ExpertService expertService;

    @PostMapping("/create")
    public ResponseEntity<ResponseResult<CreateUpdateResult>> create(@Validated @RequestBody ExpertCreateParam param){
        CreateUpdateResult result = expertService.saveOrUpdate(param);
        return ResponseEntity.ok(ResponseResult.<CreateUpdateResult>builder()
                .code(0)
                .message("Expert Successfully Created.")
                .data(result)
                .build());
    }

    @PutMapping("/update")
    public ResponseEntity<ResponseResult<CreateUpdateResult>> update(@Validated @RequestBody ExpertUpdateParam param){
        CreateUpdateResult result = expertService.saveOrUpdate(param);
        return ResponseEntity.ok(ResponseResult.<CreateUpdateResult>
                builder().code(0).message("Expert Successfully Updated.").data(result).build());
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<ResponseResult<CreateUpdateResult>> delete(@Validated @PathVariable Long id){
        expertService.delete(id);
        return ResponseEntity.ok(ResponseResult.<CreateUpdateResult>
                        builder().code(0).message("Expert Successfully Deleted.")
                .data(CreateUpdateResult.builder().success(true).id(id).build()).build());
    }

    @GetMapping("/load/{id}")
    public ResponseEntity<ResponseResult<ExpertFindResult>> read(@Validated @PathVariable Long id){
        ExpertFindResult result = (ExpertFindResult) expertService.get(id);
        return ResponseEntity.ok(ResponseResult.<ExpertFindResult>builder()
                .code(0)
                .message("Successfully Found Expert.")
                .data(result).build());
    }

    @GetMapping("/all")
    public ResponseEntity<ResponseResult<List<ExpertFindResult>>> readAll(){
        List<ExpertFindResult> results = expertService.list().stream()
                .map((baseOutDto -> (ExpertFindResult) baseOutDto))
                .collect(Collectors.toList());
        return ResponseEntity.ok(ResponseResult.<List<ExpertFindResult>>builder()
                .code(0)
                .message("Successfully Found All Experts.")
                .data(results)
                .build());
    }
}