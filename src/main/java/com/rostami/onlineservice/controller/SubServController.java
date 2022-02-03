package com.rostami.onlineservice.controller;

import com.rostami.onlineservice.dto.api.ResponseResult;
import com.rostami.onlineservice.dto.in.create.SubServCreateParam;
import com.rostami.onlineservice.dto.in.update.SubServUpdateParam;
import com.rostami.onlineservice.dto.out.CreateUpdateResult;
import com.rostami.onlineservice.dto.out.single.SubServFindResult;
import com.rostami.onlineservice.service.SubServService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequestMapping("/subServs")
@RequiredArgsConstructor
@RestController
public class SubServController {
    private final SubServService subServService;

    @PostMapping("/create")
    @PreAuthorize("hasAnyRole('ADMIN')")
    public ResponseEntity<ResponseResult<CreateUpdateResult>> create(@Validated @RequestBody SubServCreateParam param){
        CreateUpdateResult result = subServService.saveOrUpdate(param);
        return ResponseEntity.ok(ResponseResult.<CreateUpdateResult>builder()
                .code(0)
                .message("SubServ Successfully Created.")
                .data(result)
                .build());
    }

    @PutMapping("/update")
    @PreAuthorize("hasAnyRole('ADMIN')")
    public ResponseEntity<ResponseResult<CreateUpdateResult>> update(@Validated @RequestBody SubServUpdateParam param){
        CreateUpdateResult result = subServService.saveOrUpdate(param);
        return ResponseEntity.ok(ResponseResult.<CreateUpdateResult>
                builder().code(0).message("SubServ Successfully Updated.").data(result).build());
    }

    @DeleteMapping("/delete/{id}")
    @PreAuthorize("hasAnyRole('ADMIN')")
    public ResponseEntity<ResponseResult<CreateUpdateResult>> delete(@Validated @PathVariable Long id){
        subServService.delete(id);
        return ResponseEntity.ok(ResponseResult.<CreateUpdateResult>
                        builder().code(0).message("SubServ Successfully Deleted.")
                .data(CreateUpdateResult.builder().success(true).id(id).build()).build());
    }

    @GetMapping("/load/{id}")
    @PreAuthorize("hasAnyRole('ADMIN', 'EXPERT', 'CUSTOMER')")
    public ResponseEntity<ResponseResult<SubServFindResult>> read(@Validated @PathVariable Long id){
        SubServFindResult result = (SubServFindResult) subServService.get(id);
        return ResponseEntity.ok(ResponseResult.<SubServFindResult>builder()
                .code(0)
                .message("Successfully Found SubServ.")
                .data(result).build());
    }

    @GetMapping("/all")
    @PreAuthorize("hasAnyRole('ADMIN', 'EXPERT', 'CUSTOMER')")
    public ResponseEntity<ResponseResult<List<SubServFindResult>>> readAll(@RequestParam Integer page){
        Pageable pageable = PageRequest.of(page, 5);
        List<SubServFindResult> results = subServService.findAll(pageable)
                .stream().map(subServOutDto -> (SubServFindResult) subServOutDto).toList();
        return ResponseEntity.ok(ResponseResult.<List<SubServFindResult>>builder()
                .code(0)
                .message("Successfully Found All SubServs.")
                .data(results)
                .build());
    }

    @GetMapping("/subServsOfMainServ/{mainServId}")
    public ResponseEntity<ResponseResult<List<SubServFindResult>>> findSubServsOfMainServ(@PathVariable Long mainServId){
        List<SubServFindResult> results = subServService.findSubServsOfMainServ(mainServId);
        return ResponseEntity.ok(ResponseResult.<List<SubServFindResult>>builder()
                .code(0)
                .message("Successfully Load All SubServices Of Given MainService")
                .data(results)
                .build());
    }
}
