package com.example.groupproject.services;

import com.example.groupproject.models.Book;
import com.example.groupproject.models.User;
import com.example.groupproject.models.UserLogin;
import com.example.groupproject.repositories.UserRepo;
import jakarta.transaction.Transactional;
import org.mindrot.jbcrypt.BCrypt;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.validation.BindingResult;

import java.util.Optional;

@Service
public class UserService {
    @Autowired
    private UserRepo userRepo;

    @Transactional
    public User register(User newUser , BindingResult result){

        Optional<User> theUser = this.userRepo.findByEmail(newUser.getEmail());

        if (theUser.isPresent()){
            result.rejectValue("email","Email Taken","Email address already exist!");
        }
        if (!newUser.getPassword().equals(newUser.getConfirm())){
            result.rejectValue("confirm","Matches","Passwords must match");
        }

        if (result.hasErrors()){
            return null;
        }else {
            String hashed = BCrypt.hashpw(newUser.getPassword(),BCrypt.gensalt());
            newUser.setPassword(hashed);
            return userRepo.save(newUser);
        }
    }

    public User login(UserLogin newLogin , BindingResult result){
        Optional<User>theUser = userRepo.findByEmail(newLogin.getEmail());
        if (!theUser.isPresent()){
            result.rejectValue("email","EmailNotFound","No user found with that email address");
        }else {
            if (!BCrypt.checkpw(newLogin.getPassword(),theUser.get().getPassword())){
                result.rejectValue("password","Matches","Invalid Password");
            }
        }
        if (result.hasErrors()){
            return null;
        }else {
            return theUser.get();
        }
    }

    public User findUser(Long id){
        return userRepo.findById(id).orElse(null);
    }


}

