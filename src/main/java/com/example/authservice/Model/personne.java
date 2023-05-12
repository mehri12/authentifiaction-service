package com.example.authservice.Model;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import java.util.UUID;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Data

public class personne {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int id;
    private String nom;
    private String prenom;
    private String role;
    private String rib;
    private String mail;
    private String password;


}
