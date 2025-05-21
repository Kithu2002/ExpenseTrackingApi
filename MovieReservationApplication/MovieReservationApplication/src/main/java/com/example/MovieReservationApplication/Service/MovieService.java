package com.example.MovieReservationApplication.Service;


import com.example.MovieReservationApplication.DTO.MovieDTO;
import com.example.MovieReservationApplication.Entity.Movie;
import com.example.MovieReservationApplication.Repository.MovieRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class MovieService {

    @Autowired
    private MovieRepository movieRepository;


    public Movie addMovie(MovieDTO movieDTO) {
        Movie movie = new Movie();
        movie.setTitle(movieDTO.getTitle());
        movie.setGenre(movieDTO.getGenre());
        movie.setReleaseDate(movieDTO.getReleaseDate());
        movie.setDuration(movieDTO.getDuration());
        return movieRepository.save(movie);
    }

    public List<Movie> getAllMovies() {
        return List.of();
    }

    public List<Movie> getMoviesByGenre(String genre) {
        Optional<List<Movie>> listOfMovie =movieRepository.findByGenre(genre);
        if(listOfMovie.isPresent()) {
            return listOfMovie.get();
        }else throw  new RuntimeException("There is no such genre");

    }

    public   Movie getMovieByTitle(String title) {
        Optional<Movie> movieBox =movieRepository.findByTitle(title);
        if(movieBox.isPresent()) {
            return movieBox.get();
        }else throw  new RuntimeException("There is no such title");
    }

    public ResponseEntity<Movie> updateMovie(Long id, MovieDTO movieDTO) {
         Movie movie = movieRepository.findById(id)
    }

    public void deleteMovie(Long id) {
    }
}
