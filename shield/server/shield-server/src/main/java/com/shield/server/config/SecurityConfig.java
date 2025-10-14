package com.shield.server.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.socket.EnableWebSocketSecurity;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;

@Configuration
@EnableWebSecurity
@EnableWebSocketSecurity
public class SecurityConfig {

    /**
     * UserDetailsService bean defines users and their credentials.
     * 
     * Currently uses in-memory user store suitable for development and testing.
     * 
     * Production improvements:
     * - Replace with a UserDetailsService that loads users from a database.
     * - Integrate with external identity providers (e.g., OAuth2, LDAP).
     */
    @Bean
    public UserDetailsService userDetailsService() {
        User.UserBuilder users = User.builder().passwordEncoder(passwordEncoder()::encode);

        UserDetails user = users.username("user").password("password").roles("USER").build();
        UserDetails admin = users.username("admin").password("password").roles("ADMIN", "USER").build();

        return new InMemoryUserDetailsManager(user, admin);
    }

    /**
     * PasswordEncoder bean defines the strategy for encoding passwords.
     * 
     * BCrypt is recommended for production due to its strength and adaptive hashing.
     * 
     * Production improvements:
     * - Adjust BCrypt strength/cost 
     * - Consider alternative strong algorithms (Argon2)
     */
    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
    
}
