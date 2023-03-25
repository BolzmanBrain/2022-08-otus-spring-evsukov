package ru.otus.spring.domain;

import lombok.Data;
import lombok.RequiredArgsConstructor;

import javax.persistence.*;

@Entity
@Table(name = "users_tbl")
@Data
@RequiredArgsConstructor
public class ApplicationUser {
    @Id
    @Column(name = "id_user")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private final Long id;

    @Column(name = "username", unique = true)
    private final String username;

    @Column(name = "password")
    private final String password;

    @ManyToOne
    @JoinColumn(name = "id_role")
    private final Role role;

    public ApplicationUser() {
        this.id = null;
        this.username = null;
        this.password = null;
        this.role = null;
    }
}
