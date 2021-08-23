package executorexample.springbootmultithreading.service;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.logging.*;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import org.apache.http.message.BufferedHeader;
import org.slf4j.*;
import org.slf4j.Logger;

import executorexample.springbootmultithreading.entity.User;
import executorexample.springbootmultithreading.repository.UserRepository;

@Service
public class UserService {
	
	@Autowired
	private UserRepository repository;
	
	Object target;
	
	Logger logger=LoggerFactory.getLogger(UserService.class);
	
	@Async
	public CompletableFuture<List<User>> saveUsers(MultipartFile file) throws Exception{
		
		long start_time=System.currentTimeMillis();
		
		List<User> users=parseCsvFile(file);
		
		logger.info("Saving list of users of size {}",users.size()," "+Thread.currentThread().getName());
		users=repository.saveAll(users);
		long end_time=System.currentTimeMillis();
		logger.info("Total time taken for execution {}"+(end_time-start_time));
		
		return CompletableFuture.completedFuture(users);
		
	}
	
	@Async
	public CompletableFuture<List<User>> findAllUsers(){
		logger.info("get list of user by"+Thread.currentThread().getName());
		List<User> users=repository.findAll();
		return CompletableFuture.completedFuture(users);
	}
	
	private List<User> parseCsvFile(final MultipartFile file) throws Exception{
		final List<User> users= new ArrayList<>();
		try{
			try(final BufferedReader br = new BufferedReader(new InputStreamReader(file.getInputStream()))){
				String line;
				while((line=br.readLine())!=null){
					final String[] data=line.split(",");
					final User user=new User();
					user.setName(data[0]);
					user.setEmail(data[1]);
					user.setGender(data[2]);
					users.add(user);
				}
				return users;
			}
		}catch(final IOException e){
			logger.error("Failed to parse CSV file {}",e);
			throw new Exception("Failed to parse CSV file {}",e);
		}
	}
}
