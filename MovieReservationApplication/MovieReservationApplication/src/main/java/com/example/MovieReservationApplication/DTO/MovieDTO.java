package com.example.MovieReservationApplication.DTO;

import java.time.LocalDate;

import lombok.Data;


@Data
public class MovieDTO {

    private String title;

    private String genre;

    private String description;

    private Integer duration;

    private LocalDate releaseDate;
    private  String language;



}
