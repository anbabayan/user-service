package com.homework.userservice;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ServerUsageRepository extends MongoRepository<ServerUsage, String> {

}
