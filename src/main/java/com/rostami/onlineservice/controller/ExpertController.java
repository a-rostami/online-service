package com.rostami.onlineservice.controller;

import com.rostami.onlineservice.dto.api.ResponseResult;
import com.rostami.onlineservice.dto.in.create.ExpertCreateParam;
import com.rostami.onlineservice.dto.out.CreateUpdateResult;
import com.rostami.onlineservice.service.ExpertService;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;

@RestController
public class ExpertController {
    final ExpertService service;

    public ExpertController(ExpertService service) {
        this.service = service;
    }

    @PostMapping(value = "/experts/save", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<ResponseResult<CreateUpdateResult>> create(ExpertCreateParam expertParam) throws IOException {
        return null;
    }
}