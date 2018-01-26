package com.techragesh.dbservice.domain;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Data
@NoArgsConstructor
@Entity
@Table(name = "quotes")
public class Quote {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id")
    private Integer id;
    @Column(name = "user_name")
    private String userName;
    @Column(name = "quote")
    private String quote;


    public Quote(String userName, String quote) {
        this.userName = userName;
        this.quote = quote;
    }
}
