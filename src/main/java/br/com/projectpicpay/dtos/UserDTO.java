package br.com.projectpicpay.dtos;

import br.com.projectpicpay.model.entities.user.UserType;

import java.math.BigDecimal;

public record UserDTO(String firstName, String lastName, String document, BigDecimal balance, String email, String password, UserType userType) {
}
