package by.tretiak.demo.service.security;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

@Service
public class SecurityFilterAssistant {

    public boolean checkAuthorization(UserDetails userDetails) {
        if (userDetails instanceof UserDetailsImpl) return ((UserDetailsImpl) userDetails).isEnable();
        return false;
    }

}
