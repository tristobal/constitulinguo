package com.wakul.constitulinguo.router;

import com.wakul.constitulinguo.handler.TopicHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.server.RouterFunction;
import org.springframework.web.reactive.function.server.RouterFunctions;
import org.springframework.web.reactive.function.server.ServerResponse;

import static org.springframework.http.MediaType.APPLICATION_JSON;
import static org.springframework.web.reactive.function.server.RequestPredicates.DELETE;
import static org.springframework.web.reactive.function.server.RequestPredicates.GET;
import static org.springframework.web.reactive.function.server.RequestPredicates.POST;
import static org.springframework.web.reactive.function.server.RequestPredicates.PUT;
import static org.springframework.web.reactive.function.server.RequestPredicates.accept;

@Configuration
public class TopicRouter {

    private TopicHandler handler;

    @Autowired
    public TopicRouter(TopicHandler handler) {
        this.handler = handler;
    }

    @Bean
    public RouterFunction<ServerResponse> composeRoutes() {
        return RouterFunctions.route(GET("/topics").and(accept(APPLICATION_JSON)), handler::findAll)
                .andRoute(GET("/topics/{id}").and(accept(APPLICATION_JSON)), handler::get)
                .andRoute(POST("/topics").and(accept(APPLICATION_JSON)), handler::save)
                .andRoute(PUT("/topics/{id}").and(accept(APPLICATION_JSON)), handler::put)
                .andRoute(DELETE("/topics/{id}").and(accept(APPLICATION_JSON)), handler::delete)
                .andRoute(GET("/get_question_right_answare").and(accept(APPLICATION_JSON)), handler::getQuestionRightAnswer)
                .andRoute(GET("/get_level_info").and(accept(APPLICATION_JSON)), handler::getLevelInfo)
                .andRoute(GET("/question_bank_url").and(accept(APPLICATION_JSON)), handler::getQuestionRightAnswer);
    }
}
