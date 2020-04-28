package com.ryankim.mycourse.Repositories;

import java.util.List;
import java.util.Optional;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.ryankim.mycourse.models.User;


@Repository
public interface UserRepository extends CrudRepository<User, Long>{
	List<User> findAll();
	User findByEmail(String email);
	Optional<User> findById(Long id);
}
