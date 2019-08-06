package com.bot.exchanges.commons.entities;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

@Getter
@Setter
@EqualsAndHashCode(callSuper = true)
@Entity
@Table(name = "Authentication")
public class Authentication extends AbstractStringIDEntity {

    @Column(name = "UserId")
    private String userId;

    @JoinColumn(name = "Exchange_ID", nullable = false, referencedColumnName = "id")
    @ManyToOne(fetch = FetchType.LAZY)
    private Exchange exchange;

    @Column(name = "Key")
    private String key;

    @Column(name = "Secret")
    private String secret;

    @NotNull
    @Column(name = "Active", nullable = false)
    private boolean active;
}
