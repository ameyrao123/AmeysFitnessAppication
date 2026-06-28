package com.fitness.activityService.Config;

import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.mongodb.config.AbstractMongoClientConfiguration;
import org.springframework.data.mongodb.config.EnableMongoAuditing;

@Configuration
@EnableMongoAuditing
public class MongoConfig extends AbstractMongoClientConfiguration {

    @Override
    protected String getDatabaseName() {
        return "FitnessDB";
    }

    @Override
    public MongoClient mongoClient() {
        // This explicitly forces Spring to connect with the admin user and authSource
        return MongoClients.create("mongodb://admin:password@localhost:27017/FitnessDB?authSource=admin");
    }
}