package com.rediscache.util;

import com.rediscache.entity.Employee;
import com.rediscache.responce.EmployeeResponce;

import java.time.LocalDate;
import java.time.Period;
import java.time.format.DateTimeFormatter;

public class CommonUtil {
    public static int calculateAge(String dob) {
        LocalDate birthDate = null;
        try {
            DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
            birthDate = LocalDate.parse(dob, dateTimeFormatter);
        } catch (Exception e) {
            e.printStackTrace();
        }

        return Period.between(birthDate, LocalDate.now()).getYears();
    }

    public static EmployeeResponce entityToEmployeeResponce(Employee employee) {
        if (employee == null) {
            return null;
        }
        return EmployeeResponce.builder()
                .id(employee.getID())
                .fullName(employee.getFullName())
                .email(employee.getEmail())
                .dob(employee.getDob())
                .age(employee.getAge())
                .build();
    }
}
