package com.devsuperior.dscatalog.dto;

import com.devsuperior.dscatalog.service.validation.UserInsertValid;
import com.devsuperior.dscatalog.service.validation.UserUpdateValid;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@UserUpdateValid
public class UserUpdateDTO extends UserDTO implements Serializable {

}
