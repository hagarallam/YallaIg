package com.yallaIg.yallaIg_backend.model;

import com.yallaIg.yallaIg_backend.enums.ResourcesCategoryEnum;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "resources")
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class Resource extends Auditable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "resource_id")
    private Integer resourceId;

    @Column(name = "title")
    private String title;

    @Column(name = "description")
    private String description ;

    @Column(name = "price")
    private Double price;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "file_id")
    private File resourceFile;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "image_id")
    private File resourceImage;


    @ManyToOne
    @JoinColumn(name = "resource_category",referencedColumnName = "category_id")
    private ResourcesCategory resourcesCategory;
}
