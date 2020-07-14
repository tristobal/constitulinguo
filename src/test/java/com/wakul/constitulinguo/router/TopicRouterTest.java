package com.wakul.constitulinguo.router;

import com.wakul.constitulinguo.domain.Alternative;
import com.wakul.constitulinguo.domain.Answer;
import com.wakul.constitulinguo.domain.Question;
import com.wakul.constitulinguo.domain.Topic;
import com.wakul.constitulinguo.handler.TopicHandler;
import com.wakul.constitulinguo.repository.TopicRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.reactive.WebFluxTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.ApplicationContext;
import org.springframework.http.MediaType;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.reactive.server.WebTestClient;
import reactor.core.publisher.Mono;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@ExtendWith(SpringExtension.class)
@ContextConfiguration(classes = {TopicRouter.class, TopicHandler.class})
@WebFluxTest
class TopicRouterTest {

    @Autowired
    private ApplicationContext context;

    @MockBean
    private TopicRepository repository;

    private WebTestClient webTestClient;

    @BeforeEach
    public void setUp() {
        webTestClient = WebTestClient.bindToApplicationContext(context).build();
    }

    @Test
    public void testGetTopicById() {
        List<Alternative> alternatives = Arrays.asList(
                new Alternative(1, "Respuesta Verdadera"),
                new Alternative(2, "Respuesta Falsa")
        );
        Answer answer = new Answer(1, "Respuesta Verdadera");

        List<Question> questions = Collections.singletonList(new Question(1, "Pregunta", alternatives, answer));
        Topic topic = new Topic("1", "Tema", questions);

        Mono<Topic> topicMono = Mono.just(topic);
        when(repository.findById("1")).thenReturn(topicMono);

        webTestClient.get()
                .uri("/topics/1")
                .accept(MediaType.APPLICATION_JSON)
                .exchange()
                .expectStatus().isOk()
                .expectBody(Topic.class)
                .value(topicResponse -> {
                    assertThat(topicResponse.getId()).isEqualTo("1");
                    assertThat(topicResponse.getName()).isEqualTo("Tema");
                    assertThat(topicResponse.getQuestions()).isEqualTo(questions);
                });
    }

    @Test
    public void testCreateTopic() {
        List<Alternative> alternatives = Arrays.asList(
                new Alternative(1, "Respuesta Verdadera"),
                new Alternative(2, "Respuesta Falsa")
        );
        Answer answer = new Answer(1, "Respuesta Verdadera");

        List<Question> questions = Collections.singletonList(new Question(1, "Pregunta", alternatives, answer));
        Topic topic = new Topic("1", "Tema", questions);

        Mono<Topic> topicMono = Mono.just(topic);
        when(repository.save(any())).thenReturn(topicMono);

        webTestClient.post()
                .uri("/topics")
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .body(Mono.just(topicMono), Topic.class)
                .exchange()
                .expectStatus().isOk()
                .expectBody(Topic.class)
                .value(topicResponse -> {
                    assertThat(topicResponse.getId()).isEqualTo("1");
                    assertThat(topicResponse.getName()).isEqualTo("Tema");
                    assertThat(topicResponse.getQuestions()).isEqualTo(questions);
                });
    }
}
