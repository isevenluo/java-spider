package org.pekxxoo;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.data.redis.RedisAutoConfiguration;
import org.springframework.boot.autoconfigure.mongo.MongoAutoConfiguration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication(exclude = {MongoAutoConfiguration.class})
@EnableScheduling //@EnableScheduling 注解的作用是发现注解@Scheduled的任务并后台执行
@PropertySource("classpath:/config/mongodb-config.properties")
public class SpiderCoreApplication {

	public static void main(String[] args) {
		SpringApplication.run(SpiderCoreApplication.class, args);
	}
}
