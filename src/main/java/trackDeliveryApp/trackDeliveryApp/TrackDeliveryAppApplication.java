package trackDeliveryApp.trackDeliveryApp;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;

@SpringBootApplication
public class TrackDeliveryAppApplication {

	public static void main(String[] args) {
		SpringApplication.run(TrackDeliveryAppApplication.class, args);

	}


}
