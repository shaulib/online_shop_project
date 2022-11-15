package com.example.vmWare_project.model;

import com.example.vmWare_project.model.enums.ContactType;
import com.sun.istack.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class RegisterRequest {
    private String firstName;
    private String LastName;
    private String age;
    @NotNull
    private String phone;
    @NotNull
    private String email;
    @NotNull
    private String password;
    private ContactType contactType;
    private String role;
}
