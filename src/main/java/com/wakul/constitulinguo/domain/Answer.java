package com.wakul.constitulinguo.domain;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class Answer {

    public String id;
    public String text;
    public Boolean isCorrect;
}
