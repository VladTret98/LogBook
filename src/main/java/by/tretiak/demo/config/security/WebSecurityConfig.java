package by.tretiak.demo.config.security;

import by.tretiak.demo.config.security.jwt.AuthEntryPoint;
import by.tretiak.demo.config.security.jwt.AuthTokenFilter;
import by.tretiak.demo.service.security.UserDetailsServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@EnableWebSecurity
@Configuration
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

    private static final String[] OPEN_PATHS = {"/auth/signup", "/auth/signin", "/updatepassword", "/admins/new", "/newpassword"};

    private static final String[] KEEPER_PATHS = {"/", "/cards/*", "/employees/*"};

    private static final String[] ADMIN_PATHS = {"/companies/new", "/new"};

    private static final String[] USER_PATHS = {"/cards"};

    @Autowired
    private UserDetailsServiceImpl userDetailsService;

    @Autowired
    private AuthEntryPoint unauthorizedHandler;

    @Bean
    public AuthTokenFilter authTokenFilter() {
        return new AuthTokenFilter();
    }

    @Bean
    @Override
    public AuthenticationManager authenticationManagerBean() throws Exception {
        return super.authenticationManagerBean();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Override
    public void configure(AuthenticationManagerBuilder authenticationManagerBuilder) throws Exception {
        authenticationManagerBuilder.userDetailsService(userDetailsService).passwordEncoder(passwordEncoder());
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
                .csrf().disable()
                .exceptionHandling().authenticationEntryPoint(unauthorizedHandler).and()
                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS).and()
                .authorizeRequests()
                .antMatchers(OPEN_PATHS).permitAll()
                .antMatchers(USER_PATHS).hasRole(SecurityRoles.USER.value)
                .antMatchers(KEEPER_PATHS).hasRole(SecurityRoles.KEEPER.value)
                .antMatchers(ADMIN_PATHS).hasRole(SecurityRoles.ADMIN.value)
                .and()
                .logout()
                .invalidateHttpSession(true)
                .and()
                .rememberMe()
                .tokenValiditySeconds(3600);

        http.addFilterBefore(authTokenFilter(), UsernamePasswordAuthenticationFilter.class);
    }

    enum SecurityRoles {
        USER("USER"),
        ADMIN("ADMIN"),
        KEEPER("KEEPER");

        private String value;


        SecurityRoles(String value) {
            this.value = value;
        }

        public String getValue() {
            return this.value;
        }
    }

}

