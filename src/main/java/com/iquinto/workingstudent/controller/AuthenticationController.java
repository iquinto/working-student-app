package com.iquinto.workingstudent.controller;

import com.iquinto.workingstudent.model.*;
import com.iquinto.workingstudent.model.enums.Province;
import com.iquinto.workingstudent.model.enums.Role;
import com.iquinto.workingstudent.payload.LoginRequest;
import com.iquinto.workingstudent.payload.MessageResponse;
import com.iquinto.workingstudent.payload.RegisterRequest;
import com.iquinto.workingstudent.payload.RegisterResponse;
import com.iquinto.workingstudent.service.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import com.iquinto.workingstudent.security.AuthUserDetails;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.*;
import java.util.List;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
public class AuthenticationController {
    private static final Logger log = LoggerFactory.getLogger(AuthenticationController.class);

    @Autowired
    PasswordEncoder encoder;

    @Autowired
    UserService userService;

    @Autowired
    JobPositionService jobPositionService;

    @Autowired
    StudentService studentService;

    @Autowired
    EmployerService employerService;

    @Autowired
    AuthenticationService authenticationService;

    @Autowired
    DataService dataService;

    @Autowired
    UniversityService universityService;

    @Autowired
    AddressService addressService;

    @Autowired
    AreaService areaService;

