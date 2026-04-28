package com.example.demo.service;

import com.example.demo.entity.*;
import com.example.demo.repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.*;

@Service
public class ApplicationService {

    @Autowired
    private ApplicationRepository applicationRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private JobRepository jobRepository;

    // 🔥 APPLY JOB
    public String applyJob(Long userId, Long jobId) {

        // 1. Fetch user & job
        User user = userRepository.findById(userId).orElseThrow();
        Job job = jobRepository.findById(jobId).orElseThrow();

        // 2. Check job expiry
        if (!job.getActive() || job.getExpiryDate().isBefore(LocalDate.now())) {
            return "Job expired!";
        }

        // 3. Prevent duplicate apply
        Optional<Application> existing =
                applicationRepository.findByUserIdAndJobId(userId, jobId);

        if (existing.isPresent()) {
            return "Already applied!";
        }

        // 4. Calculate match score 🔥
        int matchScore = calculateMatch(user.getSkills(), job.getRequiredSkills());

        // 5. Create application
        Application app = new Application();
        app.setUser(user);
        app.setJob(job);
        app.setStatus("APPLIED");
        app.setMatchScore(matchScore);

        applicationRepository.save(app);

        String missingSkills = findMissingSkills(user.getSkills(), job.getRequiredSkills());

        return "Applied successfully! Match Score: " + matchScore + 
               "% | Missing Skills: " + missingSkills;    }
    private String findMissingSkills(String userSkills, String jobSkills) {

        Set<String> userSet = new HashSet<>(Arrays.asList(userSkills.toLowerCase().split(",")));
        Set<String> jobSet = new HashSet<>(Arrays.asList(jobSkills.toLowerCase().split(",")));

        jobSet.removeAll(userSet); // remove matched skills

        return String.join(", ", jobSet);
    }
    public List<Application> getTopCandidates(Long jobId) {
        return applicationRepository.findByJobIdOrderByMatchScoreDesc(jobId);
    }
   
    public List<Map<String, Object>> getUserApplications(Long userId) {

        List<Application> apps = applicationRepository.findByUserId(userId);

        List<Map<String, Object>> result = new ArrayList<>();

        for (Application app : apps) {
            Map<String, Object> map = new HashMap<>();
            map.put("id", app.getId());
            map.put("matchScore", app.getMatchScore());
            map.put("jobTitle", app.getJob().getTitle()); // 🔥 IMPORTANT
            result.add(map);
        }

        return result;
    }
    // 🔥 MATCHING LOGIC
    private int calculateMatch(String userSkills, String jobSkills) {

        Set<String> userSet = new HashSet<>(Arrays.asList(userSkills.toLowerCase().split(",")));
        Set<String> jobSet = new HashSet<>(Arrays.asList(jobSkills.toLowerCase().split(",")));

        userSet.retainAll(jobSet);

        return (userSet.size() * 100) / jobSet.size();
    }
}