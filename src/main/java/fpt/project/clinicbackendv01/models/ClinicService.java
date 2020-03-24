package fpt.project.clinicbackendv01.models;

import lombok.*;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name = "clinic_services")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class ClinicService {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    private String description;
    private int price;
    private Date createdAt;
    private Date updatedAt;
    @ManyToOne
    @ToString.Exclude
    @EqualsAndHashCode.Exclude
    private Clinic clinic;
}