    @RequestMapping(value = {"/register"}, method = RequestMethod.POST)
    public ResponseEntity<?> register(@RequestBody RegisterRequest registerRequest) {
        String message = "";

        log.info("[c: authentication] [m:registerUser] registering new user with username : " + registerRequest.getUsername());

        if (userService.existsByUsername(registerRequest.getUsername())) {
            message = "Error: El usuario "+ registerRequest.getUsername() +" ya existe en nuestro servidor!";
            log.error("[c: authentication] [m:registerUser] username already exist");
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new MessageResponse(message));
        }
        if (userService.existsByEmail(registerRequest.getEmail())) {
            message = "Error: El correo "+ registerRequest.getEmail() +" ya existe en nuestro servidor!";
            log.error("[c: authentication] [m:registerUser] email already exist");
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new MessageResponse(message));
        }

        if (!registerRequest.getEmail().contains("edu") && registerRequest.getRole() == Role.ROLE_STUDENT) {
            message = "Error:  El correo debe ser de una universidad (.edu)";
            log.error("[c: authentication] [m:registerUser] email incorrect");
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new MessageResponse(message));
        }

        Employer employer = null;
        Student student = null;

        if (registerRequest.getRole() == Role.ROLE_STUDENT){
            log.info("[c: authentication] [m:registerUser] creating new student : " + registerRequest.getUsername());
            student = new Student(registerRequest.getUsername(), registerRequest.getEmail(), registerRequest.getAddress(), Role.ROLE_STUDENT);
            student.setPassword(encoder.encode(registerRequest.getPassword()));
            Set<JobPosition> jobPositions = new HashSet<>();
            for (Long id : registerRequest.getJobPositions()){
                jobPositions.add(jobPositionService.findById(id));
            }
            student.setJobPositions(jobPositions);
            student.setName(registerRequest.getName());
            student.setSurname(registerRequest.getSurname());
            student.setPhone(registerRequest.getPhone());
            student.setSex(registerRequest.getSex());
            student.setBirthday(registerRequest.getBirthday());
            student.setDescription(registerRequest.getDescription());
            student.setUniversity(universityService.findById(registerRequest.getUniversity()));
            student.setStudentId(registerRequest.getStudentId());
            student.setHasCar(registerRequest.isHasCar());
            studentService.save(student);

        } else {
            log.info("[c: authentication] [m:registerUser] creating new employer : " + registerRequest.getUsername());
            employer = new Employer(registerRequest.getUsername(), registerRequest.getEmail(), registerRequest.getAddress(), Role.ROLE_EMPLOYER);
            employer.setPassword(encoder.encode(registerRequest.getPassword()));
            employer.setArea(areaService.findById(registerRequest.getArea()));
            employer.setName(registerRequest.getName());
            employer.setDescription(registerRequest.getDescription());
            employer.setWebsite(registerRequest.getWebsite());
            employer.setSurname(registerRequest.getSurname());
            employer.setPhone(registerRequest.getPhone());
            employer.setHasCompany(registerRequest.isHasCompany());
            employerService.save(employer);
        }
        log.info("[c: authentication] [m:registerUser] user created sucessfully : " + registerRequest.getUsername());
        return ResponseEntity.status(HttpStatus.OK).body(new MessageResponse(MessageResponse.REGISTER_CORRECTLY));
    }

    @RequestMapping(value = {"/login"}, method = RequestMethod.POST)
    public ResponseEntity<?> login(@Valid @RequestBody LoginRequest loginRequest) {
        log.info("[c: authentication] [m:authenticateUser] Error please check username or password " + loginRequest.getUsername());
        String message = "";
        LinkedHashMap<String,Object> auth = new LinkedHashMap<String,Object>();
        try{
            auth = authenticationService.setAuthentication(loginRequest.getUsername(), loginRequest.getPassword());
        }catch (Exception e){
            message = "Error: Datos incorrectos! Por favor comprueba los datos.";
            log.error("[c: authentication] [m:authenticateUser] Error please check username or password " + e.getMessage());
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new MessageResponse(message));
        }

        String jwt = (String) auth.get("token");
        List<String> roles = (List<String>) auth.get("roles");
        RegisterResponse registerResponse = null;

        if (roles.contains(Role.ROLE_STUDENT.name())){
            Student user = studentService.findByUsername(loginRequest.getUsername());
            log.info("[c: authentication] [m:authenticateUser] student is found :" + user.toString());
            registerResponse = new RegisterResponse(user.getId(), user.getName(), user.getSurname(), user.getUsername(), user.getPassword(), user.getEmail());
            registerResponse.setAccessToken(jwt);
            registerResponse.setRole(user.getRole());
            registerResponse.setAddress(user.getAddress());
            registerResponse.setHasCar(user.isHasCar());
            registerResponse.setPhoto(user.getPhoto());
            registerResponse.setJobPositions(user.getJobPositions());
            log.info("[c: authentication] [m:authenticateUser] user is authenticated :");
        } else {
            Employer user = employerService.findByUsername(loginRequest.getUsername());
            log.info("[c: authentication] [m:authenticateUser] employer is found :" + user.toString());
            registerResponse = new RegisterResponse(user.getId(), user.getName(), user.getSurname(), user.getUsername(), user.getPassword(), user.getEmail());
            registerResponse.setAccessToken(jwt);
            registerResponse.setRole(user.getRole());
            registerResponse.setAddress(user.getAddress());
            registerResponse.setArea(user.getArea());
            registerResponse.setHasCompany(user.isHasCompany());
            registerResponse.setPhoto(user.getPhoto());
            log.info("[c: authentication] [m:authenticateUser] employer is authenticated :");
        }
        return ResponseEntity.status(HttpStatus.OK).body(registerResponse);
    }

    @RequestMapping(value = {"/profile"}, method = RequestMethod.GET)
    public ResponseEntity<?> loadProfile() {
        log.info("[c: authentication] [m:loadProfile] retreiving profile : " );
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        AuthUserDetails userDetails = (AuthUserDetails) authentication.getPrincipal();
        if(userDetails.getAuthorities().stream().anyMatch(grantedAuthority -> grantedAuthority.getAuthority().equals(Role.ROLE_STUDENT.name()))){
            Student student = studentService.findByUsername(userDetails.getUsername());
            log.info("[c: authentication] [m:loadProfile] student is found: " + student.getUsername());
            return ResponseEntity.status(HttpStatus.OK).body(student);
        }else{
            Employer employer= employerService.findByUsername(userDetails.getUsername());
            log.info("[c: authentication] [m:loadProfile] employer is found: " + employer.toString());
            return ResponseEntity.status(HttpStatus.OK).body(employer);
        }
    }

    @RequestMapping(value = {"/profile/update"}, method = RequestMethod.PUT)
    public ResponseEntity<?> updateProfile(@Valid @RequestBody RegisterRequest registerRequest) {
        User user = userService.findByUsername(authenticationService.getCurrentUsername());
        if(user instanceof  Student){
            log.info("[c: authentication] [m:updateProfile] updating profile of user : " + user.getUsername());
            Student student = (Student) user;
            Set<JobPosition> jobPositions = new HashSet<>();
            for (Long id : registerRequest.getJobPositions()){
                jobPositions.add(jobPositionService.findById(id));
            }
            student.setJobPositions(jobPositions);
            addressService.update(registerRequest.getAddress());
            student.setName(registerRequest.getName());
            student.setSurname(registerRequest.getSurname());
            student.setPhone(registerRequest.getPhone());
            student.setSex(registerRequest.getSex());
            student.setBirthday(registerRequest.getBirthday());
            student.setDescription(registerRequest.getDescription());
            student.setUniversity(universityService.findById(registerRequest.getUniversity()));
            student.setStudentId(registerRequest.getStudentId());
            student.setHasCar(registerRequest.isHasCar());
            studentService.save(student);
        }else{
            log.info("[c: authentication] [m:updateProfile] updating profile of user : " + user.getUsername());
            Employer employer = (Employer) user;
            employer.setArea(areaService.findById(registerRequest.getArea()));
            addressService.update(registerRequest.getAddress());
            employer.setName(registerRequest.getName());
            employer.setSurname(registerRequest.getSurname());
            employer.setPhone(registerRequest.getPhone());
            employer.setDescription(registerRequest.getDescription());
            employer.setHasCompany(registerRequest.isHasCompany());
            employer.setWebsite(registerRequest.getWebsite());
            employerService.save(employer);
        }
        log.info("[c: authentication] [m:updateProfile]  user profile  updated corretly");
        return ResponseEntity.status(HttpStatus.OK).body(new MessageResponse(MessageResponse.UPDATE_CORRECTLY));
    }

    @RequestMapping(value = {"/reset"}, method = RequestMethod.PUT)
    public ResponseEntity<?> resetPassword(@Valid @RequestBody RegisterRequest registerRequest) {
        String message = "";
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        AuthUserDetails userDetails = (AuthUserDetails) authentication.getPrincipal();
        User user = userService.findByUsername(userDetails.getUsername());
        user.setPassword(encoder.encode(registerRequest.getPassword()));
        userService.save(user);

        message = "S'ha actualizado los la contraseña del usuario " + user.getUsername() + "  corectamente" ;
        log.info("[c: authentication] [m:resetPassword]  " + message);
        return ResponseEntity.status(HttpStatus.OK).body(new MessageResponse(message));
    }

    @RequestMapping(value ="/auth/jobpositions", method = RequestMethod.GET)
    public ResponseEntity<?> jobpositions(  @RequestParam String name) {
        log.info("[c: categories] loading.. " );
        return ResponseEntity.status(HttpStatus.OK).body(jobPositionService.findAllByNameLike(name.toLowerCase()));
    }

    @RequestMapping(value ="/auth/universities", method = RequestMethod.GET)
    public ResponseEntity<?> universities(@RequestParam String name) {
        log.info("[c: universities] loading.. " );
        return ResponseEntity.status(HttpStatus.OK).body(universityService.findAllByNameLike(name.toLowerCase()));
    }

    @RequestMapping(value ="/auth/areas", method = RequestMethod.GET)
    public ResponseEntity<?> areas(@RequestParam String name) {
        log.info("[c: areas] loading.. " );
        return ResponseEntity.status(HttpStatus.OK).body(areaService.findAllByNameLike(name.toLowerCase()));
    }

    @RequestMapping(value ="/auth/provinces", method = RequestMethod.GET)
    public ResponseEntity<?> provinces(  @RequestParam String name) {
        log.info("[c: provinces] loading.. " );
        List<String> list = new ArrayList<>();
        for (Province province : Province.values()) {
            if(province.getText().toLowerCase().contains(name.toLowerCase())){
                list.add(province.getText());
            }
        }
        return ResponseEntity.status(HttpStatus.OK).body(list);
    }

}
