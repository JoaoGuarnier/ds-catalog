package com.devsuperior.dscatalog.dto;

import com.devsuperior.dscatalog.service.validation.UserInsertValid;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@NoArgsConstructor
@AllArgsConstructor
@UserInsertValid
public class UserInsertDTO extends UserDTO implements Serializable {

    private String password;

}
