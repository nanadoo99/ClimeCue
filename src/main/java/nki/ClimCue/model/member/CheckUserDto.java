package nki.ClimCue.model.member;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class CheckUserDto {
    private String username;
    private String password;
    private String confirmPassword;

    public CheckUserDto(RegisterUserDto registerUserDto) {
        this.username = registerUserDto.getUsername();
        this.password = registerUserDto.getPassword();
        this.confirmPassword = registerUserDto.getConfirmPassword();
    }
}
