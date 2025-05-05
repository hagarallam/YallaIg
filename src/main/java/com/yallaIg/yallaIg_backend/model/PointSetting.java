package com.yallaIg.yallaIg_backend.model;

import com.yallaIg.yallaIg_backend.enums.PointSettingEnum;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;

@Entity
@Table(name = "points_settings")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class PointSetting implements Serializable {

    @Id
    @Enumerated(value = EnumType.STRING)
    @Column(name = "setting_name")
    private PointSettingEnum pointSettingEnum ;

    @Column(name = "point_count")
    private Integer pointCount;
}
