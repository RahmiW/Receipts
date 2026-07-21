package org.example.megahottakes.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class LoginResponseDTO {
    private String token;
    private UserDTO user;

    public LoginResponseDTO(String token, UserDTO user) {
        this.token = token;
        this.user = user;
    }
}
