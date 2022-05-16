package hu.nye.progkor.cinema.converter;

import hu.nye.progkor.cinema.model.dto.MovieDTO;
import hu.nye.progkor.cinema.model.request.MovieRequest;
import lombok.NonNull;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

/**
 * Javadoc comment.
 */
@Component
@Slf4j
public class MovieRequestToDtoConverter implements Converter<MovieRequest, MovieDTO> {

    @Override
    public MovieDTO convert(@NonNull final MovieRequest source) {
        log.info("Convert MovieRequest:{} to MovieDTO.", source);
        return new MovieDTO(null,
                source.getName(),
                source.getReleaseYear(),
                source.getTime(),
                source.getMovieType(),
                source.getDescription()
        );
    }
}
