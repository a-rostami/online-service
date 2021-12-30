package com.rostami.onlineservice.service;

import com.rostami.onlineservice.config.AppConfig;
import com.rostami.onlineservice.entity.Expert;
import com.rostami.onlineservice.entity.enums.UserStatus;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringJUnitConfig;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
@ActiveProfiles("test")
@SpringJUnitConfig({AppConfig.class})
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
class ExpertServiceTest {
    @Autowired
    ExpertService expertService;

    @Test
    void avatar_save_and_upload_isOk() throws IOException {
        File file = new File("C:\\Users\\arash\\Desktop\\sample.jpg");
        byte[] byteFile = new byte[(int) file.length()];
        FileInputStream fileInputStream = new FileInputStream(file);
        int read = fileInputStream.read(byteFile);
        fileInputStream.close();

        Expert expert = Expert.
                builder()
                .firstname("arash")
                .lastname("rostami")
                .password("12345678")
                .username("mrrostami2")
                .userStatus(UserStatus.NEW)
                .avatar(byteFile)
                .build();
        expertService.save(expert);

        Expert byEmail = expertService.findByUsername("mrrostami2");
        byte[] avatar = byEmail.getAvatar();
        FileOutputStream fos = new FileOutputStream("C:\\Users\\arash\\Desktop\\sample2.jpg");
        fos.write(avatar);
        fos.close();
        assertArrayEquals(byteFile, avatar);
    }
}