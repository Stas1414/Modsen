package com.example.mainservice.service;

import com.example.mainservice.cfg.JwtProvider;
import com.example.mainservice.models.AuthRegRequest;
import com.example.mainservice.models.AuthResponse;
import com.example.mainservice.models.User;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthService {
    private final UserService userService;
    private final JwtProvider jwtProvider;
    private final AuthenticationManager authenticationManager;
    private final PasswordEncoder passwordEncoder;

    public AuthResponse signUp(AuthRegRequest request) {
        var user = User.builder()
                .username(request.getUsername())
                .password(passwordEncoder.encode(request.getPassword()))
                .build();

        userService.createUser(user);

        var jwt = jwtProvider.generateToken(user);

        setAuthentication(user, jwt);

        return new AuthResponse(jwt);
    }

    public AuthResponse signIn(AuthRegRequest request) {
        authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(
                request.getUsername(),
                request.getPassword()
        ));

        var user = userService.loadUserByUsername(request.getUsername());

        var jwt = jwtProvider.generateToken(user);

        setAuthentication(user, jwt);

        return new AuthResponse(jwt);
    }

    private void setAuthentication(UserDetails user, String token) {
        var authToken = new UsernamePasswordAuthenticationToken(user, token, user.getAuthorities());

        SecurityContextHolder.getContext().setAuthentication(authToken);
    }
}
