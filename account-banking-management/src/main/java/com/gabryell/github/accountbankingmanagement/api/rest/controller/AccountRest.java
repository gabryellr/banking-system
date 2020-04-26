package com.gabryell.github.accountbankingmanagement.api.rest.controller;

import com.gabryell.github.accountbankingmanagement.api.rest.converter.AccountDtoConverter;
import com.gabryell.github.accountbankingmanagement.api.rest.model.AccountOutputDTO;
import com.gabryell.github.accountbankingmanagement.core.account.model.AccountBO;
import com.gabryell.github.accountbankingmanagement.core.account.service.AccountService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@Slf4j
@RequestMapping("/api/v1/accounts")
@RestController
@AllArgsConstructor
@Api(value = "API Rest to get accounts information")
public class AccountRest {

    private AccountService accountService;
    private AccountDtoConverter accountDtoConverter;

    @GetMapping
    @ApiOperation(value = "List all accounts")
    public ResponseEntity<List<AccountOutputDTO>> listAll() {
        log.info("Searching for all accounts");
        List<AccountBO> accountsFound = accountService.listAll();

        log.info("Converting accounts to OutputDTO if found");
        List<AccountOutputDTO> accountOutputDTOS = accountDtoConverter.toOutputDTO(accountsFound);

        log.info("Returning all invoices if found");
        return ResponseEntity.ok(accountOutputDTOS);
    }

}
