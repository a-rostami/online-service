package com.rostami.onlineservice.controller;

import com.rostami.onlineservice.dto.api.ResponseResult;
import com.rostami.onlineservice.dto.in.create.MainServCreateParam;
import com.rostami.onlineservice.dto.in.update.MainServUpdateParam;
import com.rostami.onlineservice.dto.out.CreateUpdateResult;
import com.rostami.onlineservice.dto.out.single.MainServFindResult;
import com.rostami.onlineservice.model.MainServ;
import com.rostami.onlineservice.service.MainServService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@RequestMapping("/mainServs")
@RestController
public class MainServController {
    private final MainServService mainServService;

    @PostMapping("/create")
    @PreAuthorize("hasAnyRole('ADMIN')")
    public ResponseEntity<ResponseResult<CreateUpdateResult>> create(@Validated @RequestBody MainServCreateParam param){
        CreateUpdateResult result = mainServService.save(param);
        return ResponseEntity.ok(ResponseResult.<CreateUpdateResult>builder()
                .code(0)
                .message("MainServ Successfully Created.")
                .data(result)
                .build());
    }

    @PutMapping("/update")
    @PreAuthorize("hasAnyRole('ADMIN')")
    public ResponseEntity<ResponseResult<CreateUpdateResult>> update(@Validated @RequestBody MainServUpdateParam param){
        MainServ fetchedEntity = mainServService.getForUpdate(param.getId());
        MainServ updatedEntity = param.convertToDomain(fetchedEntity);
        CreateUpdateResult result = mainServService.update(updatedEntity);
        return ResponseEntity.ok(ResponseResult.<CreateUpdateResult>
                builder().code(0).message("MainServ Successfully Updated.").data(result).build());
    }

    @DeleteMapping("/delete/{id}")
    @PreAuthorize("hasAnyRole('ADMIN')")
    public ResponseEntity<ResponseResult<CreateUpdateResult>> delete(@Validated @PathVariable Long id){
        mainServService.delete(id);
        return ResponseEntity.ok(ResponseResult.<CreateUpdateResult>
                        builder().code(0).message("MainServ Successfully Deleted.")
                .data(CreateUpdateResult.builder().success(true).id(id).build()).build());
    }

    @GetMapping("/load/{id}")
    @PreAuthorize("hasAnyRole('ADMIN', 'EXPERT', 'CUSTOMER')")
    public ResponseEntity<ResponseResult<MainServFindResult>> read(@Validated @PathVariable Long id){
        MainServFindResult result = (MainServFindResult) mainServService.get(id);
        return ResponseEntity.ok(ResponseResult.<MainServFindResult>builder()
                .code(0)
                .message("Successfully Found MainServ.")
                .data(result).build());
    }

    @GetMapping("/all")
    @PreAuthorize("hasAnyRole('ADMIN', 'EXPERT', 'CUSTOMER')")
    public ResponseEntity<ResponseResult<Set<MainServFindResult>>> readAll(@RequestParam Integer page){
        Pageable pageable = PageRequest.of(page, 5);
        Set<MainServFindResult> results = mainServService.findAll(pageable)
                .stream().map(mainServOutDto -> (MainServFindResult) mainServOutDto).collect(Collectors.toSet());
        return ResponseEntity.ok(ResponseResult.<Set<MainServFindResult>>builder()
                .code(0)
                .message("Successfully Found All Main Services.")
                .data(results)
                .build());
    }
}
