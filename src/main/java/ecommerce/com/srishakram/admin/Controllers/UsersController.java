package ecommerce.com.srishakram.admin.Controllers;


import ecommerce.com.srishakram.Repository.contactRepository;
import ecommerce.com.srishakram.admin.DTO.RegisterRequest;
import ecommerce.com.srishakram.admin.Repository.AdminOrdersRepository;
import ecommerce.com.srishakram.admin.Repository.CustomerOrderRepository;
import ecommerce.com.srishakram.admin.Repository.UsersRepository;
import ecommerce.com.srishakram.admin.Service.UsersService;
import ecommerce.com.srishakram.models.Users;
import ecommerce.com.srishakram.models.Contact;
import ecommerce.com.srishakram.utils.JwtUtil;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import ecommerce.com.srishakram.utils.CustomerIdGenerator;


@RestController
@RequiredArgsConstructor
@RequestMapping("/auth/verify")
public class UsersController {

    private final UsersService usersserve;
    private final UsersRepository usersrepo;
    private final AdminOrdersRepository adminrepo;
    private final PasswordEncoder passwordEncoder;
    private final contactRepository contactrepo;
    private final JwtUtil jwtUtil;
    private final CustomerOrderRepository customerOrderRepository;


    @PostMapping("/register")
    public ResponseEntity<String> registerUser(
            @Valid @RequestBody RegisterRequest request
    ) {
        if (usersrepo.findByEmail(request.getEmail()).isPresent()) {
            return ResponseEntity
                    .status(HttpStatus.CONFLICT)
                    .body("Email already exist");
        }

        usersserve.createUsers(
                Users.builder()
                        .email(request.getEmail())
                        .password(passwordEncoder.encode(request.getPassword()))
                        .role("ROLE_USER")
                        .customerId(CustomerIdGenerator.generate())
                        .build()
        );

        return ResponseEntity.status(HttpStatus.CREATED)
                .body("Successfully Created");
    }

    @PostMapping("/login")
    public ResponseEntity<?> loginUser(@RequestBody Map<String, String> body) {

        String email = body.get("email");
        String password = body.get("password");

        Optional<Users> userOptional = usersrepo.findByEmail(email);
        Optional<Contact> contactOptional = contactrepo.findByEmail(email);
        Optional<Contact> adminOptional = adminrepo.findByEmail(email);


        if (userOptional.isEmpty()) {
            return ResponseEntity
                    .status(HttpStatus.UNAUTHORIZED)
                    .body("User not registered");
        }

        Users user = userOptional.get();


        if (!passwordEncoder.matches(password, user.getPassword())) {
            return ResponseEntity
                    .status(HttpStatus.UNAUTHORIZED)
                    .body("Invalid User");
        }

        boolean customrole = adminOptional
                .map(Contact::isCustom)
                .orElse(false);


        String userEmail = user.getEmail();
        String token = jwtUtil.generateToken(
                user.getEmail(),
                user.getRole().trim()
        );

        Map<String, Object> response = new HashMap<>();
        response.put("token", token);
        response.put("role", user.getRole());
        response.put("custom", customrole);
        response.put("customerId", user.getId());
        response.put("userEmail",userEmail);

        return ResponseEntity.ok(response);
    }


    @PostMapping("/admin/auth/create-admin")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<String> createAdmin(@RequestBody Map<String,String> body) {

        String email = body.get("email");
        String password = passwordEncoder.encode(body.get("password"));

        if (usersrepo.findByEmail(email).isPresent()) {
            return ResponseEntity.status(HttpStatus.CONFLICT)
                    .body("Email already exists");
        }

        Users admin = Users.builder()
                .email(email)
                .password(password)
                .role("ROLE_ADMIN")
                .build();

        usersrepo.save(admin);

        return ResponseEntity.ok("Admin created successfully");
    }

    @PutMapping("/allow")
    public ResponseEntity<?> toggleCustom(@RequestBody Map<String, String> body) {
        boolean updatedValue = usersserve.toggleCustomByEmail(body.get("email"));
        return ResponseEntity.ok(Map.of(
                "custom", updatedValue
        ));
    }

}
