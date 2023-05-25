package ru.otus.spring.domain;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;
import java.util.List;

@Entity
@Table(name = "diets_tbl")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Diet {
    @Id
    @Column(name = "diet_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "diet_date")
    private Date date;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private ApplicationUser user;

    @OneToMany(orphanRemoval = true, cascade = CascadeType.ALL)
    @JoinColumn(name = "diet_id")
    private List<Meal> meals;
}
