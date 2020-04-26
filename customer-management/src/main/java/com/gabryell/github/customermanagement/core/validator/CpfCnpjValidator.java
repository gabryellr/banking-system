package com.gabryell.github.customermanagement.core.validator;

import com.gabryell.github.customermanagement.core.model.CustomerBO;
import com.gabryell.github.customermanagement.exceptionHandler.BusinessException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public class CpfCnpjValidator {

    private static final int CNPJ_LENGTH = 14;
    private static final String CNPJ_ENUM = "PJ";
    private static final int CPF_LENGTH = 11;
    private static final String CPF_ENUM = "PF";

    public void validateCpfCnpj(CustomerBO customerBO) {
        String documentNumber = customerBO.getDocument().getDocumentNumber();
        String personNameType = customerBO.getDocument().getPersonTypeEnum().name();

        log.info("validating CPF/CNPJ [{}] to customer [{}]", documentNumber, customerBO.getName());
        String documentNumberFormated = documentNumber.replaceAll("\\D", "");
        customerBO.getDocument().setDocumentNumber(documentNumberFormated);

        if (documentNumberFormated.length() == CNPJ_LENGTH) {
            validateCnpj(personNameType, documentNumber);
        }

        if (documentNumberFormated.length() == CPF_LENGTH) {
            validateCpf(personNameType, documentNumber);
        }

        log.info("CPF/CNPJ with number [{}] and customer name [{}] was validate, into to next steps",
                documentNumber, customerBO.getName());
    }

    private void validateCpf(String documentType, String documentNumber) {
        if (!documentType.equals(CPF_ENUM)) {
            throw new BusinessException("document number [" + documentNumber + "] " +
                    "don't match with document type [" + documentType + "]");
        }
    }

    private void validateCnpj(String documentType, String documentNumber) {
        if (!documentType.equals(CNPJ_ENUM)) {
            throw new BusinessException("document number [" + documentNumber + "] " +
                    "don't match with document type [" + documentType + "]");
        }
    }

}
