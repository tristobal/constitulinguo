package com.wakul.constitulinguo.repository;

import com.wakul.constitulinguo.domain.Question;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface QuestionRepository extends ReactiveMongoRepository<Question, String> {
}
