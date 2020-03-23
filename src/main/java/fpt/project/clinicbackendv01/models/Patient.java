package fpt.project.clinicbackendv01.models;


import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import java.util.Date;

@Entity
@Table(name = "patients")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Patient {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    @NotBlank(message = "Name is required")
    private String name;
    private String email;
    @NotBlank(message = "Phone is required")
    private String phone;
    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(unique = true)
    @JsonIgnore
    private User user;

    public Patient(String name) {
        this.name = name;
    }
}
