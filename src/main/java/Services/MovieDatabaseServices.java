package Services;

import Contracts.APIContract;
import Contracts.MessageContract;
import Contracts.SlotContract;
import Models.IMBDMovie;
import com.amazonaws.services.lexruntime.model.PostTextResult;
import com.google.gson.Gson;
import org.json.JSONArray;
import org.json.JSONObject;

import java.net.URLEncoder;
import java.util.HashMap;
import java.util.Map;

public class MovieDatabaseServices {
    public static String returnMoviePlot(PostTextResult result){
        Map<String, String> slots = result.getSlots();
        String movie = slots.get(SlotContract.MOVIE);
        Gson gson = new Gson();
        try{
            IMBDMovie imbdMovie = gson.fromJson(returnMovieJson(movie), IMBDMovie.class);
            String plot = imbdMovie.getPlot();
            plot = "\"" + plot + "\"";
            return plot;
        }catch(Exception e){
            System.out.println("ERROR: " + e.getMessage());
            return MessageContract.API_FAILURE;
        }

    }

    public static String returnMovieGeneralInfo(PostTextResult result){
        Map<String, String> slots = result.getSlots();
        String movie = slots.get(SlotContract.MOVIE);
        Gson gson = new Gson();
        try{
            IMBDMovie imbdMovie = gson.fromJson(returnMovieJson(movie), IMBDMovie.class);
            String info = imbdMovie.getGeneralInfo();
            System.out.println("FIRST LETTER IS VOWEL: " + GrammarServices.isFirstLetterVowel(info));
            return info;
        }catch(Exception e){
            System.out.println("ERROR: " + e.getMessage());
            return MessageContract.API_FAILURE;
        }

    }

    public static String returnRottenTomatoesRating(PostTextResult result){
        Map<String, String> slots = result.getSlots();
        String movie = slots.get(SlotContract.MOVIE);
        Gson gson = new Gson();
        try{
            IMBDMovie imbdMovie = gson.fromJson(returnMovieJson(movie), IMBDMovie.class);
            movie = imbdMovie.getTitle();
            Map<String, String> rottenTomatoes = new HashMap<>();
            for(Map<String, String> map : imbdMovie.getRatings()){
                if(map.get("Source").equals("Rotten Tomatoes")){
                    rottenTomatoes = map;
                }
            }
            String status = null;
            String rating = rottenTomatoes.get("Value");
            Integer data = Integer.parseInt(rating.replace("%",""));

            if(data >= 60){
                status = "fresh";
            }else{
                status = "rotten";
            }

            String message = "%s is considered %s with a score of %s on Rotten Tomatoes.";
            message = String.format(message, movie, status, rating);
            return message;
        }catch(Exception e){
            System.out.println("ERROR: " + e.getMessage());
            return MessageContract.API_FAILURE;
        }
    }

    public static String returnMovieStars(PostTextResult result){
        Map<String, String> slots = result.getSlots();
        String movie = slots.get(SlotContract.MOVIE);
        Gson gson = new Gson();
        try{
            IMBDMovie imbdMovie = gson.fromJson(returnMovieJson(movie), IMBDMovie.class);
            String stars = imbdMovie.getActors();
            String message = "%s stars %s.";
            message = String.format(message, movie, stars);
            return message;
        }catch(Exception e){
            System.out.println("ERROR: " + e.getMessage());
            return MessageContract.API_FAILURE;
        }
    }

    public static String returnMovieRuntime(PostTextResult result){
        Map<String, String> slots = result.getSlots();
        String movie = slots.get(SlotContract.MOVIE);
        Gson gson = new Gson();
        try{
            IMBDMovie imbdMovie = gson.fromJson(returnMovieJson(movie), IMBDMovie.class);
            movie = imbdMovie.getTitle();
            String runtime = imbdMovie.getRuntime();
            String message = "%s has a runtime of %s.";
            message = String.format(message, movie, runtime);
            return message;
        }catch(Exception e){
            System.out.println("ERROR: " + e.getMessage());
            return MessageContract.API_FAILURE;
        }
    }

    public static String returnMovieReleaseDate(PostTextResult result){
        Map<String, String> slots = result.getSlots();
        String movie = slots.get(SlotContract.MOVIE);
        Gson gson = new Gson();
        try{
            IMBDMovie imbdMovie = gson.fromJson(returnMovieJson(movie), IMBDMovie.class);
            movie = imbdMovie.getTitle();
            String release = imbdMovie.getReleased();
            String message = "%s premiered on %s.";
            message = String.format(message, movie, release);
            return message;
        }catch(Exception e){
            System.out.println("ERROR: " + e.getMessage());
            return MessageContract.API_FAILURE;
        }
    }

    public static String returnMovieDirector(PostTextResult result){
        Map<String, String> slots = result.getSlots();
        String movie = slots.get(SlotContract.MOVIE);
        Gson gson = new Gson();
        try{
            IMBDMovie imbdMovie = gson.fromJson(returnMovieJson(movie), IMBDMovie.class);
            movie = imbdMovie.getTitle();
            String director = imbdMovie.getDirector();
            String message = "%s was directed by %s.";
            message = String.format(message, movie, director);
            return message;
        }catch(Exception e){
            System.out.println("ERROR: " + e.getMessage());
            return MessageContract.API_FAILURE;
        }
    }

    public static String returnMovieRating(PostTextResult result){
        Map<String, String> slots = result.getSlots();
        String movie = slots.get(SlotContract.MOVIE);
        Gson gson = new Gson();
        try{
            IMBDMovie imbdMovie = gson.fromJson(returnMovieJson(movie), IMBDMovie.class);
            movie = imbdMovie.getTitle();
            String rating = imbdMovie.getRated();
            String message = "%s is rated %s.";
            message = String.format(message, movie, rating);
            return message;
        }catch(Exception e){
            System.out.println("ERROR: " + e.getMessage());
            return MessageContract.API_FAILURE;
        }
    }

    public static String returnMovieJson(String movie){
        try{
            String movieEncoded = URLEncoder.encode(movie, "utf-8");
            String json = WebServices.getJSON(APIContract.MOVIE_TITLE_URL+movieEncoded);
            JSONObject jsonObject = new JSONObject(json);
            JSONArray jsonArray = jsonObject.getJSONArray("Search");
            JSONObject movieObject = jsonArray.getJSONObject(0);
            String id = movieObject.getString("imdbID");
            String moreJson = WebServices.getJSON(APIContract.IMDB_BASIC_URL+id);
            JSONObject moreJsonObject = new JSONObject(moreJson);
            return moreJsonObject.toString();
        }catch(Exception e){
            System.out.println("ERROR: " + e.getMessage());
            return null;
        }
    }
}
