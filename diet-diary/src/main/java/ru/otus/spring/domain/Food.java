package ru.otus.spring.domain;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "food_tbl")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Food {
    @Id
    @Column(name = "food_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "name", unique = true)
    private String name;

    @Column(name = "calories")
    private Integer calories;

    @Column(name = "proteins_in_grams")
    private Integer proteinsInGrams;

    @Column(name = "fats_in_grams")
    private Integer fatsInGrams;

    @Column(name = "carbs_in_grams")
    private Integer carbsInGrams;

    @ManyToOne
    @JoinColumn(name = "nutrient_storage_type_id")
    private NutrientStorageType nutrientStorageType;

    @ManyToOne
    @JoinColumn(name = "food_category_id")
    private FoodCategory foodCategory;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private ApplicationUser user;
}
