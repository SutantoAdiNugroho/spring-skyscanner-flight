package com.pairlearning.skyscannerflight.repositories;

import com.pairlearning.skyscannerflight.domain.User;
import com.pairlearning.skyscannerflight.exceptions.EtAuthException;

public interface UserRepository {
    Integer create(String firstName, String lastName, String email, String password) throws EtAuthException;
    User findByEmailAndPassword(String email, String password) throws EtAuthException;
    Integer getCountByEmail(String email);
    User findById(Integer userId);
}
