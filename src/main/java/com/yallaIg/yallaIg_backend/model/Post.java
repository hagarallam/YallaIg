package com.yallaIg.yallaIg_backend.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.Where;

import java.util.List;

@Table(name = "posts")
@Entity
@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
public class Post extends Auditable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "post_id")
    private Integer postId ;

    @Column(name = "title")
    private String title;

    @Column(name = "content")
    private String content;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "file_id")
    private File postFile;

    @ManyToOne
    @JoinColumn(name = "approval_status",referencedColumnName = "status_id")
    private ApprovalStatus approvalStatus;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "created_by", referencedColumnName = "user_id",insertable=false, updatable=false)
    private User user;

    @OneToMany(mappedBy = "post",cascade = CascadeType.ALL)
    private List<Comment> comments;

    @OneToMany(mappedBy = "reactableId",cascade = CascadeType.REMOVE)
    @Where(clause = "reactable_type = 'POST'")
    private List<Reaction> reactions;

}
