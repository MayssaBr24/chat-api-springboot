package tn.essat.messaging.controller;


import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import tn.essat.messaging.config.GestionToken;
import tn.essat.messaging.config.JwtRequest;
import tn.essat.messaging.config.JwtResponse;
import tn.essat.messaging.model.Utilisateur;
import tn.essat.messaging.service.UserService;


@CrossOrigin("*")
@RestController
@RequestMapping(value = "/auth")
public class RestAuth {

    @Autowired
    private GestionToken token_gen;

    @Autowired
    private UserService userService;

    @Autowired
    private AuthenticationManager authenticationManager;

    @PostMapping(value = "/login")
    public JwtResponse signIn(@RequestBody JwtRequest request) {

        Authentication authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(request.getUsername(), request.getPassword())  );

        SecurityContextHolder.getContext().setAuthentication(authentication);

        Utilisateur user = (Utilisateur) userService.loadUserByUsername(request.getUsername());
        String token = token_gen.generateToken(user);
        JwtResponse response= new JwtResponse(token);
        
        return response;
    }
}