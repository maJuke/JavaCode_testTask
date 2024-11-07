package com.karelin.testProject_javaCode.model;



import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
import java.util.UUID;
@Valid
@Setter
@Getter
public class WalletUpdater implements Serializable {
    @NotNull(message = "valletId is mandatory!")
    @JsonProperty("valletId")
    private UUID valletId;
    @NotBlank(message = "Operation type is mandatory!")
    @JsonProperty("operationType")
    private String operationType;
    @NotNull(message = "Amount is mandatory!")
    @Positive(message = "Amount should be positive!")
    private double amount;
}
