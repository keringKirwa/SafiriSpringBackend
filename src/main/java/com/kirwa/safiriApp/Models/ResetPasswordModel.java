package com.kirwa.safiriApp.Models;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor

public class ResetPasswordModel {

    private String emailAddress;
    private String oldPassword;
    private String newPassword;

}
