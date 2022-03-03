package com.rostami.onlinehomeservices.controller.registration;

import com.rostami.onlinehomeservices.dto.api.ResponseResult;
import com.rostami.onlinehomeservices.service.registration.RegistrationService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/registration")
public class RegistrationController {
    private final RegistrationService registrationService;

    @GetMapping("/confirm")
    public ResponseEntity<ResponseResult<String>> confirmEmail(@RequestParam String token, @RequestParam String role){
        String result = registrationService.confirmToken(token, role);
        return ResponseEntity.ok(ResponseResult.<String>builder()
                .message("Successfully confirmed")
                .data(result)
                .code(0)
                .build());
    }
}
