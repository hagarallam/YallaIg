package com.yallaIg.yallaIg_backend.model;

import com.yallaIg.yallaIg_backend.enums.ExamSessionEnum;
import com.yallaIg.yallaIg_backend.enums.SubjectEnum;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "exam_sessions")
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class ExamSession {


    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "exam_session_id")
    private Integer examSessionId;

    @Enumerated(EnumType.STRING)
    @Column(name = "exam_session_value")
    private ExamSessionEnum examSessionValue;
}
