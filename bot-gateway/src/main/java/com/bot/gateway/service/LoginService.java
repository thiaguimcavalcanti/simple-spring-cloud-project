package com.bot.gateway.service;

import com.bot.gateway.entities.User;

public interface LoginService {

    String login(String username, String password);

    User saveUser(User user);

    boolean logout(String token);

    Boolean isValidToken(String token);

    String createNewToken(String token);
}
