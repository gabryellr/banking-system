package com.gabryell.github.customermanagement.api.controller.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class CustomerInputDTO {

    @NotBlank(message = "Name cannot be null")
    private String name;

    @NotNull(message = "Document cannot be null")
    private Document document;

    @Data
    @AllArgsConstructor
    @NoArgsConstructor
    @Builder
    public static class Document {

        @NotNull(message = "Document Number cannot be null")
        private String documentNumber;

        @NotNull(message = "Document Number cannot be null")
        private PersonTypeEnum personTypeEnum;

    }

    public enum PersonTypeEnum {

        PF, PJ

    }

}
