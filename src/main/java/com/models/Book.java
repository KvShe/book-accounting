package com.models;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
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
