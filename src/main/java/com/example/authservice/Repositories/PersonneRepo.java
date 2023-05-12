package com.example.authservice.Repositories;

import com.example.authservice.Model.personne;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;
@Repository
public interface PersonneRepo extends JpaRepository<personne, Long> {
    List<personne> findByMail(String username);
}
