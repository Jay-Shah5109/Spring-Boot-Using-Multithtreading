package executorexample.springbootmultithreading.config;

import java.util.concurrent.Executor;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

@EnableAsync // this will tell the spring framework to run tasks in background using Thread pool concept
@Configuration
public class AsyncConfig {
	
	@Bean(name="taskExecutor")
	public Executor taskExecutor(){
		ThreadPoolTaskExecutor executor = new ThreadPoolTaskExecutor();
		executor.setCorePoolSize(2); // thread capacity -2 
		executor.setMaxPoolSize(2);
		executor.setQueueCapacity(100); // threads size in the blocking queue
		executor.setThreadNamePrefix("user-Thread:");
		executor.initialize();
		return executor;
		
		
	}
	
	

}
