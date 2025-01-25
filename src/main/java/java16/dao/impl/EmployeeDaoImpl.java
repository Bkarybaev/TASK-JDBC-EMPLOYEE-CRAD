package java16.dao.impl;

import java16.config.DBConfig;
import java16.dao.EmployeeDao;
import java16.models.Employee;
import java16.models.Job;

import java.sql.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class EmployeeDaoImpl implements EmployeeDao {
    private final Connection connection = DBConfig.getConnection();

    @Override
    public void createEmployee() {
        String sql = """
                create table employees(
                id serial primary key,
                first_name varchar(20) not null,
                last_name varchar(20)  not null,
                age int not null,
                email varchar(100) not null check(email ~* ('^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\\\.[a-zA-Z]{2,}$')),
                job_id int references jobs(id));
                """;

        try (Statement statement = connection.createStatement()) {
            statement.execute(sql);
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    @Override
    public void addEmployee(Employee employee) {
        String sql = """
                insert into employees (first_name, last_name, age, email, job_id)
                values (?, ?, ?, ?, ?)
                """;
        try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            preparedStatement.setString(1, employee.getFirstName());
            preparedStatement.setString(2, employee.getLastName());
            preparedStatement.setInt(3, employee.getAge());
            preparedStatement.setString(4, employee.getEmail());
            preparedStatement.setInt(5, employee.getJobId());
            preparedStatement.executeUpdate();
            System.out.println("Employee added");
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    @Override
    public void dropTable() {
        String sql = """
                drop table employees
                """;
        try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e.getMessage());
        }
    }

    @Override
    public void cleanTable() {
        String sql = """
                truncate table employees
                """;
        try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e.getMessage());
        }
    }

    @Override
    public void updateEmployee(Long id, Employee employee) {
        String sql = """
                update employees set first_name = ?, last_name = ?, age = ?, email = ?, job_id = ?
                where id = ?
                """;

        try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            preparedStatement.setString(1, employee.getFirstName());
            preparedStatement.setString(2, employee.getLastName());
            preparedStatement.setInt(3, employee.getAge());
            preparedStatement.setString(4, employee.getEmail());
            preparedStatement.setInt(5, employee.getJobId());
            preparedStatement.setLong(6, id);
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e.getMessage());
        }
    }

    @Override
    public List<Employee> getAllEmployees() {
        String sql = """
                select * from employees
                """;
        List<Employee> employees = new ArrayList<>();
        try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            preparedStatement.execute();
            ResultSet resultSet = preparedStatement.getResultSet();
            while (resultSet.next()) {
                Employee employee = new Employee();
                employee.setId(resultSet.getLong("id"));
                employee.setFirstName(resultSet.getString("first_name"));
                employee.setLastName(resultSet.getString("last_name"));
                employee.setAge(resultSet.getInt("age"));
                employee.setEmail(resultSet.getString("email"));
                employee.setJobId(resultSet.getInt("job_id"));
                employees.add(employee);
            }
            return employees;
        } catch (SQLException e) {
            throw new RuntimeException(e.getMessage());
        }
    }

    @Override
    public Employee findByEmail(String email) {
        String sql = """
                select * from employees where email = ?
                """;
        try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            preparedStatement.setString(1, email);
            ResultSet resultSet = preparedStatement.executeQuery();
            Employee employee = new Employee();
            if (resultSet.next()) {
                employee.setId(resultSet.getLong("id"));
                employee.setFirstName(resultSet.getString("first_name"));
                employee.setLastName(resultSet.getString("last_name"));
                employee.setAge(resultSet.getInt("age"));
                employee.setEmail(resultSet.getString("email"));
                employee.setJobId(resultSet.getInt("job_id"));
                return employee;
            } else {
                return null;
            }
        } catch (SQLException e) {
            throw new RuntimeException(e.getMessage());
        }
    }

    @Override
    public Map<Employee, Job> getEmployeeById(Long employeeId) {
        String sql = """
                select e.id as employee_id, e.first_name, e.last_name, e.age, e.email, e.job_id,
                                          j.id as job_id, j.position,j.profession,  j.experience from employees e join jobs j on e.job_id = j.id where e.id = ?
                """;
        Map<Employee, Job> employees = new HashMap<>();
        try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            preparedStatement.setLong(1, employeeId);
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                Employee employee = new Employee();
                Job job = new Job();

                employee.setId(resultSet.getLong("employee_id"));
                employee.setFirstName(resultSet.getString("first_name"));
                employee.setLastName(resultSet.getString("last_name"));
                employee.setAge(resultSet.getInt("age"));
                employee.setEmail(resultSet.getString("email"));
                employee.setJobId(resultSet.getInt("job_id"));

                job.setId(resultSet.getLong("job_id"));
                job.setProfession(resultSet.getString("profession"));
                job.setPosition(resultSet.getString("position"));
                job.setExperience(resultSet.getInt("experience"));

                employees.put(employee, job);
            }
            return employees;
        } catch (SQLException e) {
            throw new RuntimeException(e.getMessage());
        }
    }

    @Override
    public List<Employee> getEmployeeByPosition(String position) {
        String sql = """
                select e.* from employees e join jobs j on j.id = e.job_id where j.position = ?
                """;
        List<Employee> employees = new ArrayList<>();
        try (PreparedStatement preparedStatement = connection.prepareStatement(sql)){
            preparedStatement.setString(1, position);
            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                Employee employee = new Employee();

                employee.setId(resultSet.getLong("id"));
                employee.setFirstName(resultSet.getString("first_name"));
                employee.setLastName(resultSet.getString("last_name"));
                employee.setAge(resultSet.getInt("age"));
                employee.setEmail(resultSet.getString("email"));
                employee.setJobId(resultSet.getInt("job_id"));

                employees.add(employee);
            }else {
                System.out.println("employee found");
            }
            return employees;
        }catch (SQLException e) {
            throw new RuntimeException(e.getMessage());
        }
    }
}
