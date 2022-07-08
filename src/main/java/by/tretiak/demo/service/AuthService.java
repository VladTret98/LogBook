package by.tretiak.demo.service;

import by.tretiak.demo.config.security.jwt.JwtUtils;
import by.tretiak.demo.exception.NotInputException;
import by.tretiak.demo.exception.ObjectNotFoundException;
import by.tretiak.demo.exception.source.ExceptionMessageSource;
import by.tretiak.demo.model.pojo.JwtResponse;
import by.tretiak.demo.model.pojo.MessageResponse;
import by.tretiak.demo.model.user.ERole;
import by.tretiak.demo.model.user.Role;
import by.tretiak.demo.model.user.User;
import by.tretiak.demo.repository.BaseUserRepository;
import by.tretiak.demo.repository.RoleRepository;
import by.tretiak.demo.service.security.UserDetailsImpl;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestBody;

import java.security.AccessControlException;
import java.util.List;
import java.util.stream.Collectors;

@Service
@NoArgsConstructor
@Setter
public class AuthService {

    @Autowired
    private BaseUserRepository baseUserRepository;

    @Autowired
    AuthenticationManager manager;

    @Autowired
    JwtUtils jwtUtils;

    @Autowired
    RoleRepository roleRepository;

    @Autowired
    PasswordEncoder passwordEncoder;

    public MessageResponse authUser(String userName, String password) {
        Authentication authentication = manager.authenticate(new UsernamePasswordAuthenticationToken(
               userName, password));

        UserDetailsImpl details = (UserDetailsImpl) authentication.getPrincipal();

        if (details.isEnable()) {

            SecurityContext context = SecurityContextHolder.getContext();
            context.setAuthentication(authentication);

            String jwt = jwtUtils.generateJwtToken(authentication);

            UserDetailsImpl userDetails = (UserDetailsImpl) authentication.getPrincipal();
            List<String> roles = userDetails.getAuthorities().stream().map(item -> item.getAuthority())
                    .collect(Collectors.toList());

            return new JwtResponse(jwt, userDetails.getId(), userDetails.getUsername(), roles);
        }
        throw new AccessControlException(ExceptionMessageSource.getMessage(ExceptionMessageSource.NOT_ENABLE));
    }

    public User registerUser(User user) throws NotInputException, RuntimeException, ObjectNotFoundException {
            if(baseUserRepository.existsByUsername(user.getUsername())) {
                throw new RuntimeException(ExceptionMessageSource
                        .getMessage(ExceptionMessageSource.USERNAME_USED));
            }
            user = prepareNewUser(user);
            user = baseUserRepository.save(user);
            return user;
    }

    public User prepareNewUser(User user) throws NotInputException, ObjectNotFoundException {
            user.setPassword(passwordEncoder.encode(user.getPassword()));
            ERole reqRole;
            try {
                reqRole = user.getRole().getValue();
            } catch (NullPointerException e) {
                throw new NotInputException(ExceptionMessageSource.getMessage(ExceptionMessageSource.ROLE_NOT_FOUND));
            }

            Role role = setUserRoles(reqRole);

            user.setRole(role);
            return user;
    }

    private Role setUserRoles(ERole reqRole) throws ObjectNotFoundException, NotInputException {
            switch (reqRole) {
                case ROLE_ADMIN:
                    return roleRepository.findByValue(ERole.ROLE_ADMIN)
                            .orElseThrow(() -> new ObjectNotFoundException(ExceptionMessageSource
                                    .getMessage(ExceptionMessageSource.ROLE_ADMIN_NOT_FOUND)));
                case ROLE_KEEPER:
                    return roleRepository.findByValue(ERole.ROLE_KEEPER)
                            .orElseThrow(() -> new ObjectNotFoundException(ExceptionMessageSource
                                    .getMessage(ExceptionMessageSource.ROLE_KEEPER_NOT_FOUND)));
                case ROLE_USER:
                    return roleRepository.findByValue(ERole.ROLE_USER)
                            .orElseThrow(() -> new ObjectNotFoundException(ExceptionMessageSource
                                    .getMessage(ExceptionMessageSource.ROLE_USER_NOT_FOUND)));
                default: throw new NotInputException(ExceptionMessageSource.getMessage(ExceptionMessageSource.INCORRECT_ROLE));
            }
    }

    public MessageResponse updatePassword(@RequestBody String password) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        UserDetailsImpl userDetails = (UserDetailsImpl) authentication.getDetails();
        int id = userDetails.getId();
        User updatedUser = changePassword(id, password);
        return authUser(updatedUser.getUsername(), updatedUser.getPassword());
    }

    private User changePassword(int id, String newPassword) {
        User user = this.baseUserRepository.getUserRepository().findById(id).orElseThrow(() -> new RuntimeException(ExceptionMessageSource
                .getMessage(ExceptionMessageSource.SERVER_ERROR)));
        user.setPassword(newPassword);
        return this.baseUserRepository.save(user);
    }

}
