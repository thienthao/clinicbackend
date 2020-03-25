package fpt.project.clinicbackendv01.services;

import fpt.project.clinicbackendv01.exception.ClinicException;
import fpt.project.clinicbackendv01.exception.UserNotFoundException;
import fpt.project.clinicbackendv01.models.Clinic;
import fpt.project.clinicbackendv01.models.Patient;
import fpt.project.clinicbackendv01.models.User;
import fpt.project.clinicbackendv01.repositories.ClinicRepository;
import fpt.project.clinicbackendv01.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ClinicService {
    @Autowired
    private ClinicRepository clinicRepository;

    @Autowired
    private UserRepository userRepository;

    public List<Clinic> all() {
        return clinicRepository.findAll();
    }

    public Clinic one(long userId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new UserNotFoundException("User not found with id: " + userId));
        Clinic clinic = clinicRepository.findByUser(user)
                .orElseThrow(() -> new ClinicException("Clinic profile not found, please create one"));
        return clinic;
    }

    private Clinic checkClinicExist(long userId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new UserNotFoundException("User not found with id: " + userId));
        Optional<Clinic> optional = clinicRepository.findByUser(user);
        Clinic clinic = null;
        if(optional.isPresent()) clinic = optional.get();
        return clinic;
    }

    public Clinic create(Clinic newClinic, String username) {
        try {
            User user = userRepository.findByUsername(username)
                    .orElseThrow(() -> new UserNotFoundException("User not found with id: " + username));
            if(checkClinicExist(user.getId()) != null)
                throw new ClinicException("Profile already existed, please update instead");
            newClinic.setUser(user);
            Clinic savedClinic = clinicRepository.save(newClinic);
            return savedClinic;
        } catch (Exception e) {
            throw new ClinicException("Cannot save clinic profile due to: " + e.getMessage());
        }
    }

    public Clinic update(Clinic updatedClinic, String username) {
        try {
            User user = userRepository.findByUsername(username)
                    .orElseThrow(() -> new UserNotFoundException("User not found with id: " + username));
            updatedClinic.setUser(user);
            Clinic savedClinic = clinicRepository.save(updatedClinic);
            return savedClinic;
        } catch (Exception e) {
            throw new ClinicException("Cannot save clinic profile due to: " + e.getMessage());
        }
    }

}
