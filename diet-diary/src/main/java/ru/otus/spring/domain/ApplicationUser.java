package ru.otus.spring.domain;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "users_tbl")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class ApplicationUser {
    @Id
    @Column(name = "user_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "login")
    private String login;

    @Column(name = "password")
    private String password;

    @ManyToOne
    @JoinColumn(name = "role_id")
    private Role role;

    @Override
    public String toString() {
        return String.format("ApplicationUser(id=%d, login=%s, password=[HIDDEN], role=%s)",
                id, login, role.toString());
    }
}
