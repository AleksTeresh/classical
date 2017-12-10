package fi.alekster.classical.controllers;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonPrimitive;
import fi.alekster.classical.dao.ExCredentialDao;
import fi.alekster.classical.dao.ExUserDao;
import fi.alekster.classical.db.tables.pojos.Credential;
import fi.alekster.classical.db.tables.pojos.User;
import fi.alekster.classical.representations.requests.RegisterRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

/**
 * Created by aleksandr on 16.11.2017.
 */

@RestController
@RequestMapping("/register")
public class RegisterController {
    private final ExCredentialDao credentialDao;
    private final ExUserDao userDao;

    @Autowired
    public RegisterController (ExCredentialDao credentialDao, ExUserDao userDao) {
        this.userDao = userDao;
        this.credentialDao = credentialDao;
    }

    @RequestMapping(method = RequestMethod.POST)
    public ResponseEntity<?> createGig(@RequestBody RegisterRequest request) {
        if (!credentialDao.existsById(request.getEmail())) {
            BCryptPasswordEncoder bcrypt = new BCryptPasswordEncoder(9);
            String hashedPassword = bcrypt.encode(request.getPassword());

            credentialDao.insert(
                    new Credential(request.getEmail(), hashedPassword)
            );

            userDao.insert(
                    new User(request.getEmail(), request.getName())
            );

            return ResponseEntity.ok().build();
        }

        JsonObject response = new JsonObject();
        JsonElement message = new JsonPrimitive("A user with the provided email already exists.");
        response.add("message", message);

        return new ResponseEntity<String>(response.toString(), HttpStatus.BAD_REQUEST);
    }
}
