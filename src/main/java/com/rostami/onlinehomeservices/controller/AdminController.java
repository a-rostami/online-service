package com.rostami.onlinehomeservices.controller;

import com.rostami.onlinehomeservices.dto.api.ResponseResult;
import com.rostami.onlinehomeservices.dto.api.auth.in.JwtRequestParam;
import com.rostami.onlinehomeservices.dto.in.create.AdminCreateParam;
import com.rostami.onlinehomeservices.dto.in.update.AdminUpdateParam;
import com.rostami.onlinehomeservices.dto.in.update.api.PasswordUpdateParam;
import com.rostami.onlinehomeservices.dto.out.CreateUpdateResult;
import com.rostami.onlinehomeservices.dto.out.single.AdminFindResult;
import com.rostami.onlinehomeservices.service.AdminService;
import com.rostami.onlinehomeservices.util.JwtTokenUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.Set;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/admins")
@RequiredArgsConstructor
public class AdminController {

    private final AdminService adminService;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;
    private final JwtTokenUtil jwtTokenUtil;

    @PostMapping("/sign-up")
    @PreAuthorize("hasAnyRole('ADMIN')")
    public ResponseEntity<ResponseResult<CreateUpdateResult>> signup(@Validated @RequestBody AdminCreateParam param) {
        param.setPassword(bCryptPasswordEncoder.encode(param.getPassword()));
        String token = generateToken(param.getEmail(), param.getPassword());

        CreateUpdateResult result = adminService.save(param);
        return ResponseEntity.ok(ResponseResult.<CreateUpdateResult>builder()
                .code(0)
                .message("Admin Successfully Registered. Token : " + token)
                .data(result)
                .build());
    }

    private String generateToken(String email, String password){
        return jwtTokenUtil.generateAccessToken(
                JwtRequestParam.builder()
                        .email(email)
                        .password(password)
                        .build());
    }

    @PutMapping("/update")
    @PreAuthorize("hasAnyRole('ADMIN')")
    public ResponseEntity<ResponseResult<CreateUpdateResult>> update(@Validated @RequestBody AdminUpdateParam param){
        CreateUpdateResult result = adminService.update(param, param.getId());
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
        AdminFindResult result = (AdminFindResult) adminService.findById(id);
        return ResponseEntity.ok(ResponseResult.<AdminFindResult>builder()
                .code(0)
                .message("Admin Successfully Loaded.")
                .data(result)
                .build());
    }

    @GetMapping("/all")
    @PreAuthorize("hasAnyRole('ADMIN')")
    public ResponseEntity<ResponseResult<Set<AdminFindResult>>> readAll(@RequestParam Integer page){
        Pageable pageable = PageRequest.of(page, 5);
        Set<AdminFindResult> results = adminService.findAll(pageable)
                .stream().map(customerOutDto -> (AdminFindResult) customerOutDto).collect(Collectors.toSet());
        return ResponseEntity.ok(ResponseResult.<Set<AdminFindResult>>builder()
                .code(0)
                .message("All Admins Loaded Successfully.")
                .data(results)
                .build());
    }

    @PutMapping("/changePassword")
    @PreAuthorize("hasAnyRole('ADMIN')")
    public ResponseEntity<ResponseResult<CreateUpdateResult>> changePassword(@RequestBody @Valid PasswordUpdateParam param){
        String newPassword = param.getNewPassword();

        param.setNewPassword(bCryptPasswordEncoder.encode(newPassword));

        CreateUpdateResult result = adminService.changePassword(param);
        return ResponseEntity.ok(ResponseResult.<CreateUpdateResult>
                builder().code(0).message("Admin Password Successfully Updated.").data(result).build());
    }
}
