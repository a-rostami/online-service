package com.rostami.onlinehomeservices.dto.api.auth.in;

import lombok.*;

import javax.validation.constraints.NotNull;
import java.io.Serial;
import java.io.Serializable;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
public class JwtRequestParam implements Serializable {
    @Serial
    private static final long serialVersionUID = 5926468583005150707L;

    @NotNull
    private String email;
    @NotNull
    private String password;
}
