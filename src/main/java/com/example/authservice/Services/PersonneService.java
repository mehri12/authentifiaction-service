package com.example.authservice.Services;

import com.example.authservice.Model.personne;
import com.example.authservice.Repositories.PersonneRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class PersonneService {
    @Autowired
    PersonneRepo personneRepo;
    public void savepersonne(personne personne) {
        personneRepo.save(personne);
    }
}
