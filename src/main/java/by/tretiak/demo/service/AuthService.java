package by.tretiak.demo.service;

import by.tretiak.demo.config.security.jwt.JwtUtils;
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
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
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

    public ResponseEntity<?> authUser(String userName, String password) {
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

            return ResponseEntity.ok(new JwtResponse(jwt, userDetails.getId(), userDetails.getUsername(), roles));
        }
        return new ResponseEntity<>(new MessageResponse(), HttpStatus.FORBIDDEN);
    }

    public ResponseEntity<?> registerUser(User user) {
        try {
            if(baseUserRepository.existsByUsername(user.getUsername())) {
                return ResponseEntity
                        .badRequest()
                        .body(new MessageResponse(MessageResponse.USERNAME_EXISTS));
            }
            user = prepareNewUser(user);
            baseUserRepository.save(user);
            return ResponseEntity.ok(new MessageResponse(MessageResponse.USER_CREATED));
        } catch (ObjectNotFoundException e) {
            return new ResponseEntity<>(new MessageResponse(e.getMessage()), HttpStatus.NOT_FOUND);
        }
    }

    public User prepareNewUser(User user) throws ObjectNotFoundException {
            user.setPassword(passwordEncoder.encode(user.getPassword()));
            String reqRole;
            try {
                reqRole = user.getRoles().stream().findFirst().get().getValue().getValue();
            } catch (NullPointerException e) {
                throw new ObjectNotFoundException(ExceptionMessageSource.getMessage(ExceptionMessageSource.ROLE_NOT_FOUND));
            }


            Set<Role> roles = new HashSet<>();
            setUserRoles(reqRole, roles);

            user.setRoles(roles);
            return user;
    }

    private void setUserRoles(String reqRole, Set<Role> roles) throws ObjectNotFoundException {
            switch (reqRole) {
                case "ROLE_ADMIN":
                    Role adminRole = roleRepository.findByValue(ERole.ROLE_ADMIN)
                            .orElseThrow(() -> new ObjectNotFoundException(ExceptionMessageSource
                                    .getMessage(ExceptionMessageSource.ROLE_ADMIN_NOT_FOUND)));
                    roles.add(adminRole);
                    break;
                case "ROLE_KEEPER":
                    Role keeperRole = roleRepository.findByValue(ERole.ROLE_KEEPER)
                            .orElseThrow(() -> new ObjectNotFoundException(ExceptionMessageSource
                                    .getMessage(ExceptionMessageSource.ROLE_KEEPER_NOT_FOUND)));
                    roles.add(keeperRole);
                    break;
                case "ROLE_USER":
                    Role usRole = roleRepository.findByValue(ERole.ROLE_USER)
                            .orElseThrow(() -> new ObjectNotFoundException(ExceptionMessageSource
                                    .getMessage(ExceptionMessageSource.ROLE_USER_NOT_FOUND)));
                    roles.add(usRole);
                    break;
                default: throw new RuntimeException(ExceptionMessageSource.getMessage(ExceptionMessageSource.INCORRECT_ROLE));
            }
    }

    public ResponseEntity<?> updatePassword(@RequestBody String password) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        UserDetailsImpl userDetails = (UserDetailsImpl) authentication.getDetails();
        int id = userDetails.getId();
        User updatedUser = changePassword(id, password);
        return authUser(updatedUser.getUsername(), updatedUser.getPassword());
    }

    public User changePassword(int id, String newPassword) {
        User user = this.baseUserRepository.getUserRepository().findById(id).orElseThrow(() -> new RuntimeException(ExceptionMessageSource
                .getMessage(ExceptionMessageSource.SERVER_ERROR)));
        user.setPassword(newPassword);
        return this.baseUserRepository.save(user);
    }

}
