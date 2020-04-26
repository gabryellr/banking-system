package com.gabryell.github.customermanagement.api.controller.converter;

import com.gabryell.github.customermanagement.api.controller.model.CustomerInputDTO;
import com.gabryell.github.customermanagement.api.controller.model.CustomerOutputDTO;
import com.gabryell.github.customermanagement.core.model.CustomerBO;
import lombok.NonNull;
import org.springframework.stereotype.Component;

@Component
public class CustomerRestConverter {

    public CustomerBO toBO(@NonNull CustomerInputDTO customerInputDTO) {
        return CustomerBO.builder()
                .name(customerInputDTO.getName())
                .document(toDocumentBO(customerInputDTO.getDocument()))
                .build();
    }

    private CustomerBO.Document toDocumentBO(@NonNull CustomerInputDTO.Document document) {
        return CustomerBO.Document.builder()
                .documentNumber(document.getDocumentNumber())
                .personTypeEnum(CustomerBO.PersonTypeEnum.fromValue(document.getPersonTypeEnum()))
                .build();
    }

    public CustomerOutputDTO toCustomerOutputDTO(@NonNull CustomerBO customerSaved) {
        CustomerOutputDTO.Document document = CustomerOutputDTO.Document.builder()
                .documentNumber(customerSaved.getDocument().getDocumentNumber())
                .personTypeEnum(CustomerOutputDTO.PersonTypeEnum.fromValue(customerSaved.getDocument().getPersonTypeEnum()))
                .build();

        return CustomerOutputDTO.builder()
                .document(document)
                .score(customerSaved.getScore())
                .name(customerSaved.getName())
                .build();
    }

}