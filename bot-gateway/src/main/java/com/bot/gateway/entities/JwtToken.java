package com.bot.gateway.entities;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Data
@NoArgsConstructor
@Entity
@Table(name = "JwtToken")
public class JwtToken {

    @Id
    private String token;

    public JwtToken(String token) {
        this.token = token;
    }
}
