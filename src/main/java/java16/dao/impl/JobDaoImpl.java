package java16.dao.impl;

import java16.config.DBConfig;
import java16.dao.JobDao;
import java16.models.Job;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class JobDaoImpl implements JobDao {
    private final Connection connection = DBConfig.getConnection();

    @Override
    public void createJobTable() {
        String sql = """
                create table if not exists jobs (
                id serial primary key,
                position varchar(20) not null,
                profession varchar(20) not null,
                description varchar(20) not null,
                experience int not null
                );
                """;
        try (Statement stmt = connection.createStatement()) {
            stmt.execute(sql);
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    @Override
    public void addJob(Job job) {
        String sql = """
                insert into jobs (position, profession, description, experience)
                values (?,?,?,?)
                """;
        try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            preparedStatement.setString(1, job.getPosition());
            preparedStatement.setString(2, job.getProfession());
            preparedStatement.setString(3, job.getDescription());
            preparedStatement.setInt(4, job.getExperience());
            preparedStatement.executeUpdate();
            System.out.println("Job added");
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    @Override
    public Job getJobById(Long jobId) {
        String sql = """
                select * from jobs where id = ?
                """;
        Job job = new Job();
        try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            preparedStatement.setLong(1, jobId);
            ResultSet resultSet = preparedStatement.executeQuery();

            while (resultSet.next()) {
                job.setId(resultSet.getLong("id"));
                job.setPosition(resultSet.getString("position"));
                job.setProfession(resultSet.getString("profession"));
                job.setDescription(resultSet.getString("description"));
                job.setExperience(resultSet.getInt("experience"));

            }

        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return job;
    }

    @Override
    public List<Job> sortByExperience(String ascOrDesc) {
        List<Job> jobs = new ArrayList<>();
        String sql = null;
        if (ascOrDesc.equals("asc") ) {
          sql =  ("""
                select * from jobs order by experience asc
                """);
        }else {
            sql = ("""
                select * from jobs order by experience desc
                """);
        }

        try(PreparedStatement preparedStatement = connection.prepareStatement(sql)){
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                Job job = new Job();
                job.setId(resultSet.getLong("id"));
                job.setPosition(resultSet.getString("position"));
                job.setProfession(resultSet.getString("profession"));
                job.setDescription(resultSet.getString("description"));
                job.setExperience(resultSet.getInt("experience"));
                jobs.add(job);
            }
        }catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return jobs;
    }

    @Override
    public Job getJobByEmployeeId(Long employeeId) {
        String sql = """
                select * from jobs join employees on employees.job_id = jobs.id where employees.id = ?
                """;
        try (PreparedStatement preparedStatement = connection.prepareStatement(sql)){
            preparedStatement.setLong(1, employeeId);
            ResultSet resultSet = preparedStatement.executeQuery();
            Job job = new Job();
            if (resultSet.next()) {
                job.setId(resultSet.getLong("id"));
                job.setPosition(resultSet.getString("position"));
                job.setProfession(resultSet.getString("profession"));
                job.setDescription(resultSet.getString("description"));
                job.setExperience(resultSet.getInt("experience"));
            return job;
            }else {
                throw new RuntimeException("Job not found");
            }
        }catch (SQLException e) {
            throw new RuntimeException(e.getMessage());
        }
    }

    @Override
    public void deleteDescriptionColumn() {
        String sql = """
                alter table jobs drop column if exists description
                """;
        try(PreparedStatement preparedStatement = connection.prepareStatement(sql)){
            preparedStatement.executeUpdate();
        }catch (SQLException e) {
            throw new RuntimeException(e.getMessage());
        }

    }
}
