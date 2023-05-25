package ru.otus.spring.domain;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "courses_tbl")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Course {
    @Id
    @Column(name = "course_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "quantity")
    private Double quantity;

    @ManyToOne
    @JoinColumn(name = "food_id")
    private Food food;
}
