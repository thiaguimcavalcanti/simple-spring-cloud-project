package com.bot.exchanges.commons.repository;

import com.bot.exchanges.commons.entities.Authentication;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AuthenticationRepository extends JpaRepository<Authentication, String> {

    Authentication findByUserId(String userId);
}