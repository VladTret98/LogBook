package by.tretiak.demo.service.security;

import by.tretiak.demo.model.user.BookKeeper;
import by.tretiak.demo.model.user.Employee;
import by.tretiak.demo.model.user.User;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

@Service
@NoArgsConstructor
@Getter
public class UserDetailsImpl implements UserDetails {

    private Integer id;

    private String username;

    @JsonIgnore
    private String password;

    private Collection<? extends GrantedAuthority> grantedAuthorities;

    private boolean isEnable = true;

    private Integer companyId;

    public UserDetailsImpl(int id, String username, String password, List<GrantedAuthority> authorities){
        this.id = id;
        this.username = username;
        this.password = password;
        this.grantedAuthorities = authorities;
    }

    public UserDetailsImpl(int id, String username, String password, List<GrantedAuthority> authorities, boolean isEnable, int companyId){
        this.id = id;
        this.username = username;
        this.password = password;
        this.grantedAuthorities = authorities;
        this.isEnable = isEnable;
        this.companyId = companyId;
    }

    public static UserDetailsImpl build(User user) {
        List<GrantedAuthority> authorities = user.getRoles().stream()
                .map(role -> new SimpleGrantedAuthority(role.getValue().getValue()))
                .collect(Collectors.toList());

         if (user instanceof BookKeeper) {
            return new UserDetailsImpl(user.getId(), user.getUsername(), user.getPassword(), authorities,
                    ((BookKeeper) user).isEnable(), ((BookKeeper) user).getCompany().getId());
        } else if (user instanceof Employee) {
            return new UserDetailsImpl(user.getId(), user.getUsername(), user.getPassword(), authorities,
                    ((Employee) user).isEnable(), ((Employee) user).getCompany().getId());
        } else {
             return new UserDetailsImpl(user.getId(), user.getUsername(), user.getPassword(), authorities);
         }
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return this.grantedAuthorities;
    }

    @Override
    public String getPassword() {
        return this.password;
    }

    @Override
    public String getUsername() {
        return this.username;
    }

    public Integer getId() {
        return this.id;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }
}
