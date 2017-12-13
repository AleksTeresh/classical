package fi.alekster.classical.config;

import net.sargue.mailgun.Configuration;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

/**
 * Created by aleksandr on 15.11.2017.
 */
@Component
public class MailgunConfigs {
    @Value("${mailgun.api-key}")
    private String apiKey;

    private Configuration configuration;

    public MailgunConfigs() {
        configuration = new Configuration()
                .domain("sandbox8b70a8b0b50d40e6b2dbbef2fc6a2659.mailgun.org")
                .apiKey(apiKey)
                .from("Classical", "postmaster@sandbox8b70a8b0b50d40e6b2dbbef2fc6a2659.mailgun.org");
    }

    public void setApiKey (String apiKey) {
        this.apiKey = apiKey;
    }

    public Configuration getConfiguration() {
        return this.configuration;
    }
}
