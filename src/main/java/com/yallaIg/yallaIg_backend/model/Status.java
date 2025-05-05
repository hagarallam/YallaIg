package com.yallaIg.yallaIg_backend.model;

import com.yallaIg.yallaIg_backend.enums.ExamSessionEnum;
import com.yallaIg.yallaIg_backend.enums.StatusEnum;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "statuses")
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class Status {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "status_id")
    private Integer statusId;

    @Enumerated(EnumType.STRING)
    @Column(name = "status_name")
    private StatusEnum statusEnum;
}
