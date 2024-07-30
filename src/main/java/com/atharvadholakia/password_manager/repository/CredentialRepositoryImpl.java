package com.atharvadholakia.password_manager.repository;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.atharvadholakia.password_manager.data.Credential;
import org.springframework.stereotype.Repository;

import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Repository
public class CredentialRepositoryImpl implements CredentialRepository {

    public static final String DATA_FILE = "credentials.json";
    private final ObjectMapper objectMapper = new ObjectMapper();

    @Override
    public void save(Credential credential) {
        List<Credential> credentials = readAll();
        credentials.removeIf(c -> c.getUserName().equals(credential.getUserName()));
        credentials.add(credential);
        writeAll(credentials);
    }

    @Override
    public Optional<Credential> findByUsername(String username){

        return readAll().stream()
                        .filter(credential -> credential.getUserName().equals(username))
                        .findFirst();
    }

    @Override
    public List<Credential> findAll(){
        return readAll();
    }

    private List<Credential> readAll() {

        File file = new File(DATA_FILE);

        if (!file.exists())
            return List.of();

        try {
            return objectMapper.readValue(file, new TypeReference<List<Credential>>() {
            });
        } catch (IOException e) {
            e.printStackTrace();
            return List.of();
        }
    }

    private void writeAll(List<Credential> credentials) {

        try{

            objectMapper.writeValue(new File(DATA_FILE), credentials);

        } catch(IOException e){

            e.printStackTrace();
        }

    }

}
