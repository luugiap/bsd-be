package com.example.bds.entity.listing;

import com.example.bds.entity.BasedEntity;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "attribute_definitions")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class AttributeDefinition extends BasedEntity {

    @ManyToOne
    @JoinColumn(name = "property_type_id",  nullable = true)
    private Property property;

    @ManyToOne
    @JoinColumn(name = "attribute_id", nullable = true )
    private Attributes attribute;

    @Enumerated(EnumType.STRING )
    @Column(name = "storage_type", nullable = true)
    private StorageType storageType;

    @Enumerated(EnumType.STRING)
    @Column(name = "attribute_type", nullable = true)
    private AttributeType attributeType;

    @Column(name = "is_required", nullable = true)
    private Boolean required;

    @Column(name = "is_filtered", nullable = true)
    private Boolean filterable;

    @Column(name = "displayOrder", nullable = true)
    private Integer displayOrder;
}