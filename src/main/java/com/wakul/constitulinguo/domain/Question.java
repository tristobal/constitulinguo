package com.wakul.constitulinguo.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Question {

    @Id
    public Integer id;

    @NotBlank
    public String description;

    @NotEmpty
    public List<Alternative> alternatives;

    @NotBlank
    public Answer answer;

}
