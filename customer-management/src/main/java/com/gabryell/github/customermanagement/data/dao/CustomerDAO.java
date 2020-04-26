package com.gabryell.github.customermanagement.data.dao;

import com.gabryell.github.customermanagement.data.model.CustomerDocument;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CustomerDAO extends MongoRepository<CustomerDocument, String> {

}