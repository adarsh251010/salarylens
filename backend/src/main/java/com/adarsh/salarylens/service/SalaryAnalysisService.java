package com.adarsh.salarylens.service;

import com.adarsh.salarylens.dto.SalaryRequestDTO;
import com.adarsh.salarylens.dto.SalaryResponseDTO;
import com.adarsh.salarylens.entity.SalaryData;
import com.adarsh.salarylens.exception.SalaryNotFoundException;
import com.adarsh.salarylens.repository.SalaryDataRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class SalaryAnalysisService {

    private final SalaryDataRepository repository;

    public SalaryAnalysisService(SalaryDataRepository repository) {
        this.repository = repository;
    }

    public SalaryResponseDTO saveSalary(SalaryRequestDTO requestDTO, String username) {
        SalaryData entity = new SalaryData();
        entity.setJobTitle(requestDTO.getJobTitle());
        entity.setExperienceYears(requestDTO.getExperienceYears());
        entity.setSalaryOffered(requestDTO.getSalaryOffered());
        entity.setLastSalary(requestDTO.getLastSalary());
        entity.setLocation(requestDTO.getLocation());
        entity.setUsername(username);

        SalaryData saved = repository.save(entity);
        return convertToDTO(saved);
    }

    public List<SalaryResponseDTO> getAllSalaries(String username) {
        return repository.findByUsername(username)
                .stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    private SalaryResponseDTO convertToDTO(SalaryData entity) {
        SalaryResponseDTO dto = new SalaryResponseDTO();
        dto.setId(entity.getId());
        dto.setJobTitle(entity.getJobTitle());
        dto.setExperienceYears(entity.getExperienceYears());
        dto.setSalaryOffered(entity.getSalaryOffered());
        dto.setLastSalary(entity.getLastSalary());
        dto.setLocation(entity.getLocation());
        return dto;
    }

    public SalaryResponseDTO getSalaryById(Long id, String username) {
        SalaryData entity = repository.findByIdAndUsername(id, username)
                .orElseThrow(() -> new SalaryNotFoundException(id));
        return convertToDTO(entity);
    }

    public void deleteSalary(Long id, String username) {
        SalaryData entity = repository.findByIdAndUsername(id, username)
                .orElseThrow(() -> new SalaryNotFoundException(id));
        repository.delete(entity);
    }

    public SalaryResponseDTO updateSalary(Long id, SalaryRequestDTO requestDTO, String username) {
        SalaryData entity = repository.findByIdAndUsername(id, username)
                .orElseThrow(() -> new SalaryNotFoundException(id));

        entity.setJobTitle(requestDTO.getJobTitle());
        entity.setExperienceYears(requestDTO.getExperienceYears());
        entity.setSalaryOffered(requestDTO.getSalaryOffered());
        entity.setLastSalary(requestDTO.getLastSalary());
        entity.setLocation(requestDTO.getLocation());

        SalaryData updated = repository.save(entity);
        return convertToDTO(updated);
    }

    public Page<SalaryResponseDTO> getAllSalariesPaginated(int page, int size, String username) {
        Pageable pageable = PageRequest.of(page, size);
        return repository.findByUsername(username, pageable)
                .map(this::convertToDTO);
    }

    public List<SalaryResponseDTO> getSalariesByLocation(String location, String username) {
        return repository.findByUsernameAndLocation(username, location)
                .stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    public List<SalaryResponseDTO> getSalariesByExperience(Integer years, String username) {
        return repository.findByUsernameAndExperienceYearsGreaterThanEqual(username, years)
                .stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }
}