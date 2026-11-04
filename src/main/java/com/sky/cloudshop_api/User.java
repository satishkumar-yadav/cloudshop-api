package com.sky.cloudshop_api;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.Data;
import lombok.NoArgsConstructor;

// import lombok.AllArgsConstructor;

@Data //equiv to all these annotation: Getter, Setter, RequiredArgsConstructor, ToString, EqualsAndHashCode
@NoArgsConstructor
//@AllArgsConstructor
@Entity
public class User {
    @Id 
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String firstName;
    private String lastName;
}
