package com.bot.exchanges.commons.entities;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import java.util.Set;

@Getter
@Setter
@NoArgsConstructor
@EqualsAndHashCode(callSuper = true)
@Entity
@Table(name = "Product")
public class Product extends AbstractStringIDEntity {

    @Column(name = "ImageUrl")
    private String imageUrl;

    @Column(name = "Url")
    private String url;

    @Column(name = "Name")
    private String name;

    @Column(name = "FullName")
    private String fullName;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "product")
    private Set<ExchangeProduct> exchangeProducts;

    public Product(String id) {
        setId(id);
    }
}
