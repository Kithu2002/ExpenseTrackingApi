package com.example.MovieReservationApplication.Controller;


import com.example.MovieReservationApplication.DTO.MovieDTO;
import com.example.MovieReservationApplication.Entity.Movie;
import com.example.MovieReservationApplication.Service.MovieService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/movies")
public class MovieController {

    @Autowired
    private MovieService movieService;



    @PostMapping("/addmovie")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Movie> addMovie(@RequestBody MovieDTO movieDTO) {
        return  ResponseEntity.ok(movieService.addMovie(movieDTO));
    }

    @GetMapping("/getallmovies")
    public ResponseEntity<List<Movie>> getAllMovies(){
        return ResponseEntity.ok(movieService.getAllMovies());
    }
    @GetMapping("/getMoviebygenre")
    public ResponseEntity<List<Movie>> getMovieByGenre(@RequestParam String genre) {
        return  ResponseEntity.ok(movieService.getMoviesByGenre(genre));
    }

    @GetMapping("/getmoviebytitle")
    public ResponseEntity<Movie>  getMovieByTitle(@RequestParam String  title) {
        return  ResponseEntity.ok(movieService.getMovieByTitle(title));
    }



    @PutMapping("/updatemovie/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Movie> updateMovie(@PathVariable Long id, @RequestBody MovieDTO movieDTO) {
        Movie updatedMovie = movieService.updateMovie(id, movieDTO);
        return ResponseEntity.ok(updatedMovie);
    }

    @DeleteMapping  ("deletemovie/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Void> deleteMovie(@PathVariable Long id) {
        movieService.deleteMovie(id);
        return ResponseEntity.ok().build();
    }






}
