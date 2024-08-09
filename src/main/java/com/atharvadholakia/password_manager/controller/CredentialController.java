package com.atharvadholakia.password_manager.controller;

import com.atharvadholakia.password_manager.data.Credential;
import com.atharvadholakia.password_manager.service.CredentialService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;



@RestController
@RequestMapping("/api/credentials")
public class CredentialController {
    
    private final CredentialService credentialService;

    @Autowired
    public CredentialController(CredentialService credentialService){
        this.credentialService = credentialService;
    }

    @PostMapping
    public Credential createCredential(@RequestBody Credential credential){
        System.out.println("inside createcredential");
        return credentialService.createCredential(credential.getServiceName(), credential.getUserName(), credential.getPassword(), credential.getId());
    }

    @GetMapping("/{username}")
    public Optional<Credential> getCredentialByUsername(@PathVariable String username){
        System.out.println("inside getcredentialbyusername");
        return credentialService.getCredentialByUsername(username);
    }

    @GetMapping
    public List<Credential> getAllCredentials(){
        System.out.println("inside getallcredentials");
        return credentialService.getAllCredential();
    }
}

