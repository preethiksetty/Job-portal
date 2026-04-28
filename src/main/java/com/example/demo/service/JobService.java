package com.example.demo.service;

import com.example.demo.entity.Job;
import com.example.demo.repository.JobRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
public class JobService {

    @Autowired
    private JobRepository jobRepository;

    // 🔥 CREATE JOB
    public Job createJob(Job job) {
        job.setActive(true); // default active
        return jobRepository.save(job);
    }

    // 🔥 GET ACTIVE JOBS + AUTO EXPIRY
    public List<Job> getActiveJobs() {

        List<Job> jobs = jobRepository.findAll();

        for (Job job : jobs) {
            if (job.getExpiryDate().isBefore(LocalDate.now())) {
                job.setActive(false);
                jobRepository.save(job);
            }
        }

        return jobRepository.findByActiveTrue();
    }

}