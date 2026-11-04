package com.sky.cloudshop_api;

import lombok.Data;

@Data //equiv to all these annotation: Getter, Setter, RequiredArgsConstructor, ToString, EqualsAndHashCode
public class User {
    private Long id;
    private String firstName;
    private String lastName;
}
