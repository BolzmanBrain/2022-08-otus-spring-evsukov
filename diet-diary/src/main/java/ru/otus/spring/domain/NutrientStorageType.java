package ru.otus.spring.domain;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "nutrient_storage_types_tbl")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class NutrientStorageType {
    public static String IN_100_GRAMS_CODE = "IN_100_GRAMS";
    public static String IN_SERVING = "IN_SERVING";

    @Id
    @Column(name = "nutrient_storage_type_id")
    private Long id;

    @Column(name = "code")
    private String code;

    @Column(name = "name")
    private String name;
}
