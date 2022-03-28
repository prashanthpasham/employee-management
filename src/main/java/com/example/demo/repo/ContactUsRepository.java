package com.example.demo.repo;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.demo.model.ContactUs;

public interface ContactUsRepository extends JpaRepository<ContactUs, Integer> {

}
