package com.udacity.jwdnd.course1.cloudstorage.services;

import com.udacity.jwdnd.course1.cloudstorage.mapper.CredentialMapper;
import com.udacity.jwdnd.course1.cloudstorage.model.Credential;
import com.udacity.jwdnd.course1.cloudstorage.model.User;
import org.springframework.stereotype.Service;

import java.security.SecureRandom;
import java.util.ArrayList;
import java.util.Base64;
import java.util.List;

@Service
public class CredentialService {

    private final UserService userService;
    private final CredentialMapper credentialMapper;
    private final EncryptionService encryptionService;

    public CredentialService(UserService userService, CredentialMapper credentialMapper, EncryptionService encryptionService) {
        this.userService = userService;
        this.credentialMapper = credentialMapper;
        this.encryptionService = encryptionService;
    }

    public int createCredential(String url, String username, String password, String userName) {
        User user = userService.getUserByName(userName);
        Integer userId = user.getUserId();
        String encodedKey = generateEncodedKey();
        String encodedPassword = encryptionService.encryptValue(password, encodedKey);
        return credentialMapper.insert(new Credential(null, url, username, encodedKey, encodedPassword, userId));
    }

    public void deleteCredential(Integer id) {
        credentialMapper.delete(id);
    }

    public void updateCredential(Integer id, String url, String username, String password) {
        String encodedKey = generateEncodedKey();
        String encodedPassword = encryptionService.encryptValue(password, encodedKey);
        credentialMapper.update(new Credential(id, url, username, encodedKey, encodedPassword, null));
    }

    public List<Credential> getAllCredentials(String userName) {
        User user = userService.getUserByName(userName);
        if (user != null) {
            List<Credential> credentials = credentialMapper.getUserCredentials(user.getUserId());
            for (Credential credential : credentials) {
                String decodedPassword = encryptionService.decryptValue(credential.getPassword(), credential.getKey());
                credential.setDecodedPassword(decodedPassword);
            }
            return credentials;
        }
        return new ArrayList<>();
    }

    private String generateEncodedKey() {
        SecureRandom random = new SecureRandom();
        byte[] key = new byte[16];
        random.nextBytes(key);
        return Base64.getEncoder().encodeToString(key);
    }
}
