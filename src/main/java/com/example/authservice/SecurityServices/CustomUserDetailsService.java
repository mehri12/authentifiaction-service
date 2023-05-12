package com.example.authservice.SecurityServices;

import com.example.authservice.Model.personne;
import com.example.authservice.Repositories.PersonneRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class CustomUserDetailsService implements UserDetailsService {
@Autowired
    private PersonneRepo personneRepo;
    @Override

    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        personne personne=personneRepo.findByMail(username).get(0);
        CustomUserDetails userDetails = null;
        if(personne!=null){
            userDetails=new CustomUserDetails();
            userDetails.setPersonne(personne);

        }else {
            throw new UsernameNotFoundException("employee not exist with name:"+username);
        }
        return userDetails;
    }
}
