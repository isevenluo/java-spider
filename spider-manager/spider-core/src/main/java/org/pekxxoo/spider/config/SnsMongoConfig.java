package org.pekxxoo.spider.config;

import com.mongodb.MongoClient;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.data.mongodb.core.MongoTemplate;

/**
 * Created by chong on 2017/8/9.
 */
@Configuration
@ConfigurationProperties(prefix = "spring.data.mongodb")
public class SnsMongoConfig extends BaseMongoConfig {

    @Override
    @Primary
    @Bean(name = "mongoTemplate")
    public MongoTemplate mongoTemplate() throws Exception {
        return super.mongoTemplate();
    }

    @Override
    @Primary
    @Bean(name = "mongoClient")
    public MongoClient mongo() throws Exception {
        return super.mongo();
    }
}
