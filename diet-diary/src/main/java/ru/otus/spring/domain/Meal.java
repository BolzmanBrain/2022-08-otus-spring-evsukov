package ru.otus.spring.domain;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;
import java.util.List;

@Entity
@Table(name = "meals_tbl")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Meal {
    @Id
    @Column(name = "meal_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "meal_timestamp")
    private Date timestamp;

    @OneToMany(orphanRemoval = true, cascade = CascadeType.ALL)
    @JoinColumn(name = "meal_id")
    private List<Course> courses;

    @ManyToOne
    @JoinColumn(name = "meal_type_id")
    private MealType mealType;
}
