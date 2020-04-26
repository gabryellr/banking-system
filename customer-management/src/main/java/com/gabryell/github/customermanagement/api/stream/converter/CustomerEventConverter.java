package com.gabryell.github.customermanagement.api.stream.converter;

import com.gabryell.github.customermanagement.api.controller.model.CustomerOutputDTO;
import com.gabryell.github.customermanagement.api.stream.model.CustomerEvent;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@AllArgsConstructor
public class CustomerEventConverter {

    public CustomerEvent toEvent(CustomerOutputDTO customerOutputDTO) {
        CustomerOutputDTO.Document document = customerOutputDTO.getDocument();

        CustomerEvent.Customer customer = CustomerEvent.Customer.builder()
                .name(customerOutputDTO.getName())
                .score(customerOutputDTO.getScore())
                .document(toDocumentEvent(document))
                .build();

        return CustomerEvent.builder()
                .customer(customer)
                .build();
    }

    private CustomerEvent.Document toDocumentEvent(CustomerOutputDTO.Document document) {
        return CustomerEvent.Document.builder()
                .documentNumber(document.getDocumentNumber())
                .personType(CustomerEvent.PersonTypeEnum.fromValue(document.getPersonTypeEnum()))
                .build();
    }

}