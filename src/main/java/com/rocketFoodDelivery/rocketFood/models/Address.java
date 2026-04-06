// package declaration
package com.rocketFoodDelivery.rocketFood.models;

// Lombok imports
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

// JPA and validation imports
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;

// Hibernate imports
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

// Java imports
import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name = "addresses")
public class Address {
    // todo: Review and complete the Address class implementation.
    @Id
    @GeneratedValue( strategy = GenerationType.IDENTITY)
    private int id;

    @NotNull
    private String streetAddress;

    @NotNull
    private String city;

    @NotNull
    private String postalCode;

    @CreationTimestamp
    private LocalDateTime createdOn;

    @UpdateTimestamp
    private LocalDateTime updateOn;
}
