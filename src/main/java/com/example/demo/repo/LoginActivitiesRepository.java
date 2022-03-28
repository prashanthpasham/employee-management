package com.example.demo.repo;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.demo.model.LoginActivities;

public interface LoginActivitiesRepository extends JpaRepository<LoginActivities, Long> {

	LoginActivities findByUserId(Long userId);

}
