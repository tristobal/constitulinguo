package com.wakul.constitulinguo.controller;

import com.wakul.constitulinguo.domain.Question;
import com.wakul.constitulinguo.repository.QuestionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import javax.validation.Valid;

@RestController
public class QuestionController {

    private QuestionRepository repository;

    @Autowired
    public QuestionController(QuestionRepository repository) {
        this.repository = repository;
    }

    @GetMapping("/questions")
    public Flux<Question> getAllQuestions() {
        return  repository.findAll();
    }

    @PostMapping("/questions")
    public Mono<Question> createQuestion(@Valid @RequestBody Question Question) {
        return repository.save(Question);
    }

    @GetMapping("/questions/{id}")
    public Mono<ResponseEntity<Question>> getQuestionById(@PathVariable(value = "id") String QuestionId) {
        return repository.findById(QuestionId)
                .map(ResponseEntity::ok)
                .defaultIfEmpty(ResponseEntity.notFound().build());
    }

    @PutMapping("/questions/{id}")
    public Mono<ResponseEntity<Question>> updateQuestion(@PathVariable(value = "id") String QuestionId,
                                                         @Valid @RequestBody Question Question) {
        return repository.findById(QuestionId)
                .flatMap(existingQuestion -> {
                    existingQuestion.setDescription(Question.getDescription());
                    return repository.save(existingQuestion);
                })
                .map(updatedQuestion -> new ResponseEntity<>(updatedQuestion, HttpStatus.OK))
                .defaultIfEmpty(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    @DeleteMapping("/questions/{id}")
    public Mono<ResponseEntity<Void>> deleteQuestion(@PathVariable(value = "id") String QuestionId) {

        return repository.findById(QuestionId)
                .flatMap(existingQuestion -> repository.delete(existingQuestion)
                        .then(Mono.just(new ResponseEntity<Void>(HttpStatus.OK))))
                .defaultIfEmpty(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }
}
