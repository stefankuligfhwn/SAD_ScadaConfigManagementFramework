package at.ac.fhwn.sad_scadaconfigmanagementframework.services;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class ConfigurationComponentService {

    @Value("${configuration.component.url}")
    private String configComponentUrl;

    private final RestTemplate restTemplate;

    public ConfigurationComponentService(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    public boolean checkAvailability() {
        try {
            HttpStatus statusCode = restTemplate.headForHeaders(configComponentUrl + "/health").getStatusCode();
            return statusCode == HttpStatus.OK;
        } catch (Exception e) {
            return false;
        }
    }

    public ResponseEntity<String> sendFullProgramConfiguration(ProgramDto programDto) {
        if (!checkAvailability()) {
            return ResponseEntity.status(HttpStatus.SERVICE_UNAVAILABLE).body("Configuration component is not available");
        }

        HttpHeaders headers = new HttpHeaders();
        headers.set("Content-Type", "application/json");

        HttpEntity<ProgramDto> request = new HttpEntity<>(programDto, headers);

        try {
            ResponseEntity<String> response = restTemplate.postForEntity(configComponentUrl + "/programs/full", request, String.class);
            return response;
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Failed to send program configuration: " + e.getMessage());
        }
    }
}
