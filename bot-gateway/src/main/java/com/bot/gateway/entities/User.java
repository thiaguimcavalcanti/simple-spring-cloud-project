package com.bot.gateway.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.validator.constraints.Length;

import javax.persistence.*;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.util.Set;

@Getter
@Setter
@EqualsAndHashCode(callSuper = true)
@Entity
@Table(name = "User")
public class User extends AbstractLongIDEntity {

    private static final long serialVersionUID = -2851084191271890732L;

    @NotNull
    @Length(max = 120, message = "validator.length")
    @Column(name = "Email", length = 120, unique = true, nullable = false)
    @Email(message = "*Please provide a valid email")
    private String email;

    @JsonIgnore
    @Length(max = 200, message = "validator.length")
    @Column(name = "Password", length = 200)
    @NotEmpty(message = "*Please provide your password")
    private String password;

    @Length(max = 45, message = "validator.length")
    @Column(name = "UserName", length = 45)
    private String userName;

    /**
     * First name
     */
    @Length(max = 45, message = "validator.length")
    @Column(name = "FirstName", length = 45)
    private String firstName;

    /**
     * Last name
     */
    @Length(max = 100, message = "validator.length")
    @Column(name = "LastName", length = 100)
    private String lastName;

    @NotNull
    @Column(name = "isActive", nullable = false)
    private boolean active;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "user")
    private Set<UserAuthenticationRule> roles;
}
