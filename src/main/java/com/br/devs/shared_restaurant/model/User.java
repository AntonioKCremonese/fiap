package com.br.devs.shared_restaurant.model;

import com.br.devs.shared_restaurant.model.enums.UserTypeEnum;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.UpdateTimestamp;
import org.hibernate.annotations.UuidGenerator;

import java.time.OffsetDateTime;

@NoArgsConstructor
@Getter
@Setter
@Entity
@Table(name = "Users")
public class User {

    @Id
    @GeneratedValue
    @UuidGenerator
    @Column(name = "id", columnDefinition = "varchar(36)")
    private String id;

    @Column(name = "name")
    private String name;

    @Column(name = "email")
    private String mail;

    @Column(name = "login")
    private String login;

    @Column(name = "password")
    private String password;

    @UpdateTimestamp
    @Column(name = "last_update", columnDefinition = "datetime")
    private OffsetDateTime lastUpdate;

    @Enumerated(EnumType.STRING)
    @Column(name = "user_type")
    private UserTypeEnum userType;

    @OneToOne
    @JoinColumn(name = "address_id", referencedColumnName = "id")
    private Address address;

    public User(String name, String mail, String login, String password, UserTypeEnum userType) {
        this.name = name;
        this.mail = mail;
        this.login = login;
        this.password = password;
        this.userType = userType;
    }
}