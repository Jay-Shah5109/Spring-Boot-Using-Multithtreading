package executorexample.springbootmultithreading.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import executorexample.springbootmultithreading.entity.User;

public interface UserRepository extends JpaRepository<User, Integer>{

}
