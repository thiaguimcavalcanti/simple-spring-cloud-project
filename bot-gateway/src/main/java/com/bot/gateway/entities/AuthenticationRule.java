package com.bot.gateway.entities;

import com.bot.gateway.enums.AuthenticationRuleEnum;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Data
@EqualsAndHashCode(callSuper = true)
@NoArgsConstructor
@Entity
@Table(name = "AuthenticationRule")
public class AuthenticationRule extends AbstractLongIDEntity {

    @Enumerated(EnumType.STRING)
    @Column(name = "Rule", nullable = false)
    private AuthenticationRuleEnum rule;

    public AuthenticationRule (AuthenticationRuleEnum rule) {
        this.rule = rule;
    }
}
