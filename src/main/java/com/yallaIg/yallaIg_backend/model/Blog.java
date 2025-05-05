package com.yallaIg.yallaIg_backend.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "blogs")
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class Blog extends Auditable{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "blog_id")
    private Integer blogId;

    @Column(name = "title")
    private String title;

    @Column(name = "content")
    private String content ;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "file_id")
    private File blogFile;

    @ManyToOne
    @JoinColumn(name = "blog_category",referencedColumnName = "subject_id")
    private Subject blogSubjectCategory;
}
