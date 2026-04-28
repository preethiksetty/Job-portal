package com.example.demo.controller;

import com.example.demo.entity.Job;
import com.example.demo.service.JobService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/jobs")
@CrossOrigin(origins = "http://localhost:3000")
public class JobController {

    @Autowired
    private JobService jobService;

    // 🔥 CREATE JOB
    @PostMapping("/create")
    public Job createJob(@RequestBody Job job) {
        return jobService.createJob(job);
    }

    // 🔥 GET ACTIVE JOBS
    @GetMapping("/active")
    public List<Job> getActiveJobs() {
        return jobService.getActiveJobs();
    }
}