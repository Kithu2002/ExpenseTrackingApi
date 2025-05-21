package com.example.MovieReservationApplication.Repository;

import com.example.MovieReservationApplication.Entity.Movie;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface MovieRepository extends JpaRepository<Movie, Integer> {
    Optional<List<Movie>> findByGenre(String genre);

    Optional<Movie> findByTitle(String title);

    Optional<Movie> findById(Long id);
}

