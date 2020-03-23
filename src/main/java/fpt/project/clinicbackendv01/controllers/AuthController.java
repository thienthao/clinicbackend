package fpt.project.clinicbackendv01.controllers;

import fpt.project.clinicbackendv01.models.ERole;
import fpt.project.clinicbackendv01.models.Role;
import fpt.project.clinicbackendv01.models.User;
import fpt.project.clinicbackendv01.payload.request.LoginRequest;
import fpt.project.clinicbackendv01.payload.request.SignUpRequest;
import fpt.project.clinicbackendv01.payload.response.JwtResponse;
import fpt.project.clinicbackendv01.payload.response.MessageResponse;
import fpt.project.clinicbackendv01.repositories.RoleRepository;
import fpt.project.clinicbackendv01.repositories.UserRepository;
import fpt.project.clinicbackendv01.security.jwt.JwtUtils;
import fpt.project.clinicbackendv01.security.services.UserDetailsImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.stream.Collectors;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/api/auth")
public class AuthController {

    @Autowired
    AuthenticationManager authenticationManager;

    @Autowired
    UserRepository userRepository;

    @Autowired
    RoleRepository roleRepository;

    @Autowired
    PasswordEncoder encoder;

    @Autowired
    JwtUtils jwtUtils;

    @PostMapping("/signin")
    public ResponseEntity<?> authenticateUser(@Valid @RequestBody LoginRequest loginRequest) {

        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(loginRequest.getUsername(), loginRequest.getPassword()));

        SecurityContextHolder.getContext().setAuthentication(authentication);
        String jwt = jwtUtils.generateJwtToken(authentication);

        UserDetailsImpl userDetails = (UserDetailsImpl) authentication.getPrincipal();
        List<String> roles = userDetails.getAuthorities().stream()
                .map(item -> item.getAuthority())
                .collect(Collectors.toList());

        return ResponseEntity.ok(new JwtResponse(jwt,
                userDetails.getId(),
                userDetails.getUsername(),
                roles.get(0)));
    }

    @PostMapping("/signup")
    public ResponseEntity<?> registerUser(@Valid @RequestBody SignUpRequest signUpRequest) {
        if (userRepository.existsByUsername(signUpRequest.getUsername())) {
            return ResponseEntity
                    .badRequest()
                    .body(new MessageResponse("Error: Username is already taken!"));
        }

        // Tao 1 account moi
        User user = new User(signUpRequest.getUsername(),
                encoder.encode(signUpRequest.getPassword()));

        String role = signUpRequest.getRole();
        Role accountRole =null;

        if (role == null) {
            accountRole = roleRepository.findByName(ERole.ROLE_PATIENT)
                    .orElseThrow(() -> new RuntimeException("Error: Role is not found."));
        } else {
                switch (role) {
                    case "admin":
                        accountRole = roleRepository.findByName(ERole.ROLE_ADMIN)
                                .orElseThrow(() -> new RuntimeException("Error: Role is not found."));
                        break;
                    case "clinic":
                        accountRole = roleRepository.findByName(ERole.ROLE_CLINIC)
                                .orElseThrow(() -> new RuntimeException("Error: Role is not found."));
                        break;
                    default:
                        accountRole = roleRepository.findByName(ERole.ROLE_PATIENT)
                                .orElseThrow(() -> new RuntimeException("Error: Role is not found."));
                };
        }

        user.setRole(accountRole);
        userRepository.save(user);

        return ResponseEntity.ok(new MessageResponse("User registered successfully!"));
    }
}
