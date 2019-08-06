package com.bot.gateway.security;

import com.bot.gateway.entities.JwtToken;
import com.bot.gateway.repository.JwtTokenRepository;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import javax.servlet.http.HttpServletRequest;
import javax.transaction.Transactional;
import java.util.Base64;
import java.util.Date;
import java.util.List;

@Transactional
@Component
public class JwtTokenProvider {

    private static final String AUTH = "auth";
    private static final String AUTHORIZATION = "Authorization";
    private static final long VALIDITY_IN_MILLISECONDS = 120000; // 1h

    private String secretKey = "secret-key";

    @Autowired
    private JwtTokenRepository jwtTokenRepository;

    @Qualifier("CustomUserDetailsService")
    @Autowired
    private UserDetailsService userDetailsService;

    @PostConstruct
    protected void init() {
        secretKey = Base64.getEncoder().encodeToString(secretKey.getBytes());
    }

    public String createToken(String username, List<String> roles) {
        Claims claims = Jwts.claims().setSubject(username);
        claims.put(AUTH, roles);

        Date now = new Date();
        Date validity = new Date(now.getTime() + VALIDITY_IN_MILLISECONDS);
        String token = Jwts.builder()
                .setClaims(claims)
                .setIssuedAt(now)
                .setExpiration(validity)
                .signWith(SignatureAlgorithm.HS256, secretKey)
                .compact();

        jwtTokenRepository.save(new JwtToken(token));

        return token;
    }

    String resolveToken(HttpServletRequest req) {
        return req.getHeader(AUTHORIZATION);
    }

    public boolean validateToken(String token) throws JwtException, IllegalArgumentException {
        Jwts.parser().setSigningKey(secretKey).parseClaimsJws(token);
        return true;
    }

    boolean isTokenPresentInDB(String token) {
        return jwtTokenRepository.findById(token).isPresent();
    }

    public List<String> getRoleList(String token) {
        return (List<String>) Jwts.parser()
                .setSigningKey(secretKey)
                .parseClaimsJws(token)
                .getBody()
                .get(AUTH);
    }

    public String getUsername(String token) {
        return Jwts.parser().setSigningKey(secretKey).parseClaimsJws(token).getBody().getSubject();
    }

    Authentication getAuthentication(String token) {
        UserDetails userDetails = userDetailsService.loadUserByUsername(getUsername(token));
        return new UsernamePasswordAuthenticationToken(userDetails, userDetails.getPassword(), userDetails.getAuthorities());
    }
}