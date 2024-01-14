package com.example.markethome.controller;

import com.example.markethome.DTO.AuthReponse;
import com.example.markethome.DTO.AuthRequest;
import com.example.markethome.Entities.User;
import com.example.markethome.JWT.JwtTokenUtil;
import com.example.markethome.reponsitory.*;
import com.example.markethome.service.FileStorageService;
import com.example.markethome.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import com.example.markethome.DTO.registerDTO;

import javax.annotation.security.RolesAllowed;
import javax.validation.Valid;
import javax.xml.bind.DatatypeConverter;
import java.io.ByteArrayInputStream;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/auth")
@CrossOrigin(origins = "*")
public class AuthApi {
    @Autowired
    AuthenticationManager authManager;
    @Autowired
    JwtTokenUtil jwtUtil;
    @Autowired
    private FileStorageService fileStorageService;
    @Autowired
    private UserService userService;
    @Autowired
    private userRepository userRepository;
    @Autowired
    private RoleRepository roleRepository;

    @Autowired
    private BookingDetailRepository bookingDetailRepository;
    private final SimpMessagingTemplate messagingTemplate;

    public AuthApi(SimpMessagingTemplate messagingTemplate) {
        this.messagingTemplate = messagingTemplate;
    }
    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody @Valid AuthRequest request) {
        try {
            Authentication authentication = authManager.authenticate(
                    new UsernamePasswordAuthenticationToken(
                            request.getPhone(), request.getPassword())
            );
            User user = (User) authentication.getPrincipal();
            String accessToken = jwtUtil.generateAccessToken(user);
            AuthReponse response = new AuthReponse(user.getPhone(), user.getName(), user.getImg(), user.getId(), accessToken, user.getBirthday(), user.getEmail());
            return ResponseEntity.ok().body(response);

        } catch (BadCredentialsException ex) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }
    }

    @GetMapping
    @RolesAllowed("ROLE_ADMIN")
    public List<User> list() {
        return userRepository.findAll();
    }

    @RolesAllowed("ROLE_ADMIN")
    @GetMapping("/detail/{id}")
    public ResponseEntity<?> listId(@PathVariable Integer id) {
        if (!userRepository.existsById(id)) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        } else {
            return ResponseEntity.ok().body(userRepository.findById(id).get());
        }
    }

    @RolesAllowed("ROLE_ADMIN")
    @GetMapping("/role")
    public ResponseEntity<?> listRole() {
        return ResponseEntity.ok().body(roleRepository.findAll());
    }

    @PutMapping("/update")
    public ResponseEntity<?> update(@RequestBody @Valid AuthReponse request) {

        User user = userService.findByPhone(request.getPhone());
        user.setEmail(request.getEmail());
        user.setBirthday(request.getBirthday());
        user.setName(request.getName());
        User newUser = userService.update(user);
        AuthReponse response = new AuthReponse(newUser.getPhone(), newUser.getName(), newUser.getImg(), newUser.getId(), request.getAccessToken(), newUser.getBirthday(), newUser.getEmail());
        return ResponseEntity.ok().body(response);
    }

    @RolesAllowed("ROLE_ADMIN")
    @PostMapping("/register")
    public ResponseEntity<?> register(@RequestBody @Valid registerDTO request) {
        if (!userRepository.existsByEmail(request.getEmail())) {
            if (!userRepository.existsByPhone(request.getPhone())) {
                userService.register(request);
                return ResponseEntity.ok().body("done");
            } else {
                return ResponseEntity.ok().body("phone");
            }
        } else {
            return ResponseEntity.ok().body("email");
        }
    }


    @PutMapping("/updateImg")
    public ResponseEntity<?> updateImg(@RequestBody @Valid AuthReponse request) {

        User user = userService.findByPhone(request.getPhone());
        if (user.getImg() != null) {
            String[] pathSegments = user.getImg().split("/");
            fileStorageService.removeFile(pathSegments[pathSegments.length - 1]);
        }
        LocalDateTime currentDateTime = LocalDateTime.now();
        String dateTimeString = currentDateTime.format(DateTimeFormatter.ofPattern("yyyy-MM-dd_HH-mm-ss"));
        String[] strings = request.getImg().split(",");
        String extension;
        switch (strings[0]) {//check image's extension
            case "data:image/jpeg;base64":
                extension = "jpeg";
                break;
            case "data:image/png;base64":
                extension = "png";
                break;
            default://should write cases for more images types
                extension = "jpg";
                break;
        }
        byte[] data = DatatypeConverter.parseBase64Binary(strings[1]);
        String uniqueImageName = UUID.randomUUID().toString();
        String pathfile = uniqueImageName + dateTimeString + "." + extension;
        fileStorageService.storeFile(new ByteArrayInputStream(data), pathfile);
        String fileDownloadUri = ServletUriComponentsBuilder.fromCurrentContextPath()
                .path("/img/")
                .path(pathfile)
                .toUriString();
        user.setImg(fileDownloadUri);
        User newUser = userService.update(user);
        AuthReponse response = new AuthReponse(newUser.getPhone(), newUser.getName(), newUser.getImg(), newUser.getId(), request.getAccessToken(), newUser.getBirthday(), newUser.getEmail());
        return ResponseEntity.ok().body(response);
    }

    @GetMapping("/cart/{id}")
    public int getCart(@PathVariable Integer id) {
        return bookingDetailRepository.findByBookingId(id).size();
    }

}