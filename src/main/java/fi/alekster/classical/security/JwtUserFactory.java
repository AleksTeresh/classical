package fi.alekster.classical.security;

import fi.alekster.classical.db.tables.pojos.Credential;

import java.util.ArrayList;

/**
 * Created by aleksandr on 16.11.2017.
 */

public class JwtUserFactory {
    private JwtUserFactory() {
    }

    public static JwtUser create(Credential credential) {
        // GrantedAuthority authority = getGrantedAuthority(user.getRank());
        // ArrayList<GrantedAuthority> authorities = new ArrayList<>();
        // authorities.add(authority);

        return new JwtUser(
                credential.getEmail(),
                credential.getPassword(),
                new ArrayList<>(),
                true
        );
    }
}
