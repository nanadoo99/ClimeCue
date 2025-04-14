package nki.ClimCue.model.member;

import lombok.*;
import jakarta.validation.constraints.*;


@Data
public class RegisterUserDto {
    @NotBlank(message = "사용자 이름은 필수 입력 항목입니다.")
    @Size(min = 3, max = 12, message = "사용자 이름은 3자 이상, 12자 이하로 입력해주세요.")
    private String username;

    @NotBlank(message = "비밀번호는 필수 입력 항목입니다.")
    @Size(min = 4, max=20, message = "비밀번호는 4자 이상, 20자 이하로 입력해주세요.")
    @Pattern(regexp = "^[^\\s]+$", message = "비밀번호에 공백을 포함할 수 없습니다.")
    private String password;

    @NotBlank(message = "비밀번호 확인은 필수 입력 항목입니다.")
    private String confirmPassword;

    public void setUsername(String username) {
        this.username = username != null ? username.strip().replaceAll("\\s+", "") : null;
    }

    public boolean isPasswordMatching() {
        return this.password != null && this.password.equals(this.confirmPassword);
    }
}
