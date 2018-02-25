package Controllers;

import Contracts.*;
import Models.Message;
import Services.MovieDatabaseServices;
import com.amazonaws.services.lexruntime.AmazonLexRuntime;
import com.amazonaws.services.lexruntime.model.PostTextRequest;
import com.amazonaws.services.lexruntime.model.PostTextResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.atomic.AtomicLong;

@RestController
public class LexResponseController {

    private final AtomicLong counter = new AtomicLong();
    private final AmazonLexRuntime lexRuntime = Application.lexRuntime;
    private final Map<String, String> sessionAttributes = Application.sessionAttributes;
    private String previousIntent = null;

    @RequestMapping("/lexresponse")
    public Message response(@RequestParam(value="phrase", defaultValue="empty") String phrase) {
        String content = MessageContract.API_FAILURE;

        System.out.println("PREVIOUS INTENT: " + previousIntent);
//        AmazonLexRuntime lexRuntime = Application.lexRuntime;

        System.out.println("PHRASE: " + phrase+"\n");
        PostTextRequest request = new PostTextRequest();
        request.setBotName(LexKeyContract.BOT_NAME);
        request.setBotAlias(LexKeyContract.BOT_ALIAS);
        request.setSessionAttributes(sessionAttributes);
        request.setUserId("carterdmorgan");
        Map<String, String> sessionAttributes = new HashMap<>();
        sessionAttributes.put("Name", "Carter");
        request.setSessionAttributes(sessionAttributes);
        request.setInputText(phrase);

        PostTextResult result = lexRuntime.postText(request);
        System.out.println("REQUEST: " + request.toString() +"\n");
        System.out.println("RESULT: " + result.toString() + "\n");


        String intent = result.getIntentName();
        String dialogState = result.getDialogState();

        if(dialogState.equals(DialogStateContract.READY_FOR_FULFILLMENT)){
            sessionAttributes.put(SlotContract.MOVIE, result.getSlots().get(SlotContract.MOVIE));
            if(intent.equals(IntentContract.MOVIE_PLOT)){
                content = MovieDatabaseServices.returnMoviePlot(result);
            }else if(intent.equals(IntentContract.MOVIE_RATING)){
                content = MovieDatabaseServices.returnMovieRating(result);
            }else if(intent.equals(IntentContract.MOVIE_DIRECTOR)){
                content = MovieDatabaseServices.returnMovieDirector(result);
            }else if(intent.equals(IntentContract.MOVIE_RELEASE_DATE)){
                content = MovieDatabaseServices.returnMovieReleaseDate(result);
            }else if(intent.equals(IntentContract.MOVIE_RUNTIME)){
                content = MovieDatabaseServices.returnMovieRuntime(result);
            }else if(intent.equals(IntentContract.MOVIE_STARS)){
                content = MovieDatabaseServices.returnMovieStars(result);
            }else if(intent.equals(IntentContract.MOVIE_GENERAL_INFO)){
                content = MovieDatabaseServices.returnMovieGeneralInfo(result);
            }else if(intent.equals(IntentContract.MOVIE_ROTTEN_TOMATOES)){
                content = MovieDatabaseServices.returnRottenTomatoesRating(result);
            }
        }else{
            content = result.getMessage();
        }

        return new Message(counter.incrementAndGet(),content);
    }
}
