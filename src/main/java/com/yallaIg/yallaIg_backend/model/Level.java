package com.yallaIg.yallaIg_backend.model;

import com.yallaIg.yallaIg_backend.enums.LevelEnum;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "levels")
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class Level {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "level_id")
    private Integer levelId;

    @Enumerated(EnumType.STRING)
    @Column(name = "level_name")
    private LevelEnum levelEnum;
}
