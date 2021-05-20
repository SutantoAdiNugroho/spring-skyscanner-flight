package com.pairlearning.skyscannerflight.appuser;

import com.pairlearning.skyscannerflight.registration.token.ConfirmationToken;
import com.pairlearning.skyscannerflight.registration.token.ConfirmationTokenService;
import lombok.AllArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.UUID;

@Service
@AllArgsConstructor
public class AppUserService implements UserDetailsService {

    private final static String USER_NOT_FOUND_MSG = "User with email %s not found";

    private final AppUserRepository appUserRepository;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;
    private final ConfirmationTokenService confirmationTokenService;

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        return appUserRepository.findByEmail(email)
                .orElseThrow(() ->
                        new UsernameNotFoundException(String.format(USER_NOT_FOUND_MSG, email)));
    }

    public String signUpUser(AppUser appUser) {
        boolean isUserExist = appUserRepository.findByEmail(appUser.getEmail())
                .isPresent();
        if (isUserExist)
            throw new IllegalStateException("Email already used with another user");

        String encodedPass = bCryptPasswordEncoder.encode(appUser.getPassword());
        appUser.setPassword(encodedPass);
        appUserRepository.save(appUser);

        String token = UUID.randomUUID().toString();
        ConfirmationToken confirmationToken = new ConfirmationToken(
                token,
                LocalDateTime.now(),
                LocalDateTime.now().plusMinutes(20),
                appUser
        );
         confirmationTokenService.saveConfirmationToken(confirmationToken);

        return token;
    }

    public int enabledAppUser(String email) {
        return appUserRepository.enabledAppUser(email);
    }
}
