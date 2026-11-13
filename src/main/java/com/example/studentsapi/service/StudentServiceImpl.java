package com.example.studentsapi.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.studentsapi.dto.StudentDto;
import com.example.studentsapi.entity.Student;
import com.example.studentsapi.exception.ResourceNotFoundException;
import com.example.studentsapi.repository.StudentRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class StudentServiceImpl implements StudentService {

    private final StudentRepository repository;

    @Override
    @Transactional(readOnly = true)
    public List<StudentDto> findAll() {
        return repository.findAll().stream().map(this::toDto).toList();
    }

    @Override
    @Transactional(readOnly = true)
    public StudentDto findById(Long id) {
        return toDto(getOrThrow(id));
    }

    @Override
    @Transactional
    public StudentDto create(StudentDto dto) {
        if (repository.existsByEmail(dto.getEmail())) {
            throw new IllegalArgumentException("Ya existe un estudiante con el email " + dto.getEmail());
        }
        Student student = toEntity(dto);
        student.setId(null);
        return toDto(repository.save(student));
    }

    @Override
    @Transactional
    public StudentDto update(Long id, StudentDto dto) {
        Student student = getOrThrow(id);
        if (repository.existsByEmailAndIdNot(dto.getEmail(), id)) {
            throw new IllegalArgumentException("Ya existe otro estudiante con el email " + dto.getEmail());
        }
        student.setFirstName(dto.getFirstName());
        student.setLastName(dto.getLastName());
        student.setEmail(dto.getEmail());
        student.setAge(dto.getAge());
        return toDto(repository.save(student));
    }

    @Override
    @Transactional
    public void delete(Long id) {
        repository.delete(getOrThrow(id));
    }

    private Student getOrThrow(Long id) {
        return repository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Estudiante no encontrado con id " + id));
    }

    private StudentDto toDto(Student s) {
        return StudentDto.builder()
                .id(s.getId())
                .firstName(s.getFirstName())
                .lastName(s.getLastName())
                .email(s.getEmail())
                .age(s.getAge())
                .build();
    }

    private Student toEntity(StudentDto d) {
        return Student.builder()
                .id(d.getId())
                .firstName(d.getFirstName())
                .lastName(d.getLastName())
                .email(d.getEmail())
                .age(d.getAge())
                .build();
    }
}
