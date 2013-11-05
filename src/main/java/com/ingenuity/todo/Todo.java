package com.ingenuity.todo;

import lombok.Data;
import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.NotEmpty;

@Data
public class Todo {

    private int id;

    @Length(max = 500)
    private String description;

    @NotEmpty
    @Length(max = 100)
    private String title;
}
