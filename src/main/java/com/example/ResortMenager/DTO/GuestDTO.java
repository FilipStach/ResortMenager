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

    @NotBlank
    private String name;
    @NotBlank
    private String surrname;
    @Email
    @NotBlank
    private String email;
}
