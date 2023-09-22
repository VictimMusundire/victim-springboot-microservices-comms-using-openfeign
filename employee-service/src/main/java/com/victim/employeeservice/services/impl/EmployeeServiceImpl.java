package com.victim.employeeservice.services.impl;

import com.victim.employeeservice.dtos.APIResponseDto;
import com.victim.employeeservice.dtos.DepartmentDto;
import com.victim.employeeservice.dtos.EmployeeDto;
import com.victim.employeeservice.entities.Employee;
import com.victim.employeeservice.repositories.EmployeeRepository;
import com.victim.employeeservice.services.EmployeeService;
import lombok.AllArgsConstructor;
import com.victim.employeeservice.services.APIClientDepartmentService;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class EmployeeServiceImpl implements EmployeeService {

    private EmployeeRepository employeeRepository;

    // Injecting the interface
    private APIClientDepartmentService apiClientDepartmentService;

    @Override
    public EmployeeDto saveEmployee(EmployeeDto employeeDto) {

        Employee employee = new Employee(
                employeeDto.getId(),
                employeeDto.getFirstName(),
                employeeDto.getLastName(),
                employeeDto.getEmail(),
                employeeDto.getDepartmentCode()
        );

        Employee saveDEmployee = employeeRepository.save(employee);

        EmployeeDto savedEmployeeDto = new EmployeeDto(
                saveDEmployee.getId(),
                saveDEmployee.getFirstName(),
                saveDEmployee.getLastName(),
                saveDEmployee.getEmail(),
                saveDEmployee.getDepartmentCode()
        );

        return savedEmployeeDto;
    }

    @Override
    public APIResponseDto getEmployeeById(Long employeeId) {

        Employee employee = employeeRepository.findById(employeeId).get();


        // Using FeignClient
        // You add openfeign dependency in your POM file.
        // Also configure your spring-cloud in your POM file.
        // Then add an interface for the service you need to reach
        // In the interface define the methods as they are in the target service.

        DepartmentDto departmentDto = apiClientDepartmentService.getDepartment(employee.getDepartmentCode());

        EmployeeDto employeeDto = new EmployeeDto(
                employee.getId(),
                employee.getFirstName(),
                employee.getLastName(),
                employee.getEmail(),
                employee.getDepartmentCode()
        );

        APIResponseDto apiResponseDto = new APIResponseDto();
        apiResponseDto.setEmployee(employeeDto);
        apiResponseDto.setDepartment(departmentDto);

        return apiResponseDto;
    }
}
