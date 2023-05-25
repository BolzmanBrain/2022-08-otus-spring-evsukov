package ru.otus.spring.domain;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "meal_types_tbl")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class MealType {
    @Id
    @Column(name = "meal_type_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "name")
    private String name;
}
