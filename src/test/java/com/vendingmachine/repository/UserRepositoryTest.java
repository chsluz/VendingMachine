package com.vendingmachine.repository;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.HashSet;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import com.vendingmachine.entity.User;

@DataJpaTest
public class UserRepositoryTest {
	
	
 @Autowired
 private UserRepository userRepository;
 private User userForTest = null;
 
 @BeforeEach
 public void init() {
	 userForTest = userRepository.save(new User(null,"test name 1","test1","123456",0,new HashSet<>()));
 }
 
 @Test
 public void shouldSaveUser() {
	 User save = userRepository.save(new User(null,"test name","test","123456",0,new HashSet<>()));
	 assertThat(save).isNotNull();
	 assertThat(save.getId()).isNotNull();
	 assertThat(save.getUsername()).isEqualTo("test");
 }
 
 @Test
 public void shouldEditUser() {
	 userForTest.setDeposit(100);
	 userForTest = userRepository.save(userForTest);
	 assertThat(userForTest).isNotNull();
	 assertThat(userForTest.getId()).isNotNull();
	 assertThat(userForTest.getDeposit()).isEqualTo(100);
 }
 
 @Test
 public void shouldDeleteUser() {
	 userRepository.deleteById(userForTest.getId());
	 Optional<User> find = userRepository.findById(userForTest.getId());
	 assertThat(find.isPresent()).isEqualTo(false);
 }

}
