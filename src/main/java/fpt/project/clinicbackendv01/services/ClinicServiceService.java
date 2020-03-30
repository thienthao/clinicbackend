package fpt.project.clinicbackendv01.services;

import fpt.project.clinicbackendv01.exception.ClinicException;
import fpt.project.clinicbackendv01.exception.ClinicServiceException;
import fpt.project.clinicbackendv01.exception.UserNotFoundException;
import fpt.project.clinicbackendv01.models.Clinic;
import fpt.project.clinicbackendv01.models.ClinicService;
import fpt.project.clinicbackendv01.models.User;
import fpt.project.clinicbackendv01.repositories.ClinicRepository;
import fpt.project.clinicbackendv01.repositories.ClinicServiceRepository;
import fpt.project.clinicbackendv01.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ClinicServiceService {
    @Autowired
    private ClinicServiceRepository clinicServiceRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private  ClinicRepository clinicRepository;

    public List<ClinicService> all(String username) {
        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new UserNotFoundException("User not found with username: " + username));
        Clinic clinic = clinicRepository.findByUser(user)
                .orElseThrow(() -> new ClinicException("Clinic profile not found, please create one"));
        return clinicServiceRepository.findAllByClinic(clinic);
    }

    public ClinicService one(long serviceId, String username) {
        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new UserNotFoundException("User not found with username: " + username));
        Clinic clinic = clinicRepository.findByUser(user)
                .orElseThrow(() -> new ClinicException("Clinic profile not found, please create one"));
        Optional<ClinicService> optional = clinicServiceRepository.findById(serviceId);
        ClinicService clinicService = optional.get();
        if(!clinicService.getClinic().equals(clinic)) {
            throw new ClinicServiceException("Service not found");
        }
        return clinicService;
    }


    public ClinicService create(ClinicService clinicService, String username) {
        try {
            User user = userRepository.findByUsername(username)
                    .orElseThrow(() -> new UserNotFoundException("User not found with username: " + username));
            Clinic clinic = clinicRepository.findByUser(user)
                    .orElseThrow(() -> new ClinicException("Clinic profile not found, please create one"));
            clinicService.setClinic(clinic);
            return clinicServiceRepository.save(clinicService);
        } catch (Exception e) {
            throw new ClinicServiceException("Create failed due to: " + e.getMessage());
        }
    }

    public ClinicService update(ClinicService clinicService, String username) {
        try {
            User user = userRepository.findByUsername(username)
                    .orElseThrow(() -> new UserNotFoundException("User not found with username: " + username));
            Clinic clinic = clinicRepository.findByUser(user)
                    .orElseThrow(() -> new ClinicException("Clinic profile not found, please create one"));
            clinicService.setClinic(clinic);
            return clinicServiceRepository.save(clinicService);
        } catch (Exception e) {
            throw new ClinicServiceException("Update failed due to: " + e.getMessage());
        }
    }

    public void delete(long id, String username) {
        clinicServiceRepository.delete(one(id, username));
    }

    public List<ClinicService> allforPatient() {
        return clinicServiceRepository.findAll();
    }

    public List<ClinicService> allByClinicId(long clinicId) {
        Clinic clinic = clinicRepository.findById(clinicId)
                .orElseThrow(() -> new ClinicException("Clinic not found"));
        return clinicServiceRepository.findAllByClinic(clinic);
    }
}
