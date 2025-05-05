package com.yallaIg.yallaIg_backend.model;

import com.yallaIg.yallaIg_backend.enums.ReactableType;
import com.yallaIg.yallaIg_backend.enums.ReactionType;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


@Table(name = "reactions")
@Entity
@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
public class Reaction {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "reaction_id")
    private Integer reactionId;

    @Column(name = "reactable_id")
    private Integer reactableId;

    @Column(name = "reaction_type")
    @Enumerated(value = EnumType.STRING)
    private ReactionType reactionType;

    @Column(name = "reactable_type")
    @Enumerated(value = EnumType.STRING)
    private ReactableType reactableType;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

}
