package com.kirwa.safiriApp.Models;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor

public class TokenModel {
    private String token;
    private String forgotPasswordToken;

}
