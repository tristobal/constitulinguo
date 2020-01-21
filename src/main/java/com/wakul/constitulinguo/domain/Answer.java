package com.wakul.constitulinguo.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Answer {

    public String id;
    public String text;
    public Boolean isCorrect;
}
