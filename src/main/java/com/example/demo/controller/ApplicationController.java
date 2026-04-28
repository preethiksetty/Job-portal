package com.example.demo.controller;

import com.example.demo.entity.Application;
import com.example.demo.service.ApplicationService;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/applications")
@CrossOrigin(origins = "http://localhost:3000")
public class ApplicationController {

    @Autowired
    private ApplicationService applicationService;

    @GetMapping("/top-candidates")
    public List<Application> getTopCandidates(@RequestParam Long jobId) {
        return applicationService.getTopCandidates(jobId);
    }
    @GetMapping("/user-applications")
    public List<Map<String, Object>> getUserApplications(@RequestParam Long userId) {
        return applicationService.getUserApplications(userId);
    }
    @PostMapping("/apply")
    public String applyJob(@RequestParam Long userId,
                           @RequestParam Long jobId) {

        return applicationService.applyJob(userId, jobId);
    }
}