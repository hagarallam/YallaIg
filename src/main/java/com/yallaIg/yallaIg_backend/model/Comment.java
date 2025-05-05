package com.yallaIg.yallaIg_backend.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.Cascade;
import org.hibernate.annotations.Where;

import java.util.List;

@Entity
@Table(name = "comments")
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class Comment extends Auditable{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "comment_id")
    private Integer commentId ;

    @Column(name = "content")
    private String content;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "file_id")
    private File commentFile;

    @ManyToOne
    @JoinColumn(name = "approval_status",referencedColumnName = "status_id")
    private ApprovalStatus approvalStatus;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "created_by",referencedColumnName = "user_id",insertable = false,updatable = false)
    @Cascade(org.hibernate.annotations.CascadeType.REMOVE)
    private User user;

    @ManyToOne
    @JoinColumn(name = "post_id")
    @Cascade(org.hibernate.annotations.CascadeType.REMOVE)
    private Post post;

    @OneToMany(mappedBy = "reactableId", cascade = CascadeType.REMOVE)
    @Where(clause = "reactable_type = 'COMMENT'")
    private List<Reaction> reactions;
}
