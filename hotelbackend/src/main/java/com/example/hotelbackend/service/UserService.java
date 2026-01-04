package com.example.hotelbackend.service;


import java.util.List;

import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.hotelbackend.exception.UserAlreadyExistsException;
import com.example.hotelbackend.model.Role;
import com.example.hotelbackend.model.User;
import com.example.hotelbackend.repository.UserRepository;

import lombok.RequiredArgsConstructor;


@Service
@RequiredArgsConstructor
public class UserService implements IUserService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    // private final RoleRepository roleRepository;

    @Override
    public User registerUser(User user) {
        if (userRepository.existsByEmail(user.getEmail())){
            throw new UserAlreadyExistsException(user.getEmail() + " already exists");
        }
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        // user.setId();
        System.out.println("this is password incoder"+user.getPassword());
    //    Role userRole = roleRepository.findByName("ROLE_USER").get();
      user.getRoles().add(Role.USER);
    // user.setRoles(Role.USER);
                System.out.println("i amnear the userRepository"+user);

        return userRepository.save(user);
    }

    @Override
    public List<User> getUsers() {
        return userRepository.findAll();
    }

    @Transactional
    @Override
    public void deleteUser(String email) {
        User theUser = getUser(email);
        if (theUser != null){
            userRepository.deleteByEmail(email);
        }

    }

    @Override
    public User getUser(String email) {
        return userRepository.findByEmail(email)
                .orElseThrow(() -> new UsernameNotFoundException("User not found"));
    }
    @Override
     public User getUserId(String Id) {
        return userRepository.findById(Id)
                .orElseThrow(() -> new UsernameNotFoundException("User not found"));
    }
}
