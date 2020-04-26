package com.gabryell.github.accountbankingmanagement.data.account.dao;

import com.gabryell.github.accountbankingmanagement.data.account.model.AccountDocument;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface AccountDAO extends MongoRepository<AccountDocument, String> {

    @Query("{'customer.documentNumber': ?0}")
    Optional<AccountDocument> findByCustomerDocumentNumber(final String customerDocumentNumber);

}