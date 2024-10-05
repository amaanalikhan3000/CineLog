package com.cineLog.cineLog.entity;

import com.cineLog.cineLog.controller.MovieEntityController;
import lombok.Getter;
import lombok.Setter;
import org.bson.types.ObjectId;

import java.util.Date;
import java.util.List;

@Getter
@Setter
public class MovieEntity extends MovieEntityController {

    private ObjectId id;
    private String title;
    private Date releaseDate;
    private List<String> genres;
    private List<String> tags;

}
