package com.rediscache.controller;

import com.rediscache.entity.Employee;
import com.rediscache.responce.EmployeeResponce;
import com.rediscache.service.IEmployeeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/employee")
public class EmployeeController {
    @Autowired
    private IEmployeeService iEmployeeService;
    @PostMapping("/save")
    public ResponseEntity<EmployeeResponce> saveUser(@RequestBody Employee employee){
        EmployeeResponce employeeResponce ;
        try{
           employeeResponce= iEmployeeService.saveUser(employee);
        }catch (Exception e){
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();

        }

        return new ResponseEntity<>(employeeResponce,HttpStatus.CREATED);

    }
    @GetMapping("/getEmployeeById/{id}")
    public ResponseEntity<EmployeeResponce> employeeGetById(@PathVariable Long id){
        EmployeeResponce employeeResponce ;
        try {
   employeeResponce=iEmployeeService.getEmployeeById(id);
        }catch (Exception e){
            e.printStackTrace();
            return  ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }
        return new ResponseEntity<>(employeeResponce,HttpStatus.OK);

    }
    @DeleteMapping("/deleteById/{id}")
    public String deleteById(@PathVariable Long id){
      return   iEmployeeService.deleteById(id);
    }
    @DeleteMapping("/deleteByMultiId")
    public String deleteById(@RequestBody List<Long> ids) {
        return iEmployeeService.deleteByMultiId(ids);
    }
}
