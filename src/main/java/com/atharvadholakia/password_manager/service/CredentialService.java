package com.atharvadholakia.password_manager.service;

import com.atharvadholakia.password_manager.data.Credential;
import com.atharvadholakia.password_manager.exception.EntityNotFoundException;
import com.atharvadholakia.password_manager.repository.CredentialRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class CredentialService {

    private final CredentialRepository credentialRepository;

    @Autowired
    public CredentialService(CredentialRepository credentialRepository) {
        this.credentialRepository = credentialRepository;
    }

    public Credential createCredential(String username, String password, String servicename, String id){

        if(credentialRepository.findByUsername(username).isPresent()){
            throw new IllegalArgumentException("Username already exists");
        }

        Credential credential = new Credential();
        credential.setUsername(username);
        credential.setPassword(password);
        credential.setUsername(servicename);
        credential.setId(id);

        credentialRepository.save(credential);

        return credential;
    }

    public Optional<Credential> getCredentialByUsername(String username){
        return credentialRepository.findByUsername(username);
    }

    public List<Credential> getAllCredential(){
        return credentialRepository.findAll();
    }

}
