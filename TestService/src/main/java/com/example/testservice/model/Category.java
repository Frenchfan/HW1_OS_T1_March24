package com.example.testservice.model;

import lombok.*;


@Setter
@Getter
@AllArgsConstructor
@Builder
@ToString
public class Category {

    private Long id;
    private String name;

}
