package com.example.bds.entity.listing;

import com.example.bds.entity.BasedEntity;
import jakarta.persistence.*;
import lombok.*;

import java.util.List;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Table(name = "category")
public class Category extends BasedEntity {

    @Column(name = "code_name")
    private String codeName;

    @Column(name = "description")
    private String description;





    @OneToMany(cascade = CascadeType.ALL, mappedBy = "category")

    private List<Property> properties;

}
