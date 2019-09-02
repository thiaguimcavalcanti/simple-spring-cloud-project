package com.bot.exchanges.commons.entities;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.MappedSuperclass;

@Getter
@Setter
@EqualsAndHashCode(callSuper = false)
@MappedSuperclass
public abstract class AbstractStringIDEntity extends AbstractEntity<String> {
    @Id
    @Column(name = "id")
    @GenericGenerator(name = "assigned-sequence", strategy = "com.bot.exchanges.commons.entities.GenerateUUIDIdentifier")
    @GeneratedValue(generator = "assigned-sequence", strategy = GenerationType.IDENTITY)
    private String id;
}