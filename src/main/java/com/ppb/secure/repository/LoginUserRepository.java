package com.ppb.secure.repository;

import com.ppb.secure.model.LoginUser;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface LoginUserRepository extends PagingAndSortingRepository<LoginUser, Integer> {
    LoginUser findByUsername(String username);
}
