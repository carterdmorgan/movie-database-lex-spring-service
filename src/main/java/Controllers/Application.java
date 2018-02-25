package Controllers;

import com.amazonaws.regions.Regions;
import com.amazonaws.services.lexruntime.AmazonLexRuntime;
import com.amazonaws.services.lexruntime.AmazonLexRuntimeClientBuilder;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.util.Map;

@SpringBootApplication
public class Application {

    static AmazonLexRuntime lexRuntime;
    static Map<String, String> sessionAttributes;

    public static void main(String[] args){
        lexRuntime = AmazonLexRuntimeClientBuilder.standard().withRegion(Regions.US_EAST_1).build();
        SpringApplication.run(Application.class, args);
    }
}
