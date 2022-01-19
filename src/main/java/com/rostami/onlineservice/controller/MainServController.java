package com.rostami.onlineservice.controller;

import com.rostami.onlineservice.dto.api.ResponseResult;
import com.rostami.onlineservice.dto.in.create.MainServCreateParam;
import com.rostami.onlineservice.dto.out.CreateUpdateResult;
import com.rostami.onlineservice.dto.out.single.MainServFindResult;
import com.rostami.onlineservice.service.MainServService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@RequestMapping("/mainServs")
@RestController
public class MainServController {
    private final MainServService mainServService;

    @PostMapping("/create")
    public ResponseEntity<ResponseResult<CreateUpdateResult>> create(@Validated @RequestBody MainServCreateParam param){
        CreateUpdateResult result = mainServService.saveOrUpdate(param);
        return ResponseEntity.ok(ResponseResult.<CreateUpdateResult>builder()
                .code(0)
                .message("MainServ Successfully Created.")
                .data(result)
                .build());
    }

    @GetMapping("/load/{id}")
    public ResponseEntity<ResponseResult<MainServFindResult>> read(@Validated @PathVariable Long id){
        MainServFindResult result = (MainServFindResult) mainServService.get(id);
        return ResponseEntity.ok(ResponseResult.<MainServFindResult>builder()
                .code(0)
                .message("Successfully Found MainServ.")
                .data(result).build());
    }

    @GetMapping("/all")
    public ResponseEntity<ResponseResult<List<MainServFindResult>>> readAll(){
        List<MainServFindResult> results = mainServService.list().stream()
                .map((baseOutDto -> (MainServFindResult) baseOutDto))
                .collect(Collectors.toList());
        return ResponseEntity.ok(ResponseResult.<List<MainServFindResult>>builder()
                .code(0)
                .message("Successfully Found All Experts.")
                .data(results)
                .build());
    }
}
