package com.pairlearning.skyscannerflight.registration;

import com.pairlearning.skyscannerflight.appuser.AppUser;
import com.pairlearning.skyscannerflight.appuser.AppUserRole;
import com.pairlearning.skyscannerflight.appuser.AppUserService;
import com.pairlearning.skyscannerflight.registration.token.ConfirmationToken;
import com.pairlearning.skyscannerflight.registration.token.ConfirmationTokenService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.UUID;

@Service
@AllArgsConstructor
public class RegistrationService {

    private final AppUserService appUserService;
    private final ConfirmationTokenService confirmationTokenService;
    private EmailValidator emailValidator;

    public String register(RegistrationRequest registrationRequest) {
        Boolean isValid = emailValidator.test(registrationRequest.getEmail());
        if (!isValid)
            throw new IllegalStateException("email not valid");

        UUID uuid = UUID.randomUUID();
        String usernameId = uuid.toString().substring(0, 6);

        AppUser appUser = new AppUser(
                registrationRequest.getFullName(),
                usernameId,
                registrationRequest.getEmail(),
                registrationRequest.getPassword(),
                AppUserRole.USER
        );

        return appUserService.signUpUser(appUser);
    }

    @Transactional
    public String confirmToken(String token) {
        ConfirmationToken confirmationToken = confirmationTokenService
                .getToken(token)
                .orElseThrow(() -> new IllegalStateException("token not found"));

        if (confirmationToken.getConfirmedAt() != null) {
            throw new IllegalStateException("email already confirmed");
        }

        LocalDateTime expiredAt = confirmationToken.getExpiredAt();

        if (expiredAt.isBefore(LocalDateTime.now())) {
            throw new IllegalStateException("token expired");
        }

        confirmationTokenService.setConfirmedAt(token);
        appUserService.enabledAppUser(confirmationToken.getAppUser().getEmail());

        return "confirmed";
    }
}
