package com.example.studentsapi.service;

import java.util.List;

import com.example.studentsapi.dto.StudentDto;

public interface StudentService {

    List<StudentDto> findAll();

    StudentDto findById(Long id);

    StudentDto create(StudentDto dto);

    StudentDto update(Long id, StudentDto dto);

    void delete(Long id);
}
