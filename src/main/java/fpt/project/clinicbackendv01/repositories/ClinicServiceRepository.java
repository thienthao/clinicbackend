package fpt.project.clinicbackendv01.repositories;

import fpt.project.clinicbackendv01.models.Clinic;
import fpt.project.clinicbackendv01.models.ClinicService;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ClinicServiceRepository extends JpaRepository<ClinicService, Long> {
    List<ClinicService> findAllByClinic(Clinic clinic);
}
