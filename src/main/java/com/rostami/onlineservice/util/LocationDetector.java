package com.rostami.onlineservice.util;

import io.ipgeolocation.api.Geolocation;
import io.ipgeolocation.api.GeolocationParams;
import io.ipgeolocation.api.IPGeolocationAPI;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class LocationDetector {
    private static final IPGeolocationAPI IP_GEOLOCATION_API = new IPGeolocationAPI("YOUR_API_KEY");

        public Geolocation getGeolocation(String ip){
            GeolocationParams params = new GeolocationParams();
            params.setIPAddress(ip);
            Geolocation geolocation = IP_GEOLOCATION_API.getGeolocation(params);
            log.info(String.format("geo location call :  status code : %s , message : %s "
                    , geolocation.getStatus(), geolocation.getMessage()));
            return geolocation;
        }
}
