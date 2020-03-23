package fpt.project.clinicbackendv01.models;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import java.util.Date;

@Entity
@Table(name = "users")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    @NotBlank
    @Size(max = 20)
    private String username;
    @NotBlank
    @Size(max = 200)
    private String password;
    @Transient
    private String confirmPassword;
    @ManyToOne(fetch = FetchType.LAZY)
    @JsonIgnore
    private Role role;

    private Date createdAt;
    private Date updatedAt;

    public User(@NotBlank @Size(max = 20) String username, @NotBlank @Size(max = 200) String password) {
        this.username = username;
        this.password = password;
    }
}
