package com.rostami.onlineservice.controller;

import com.rostami.onlineservice.dto.api.ResponseResult;
import com.rostami.onlineservice.dto.in.create.AdminCreateParam;
import com.rostami.onlineservice.dto.in.update.AdminUpdateParam;
import com.rostami.onlineservice.dto.out.CreateUpdateResult;
import com.rostami.onlineservice.dto.out.single.AdminFindResult;
import com.rostami.onlineservice.service.AdminService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/admins")
@RequiredArgsConstructor
public class AdminController {

    private final AdminService adminService;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;

    @PostMapping("/create")
    @PreAuthorize("hasAnyRole('ADMIN')")
    public ResponseEntity<ResponseResult<CreateUpdateResult>> save(@Validated @RequestBody AdminCreateParam param){
        param.setPassword(bCryptPasswordEncoder.encode(param.getPassword()));
        CreateUpdateResult result = adminService.saveOrUpdate(param);
        return ResponseEntity.ok(ResponseResult.<CreateUpdateResult>builder()
                .code(0).message("Admin Successfully Created.").data(result).build());
    }

    @PutMapping("/update")
    @PreAuthorize("hasAnyRole('ADMIN')")
    public ResponseEntity<ResponseResult<CreateUpdateResult>> update(@Validated @RequestBody AdminUpdateParam param){
        param.setPassword(bCryptPasswordEncoder.encode(param.getPassword()));
        CreateUpdateResult result = adminService.saveOrUpdate(param);
        return ResponseEntity.ok(ResponseResult.<CreateUpdateResult>
                builder().code(0).message("Admin Successfully Updated.").data(result).build());
    }

    @DeleteMapping("/delete/{id}")
    @PreAuthorize("hasAnyRole('ADMIN')")
    public ResponseEntity<ResponseResult<CreateUpdateResult>> delete(@Validated @PathVariable Long id){
        adminService.delete(id);
        return ResponseEntity.ok(ResponseResult.<CreateUpdateResult>
                        builder().code(0).message("Admin Successfully Deleted.")
                .data(CreateUpdateResult.builder().success(true).id(id).build()).build());
    }

    @GetMapping("/load/{id}")
    @PreAuthorize("hasAnyRole('ADMIN')")
    public ResponseEntity<ResponseResult<AdminFindResult>> read(@Validated @PathVariable Long id){
        AdminFindResult result = (AdminFindResult) adminService.get(id);
        return ResponseEntity.ok(ResponseResult.<AdminFindResult>builder()
                .code(0)
                .message("Admin Successfully Loaded.")
                .data(result)
                .build());
    }

    @GetMapping("/all")
    @PreAuthorize("hasAnyRole('ADMIN')")
    public ResponseEntity<ResponseResult<List<AdminFindResult>>> readAll(@RequestParam Integer page){
        Pageable pageable = PageRequest.of(page, 5);
        List<AdminFindResult> results = adminService.findAll(pageable)
                .stream().map(customerOutDto -> (AdminFindResult) customerOutDto).toList();
        return ResponseEntity.ok(ResponseResult.<List<AdminFindResult>>builder()
                .code(0)
                .message("All Admins Loaded Successfully.")
                .data(results)
                .build());
    }
}
