package com.movies.movies_api.entitites;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;

import java.util.List;

@Entity
public class RecommendationsEntity {

    @Id
    private String userId;
    private List<Integer> movieRecommendations;
}
