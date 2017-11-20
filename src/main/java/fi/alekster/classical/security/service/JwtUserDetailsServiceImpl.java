package fi.alekster.classical.security.service;

import fi.alekster.classical.dao.ExCredentialDao;
import fi.alekster.classical.db.tables.pojos.Credential;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import fi.alekster.classical.security.JwtUserFactory;

/**
 * Created by aleksandr on 16.11.2017.
 */

@Service
public class JwtUserDetailsServiceImpl implements UserDetailsService {
    private final ExCredentialDao credentialDao;

    @Autowired
    public JwtUserDetailsServiceImpl(ExCredentialDao credentialDao) {
        this.credentialDao = credentialDao;
    }

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        Credential credential = credentialDao.fetchOneByEmail(email);

        if (credential == null) {
            throw new UsernameNotFoundException(String.format("No user found with email '%s'.", email));
        } else {
            return JwtUserFactory.create(credential);
        }
    }
}
