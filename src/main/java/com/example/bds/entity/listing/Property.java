package com.example.bds.entity.listing;

import com.example.bds.entity.BasedEntity;
import jakarta.persistence.*;
import lombok.*;

import java.util.Set;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Table(name = "property")
public class Property extends BasedEntity {

    @ManyToOne
    @JoinColumn(name="category_id")
    private Category category;

    @Column(name = "code_name")
    private String codeName;

    @Column(name = "description")
    private String description;




    // @OneToMany(mappedBy = "property")
    // private Set<AttributeDefinition>   attributeDefinitions  ;





}
