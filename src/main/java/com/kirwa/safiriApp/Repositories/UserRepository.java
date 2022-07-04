package com.kirwa.safiriApp.Repositories;

import com.kirwa.safiriApp.Entities.User;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
     List<User> findAll();


//Note that we are using classNames for Queries and not the names of the tables in the database.
     @Query("SELECT u FROM User u WHERE u.emailAddress = ?1")
     public User findByEmailAddress(String emailAddress);
}
