package hu.nye.progkor.cinema.model.request;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonPOJOBuilder;
import hu.nye.progkor.cinema.model.MovieType;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.ToString;

@Builder
@EqualsAndHashCode
@Getter
@JsonDeserialize(builder = MovieRequest.MovieRequestBuilder.class)
@ToString
public class MovieRequest {

    private final String name;
    private final Integer releaseYear;
    private final Integer time;
    private final MovieType movieType;
    private final String description;

    @JsonPOJOBuilder(withPrefix = "")
    public static class MovieRequestBuilder {

    }
}
