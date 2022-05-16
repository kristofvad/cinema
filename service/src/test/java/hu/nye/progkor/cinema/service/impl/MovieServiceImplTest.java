package hu.nye.progkor.cinema.service.impl;

import hu.nye.progkor.cinema.model.Movie;
import hu.nye.progkor.cinema.model.MovieType;
import hu.nye.progkor.cinema.model.dto.MovieDTO;
import hu.nye.progkor.cinema.model.exception.NotFoundException;
import hu.nye.progkor.cinema.repository.MovieRepository;
import hu.nye.progkor.cinema.service.MovieService;
import org.hamcrest.Matchers;
import org.hamcrest.collection.IsCollectionWithSize;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.core.convert.converter.Converter;
import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.verify;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

public class MovieServiceImplTest {

    private static final Long MOVIE_ID = 22L;
    private static final String MOVIE_NAME = "Lion King";
    private static final int RELEASE_YEAR = 1999;
    private static final int MOVIE_TIME = 145;
    private static final MovieType MOVIE_SUGGESTED_MOVIE_TYPE = MovieType.MOVIE_2D;
    private static final String MOVIE_DESCRIPTION = "25 kg krumpli.";
    private static final Movie MOVIE = new Movie(MOVIE_ID, MOVIE_NAME, RELEASE_YEAR, MOVIE_TIME, MOVIE_SUGGESTED_MOVIE_TYPE, MOVIE_DESCRIPTION);
    private static final Movie UNSAVED_MOVIE = new Movie(null, MOVIE_NAME, RELEASE_YEAR, MOVIE_TIME, MOVIE_SUGGESTED_MOVIE_TYPE, MOVIE_DESCRIPTION);
    private static final MovieDTO MOVIE_DTO = new MovieDTO(MOVIE_ID, MOVIE_NAME, RELEASE_YEAR, MOVIE_TIME, MOVIE_SUGGESTED_MOVIE_TYPE, MOVIE_DESCRIPTION);
    private static final MovieDTO UNSAVED_MOVIE_DTO = new MovieDTO(null, MOVIE_NAME, RELEASE_YEAR, MOVIE_TIME, MOVIE_SUGGESTED_MOVIE_TYPE, MOVIE_DESCRIPTION);
    private static final List<Movie> MOVIES = Arrays.asList(MOVIE, MOVIE);
    private static final List<MovieDTO> DTO_MOVIES = Arrays.asList(MOVIE_DTO, MOVIE_DTO);
    private static final String MOVIE_NOT_FOUND_EXCEPTION_ERROR_MESSAGE = "There is no Movie with ID:" + MOVIE_ID;
    private static final String MOVIE_ILLEGAL_ARGUMENT_EXCEPTION_ERROR_MESSAGE = "Provided parameter is invalid: null";

    @Mock
    private MovieRepository movieRepository;
    @Mock
    private Converter<MovieDTO, Movie> movieDtoToEntityConverter;
    @Mock
    private Converter<Movie, MovieDTO> movieEntityToDtoConverter;

    private MovieService underTest;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        underTest = new MovieServiceImpl(movieRepository, movieDtoToEntityConverter, movieEntityToDtoConverter);
    }

    @Test
    void getAllMoviesShouldReturnConvertedMoviesWhenThereWereSavedMovies() {
        // given
        given(movieRepository.findAll()).willReturn(MOVIES);
        given(movieEntityToDtoConverter.convert(MOVIE)).willReturn(MOVIE_DTO);
        // when
        final List<MovieDTO> actual = underTest.getAllMovies();
        // then
        assertThat(actual, Matchers.containsInAnyOrder(DTO_MOVIES.toArray()));
    }

    @Test
    void getAllMoviesShouldReturnEmptyListWhenThereWasNoAnySavedMovies() {
        // given
        given(movieRepository.findAll()).willReturn(Collections.emptyList());
        // when
        final List<MovieDTO> actual = underTest.getAllMovies();
        // then
        assertThat(actual, IsCollectionWithSize.hasSize(0));
    }

    @Test
    void getMovieShouldReturnConvertedMovieWhenGivenIDOfExistingMovie() {
        // given
        given(movieRepository.findById(MOVIE_ID)).willReturn(Optional.of(MOVIE));
        given(movieEntityToDtoConverter.convert(MOVIE)).willReturn(MOVIE_DTO);
        // when
        final MovieDTO actual = underTest.getMovie(MOVIE_ID);
        // then
        assertThat(actual, equalTo(MOVIE_DTO));
    }


    @Test
    void createMovieShouldThrowIllegalArgumentExceptionWhenGivenNullAsMovie() {
        // given
        // when - then
        final IllegalArgumentException actual = assertThrows(IllegalArgumentException.class, () -> underTest.createMovie(null));
        assertThat(actual.getMessage(), equalTo(MOVIE_ILLEGAL_ARGUMENT_EXCEPTION_ERROR_MESSAGE));
    }

    @Test
    void createMovieShouldReturnSavedMovieWhenGivenMovieDetails() {
        // given
        given(movieRepository.save(UNSAVED_MOVIE)).willReturn(MOVIE);
        given(movieEntityToDtoConverter.convert(MOVIE)).willReturn(MOVIE_DTO);
        given(movieDtoToEntityConverter.convert(UNSAVED_MOVIE_DTO)).willReturn(UNSAVED_MOVIE);
        // when
        final MovieDTO actual = underTest.createMovie(UNSAVED_MOVIE_DTO);
        // then
        assertThat(actual, equalTo(MOVIE_DTO));
    }

    @Test
    void updateMovieShouldThrowNotFoundExceptionWhenGivenIDOfNotExistingMovie() {
        // given
        given(movieRepository.findById(MOVIE_ID)).willReturn(Optional.empty());
        // when - then
        final NotFoundException actual = assertThrows(NotFoundException.class, () -> underTest.updateMovie(MOVIE_ID, MOVIE_DTO));
        assertThat(actual.getMessage(), equalTo(MOVIE_NOT_FOUND_EXCEPTION_ERROR_MESSAGE));
    }

    @Test
    void updateMovieShouldUpdateTheMovieWhenGivenIDOfExistingMovieAndMovieDetails() {
        // given
        final Movie iceCream = new Movie(MOVIE_ID, "Leszámolás kistokyoban", 1991, 150, MovieType.MOVIE_2D, "Nehéz dolog helyesen cselekedni akkor is, ha tudjuk, hogy kell.");
        given(movieRepository.findById(MOVIE_ID)).willReturn(Optional.of(iceCream));
        given(movieEntityToDtoConverter.convert(MOVIE)).willReturn(MOVIE_DTO);
        given(movieRepository.save(MOVIE)).willReturn(MOVIE);
        // when
        final MovieDTO actual = underTest.updateMovie(MOVIE_ID, MOVIE_DTO);
        // then
        verify(movieRepository).save(MOVIE);
        assertThat(actual, equalTo(MOVIE_DTO));
    }

    @Test
    void deleteMovieShouldDelegateDeleteByIdWhenGivenMovieID() {
        // given
        // when
        underTest.deleteMovie(MOVIE_ID);
        // then
        verify(movieRepository).deleteById(MOVIE_ID);
    }
}

