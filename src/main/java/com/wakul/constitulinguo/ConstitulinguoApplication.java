package com.wakul.constitulinguo;

import com.wakul.constitulinguo.domain.Alternative;
import com.wakul.constitulinguo.domain.Answer;
import com.wakul.constitulinguo.domain.Question;
import com.wakul.constitulinguo.domain.Topic;
import com.wakul.constitulinguo.repository.TopicRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Profile;
import reactor.core.publisher.Flux;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

@SpringBootApplication
public class ConstitulinguoApplication {

	private Logger log = LoggerFactory.getLogger(this.getClass());

	public static void main(String[] args) {
		SpringApplication.run(ConstitulinguoApplication.class, args);
	}

	@Bean
	@Profile("dev")
	CommandLineRunner start(TopicRepository repository) {
		return args -> {
			List<Alternative> alternatives = Arrays.asList(
					new Alternative(1, "sed do eiusmod tempor incididunt"),
					new Alternative(2, "ut labore et dolore magna aliqua")
			);
			Answer answer = new Answer(1, "sed do eiusmod tempor incididunt");
			Question question = new Question(1, "Lorem ipsum dolor sit amet, consectetur adipiscing elit", alternatives, answer);
			Topic topic = new Topic("1", "Primarius", Collections.singletonList(question));

			List<Alternative> alternatives2 = Arrays.asList(
				new Alternative(1, " Duis aute irure dolor in reprehenderit"),
				new Alternative(2, "in voluptate velit esse cillum dolore"),
				new Alternative(3, "eu fugiat nulla pariatur")
			);
			Answer answer2 = new Answer(3, "eu fugiat nulla pariatur");
			Question question2 = new Question(2, "Ut enim ad minim veniam, quis nostrud exercitation ullamco laboris nisi ut aliquip ex ea commodo consequat", alternatives2, answer2);
			Topic topic2 = new Topic("2", "Secundus", Collections.singletonList(question2));

			Flux.just(topic, topic2)
					.flatMap(repository::save)
					.subscribe(t -> log.info("topic: {}", t));
		};
	}
}
