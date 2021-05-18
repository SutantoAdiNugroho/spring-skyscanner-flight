package com.pairlearning.skyscannerflight.services;

import com.pairlearning.skyscannerflight.domain.User;
import com.pairlearning.skyscannerflight.exceptions.EtAuthException;

public interface UserService {
    User validateUser(String email, String password) throws EtAuthException;
    User registerUser(String firstName, String lastName, String email, String password) throws EtAuthException;
}
