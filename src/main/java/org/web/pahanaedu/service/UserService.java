package org.web.pahanaedu.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.web.pahanaedu.model.User;
import org.web.pahanaedu.repository.UserRepository;

import java.util.List;

@Service
public class UserService
{
    @Autowired
    private UserRepository userRepo;

    public List<User> getAllUsers() {
        return userRepo.findAll();
    }

    public User addUser(User user) {
        return userRepo.save(user);
    }

    public void deleteUser(String username) {
        userRepo.deleteById(username);
    }
}
