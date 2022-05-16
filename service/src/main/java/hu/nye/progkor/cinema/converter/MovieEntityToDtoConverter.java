package hu.nye.progkor.cinema.converter;

import hu.nye.progkor.cinema.model.Movie;
import hu.nye.progkor.cinema.model.dto.MovieDTO;
import lombok.NonNull;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

/**
 * MovieEntityToDtoConverter.
 */
@Component
@Slf4j
public class MovieEntityToDtoConverter implements Converter<Movie, MovieDTO> {

    @Override
    public MovieDTO convert(@NonNull final Movie source) {
        log.info("Convert Movie:{} to MovieDTO.", source);
        return new MovieDTO(source.getId(),
                source.getName(),
                source.getReleaseYear(),
                source.getTime(),
                source.getSuggestedMovieType(),
                source.getDescription()
        );
    }
}
