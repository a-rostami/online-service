package com.rostami.onlineservice.controller.util;

import com.rostami.onlineservice.dto.api.ResponseResult;
import io.ipgeolocation.api.Geolocation;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import com.rostami.onlineservice.util.LocationDetector;

@RestController("/location")
@RequiredArgsConstructor
public class LocationController {

    private final LocationDetector locationDetector;

    @GetMapping("/")
    public ResponseEntity<ResponseResult<Geolocation>> getLocationInformation(@RequestParam String ip){
        Geolocation geolocation = locationDetector.getGeolocation(ip);
        return ResponseEntity.ok(ResponseResult.<Geolocation>builder()
                .code(0)
                .message(geolocation.getMessage())
                .data(geolocation)
                .build());
    }
}
