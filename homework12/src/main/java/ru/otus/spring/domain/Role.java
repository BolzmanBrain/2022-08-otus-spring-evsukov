package ru.otus.spring.domain;

import lombok.Data;
import lombok.RequiredArgsConstructor;

import javax.persistence.*;

@Entity
@Table(name = "roles_tbl")
@Data
@RequiredArgsConstructor
public class Role {
    @Id
    @Column(name = "id_role")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private final Long id;

    @Column(name = "name", unique = true)
    private final String name;

    public Role() {
        this.id = null;
        this.name = null;
    }
}
