package com.bot.gateway.entities;

import lombok.*;
import org.springframework.security.core.GrantedAuthority;

import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import java.util.Objects;

@Getter
@Setter
@Entity
@Table(name = "User_AuthenticationRule", uniqueConstraints = {
        @javax.persistence.UniqueConstraint(columnNames = {"User_ID", "AuthenticationRule_ID"})})
public class UserAuthenticationRule extends AbstractLongIDEntity implements GrantedAuthority {

    private static final long serialVersionUID = -3460869128483074085L;

    @ToString.Exclude
    @JoinColumn(name = "User_ID", nullable = false, referencedColumnName = "id")
    @ManyToOne(fetch = javax.persistence.FetchType.LAZY)
    private User user;

    /**
     * AuthenticationRule
     */
    @ToString.Exclude
    @JoinColumn(name = "AuthenticationRule_ID", nullable = false, referencedColumnName = "id")
    @ManyToOne(fetch = javax.persistence.FetchType.LAZY)
    private AuthenticationRule authenticationRule;

    @Override
    public String getAuthority() {
        return authenticationRule.getRule() != null ? authenticationRule.getRule().name() : null;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof UserAuthenticationRule)) return false;
        if (!super.equals(o)) return false;
        UserAuthenticationRule that = (UserAuthenticationRule) o;
        return getUser().equals(that.getUser()) &&
                getAuthenticationRule().equals(that.getAuthenticationRule());
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode());
    }
}
