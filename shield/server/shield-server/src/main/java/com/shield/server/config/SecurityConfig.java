package com.shield.server.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import static org.springframework.security.config.Customizer.withDefaults;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.socket.EnableWebSocketSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
@EnableWebSocketSecurity
public class SecurityConfig {

    /**
     * UserDetailsService bean defines users and their credentials.
     *
     * Currently uses in-memory user store suitable for development and testing.
     *
     * Production improvements: - Replace with a UserDetailsService that loads
     * users from a database. - Integrate with external identity providers
     * (e.g., OAuth2, LDAP).
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
     * Production improvements: - Adjust BCrypt strength/cost if desired
     * Consider alternative strong algorithms (Argon2)
     */
    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    /**
     * SecurityFilterChain configures HTTP request security.
     *
     * Current config:
     * - Uses stateless sessions.
     * - Disables CSRF for simplified API testing.
     * - Uses HTTP Basic auth for quick dev/testing authentication.
     *
     * Production improvements:
     * - Use HTTPS exclusively in production to encrypt traffic.
     * - Enable and properly configure CSRF protection for web clients.
     * - Replace HTTP Basic with strong authentication (OAuth2/JWT).
     * - Add security headers (CSP, HSTS, X-Content-Type-Options).
     * - Configure comprehensive CORS policies if serving cross-origin clients.
     * - Implement rate limiting and request throttling to mitigate abuse.
     * - Log and audit authentication events for security monitoring.
     * - Handle session fixation and concurrent session controls if stateful sessions are used.
     */
    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
            .authorizeHttpRequests(auth -> auth
                .requestMatchers("/public/**").permitAll()
                .anyRequest().authenticated()
                )
                .sessionManagement(session -> session
                    .sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                )
                .httpBasic(withDefaults())
                .csrf(csrf -> csrf.disable());

        // TODO: add security headers here in production

        return http.build();
    }
}
