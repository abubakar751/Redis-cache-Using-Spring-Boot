package com.rediscache.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.rediscache.entity.Employee;
import com.rediscache.repo.EmployeeRepository;
import com.rediscache.responce.EmployeeResponce;
import com.rediscache.util.CommonUtil;
import com.rediscache.util.RedisService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class EmployeeService implements IEmployeeService {
    @Autowired
    private EmployeeRepository employeeRepository;
    @Autowired
    private RedisService redisService;
    private static final String EMPLoYEE_KEY = "EMP_";

    @Override
    public EmployeeResponce saveUser(Employee employee) throws JsonProcessingException {
        //  int i = CommonUtil.calculateAge(employee.getDob().toString());
        employee.setAge(CommonUtil.calculateAge(employee.getDob().toString()));
        Employee save = employeeRepository.save(employee);
        redisService.setKey(EMPLoYEE_KEY + save.getID().toString(), save, 300l);
        return CommonUtil.entityToEmployeeResponce(save);


    }

    @Override
    public EmployeeResponce getEmployeeById(Long id) {
        Employee employee = redisService.getValueByKey(EMPLoYEE_KEY + id.toString(), Employee.class);
        if (employee != null) {
            return EmployeeResponce.builder()
                    .id(employee.getID())
                    .fullName(employee.getFullName())
                    .email(employee.getEmail())
                    .dob(employee.getDob())
                    .age(employee.getAge())
                    .build();
        }
        Optional<Employee> employee1 = employeeRepository.findById(id);

        return employee1.isPresent() ? CommonUtil.entityToEmployeeResponce(employee1.get()) : null;
    }

    @Override
    public String deleteById(Long id) {
        boolean isDeleted = redisService.deleteFromRedisCache(EMPLoYEE_KEY + id.toString());

        return isDeleted ? id.toString() + "is deleted successfully !" : "key not found";
    }

    @Override
    public String deleteByMultiId(List<Long> ids) {
        List<String> list = ids.stream().map(id -> EMPLoYEE_KEY + id).toList();
        Long deletedCount = redisService.deleteMultiFromRedisCache(list);

        return deletedCount > 0 ? deletedCount.toString() + " record deleted " : " not found";
    }


}
