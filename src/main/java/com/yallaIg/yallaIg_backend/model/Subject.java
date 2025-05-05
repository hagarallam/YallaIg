package com.yallaIg.yallaIg_backend.model;

import com.yallaIg.yallaIg_backend.enums.SubjectEnum;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "subjects")
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class Subject {


    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "subject_id")
    private Integer subjectId;

    @Enumerated(EnumType.STRING)
    @Column(name = "subject_name")
    private SubjectEnum subjectEnum;
}
