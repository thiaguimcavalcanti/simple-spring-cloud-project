package com.bot.gateway.controller;

import com.bot.gateway.auth.AuthResponse;
import com.bot.gateway.auth.LoginRequest;
import com.bot.gateway.service.LoginService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@Controller
@RequestMapping("/api")
public class LoginController {

    @Autowired
    private LoginService loginService;

    @CrossOrigin("*")
    @PostMapping("/signin")
    @ResponseBody
    public ResponseEntity<AuthResponse> login(@RequestBody LoginRequest loginRequest) {
        String token = loginService.login(loginRequest.getUsername(), loginRequest.getPassword());
        return getAuthResponseResponseEntity(token);
    }

    @CrossOrigin("*")
    @PostMapping("/signout")
    @ResponseBody
    public ResponseEntity<AuthResponse> logout(@RequestHeader(value = "Authorization") String token) {
        HttpHeaders headers = new HttpHeaders();
        if (loginService.logout(token)) {
            headers.remove("Authorization");
            return new ResponseEntity<>(new AuthResponse("logged out"), headers, HttpStatus.CREATED);
        }
        return new ResponseEntity<>(new AuthResponse("Logout Failed"), headers, HttpStatus.NOT_MODIFIED);
    }

    /**
     * if request reach here it means it is a valid token.
     *
     * @param token Token to validate
     * @return boolean
     */
    @PostMapping("/valid/token")
    @ResponseBody
    public Boolean isValidToken(@RequestHeader(value = "Authorization") String token) {
        return true;
    }


    @PostMapping("/signin/token")
    @CrossOrigin("*")
    @ResponseBody
    public ResponseEntity<AuthResponse> createNewToken(@RequestHeader(value = "Authorization") String token) {
        String newToken = loginService.createNewToken(token);
        return getAuthResponseResponseEntity(newToken);
    }

    private ResponseEntity<AuthResponse> getAuthResponseResponseEntity(String token) {
        HttpHeaders headers = new HttpHeaders();
        headers.set("Authorization", token);

        List<String> headerList = new ArrayList<>();
        headerList.add("Content-Type");
        headerList.add(" Accept");
        headerList.add("X-Requested-With");
        headerList.add("Authorization");
        headers.setAccessControlAllowHeaders(headerList);

        List<String> exposeList = new ArrayList<>();
        exposeList.add("Authorization");
        headers.setAccessControlExposeHeaders(exposeList);

        return new ResponseEntity<>(new AuthResponse(token), headers, HttpStatus.CREATED);
    }
}