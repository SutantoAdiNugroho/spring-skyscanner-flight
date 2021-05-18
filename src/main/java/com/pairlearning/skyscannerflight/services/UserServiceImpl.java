package com.pairlearning.skyscannerflight.services;

import com.pairlearning.skyscannerflight.domain.User;
import com.pairlearning.skyscannerflight.exceptions.EtAuthException;

public class UserServiceImpl implements UserService{

    @Override
    public User validateUser(String email, String password) throws EtAuthException {
        return null;
    }

    @Override
    public User registerUser(String firstName, String lastName, String email, String password) throws EtAuthException {
        return null;
    }
}
