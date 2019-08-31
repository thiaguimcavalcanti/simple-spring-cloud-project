package com.bot.gateway.service.impl;

import com.bot.gateway.entities.JwtToken;
import com.bot.gateway.entities.User;
import com.bot.gateway.entities.UserAuthenticationRule;
import com.bot.gateway.exception.CustomException;
import com.bot.gateway.repository.JwtTokenRepository;
import com.bot.gateway.repository.UserRepository;
import com.bot.gateway.security.JwtTokenProvider;
import com.bot.gateway.service.LoginService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class LoginServiceImpl implements LoginService {

    private final PasswordEncoder passwordEncoder;

    private final JwtTokenProvider jwtTokenProvider;

    private final AuthenticationManager authenticationManager;

    private final UserRepository userRepository;
    private final JwtTokenRepository jwtTokenRepository;

    @Autowired
    public LoginServiceImpl(PasswordEncoder passwordEncoder,
                            JwtTokenProvider jwtTokenProvider,
                            AuthenticationManager authenticationManager,
                            UserRepository userRepository,
                            JwtTokenRepository jwtTokenRepository) {
        this.passwordEncoder = passwordEncoder;
        this.jwtTokenProvider = jwtTokenProvider;
        this.authenticationManager = authenticationManager;
        this.userRepository = userRepository;
        this.jwtTokenRepository = jwtTokenRepository;
    }

    @Override
    public String login(String username, String password) {
        try {
            authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(username,
                    password));

            User user = userRepository.findByEmail(username);

            return jwtTokenProvider.createToken(username, user.getRoles().stream()
                    .map(UserAuthenticationRule::getAuthority)
                    .collect(Collectors.toList()));

        } catch (AuthenticationException e) {
            throw new CustomException("Invalid username or password.", HttpStatus.UNAUTHORIZED);
        }
    }

    @Override
    public User saveUser(User user) {
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        return userRepository.save(user);
    }

    @Override
    public boolean logout(String token) {
        jwtTokenRepository.delete(new JwtToken(token));
        return true;
    }

    @Override
    public Boolean isValidToken(String token) {
        return jwtTokenProvider.validateToken(token);
    }

    @Override
    public String createNewToken(String token) {
        String username = jwtTokenProvider.getUsername(token);
        List<String> roleList = jwtTokenProvider.getRoleList(token);
        return jwtTokenProvider.createToken(username, roleList);
    }
}
