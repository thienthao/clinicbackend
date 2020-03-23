package fpt.project.clinicbackendv01.payload.request;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

public class SignUpRequest {
    @NotBlank
    @Size(min = 3, max = 20)
    private String username;

    private String role;

    @NotBlank
    @Size(min = 6, max = 200)
    private String password;

    public SignUpRequest(@NotBlank @Size(min = 3, max = 20) String username, @NotBlank @Size(min = 6, max = 200) String password, String role) {
        this.username = username;
        this.role = role;
        this.password = password;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
