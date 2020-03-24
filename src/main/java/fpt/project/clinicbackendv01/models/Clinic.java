package fpt.project.clinicbackendv01.models;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "clinics")
@NoArgsConstructor
@AllArgsConstructor
public class Clinic {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @NotBlank(message = "Name is required")
    private String name;
    @NotBlank(message = "Address is required")
    private String address;
    private String email;
    @NotBlank(message = "Phone is required")
    private String phone;
    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(unique = true)
    @JsonIgnore
    private User user;
    @OneToMany(mappedBy = "clinic")
    @ToString.Exclude
    @EqualsAndHashCode.Exclude
    @JsonIgnore
    private List<ClinicService> services = new ArrayList<>();

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public List<ClinicService> getServices() {
        return services;
    }

    public void addServices(ClinicService service) {
        this.services.add(service);
    }

    public void removeServices(ClinicService service) {
        this.services.remove(service);
    }
}
