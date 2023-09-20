package com.sap.ase.poker.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.sap.ase.poker.security.JsonUsernamePasswordAuthenticationFilter;
import com.sap.ase.poker.security.JwtAuthenticationRequestFilter;
import com.sap.ase.poker.security.JwtTools;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.ProviderManager;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity()
public class WebSecurityConfig {

    private final ObjectMapper objectMapper;

    public WebSecurityConfig(ObjectMapper objectMapper) {
        super();
        this.objectMapper = objectMapper;
    }

    @Bean
    public UserDetailsService users() {
        User.UserBuilder userBuilder = User.builder()
                .passwordEncoder(PasswordEncoderFactories.createDelegatingPasswordEncoder()::encode)
                .roles("USER");
        return new InMemoryUserDetailsManager(
                userBuilder.username("al-capone").password("all-in").build(),
                userBuilder.username("pat-garret").password("all-in").build(),
                userBuilder.username("wyatt-earp").password("all-in").build(),
                userBuilder.username("doc-holiday").password("all-in").build(),
                userBuilder.username("wild-bill").password("all-in").build(),
                userBuilder.username("stu-ungar").password("all-in").build(),
                userBuilder.username("kitty-leroy").password("all-in").build(),
                userBuilder.username("poker-alice").password("all-in").build(),
                userBuilder.username("madame-moustache").password("all-in").build()
        );
    }

    @Bean
    public AuthenticationProvider userDetailsAuthProvider() {
        DaoAuthenticationProvider provider = new DaoAuthenticationProvider();
        provider.setUserDetailsService(users());
        return provider;
    }
    @Bean
    public AuthenticationManager authenticationManager() throws Exception {
        return new ProviderManager(userDetailsAuthProvider());
    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        JwtTools jwtTools = new JwtTools(JwtTools.SECRET);
        http.csrf(AbstractHttpConfigurer::disable)
            .authorizeHttpRequests(request -> request
                    .requestMatchers("/table/**").authenticated()
                    .requestMatchers("/login/**").permitAll()
                    .anyRequest().authenticated())
            .addFilter(new JwtAuthenticationRequestFilter(authenticationManager(), jwtTools))
            .addFilter(new JsonUsernamePasswordAuthenticationFilter(authenticationManager(), objectMapper, jwtTools))
            .formLogin(form -> form.loginPage("/login/index.html").permitAll())
            .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS));
        return http.build();
    }
}