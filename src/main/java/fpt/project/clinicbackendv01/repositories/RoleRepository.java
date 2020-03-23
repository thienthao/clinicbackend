package fpt.project.clinicbackendv01.repositories;

import fpt.project.clinicbackendv01.models.ERole;
import fpt.project.clinicbackendv01.models.Role;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface RoleRepository extends JpaRepository<Role, Long> {
    Optional<Role> findByName(ERole name);
}
