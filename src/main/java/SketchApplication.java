import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@EnableScheduling
@SpringBootApplication
public class SketchApplication {

    public static void main(String[] args) {
        SpringApplication.run(SketchApplication.class, args);
    }

}
