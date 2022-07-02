package by.tretiak.demo.service;

import by.tretiak.demo.model.user.User;
import by.tretiak.demo.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    public User findByUserName(String userName) {
        return this.userRepository.findByUsername(userName);
    }

}
