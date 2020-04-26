package com.gabryell.github.customermanagement.api.controller.rest;

import com.gabryell.github.customermanagement.api.controller.converter.CustomerRestConverter;
import com.gabryell.github.customermanagement.api.controller.model.CustomerInputDTO;
import com.gabryell.github.customermanagement.api.controller.model.CustomerOutputDTO;
import com.gabryell.github.customermanagement.api.stream.producer.CustomerEventProducer;
import com.gabryell.github.customermanagement.core.model.CustomerBO;
import com.gabryell.github.customermanagement.core.service.CustomerService;
import com.gabryell.github.customermanagement.core.validator.CpfCnpjValidator;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

import static java.net.HttpURLConnection.HTTP_CREATED;
import static java.util.stream.Collectors.toList;

@Slf4j
@RequestMapping("/api/v1/customers")
@RestController
@AllArgsConstructor
@Api(value = "API Rest to create a new customer")
public class CustomerRest {

    private CustomerRestConverter customerRestConverter;
    private CustomerService customerService;
    private CpfCnpjValidator cpfCnpjValidator;
    private CustomerEventProducer customerEventProducer;

    @GetMapping
    @ApiOperation(value = "List all customers")
    public ResponseEntity<List<CustomerOutputDTO>> listAll() {
        log.info("Searching for all customers");

        log.info("Converting accounts to outputDTO");
        List<CustomerOutputDTO> customerOutputDTOS = customerService.findAll().stream()
                .map(customerRestConverter::toCustomerOutputDTO)
                .collect(toList());

        log.info("Returning all customers found");
        return ResponseEntity.ok(customerOutputDTOS);
    }

    @PostMapping
    @ApiOperation(value = "Create a new customer")
    public ResponseEntity<CustomerOutputDTO> createCustomer(@RequestBody @Valid CustomerInputDTO customerInputDTO) {
        log.info("Customer received [{}]", customerInputDTO);

        CustomerBO customerBO = customerRestConverter.toBO(customerInputDTO);

        cpfCnpjValidator.validateCpfCnpj(customerBO);
        customerService.generateScore(customerBO);

        CustomerBO customerSaved = customerService.saveCustomer(customerBO);

        log.info("Converting customer saved to customerOutputDTO");
        CustomerOutputDTO customerOutputDTO = customerRestConverter.toCustomerOutputDTO(customerSaved);
        log.info("Customer has been converted to CustomerOutputDTO with body [{}]", customerOutputDTO);

        customerEventProducer.communicateCreatedCustomer(customerOutputDTO);

        return ResponseEntity.status(HTTP_CREATED).body(customerOutputDTO);
    }

}
