package executorexample.springbootmultithreading.controller;

import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.function.Function;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import executorexample.springbootmultithreading.entity.User;
import executorexample.springbootmultithreading.service.UserService;

@RestController
public class UserController {
	
	@Autowired
	private UserService service;
	
	@PostMapping(value="/users",consumes={MediaType.MULTIPART_FORM_DATA_VALUE},produces="application/json")
	public ResponseEntity saveUsers(@RequestParam(value="files")MultipartFile[] files){
		
		for(MultipartFile file: files){
			try {
				service.saveUsers(file);
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		return ResponseEntity.status(HttpStatus.CREATED).build();
	}
	
	@GetMapping(value="/users",produces="application/json")
	public CompletableFuture<ResponseEntity> findAllUsers(){
		return service.findAllUsers().thenApply(ResponseEntity::ok);
	}
	
	@GetMapping(value="/getUsersByMultipleThreads",produces="application/json")
	public ResponseEntity getUsers(){
		CompletableFuture<List<User>> user1= service.findAllUsers();
		CompletableFuture<List<User>> user2= service.findAllUsers();
		CompletableFuture<List<User>> user3= service.findAllUsers(); // using 3 users to fetch the data from csv filw
		// and threads used are 2 only, refer the AsyncConfig.java file
		
		CompletableFuture.allOf(user1,user2,user3).join(); //allOf method will wait for the completion of all 3 threads,
		// once it completes, it will join them
		
		return ResponseEntity.status(HttpStatus.OK).build();
		
		
		
		
	}
	
	

}
