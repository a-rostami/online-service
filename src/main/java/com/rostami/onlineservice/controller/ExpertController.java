package com.rostami.onlineservice.controller;

import com.rostami.onlineservice.dto.ResponseResult;
import com.rostami.onlineservice.dto.in.create.ExpertCreateParam;
import com.rostami.onlineservice.dto.out.CreateResult;
import com.rostami.onlineservice.dto.out.UpdateResult;
import com.rostami.onlineservice.entity.Expert;
import com.rostami.onlineservice.entity.enums.Role;
import com.rostami.onlineservice.entity.enums.UserStatus;
import com.rostami.onlineservice.service.ExpertService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.Valid;
import java.io.IOException;

@RestController
public class ExpertController {
    final ExpertService service;

    public ExpertController(ExpertService service) {
        this.service = service;
    }

    @PostMapping(value = "/experts/save", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<ResponseResult<CreateResult>> create(ExpertCreateParam expertParam) throws IOException {
        return null;
    }
}