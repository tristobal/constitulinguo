package com.wakul.constitulinguo.controller;

import com.wakul.constitulinguo.domain.Topic;
import com.wakul.constitulinguo.repository.TopicRepository;
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
public class TopicController {

    private TopicRepository repository;

    @Autowired
    public TopicController(TopicRepository repository) {
        this.repository = repository;
    }

    @GetMapping("/topics")
    public Flux<Topic> getAllQuestions() {
        return  repository.findAll();
    }

    @PostMapping("/topics")
    public Mono<Topic> createQuestion(@Valid @RequestBody Topic topic) {
        return repository.save(topic);
    }

    @GetMapping("/topics/{id}")
    public Mono<ResponseEntity<Topic>> getQuestionById(@PathVariable(value = "id") String topicId) {
        return repository.findById(topicId)
                .map(ResponseEntity::ok)
                .defaultIfEmpty(ResponseEntity.notFound().build());
    }

    @PutMapping("/topics/{id}")
    public Mono<ResponseEntity<Topic>> updateQuestion(@PathVariable(value = "id") String topicId,
                                                         @Valid @RequestBody Topic topic) {
        return repository.findById(topicId)
                .flatMap(existingTopic -> {
                    existingTopic.setName(topic.getName());
                    return repository.save(existingTopic);
                })
                .map(updatedTopic -> new ResponseEntity<>(updatedTopic, HttpStatus.OK))
                .defaultIfEmpty(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    @DeleteMapping("/topics/{id}")
    public Mono<ResponseEntity<Void>> deleteQuestion(@PathVariable(value = "id") String topicId) {

        return repository.findById(topicId)
                .flatMap(existingTopic -> repository.delete(existingTopic)
                        .then(Mono.just(new ResponseEntity<Void>(HttpStatus.OK))))
                .defaultIfEmpty(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }
}
