package java16.service.impl;

import java16.dao.impl.EmployeeDaoImpl;
import java16.models.Employee;
import java16.models.Job;
import java16.service.EmployeeService;

import java.util.List;
import java.util.Map;

public class EmployeeServiceImpl implements EmployeeService {
    private final EmployeeDaoImpl employeeDao = new EmployeeDaoImpl();

    @Override
    public void createEmployee() {
        employeeDao.createEmployee();
    }

    @Override
    public void addEmployee(Employee employee) {
        employeeDao.addEmployee(employee);
    }

    @Override
    public void dropTable() {
        try {
            employeeDao.dropTable();
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    @Override
    public void cleanTable() {
        try {
            employeeDao.cleanTable();
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    @Override
    public void updateEmployee(Long id, Employee employee) {
        try {
            employeeDao.updateEmployee(id, employee);
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    @Override
    public List<Employee> getAllEmployees() {
        try {
           return employeeDao.getAllEmployees();
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        return null;

    }

    @Override
    public Employee findByEmail(String email) {
        try {
           return employeeDao.findByEmail(email);
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        return null;
    }

    @Override
    public Map<Employee, Job> getEmployeeById(Long employeeId) {
        try {
            return employeeDao.getEmployeeById(employeeId);
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        return Map.of();
    }

    @Override
    public List<Employee> getEmployeeByPosition(String position) {
        try {
            return employeeDao.getEmployeeByPosition(position);
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        return List.of();
    }
}
