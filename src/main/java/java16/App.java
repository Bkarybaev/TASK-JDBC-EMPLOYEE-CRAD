package java16;

import java16.config.DBConfig;
import java16.models.Employee;
import java16.models.Job;
import java16.service.EmployeeService;
import java16.service.JobService;
import java16.service.impl.EmployeeServiceImpl;
import java16.service.impl.JobServiceImpl;

import java.sql.SQLException;

/**
 * Hello world!
 *
 */
public class App 
{
    public static void main( String[] args ) {
        JobService jobService = new JobServiceImpl();
        EmployeeService employeeService = new EmployeeServiceImpl();

        //todo create job table
//        jobService.createJobTable();

        //todo create employee table
//        employeeService.createEmployee();


        //todo add job
//        jobService.addJob(new Job("Mentor","Java","Backend developer",3));
//        jobService.addJob(new Job("Management","Java","Backend developer",3));
//        jobService.addJob(new Job("Instructor","JavaScript","Fronted developer",23));

        //todo add employee
//        employeeService.addEmployee(new Employee("Urmat","Taichikov",20,"example@example.com",1));
//        employeeService.addEmployee(new Employee("Aizat","Aibekov",20,"aizat@gmail.com",3));

        //todo get by id job
//        System.out.println(jobService.getJobById(1L));

        //todo sort By Experience
//        jobService.sortByExperience("desc").forEach(System.out::println);

        //todo get Job By Employee Id
//        System.out.println(jobService.getJobByEmployeeId(64L));

        //todo delete Description Column
//        jobService.deleteDescriptionColumn();
//        System.out.println(jobService.getJobById(1L));

        //todo delete drop table
//        employeeService.dropTable();

        //todo clean table
//        employeeService.createEmployee();

        //todo get all employee
//        employeeService.getAllEmployees().forEach(System.out::println);

        //todo update employee
//        employeeService.updateEmployee(5L,new Employee("Rabia","Aibekovna",20,"rabia@gmail.com",2));
//        employeeService.getAllEmployees().forEach(System.out::println);

        //todo find by email employee
//        System.out.println(employeeService.findByEmail("rabia@gmail.com"));

        //todo get map employee
//        System.out.println(employeeService.getEmployeeById(5L));

        //todo get employee by position
        employeeService.getEmployeeByPosition("Management").forEach(System.out::println);
    }
}
