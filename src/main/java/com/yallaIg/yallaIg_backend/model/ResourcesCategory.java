package com.yallaIg.yallaIg_backend.model;

import com.yallaIg.yallaIg_backend.enums.ResourcesCategoryEnum;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "resource_categories")
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class ResourcesCategory {


    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "category_id")
    private Integer categoryId;

    @Enumerated(EnumType.STRING)
    @Column(name = "category_name")
    private ResourcesCategoryEnum resourcesCategoryEnum;
}
