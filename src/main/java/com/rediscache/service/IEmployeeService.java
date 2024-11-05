package com.rediscache.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.rediscache.entity.Employee;
import com.rediscache.responce.EmployeeResponce;

import java.util.List;

public interface IEmployeeService {
    public EmployeeResponce saveUser(Employee employee) throws JsonProcessingException;
   public  EmployeeResponce getEmployeeById(Long id);

    String deleteById(Long id);

    String deleteByMultiId(List<Long> ids);
}
