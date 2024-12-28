package com.movies.movies_api.repositories;

import com.movies.movies_api.entitites.MoviesEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface MoviesRepository extends JpaRepository<MoviesEntity, Long> {
    Optional<MoviesEntity> findByMovieId(int movieId);
}
