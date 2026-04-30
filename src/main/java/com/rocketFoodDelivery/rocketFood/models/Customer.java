// package declaration
package com.rocketFoodDelivery.rocketFood.models;

// Lombok imports
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

// JPA and validation imports
import jakarta.persistence.*;
import jakarta.validation.constraints.Email;

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
@Table(name = "customers")
public class Customer {
    @Id
    @GeneratedValue( strategy = GenerationType.IDENTITY)
    private int id;

    @OneToOne
    @JoinColumn(name = "user_id", unique = true , nullable = false)
    private User user;

    @ManyToOne
    @JoinColumn(name = "address_id" , nullable = false)
    private Address address;

    @Column(nullable = false, columnDefinition = "boolean default true")
    private boolean active;

    @Column(nullable = false)
    private String phone;

    @Email
    private String email;

    @CreationTimestamp
    private LocalDateTime createdOn;

    @UpdateTimestamp
    private LocalDateTime updateOn;
}
