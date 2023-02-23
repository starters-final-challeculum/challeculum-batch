package companion.challeculum.batch;

import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@EnableBatchProcessing
@SpringBootApplication
public class ChalleculumBatchApplication {

    public static void main(String[] args) {
        SpringApplication.run(ChalleculumBatchApplication.class, args);
    }

}
