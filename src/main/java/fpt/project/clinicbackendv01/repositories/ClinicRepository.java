package fpt.project.clinicbackendv01.repositories;

import fpt.project.clinicbackendv01.models.Clinic;
import fpt.project.clinicbackendv01.models.Patient;
import fpt.project.clinicbackendv01.models.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ClinicRepository extends JpaRepository<Clinic, Long> {
    Optional<Clinic> findByUser(User userId);
}
