package com.wakul.constitulinguo.handler;

import com.wakul.constitulinguo.domain.Topic;
import com.wakul.constitulinguo.repository.TopicRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Mono;

import static org.springframework.http.MediaType.APPLICATION_JSON;
import static org.springframework.web.reactive.function.BodyInserters.fromPublisher;
import static org.springframework.web.reactive.function.server.ServerResponse.noContent;
import static org.springframework.web.reactive.function.server.ServerResponse.notFound;
import static org.springframework.web.reactive.function.server.ServerResponse.ok;

@Component
public class TopicHandler {

    private TopicRepository repository;

    @Autowired
    public TopicHandler(TopicRepository repository) {
        this.repository = repository;
    }

    public Mono<ServerResponse> get(ServerRequest request) {
        final String id = request.pathVariable("id");
        final Mono<Topic> topic = repository.findById(id);
        return topic.flatMap(t -> ok().contentType(APPLICATION_JSON).body(fromPublisher(topic, Topic.class)))
                .switchIfEmpty(notFound().build());
    }

    public Mono<ServerResponse> findAll(ServerRequest request) {
        return ok()
                .contentType(APPLICATION_JSON)
                .body(fromPublisher(repository.findAll(), Topic.class));
    }

    public Mono<ServerResponse> save(ServerRequest request) {
        final Mono<Topic> topic = request.bodyToMono(Topic.class);
        return ok()
                .contentType(APPLICATION_JSON)
                .body(fromPublisher(topic.flatMap(repository::save), Topic.class));
    }

    public Mono<ServerResponse> put(ServerRequest request) {
        final String id = request.pathVariable("id");
        final Mono<Topic> updatedTopic = request.bodyToMono(Topic.class);
        return repository
                .findById(id)
                .flatMap(t -> ok().contentType(APPLICATION_JSON).body(fromPublisher(updatedTopic.flatMap(repository::save), Topic.class)))
                .switchIfEmpty(notFound().build());
    }

    public Mono<ServerResponse> delete(ServerRequest request) {
        final String id = request.pathVariable("id");
        return repository.findById(id)
                .flatMap(t -> noContent().build(repository.delete(t)))
                .switchIfEmpty(notFound().build());
    }

}
