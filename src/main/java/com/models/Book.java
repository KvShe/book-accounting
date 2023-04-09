package com.models;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Book {
    private int book_id;
    private String author;
    private String name;
    private int age;

    @Override
    public String toString() {
        return name;
    }
}
