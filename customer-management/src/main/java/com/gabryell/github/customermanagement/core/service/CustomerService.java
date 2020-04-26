package com.gabryell.github.customermanagement.core.service;

import com.gabryell.github.customermanagement.core.model.CustomerBO;
import com.gabryell.github.customermanagement.data.repository.CustomerRepository;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

import static java.util.concurrent.ThreadLocalRandom.current;

@Service
@Slf4j
@AllArgsConstructor
public class CustomerService {

    private static final int MINIMUM_SCORE_VALUE = 0;
    private static final int MAXIMUM_SCORE_VALUE = 10;
    private CustomerRepository customerRepository;

    public void generateScore(CustomerBO customerBO) {
        String documentNumber = customerBO.getDocument().getDocumentNumber();

        log.info("generating score to user with cnpj/cpf [{}]", documentNumber);
        int score = current().nextInt(MINIMUM_SCORE_VALUE, MAXIMUM_SCORE_VALUE);
        customerBO.setScore(score);
        log.info("score has been generated to user with cnpj/cpf [{}]", documentNumber);
    }

    public CustomerBO saveCustomer(CustomerBO customerBO) {
        log.info("Saving the user [{}]", customerBO);
        return customerRepository.save(customerBO);
    }

    public List<CustomerBO> findAll() {
        return customerRepository.findAll();
    }

}
