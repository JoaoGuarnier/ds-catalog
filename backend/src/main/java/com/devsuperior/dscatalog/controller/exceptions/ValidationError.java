package com.devsuperior.dscatalog.controller.exceptions;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ValidationError extends StandardError implements Serializable {

    private List<FieldMessage> errors = new ArrayList<>();

    public void addError(String fieldName, String message) {
        this.errors.add(new FieldMessage(fieldName,message));
    }

}
