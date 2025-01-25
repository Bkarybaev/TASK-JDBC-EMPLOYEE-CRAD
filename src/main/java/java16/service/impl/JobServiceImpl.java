package java16.service.impl;

import java16.dao.impl.JobDaoImpl;
import java16.models.Job;
import java16.service.JobService;

import java.util.List;

public class JobServiceImpl implements JobService {
    private final JobDaoImpl jobDao = new JobDaoImpl();

    @Override
    public void createJobTable() {
        jobDao.createJobTable();
    }

    @Override
    public void addJob(Job job) {
        jobDao.addJob(job);
    }

    @Override
    public Job getJobById(Long jobId) {
        return jobDao.getJobById(jobId);
    }

    @Override
    public List<Job> sortByExperience(String ascOrDesc) {
        try {
            if (ascOrDesc.equals("asc") || ascOrDesc.equals("desc")) {
                return jobDao.sortByExperience(ascOrDesc);
            }
            throw new RuntimeException(ascOrDesc + " try again");

        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        return List.of();
    }

    @Override
    public Job getJobByEmployeeId(Long employeeId) {
        try {
            if (employeeId == null || employeeId <= 0) {
                throw new RuntimeException(employeeId + " try again");
            }
            return jobDao.getJobByEmployeeId(employeeId);
        }catch (Exception e) {
            System.out.println(e.getMessage());
        }
        return null;
    }

    @Override
    public void deleteDescriptionColumn() {
        try {
            jobDao.deleteDescriptionColumn();
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }
}
