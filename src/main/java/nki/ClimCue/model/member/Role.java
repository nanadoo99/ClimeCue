package nki.ClimCue.model.member;

import lombok.Getter;
import org.aspectj.lang.annotation.Aspect;

public enum Role {
    USER("ROLE_USER"),
    ADMIN("ROLE_ADMIN");

    private final String role;

    Role(String role) {
        this.role = role;
    }

    public String getRole() {
        return role;
    }

}
