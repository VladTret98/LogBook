package by.tretiak.demo.service.security;

import by.tretiak.demo.model.user.User;
import by.tretiak.demo.repository.BaseUserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class UserDetailsServiceImpl implements UserDetailsService {

    @Autowired
    private BaseUserRepository baseUserRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = this.baseUserRepository.findByUsername(username);
        return UserDetailsImpl.build(user);
    }
}
