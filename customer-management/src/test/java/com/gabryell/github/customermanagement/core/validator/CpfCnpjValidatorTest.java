package com.gabryell.github.customermanagement.core.validator;


import com.gabryell.github.customermanagement.core.model.CustomerBO;
import com.gabryell.github.customermanagement.exceptionHandler.BusinessException;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class CpfCnpjValidatorTest {

    private CpfCnpjValidator cpfCnpjValidator;

    @Before
    public void setUp() {
        cpfCnpjValidator = new CpfCnpjValidator();
    }

    @Test
    public void mustApproveWithCnpfNumberAndPFPersonType() {
        CustomerBO.Document document = CustomerBO.Document.builder().documentNumber("45011895092")
                .personTypeEnum(CustomerBO.PersonTypeEnum.PF)
                .build();

        CustomerBO customerBO = CustomerBO.builder()
                .document(document)
                .build();

        cpfCnpjValidator.validateCpfCnpj(customerBO);
        assertEquals(customerBO, customerBO);
    }

    @Test
    public void mustApproveWithCpjNumberAndPJType() {
        CustomerBO.Document document = CustomerBO.Document.builder().documentNumber("03825320000179")
                .personTypeEnum(CustomerBO.PersonTypeEnum.PJ)
                .build();

        CustomerBO customerBO = CustomerBO.builder()
                .document(document)
                .build();

        cpfCnpjValidator.validateCpfCnpj(customerBO);
        assertEquals(customerBO, customerBO);
    }

    @Test(expected = BusinessException.class)
    public void mustShowBusinessExceptionWhenDocumentNumberIsCpfFormatAndPersonTypeIsPJ() {
        CustomerBO.Document document = CustomerBO.Document.builder().documentNumber("45011895092")
                .personTypeEnum(CustomerBO.PersonTypeEnum.PJ)
                .build();

        CustomerBO customerBO = CustomerBO.builder()
                .document(document)
                .build();

        cpfCnpjValidator.validateCpfCnpj(customerBO);
    }

    @Test(expected = BusinessException.class)
    public void mustShowBusinessExceptionWhenDocumentNumberIsCNPJFormatAndPersonTypeIsPF() {
        CustomerBO.Document document = CustomerBO.Document.builder().documentNumber("03825320000179")
                .personTypeEnum(CustomerBO.PersonTypeEnum.PF)
                .build();

        CustomerBO customerBO = CustomerBO.builder()
                .document(document)
                .build();

        cpfCnpjValidator.validateCpfCnpj(customerBO);
    }

}