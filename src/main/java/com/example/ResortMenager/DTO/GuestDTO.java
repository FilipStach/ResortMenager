package com.example.ResortMenager.DTO;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class GuestDTO {

    @NotBlank(message = "Guest name cant be blank")
    private String name;
    @NotBlank(message = "Guest surrname cant be blank")
    private String surrname;
    @Email(message = "Wrong email format in guest email")
    @NotBlank(message = "Guest email cant be blank")
    private String email;
    @NotBlank(message = "Guest password cant be blank")
    private String password;
}
