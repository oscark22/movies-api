package com.movies.movies_api.models;

public record Rating(String userId, int movieId, short rating) {}
