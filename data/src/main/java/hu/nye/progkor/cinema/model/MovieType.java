package hu.nye.progkor.cinema.model;

public enum MovieType {

    MOVIE_3D("2d-s film"),
    MOVIE_2D("3d-s film"),
    BOTH("2d és 3d is lehet");

    private final String value;

    MovieType(final String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }
}
