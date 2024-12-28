package com.movies.movies_api.repositories;

import com.movies.movies_api.entitites.RecommendationsEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RecommendationsRepository extends JpaRepository<RecommendationsEntity, Long> {
    RecommendationsEntity findByUserId(String userId);
}
