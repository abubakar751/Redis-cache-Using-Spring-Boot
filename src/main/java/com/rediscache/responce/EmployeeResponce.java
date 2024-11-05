package com.rediscache.responce;

import lombok.Builder;

import java.time.LocalDate;
@Builder
public record EmployeeResponce(Long id, String fullName, String email, LocalDate dob,int age) {
}
