package com.example.bds.entity.listing;

import com.example.bds.entity.BasedEntity;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Entity
@Table(name = "attribute")
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class Attributes extends BasedEntity {

    @Column(name = "code", nullable = false)
    private String code;

    @Column( name = "label", nullable = false)
    private String label;

    @Column( name = "description", nullable = false)
    private String description;


    @OneToMany(mappedBy = "attribute")
    private List<AttributeDefinition>  attributeDefinitions;



}