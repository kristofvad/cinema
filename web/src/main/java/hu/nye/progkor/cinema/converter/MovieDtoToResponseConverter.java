package hu.nye.progkor.cinema.converter;

import hu.nye.progkor.cinema.model.dto.MovieDTO;
import hu.nye.progkor.cinema.model.response.MovieResponse;
import lombok.NonNull;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public class MovieDtoToResponseConverter implements Converter<MovieDTO, MovieResponse> {

    @Override
    public MovieResponse convert(@NonNull final MovieDTO source) {
        log.info("Convert MovieDTO:{} to MovieResponse.", source);
        return new MovieResponse(
                source.id(),
                source.name(),
                source.releaseYear(),
                source.time(),
                source.movieType(),
                source.description()
        );
    }
}

