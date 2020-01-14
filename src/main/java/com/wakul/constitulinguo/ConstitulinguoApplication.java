package com.wakul.constitulinguo;

import com.wakul.constitulinguo.domain.Answer;
import com.wakul.constitulinguo.domain.Question;
import com.wakul.constitulinguo.repository.QuestionRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import reactor.core.publisher.Flux;

import java.util.Arrays;
import java.util.List;

@SpringBootApplication
public class ConstitulinguoApplication {

	private Logger log = LoggerFactory.getLogger(this.getClass());

	public static void main(String[] args) {
		SpringApplication.run(ConstitulinguoApplication.class, args);
	}

	@Bean
	CommandLineRunner start(QuestionRepository repository) {
		return args -> {
			List<Answer> answers = Arrays.asList(
				new Answer("1", "sed do eiusmod tempor incididunt", Boolean.TRUE),
				new Answer("2", "ut labore et dolore magna aliqua", Boolean.FALSE));
			List<Answer> answers2 = Arrays.asList(
				new Answer("3", " Duis aute irure dolor in reprehenderit", Boolean.FALSE),
				new Answer("4", "in voluptate velit esse cillum dolore", Boolean.FALSE),
				new Answer("5", "eu fugiat nulla pariatur", Boolean.TRUE));

			Flux.just(
				new Question("1", "Lorem ipsum dolor sit amet, consectetur adipiscing elit", answers),
				new Question("2", "Ut enim ad minim veniam, quis nostrud exercitation ullamco laboris nisi ut aliquip ex ea commodo consequat", answers2))
			.flatMap(repository::save)
			.subscribe(question -> log.info("question: {}", question));
		};
	}
}
