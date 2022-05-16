package hu.nye.progkor.cinema.controller;

import java.util.List;
import javax.validation.ConstraintViolationException;

import hu.nye.progkor.cinema.model.dto.MovieDTO;
import hu.nye.progkor.cinema.model.exception.NotFoundException;
import hu.nye.progkor.cinema.model.request.MovieRequest;
import hu.nye.progkor.cinema.model.response.MovieResponse;
import hu.nye.progkor.cinema.service.MovieService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.convert.converter.Converter;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

/**
 * Javadoc comment.
 */
@Controller
@RequiredArgsConstructor
@RequestMapping("/movies")
@Slf4j
public class MovieController {

    private static final String SUCCESS_ATTRIBUTE = "success";
    private static final String MOVIE_ATTRIBUTE = "movie";
    private static final String MESSAGE_ATTRIBUTE = "message";
    public static final String REDIRECT_MOVIES_LIST_HTML_ENDPOINT = "redirect:/movies/list.html";

    private final MovieService movieService;
    private final Converter<MovieDTO, MovieResponse> movieDtoToResponseConverter;
    private final Converter<MovieRequest, MovieDTO> movieRequestMovieDTOConverter;

    /**
     * Javadoc comment.
     */
    @GetMapping(path = "/create.html")
    public String movieCreateForm(final Model model) {
        log.info("Visit Movie create form page.");
        return "movies/create";
    }

    /**
     * Javadoc comment.
     */
    @PostMapping(path = "/create", consumes = MediaType.APPLICATION_FORM_URLENCODED_VALUE)
    public String createMovie(final Model model,
                                final MovieRequest movieRequest) {
        log.info("Create Movie with name:{}.", movieRequest.getName());
        final MovieResponse movie = movieDtoToResponseConverter.convert(
                movieService.createMovie(
                        movieRequestMovieDTOConverter.convert(movieRequest)
                )
        );
        model.addAttribute(SUCCESS_ATTRIBUTE, true);
        model.addAttribute(MOVIE_ATTRIBUTE, movie);
        return "movies/create.html";
    }

    /**
     * Javadoc comment.
     */
    @GetMapping(path = "/list.html")
    public String getmovies(final Model model) {
        log.info("Retrieve all Movies.");
        final List<MovieResponse> movies = movieService.getAllMovies().stream()
                .map(movieDtoToResponseConverter::convert)
                .toList();
        model.addAttribute("movies", movies);
        return "movies/list";
    }

    /**
     * Javadoc comment.
     */
    @GetMapping(path = "/{id}/edit.html")
    public String movieEditForm(final RedirectAttributes redirectAttributes, final Model model, final @PathVariable("id") Long id) {
        log.info("Load Update form for Movie with ID:{}.", id);
        try {
            final MovieDTO movie = movieService.getMovie(id);
            model.addAttribute(MOVIE_ATTRIBUTE, movie);
            return "movies/edit";
        } catch (NotFoundException e) {
            redirectAttributes.addFlashAttribute(SUCCESS_ATTRIBUTE, false);
            redirectAttributes.addFlashAttribute(MESSAGE_ATTRIBUTE, "There is no Movie with ID:" + id);
            return REDIRECT_MOVIES_LIST_HTML_ENDPOINT;
        }
    }

    /**
     * Javadoc comment.
     */
    @PostMapping(value = "/edit", consumes = MediaType.APPLICATION_FORM_URLENCODED_VALUE)
    public String editMovie(final RedirectAttributes redirectAttributes,
                              final Model model,
                              final @RequestParam(value = "id", required = false) Long id,
                              final MovieRequest movieRequest
    ) {
        log.info("Update Movie with ID:{}. {}", id, movieRequest);
        try {
            final MovieResponse movie = movieDtoToResponseConverter.convert(
                    movieService.updateMovie(id,
                            movieRequestMovieDTOConverter.convert(movieRequest))
            );
            model.addAttribute(MOVIE_ATTRIBUTE, movie);
            return "MOVIEs/edit";
        } catch (NotFoundException e) {
            redirectAttributes.addFlashAttribute(MESSAGE_ATTRIBUTE, "Nem létezik a film, ID:" + id);
            redirectAttributes.addFlashAttribute(SUCCESS_ATTRIBUTE, false);
            return REDIRECT_MOVIES_LIST_HTML_ENDPOINT;
        }
    }

    /**
     * Javadoc comment.
     */
    @GetMapping(path = "/remove/{id}")
    public String removeMovie(final RedirectAttributes redirectAttributes, final @PathVariable("id") Long id) {
        log.info("Remove a Movie with ID: {}.", id);
        try {
            movieService.deleteMovie(id);
            redirectAttributes.addFlashAttribute(SUCCESS_ATTRIBUTE, true);
        } catch (ConstraintViolationException | DataIntegrityViolationException e) {
            redirectAttributes.addFlashAttribute(SUCCESS_ATTRIBUTE, false);
        }
        return REDIRECT_MOVIES_LIST_HTML_ENDPOINT;
    }
}

