package com.adarsh.salarylens.service;

import com.adarsh.salarylens.dto.AiAnalysisResponseDTO;
import com.adarsh.salarylens.dto.SalaryRequestDTO;
import org.springframework.stereotype.Service;

import java.util.Map;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.CompletableFuture;

@Service
public class AsyncJobService {

    private final Map<String, Object> jobStore = new ConcurrentHashMap<>();
    private final Map<String, String> jobOwner = new ConcurrentHashMap<>();
    private final AiService aiService;

    public AsyncJobService(AiService aiService) {
        this.aiService = aiService;
    }

    public String submitJob(SalaryRequestDTO requestDTO, String username) {
        String jobId = UUID.randomUUID().toString();
        jobStore.put(jobId, "PROCESSING");
        jobOwner.put(jobId, username);

        CompletableFuture.runAsync(() -> {
            try {
                AiAnalysisResponseDTO result = aiService.analyzeSalary(requestDTO);
                jobStore.put(jobId, result);
            } catch (Exception e) {
                jobStore.put(jobId, "FAILED");
            }
        });

        return jobId;
    }

    public Object getJobResult(String jobId, String username) {
        String owner = jobOwner.get(jobId);
        if (owner == null) return "JOB_NOT_FOUND";
        if (!owner.equals(username)) return "UNAUTHORIZED";
        return jobStore.getOrDefault(jobId, "JOB_NOT_FOUND");
    }
}