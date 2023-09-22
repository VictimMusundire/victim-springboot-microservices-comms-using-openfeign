package com.victim.employeeservice.services;

import com.victim.employeeservice.dtos.APIResponseDto;
import com.victim.employeeservice.dtos.EmployeeDto;

public interface EmployeeService {
    EmployeeDto saveEmployee(EmployeeDto employeeDto);

    APIResponseDto getEmployeeById(Long employeeId);
}
