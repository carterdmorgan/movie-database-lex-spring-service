package Contracts;

/**
 * Simple contract used to store commonly used URLs and other items needed to access API data.
 *
 * @author carterdmorgan
 */

public interface APIContract {
    String API_KEY = "81fc9dd";
    String BASE_URL = "http://www.omdbapi.com/?apikey=" + API_KEY + "&";
    String MOVIE_TITLE_URL = BASE_URL + "s=";
    String IMDB_BASIC_URL = BASE_URL + "i=";
}
