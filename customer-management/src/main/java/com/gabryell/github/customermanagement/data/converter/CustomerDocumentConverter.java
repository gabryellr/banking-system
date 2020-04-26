package com.gabryell.github.customermanagement.data.converter;

import com.gabryell.github.customermanagement.core.model.CustomerBO;
import com.gabryell.github.customermanagement.data.model.CustomerDocument;
import lombok.NonNull;
import org.springframework.stereotype.Component;

@Component
public class CustomerDocumentConverter {

    public CustomerDocument toDocument(@NonNull CustomerBO customerBO) {
        CustomerDocument.Customer customer = CustomerDocument.Customer.builder()
                .name(customerBO.getName())
                .documentNumber(customerBO.getDocument().getDocumentNumber())
                .personType(toPersonTypeDocument(customerBO.getDocument().getPersonTypeEnum()))
                .score(customerBO.getScore())
                .build();

        return CustomerDocument.builder()
                .customer(customer)
                .build();
    }

    private CustomerDocument.PersonTypeEnum toPersonTypeDocument(@NonNull CustomerBO.PersonTypeEnum personTypeEnum) {
        return CustomerDocument.PersonTypeEnum.valueOf(personTypeEnum.name());
    }

    public CustomerBO toBO(CustomerDocument customerSaved) {
        CustomerDocument.Customer customerDocument = customerSaved.getCustomer();

        return CustomerBO.builder()
                .name(customerDocument.getName())
                .score(customerDocument.getScore())
                .document(documentTypeToBO(customerDocument))
                .build();
    }

    private CustomerBO.Document documentTypeToBO(@NonNull CustomerDocument.Customer customer) {
        return CustomerBO.Document.builder()
                .personTypeEnum(CustomerBO.PersonTypeEnum.fromValue(customer.getPersonType()))
                .documentNumber(customer.getDocumentNumber())
                .build();
    }

}
