package com.competa.competademo.dto;

import com.competa.competademo.entity.User;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.validator.constraints.Length;

import java.util.HashSet;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class CreateUserDto extends UserDto {

    @NotBlank(message = "Password should not be empty")
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    @Length(min = 6, max = 122)
    private String password;

    @Override
    public User toEntity() {
        return User.builder()
                .nickName(this.nickName)
                .password(this.password)
                .email(this.email)
                .id(this.getId())
                .roles(new HashSet<>())
                .build();
    }

    public static CreateUserDto to(NewUserDto newUserDto) {
        CreateUserDto createUserDto = new CreateUserDto();
        createUserDto.email = newUserDto.email();
        createUserDto.password = newUserDto.password();
        createUserDto.nickName = newUserDto.nickName();
        return createUserDto;
    }
}
