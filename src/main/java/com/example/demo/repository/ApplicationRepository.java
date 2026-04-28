package com.example.demo.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import com.example.demo.entity.Application;

import java.util.List;
import java.util.Optional;
//duplicate application prevention
public interface ApplicationRepository extends JpaRepository<Application, Long> {

	List<Application> findByJobIdOrderByMatchScoreDesc(Long jobId);
	List<Application> findByUserId(Long userId);
	
	Optional<Application> findByUserIdAndJobId(Long userId, Long jobId);
	}