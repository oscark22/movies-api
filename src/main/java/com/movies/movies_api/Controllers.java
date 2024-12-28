package com.movies.movies_api;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.movies.movies_api.entitites.MoviesEntity;
import com.movies.movies_api.entitites.RecommendationsEntity;
import com.movies.movies_api.models.Click;
import com.movies.movies_api.models.Rating;
import com.movies.movies_api.repositories.MoviesRepository;
import com.movies.movies_api.repositories.RecommendationsRepository;
import jakarta.persistence.EntityExistsException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping("/movies-api")
@Slf4j
public class Controllers {

    @Autowired
    private KafkaService kafkaService;

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private RecommendationsRepository recommendationsRepository;

    @Autowired
    private MoviesRepository moviesRepository;

    @Value("${kafka.topic.clicks}")
    private String kafkaClicksTopic;

    @Value("${kafka.topic.ratings}")
    private String kafkaRatingsTopic;

    @GetMapping("/")
    public String home() {
        return "test";
    }

    @PostMapping("/clicks")
    public Click clickEvent(@RequestBody Click click) throws JsonProcessingException {
        log.info("Received click event: {}", click);
        String clickJson = objectMapper.writeValueAsString(click);
        kafkaService.sendMessage(kafkaClicksTopic, clickJson);
        return click;
    }

    @PostMapping("/ratings")
    public Rating ratingEvent(@RequestBody Rating rating) throws JsonProcessingException {
        log.info("Received rating event: {}", rating);
        String clickJson = objectMapper.writeValueAsString(rating);
        kafkaService.sendMessage(kafkaRatingsTopic, clickJson);
        return rating;
    }

    @PostMapping("/movies")
    public ResponseEntity<MoviesEntity> ratingEvent(@RequestBody MoviesEntity movie) {
        log.info("Received movie event: {}", movie);
        Optional<MoviesEntity> existingEntity = moviesRepository.findByMovieId(movie.getMovieId());

        if (existingEntity.isPresent()) {
            throw new EntityExistsException("Entity with given id already exists");
        }
        moviesRepository.save(movie);
        return ResponseEntity.ok(movie);
    }

    @GetMapping("/recommendations/{user-id}")
    public RecommendationsEntity getRecommendations(@PathVariable("user-id") String userId) {
        log.info("Received get recommendation event for user: {}", userId);
        return recommendationsRepository.findByUserId(userId);
    }
}
