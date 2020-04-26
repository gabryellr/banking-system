package com.gabryell.github.customermanagement.data.repository;

import com.gabryell.github.customermanagement.core.model.CustomerBO;
import com.gabryell.github.customermanagement.data.converter.CustomerDocumentConverter;
import com.gabryell.github.customermanagement.data.dao.CustomerDAO;
import com.gabryell.github.customermanagement.data.model.CustomerDocument;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.List;

import static java.util.stream.Collectors.toList;

@Component
@Slf4j
@AllArgsConstructor
public class CustomerRepository {

    private CustomerDocumentConverter customerDocumentConverter;
    private CustomerDAO customerDAO;

    public CustomerBO save(CustomerBO customerBO) {
        log.info("Converting customer to CustomerDocument");
        CustomerDocument customerDocument = customerDocumentConverter.toDocument(customerBO);

        log.info("Customer has been converted to CustomerDocument with body [{}]", customerDocument);
        log.info("Saving customer on database");
        CustomerDocument customerSaved = customerDAO.save(customerDocument);

        log.info("Customer has been saved!");
        return customerDocumentConverter.toBO(customerSaved);
    }

    public List<CustomerBO> findAll() {
        return customerDAO.findAll().stream()
                .map(customerDocumentConverter::toBO).collect(toList());
    }

}
