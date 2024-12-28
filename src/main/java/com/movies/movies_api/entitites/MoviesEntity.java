package com.movies.movies_api.entitites;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Data;

@Data
@Entity
@Table(name = "movies")
public class MoviesEntity {

    @Id
    private int movieId;
    private String movieName;
}
