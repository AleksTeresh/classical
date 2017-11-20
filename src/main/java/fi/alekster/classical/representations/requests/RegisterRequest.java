package fi.alekster.classical.representations.requests;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * Created by aleksandr on 17.11.2017.
 */
public class RegisterRequest {

    @JsonProperty
    private String email;

    @JsonProperty
    private String password;

    @JsonProperty
    private String name;

    public RegisterRequest() {
    }

    public RegisterRequest(String email, String password, String name) {
        this.email = email;
        this.password = password;
        this.name = name;
    }

    public String getEmail() {
        return this.email;
    }


    public String getPassword() {
        return this.password;
    }

    public String getName () {
        return this.name;
    }
}
