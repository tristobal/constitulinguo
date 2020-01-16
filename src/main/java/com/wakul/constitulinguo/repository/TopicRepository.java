package com.wakul.constitulinguo.repository;

import com.wakul.constitulinguo.domain.Topic;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TopicRepository extends ReactiveMongoRepository<Topic, String> {
}
