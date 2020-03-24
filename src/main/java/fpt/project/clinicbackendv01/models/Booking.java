package fpt.project.clinicbackendv01.models;

import lombok.*;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name = "bookings")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Booking {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
//    @Column(unique = true, updatable = false)
    private String code;
    private String description;
    @Enumerated(EnumType.STRING)
    @Column(length = 20)
    private Status status;
    private Date bookingTime;
    private String date;
    private String time;
    private Date createdAt;
    private Date updatedAt;
    @ManyToOne
    @ToString.Exclude
    @EqualsAndHashCode.Exclude
    private Patient patient;
    @ManyToOne
    @ToString.Exclude
    @EqualsAndHashCode.Exclude
    private ClinicService service;
}
