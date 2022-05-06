package hu.nye.progkor.cinema.converter;

import lombok.extern.slf4j.Slf4j;
import org.springframework.core.convert.converter.Converter;
import hu.nye.progkor.cinema.model.Movie;
import hu.nye.progkor.cinema.model.dto.MovieDTO;
import lombok.NonNull;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public class MovieDtoToEntityConverter implements Converter<MovieDTO, Movie> {

    @Override
    public Movie convert(@NonNull final MovieDTO source) {
        log.info("Convert MovieDTO:{} to Movie.", source);
        return new Movie(source.id(),
                source.name(),
                source.releaseYear(),
                source.time(),
                source.movieType(),
                source.description()
        );
    }

}
