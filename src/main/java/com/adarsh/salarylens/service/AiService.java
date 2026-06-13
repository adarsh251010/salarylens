package com.adarsh.salarylens.service;

import com.adarsh.salarylens.dto.AiAnalysisResponseDTO;
import com.adarsh.salarylens.dto.SalaryRequestDTO;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.List;
import java.util.Map;

@Service
public class AiService {

    @Value("${openai.api.key}")
    private String apiKey;

    @Value("${openai.api.url}")
    private String apiUrl;

    @Value("${openai.api.model}")
    private String model;

    private final RestTemplate restTemplate = new RestTemplate();

    @Cacheable(value = "salaryAnalysis", key = "#requestDTO.jobTitle + '-' + #requestDTO.location + '-' + #requestDTO.experienceYears")
    public AiAnalysisResponseDTO analyzeSalary(SalaryRequestDTO requestDTO) {
        String prompt = buildPrompt(requestDTO);
        String aiResponse = callOpenAI(prompt);
        return parseResponse(aiResponse);
    }
    private String buildPrompt(SalaryRequestDTO dto) {
        return String.format("""
                You are a salary negotiation expert for Indian job market.
                Analyze this salary situation and respond in exactly this JSON format:
                {
                    "analysis": "2-3 sentence market analysis",
                    "negotiationScript": "A professional script to negotiate salary",
                    "suggestedSalary": "Recommended salary range"
                }
                
                Job Title: %s
                Experience: %d years
                Location: %s
                Current Salary: %s
                Offered Salary: %s
                
                Respond ONLY with the JSON, no extra text.
                """,
                dto.getJobTitle(),
                dto.getExperienceYears(),
                dto.getLocation(),
                dto.getLastSalary(),
                dto.getSalaryOffered()
        );
    }

    private String callOpenAI(String prompt) {
        HttpHeaders headers = new HttpHeaders();
        headers.setBearerAuth(apiKey);
        headers.setContentType(MediaType.APPLICATION_JSON);

        Map<String, Object> body = Map.of(
                "model", model,
                "messages", List.of(
                        Map.of("role", "user", "content", prompt)
                ),
                "temperature", 0.7
        );

        HttpEntity<Map<String, Object>> request = new HttpEntity<>(body, headers);

        ResponseEntity<Map> response = restTemplate.postForEntity(
                apiUrl, request, Map.class);

        List<Map> choices = (List<Map>) response.getBody().get("choices");
        Map message = (Map) choices.get(0).get("message");
        return (String) message.get("content");
    }

    private AiAnalysisResponseDTO parseResponse(String jsonResponse) {
        try {
            jsonResponse = jsonResponse.trim();
            String analysis = extractField(jsonResponse, "analysis");
            String script = extractField(jsonResponse, "negotiationScript");
            String salary = extractField(jsonResponse, "suggestedSalary");
            return new AiAnalysisResponseDTO(analysis, script, salary);
        } catch (Exception e) {
            return new AiAnalysisResponseDTO(
                    "Analysis unavailable",
                    "Please try again",
                    "Unable to determine"
            );
        }
    }

    private String extractField(String json, String field) {
        String key = "\"" + field + "\"";
        int start = json.indexOf(key) + key.length() + 2;
        int end = json.indexOf("\"", start + 1);
        return json.substring(start + 1, end);
    }
}