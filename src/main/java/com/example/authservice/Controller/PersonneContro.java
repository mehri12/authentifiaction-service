package com.example.authservice.Controller;

import com.example.authservice.Model.AuthenticationModel;
import com.example.authservice.Model.personne;
import com.example.authservice.Repositories.PersonneRepo;
import com.example.authservice.SecurityServices.CustomUserDetails;
import com.example.authservice.SecurityServices.CustomUserDetailsService;
import com.example.authservice.SecurityServices.JwtUtil;
import com.example.authservice.Services.PersonneService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.util.HashMap;
import java.util.Map;


@RestController
@RequestMapping("/auth")
@CrossOrigin(value = "*")
public class PersonneContro {
    @Autowired
    private AuthenticationManager authManager;
    @Autowired
    private PersonneRepo personneRepo;
    @Autowired
    private CustomUserDetailsService customUserDetailsService;
    @Autowired
    private JwtUtil jwtUtil;
    @Autowired
    private BCryptPasswordEncoder encoder;
    @Autowired
    private PersonneService personneService;

    @GetMapping("/{id}")
    public ResponseEntity<personne> getpersonnedetails(@PathVariable("id") int id){
        personne p = new personne();
        p.setId(1);
        p.setNom("mojtabah");
        p.setPrenom("mehri");
        p.setMail("mehri.mojtabah@yahoo.com");
        p.setPassword("22222");
        p.setRole("client");
        p.setRib("cb444");
        return new ResponseEntity<personne>(p,HttpStatus.OK);
    }

    @PostMapping("/save")
    public String savepersonne(@RequestBody personne personne){
        personne.setPassword(encoder.encode(personne.getPassword()));
        personneService.savepersonne(personne);
        return "persoone enregistrer avec succès";

    }
    @GetMapping("/moj")
    public String getresponse(){
        return "authentification is good";
    }

    @PostMapping("/login")
    public ResponseEntity<?> generateToken(@RequestBody AuthenticationModel authModel) throws Exception {
        String message="invalid username";
        Map<String, Object> response = new HashMap<>();
        response.put("message", message);
        if(!personneRepo.findByMail(authModel.getMail()).isEmpty()){
            personne user = personneRepo.findByMail(authModel.getMail()).get(0);
            try {
                authManager.authenticate(new UsernamePasswordAuthenticationToken(authModel.getMail(), authModel.getPassword()));
                CustomUserDetails mud = (CustomUserDetails) customUserDetailsService.loadUserByUsername(authModel.getMail());
               /* private int id;
                private String nom;
                private String prenom;
                private String role;
                private String rib;
                private String mail;
                private String password;*/
                response.remove("message");
                response.put("personne id",user.getId());
                response.put("personne name", user.getNom());
                response.put("role",user.getRole());
                response.put("personne rib",user.getRib());
                response.put("personne mail",user.getMail());
                //response.put("personne password",user.getPassword());
                response.put("token", jwtUtil.generateToken(mud));
                return new ResponseEntity<>(response, HttpStatus.CREATED);

            } catch (Exception ex) {
                message="invalid password";
                response.put("message", message);
                return new ResponseEntity<>(response, HttpStatus.CREATED);
            }
        }
        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }
}
