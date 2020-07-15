package com.udacity.jwdnd.course1.cloudstorage.services;

import com.udacity.jwdnd.course1.cloudstorage.mapper.CredentialMapper;
import com.udacity.jwdnd.course1.cloudstorage.model.Credential;
import com.udacity.jwdnd.course1.cloudstorage.model.User;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class CredentialService {

    private static final String ENCRYPT_KEY = "abcd";

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
//        String encryptedPassword = encryptionService.encryptValue(password, ENCRYPT_KEY); // TODO fix
        return credentialMapper.insert(new Credential(null, url, username, ENCRYPT_KEY, password, userId));
    }

    public void deleteCredential(Integer id) {
        credentialMapper.delete(id);
    }

    public void updateCredential(Integer id, String url, String username, String password) {
        credentialMapper.update(new Credential(id, url, username, null, password, null));
    }

    public List<Credential> getAllCredentials(String userName) {
        User user = userService.getUserByName(userName);
        if (user != null) {
            return credentialMapper.getUserCredentials(user.getUserId());
        }
        return new ArrayList<>();
    }
}
