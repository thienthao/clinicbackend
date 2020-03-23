package fpt.project.clinicbackendv01.services;

import fpt.project.clinicbackendv01.exception.PatientNotFoundException;
import fpt.project.clinicbackendv01.exception.UserNotFoundException;
import fpt.project.clinicbackendv01.models.Clinic;
import fpt.project.clinicbackendv01.models.Patient;
import fpt.project.clinicbackendv01.models.User;
import fpt.project.clinicbackendv01.repositories.PatientRepository;
import fpt.project.clinicbackendv01.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class PatientService {
    @Autowired
    private PatientRepository patientRepository;

    @Autowired
    private UserRepository userRepository;

    public Patient one(long userId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new UserNotFoundException("User not found with id: " + userId));
        Patient patient = patientRepository.findByUser(user)
                .orElseThrow(() -> new PatientNotFoundException("Patient profile not found, please create one"));
        return patient;
    }

    private Patient checkPatientExist(long userId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new UserNotFoundException("User not found with id: " + userId));
        Optional<Patient> optional = patientRepository.findByUser(user);
        Patient patient = null;
        if(optional.isPresent()) patient = optional.get();
        return patient;
    }

    public Patient create(Patient newPatient, String username) {
        try {
            User user = userRepository.findByUsername(username)
                    .orElseThrow(() -> new UserNotFoundException("User not found with id: " + username));
            if(checkPatientExist(user.getId()) != null)
                throw new PatientNotFoundException("Profile already existed, please update instead");
            newPatient.setUser(user);
            Patient savedPatient = patientRepository.save(newPatient);
            return savedPatient;
        } catch (Exception e) {
            throw new PatientNotFoundException("Cannot save patient due to: " + e.getMessage());
        }
    }

    public Patient update(Patient updatedPatient, String username) {
        try {
            User user = userRepository.findByUsername(username)
                    .orElseThrow(() -> new UserNotFoundException("User not found with id: " + username));
            updatedPatient.setUser(user);
            Patient savedPatient = patientRepository.save(updatedPatient);
            return savedPatient;
        } catch (Exception e) {
            throw new PatientNotFoundException("Cannot save patient due to: " + e.getMessage());
        }
    }
}
