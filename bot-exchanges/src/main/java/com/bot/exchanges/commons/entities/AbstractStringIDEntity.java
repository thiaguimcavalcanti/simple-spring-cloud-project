package com.bot.exchanges.commons.entities;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.MappedSuperclass;

@Data
@EqualsAndHashCode(callSuper = false)
@MappedSuperclass
@JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class, property = "id")
abstract class AbstractStringIDEntity extends AbstractEntity<String> {

    private static final long serialVersionUID = 4047222142270705758L;

    @Id
    @Column(name = "id")
    @GenericGenerator(name = "assigned-sequence", strategy = "com.bot.exchanges.commons.entities.GenerateUUIDIdentifier")
    @GeneratedValue(generator = "assigned-sequence", strategy = GenerationType.IDENTITY)
    private String id;
}