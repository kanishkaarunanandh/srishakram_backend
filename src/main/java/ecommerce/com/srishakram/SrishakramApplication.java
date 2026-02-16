package ecommerce.com.srishakram;

import io.github.cdimascio.dotenv.Dotenv;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration;

@SpringBootApplication(exclude = { SecurityAutoConfiguration.class })
public class SrishakramApplication {

    public static void main(String[] args) {

        Dotenv dotenv = Dotenv.configure()
                .directory("./")
                .ignoreIfMalformed()
                .ignoreIfMissing()
                .load();

        if (dotenv.get("DB_URL") != null) {
            System.setProperty("DB_URL", dotenv.get("DB_URL"));
        }
        if (dotenv.get("DB_USERNAME") != null) {
            System.setProperty("DB_USERNAME", dotenv.get("DB_USERNAME"));
        }
        if (dotenv.get("DB_PASSWORD") != null) {
            System.setProperty("DB_PASSWORD", dotenv.get("DB_PASSWORD"));
        }
        if (dotenv.get("AWS_KEY") != null) {
            System.setProperty("AWS_KEY", dotenv.get("AWS_KEY"));
        }
        if (dotenv.get("AWS_SECRET_KEY") != null) {
            System.setProperty("AWS_SECRET_KEY", dotenv.get("AWS_SECRET_KEY"));
        }
        if (dotenv.get("AWS_REGION") != null) {
            System.setProperty("AWS_REGION", dotenv.get("AWS_REGION"));
        }
        if (dotenv.get("AWS_S3_BUCKET") != null) {
            System.setProperty("AWS_S3_BUCKET", dotenv.get("AWS_S3_BUCKET"));
        }
        if (dotenv.get("RAZORPAY_KEY") != null) {
            System.setProperty("RAZORPAY_KEY", dotenv.get("RAZORPAY_KEY"));
        }
        if (dotenv.get("RAZORPAY_SECRET_KEY") != null) {
            System.setProperty("RAZORPAY_SECRET_KEY", dotenv.get("RAZORPAY_SECRET_KEY"));
        }
        if (dotenv.get("GOOGLE_CLIENT_ID") != null) {
            System.setProperty("GOOGLE_CLIENT_ID", dotenv.get("GOOGLE_CLIENT_ID"));
        }
        if (dotenv.get("GOOGLE_CLIENT_SECRET") != null) {
            System.setProperty("GOOGLE_CLIENT_SECRET", dotenv.get("GOOGLE_CLIENT_SECRET"));
        }

        SpringApplication.run(SrishakramApplication.class, args);
    }
}
