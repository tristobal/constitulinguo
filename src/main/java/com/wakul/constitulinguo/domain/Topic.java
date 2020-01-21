package com.wakul.constitulinguo.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import java.util.List;

@Data
@Document
@AllArgsConstructor
@NoArgsConstructor
public class Topic {

    @Id
    private String id;

    @NotBlank
    private String name;

    @NotEmpty
    public List<Question> questions;

}
