package fi.alekster.classical.config;

import net.sargue.mailgun.Configuration;
import org.springframework.stereotype.Component;

/**
 * Created by aleksandr on 15.11.2017.
 */
@Component
public class MailgunConfigs {
    private Configuration configuration;

    public MailgunConfigs() {
        configuration = new Configuration()
                .domain("sandbox8b70a8b0b50d40e6b2dbbef2fc6a2659.mailgun.org")
                .apiKey("key-c112b60a08923e3a3d942148b12ae719")
                .from("Classical", "postmaster@sandbox8b70a8b0b50d40e6b2dbbef2fc6a2659.mailgun.org");
    }

    public Configuration getConfiguration() {
        return this.configuration;
    }
}
