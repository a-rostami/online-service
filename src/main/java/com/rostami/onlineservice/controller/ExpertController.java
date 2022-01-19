package com.rostami.onlineservice.controller;
import com.rostami.onlineservice.dto.api.ResponseResult;
import com.rostami.onlineservice.dto.in.create.ExpertCreateParam;
import com.rostami.onlineservice.dto.out.CreateUpdateResult;
import com.rostami.onlineservice.dto.out.single.ExpertFindResult;
import com.rostami.onlineservice.service.ExpertService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/experts")
@RequiredArgsConstructor
public class ExpertController {
    private final ExpertService expertService;

    @PostMapping("/create")
    public ResponseEntity<ResponseResult<CreateUpdateResult>> create(@RequestBody ExpertCreateParam param){
        CreateUpdateResult result = expertService.saveOrUpdate(param);
        return ResponseEntity.ok(ResponseResult.<CreateUpdateResult>builder()
                .code(0)
                .message("Expert Successfully Created.")
                .data(result)
                .build());
    }

    @GetMapping("/find/{id}")
    public ResponseEntity<ResponseResult<ExpertFindResult>> read(@PathVariable Long id){
        ExpertFindResult result = (ExpertFindResult) expertService.get(id);
        return ResponseEntity.ok(ResponseResult.<ExpertFindResult>builder()
                .code(0)
                .message("Successfully Found Expert.")
                .data(result).build());
    }

    @GetMapping("/find/all")
    public ResponseEntity<ResponseResult<List<ExpertFindResult>>> readAll(){
        List<ExpertFindResult> results = expertService.list().stream()
                .map((expertBaseOutDto -> (ExpertFindResult) expertBaseOutDto))
                .collect(Collectors.toList());
        return ResponseEntity.ok(ResponseResult.<List<ExpertFindResult>>builder()
                .code(0)
                .message("Successfully Found All Experts.")
                .data(results)
                .build());
    }
}