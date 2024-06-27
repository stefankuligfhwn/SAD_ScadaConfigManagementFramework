package at.ac.fhwn.sad_scadaconfigmanagementframework.controller;

import at.ac.fhwn.sad_scadaconfigmanagementframework.DTOs.ComponentDto;
import at.ac.fhwn.sad_scadaconfigmanagementframework.services.ConfigurationComponentService;
import at.ac.fhwn.sad_scadaconfigmanagementframework.services.ProgramService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/configurations")
public class ConfigurationController {

    private final ConfigurationComponentService configService;
    private final ProgramService programService;

    public ConfigurationController(ConfigurationComponentService configService, ProgramService programService) {
        this.configService = configService;
        this.programService = programService;
    }

    @PostMapping("/{configId}/components")
    public ResponseEntity<String> addComponentToConfiguration(@PathVariable Long configId, @RequestBody ComponentDto componentDto) {
        if (!configService.checkAvailability()) {
            return ResponseEntity.status(HttpStatus.SERVICE_UNAVAILABLE).body("Configuration component is not available");
        }

        programService.addComponentToConfiguration(configId, componentDto);
        return ResponseEntity.status(HttpStatus.CREATED).body("Component added successfully");
    }

    @PutMapping("/{configId}/components/{componentId}/settings")
    public ResponseEntity<String> updateComponentSettings(@PathVariable Long configId, @PathVariable Long componentId, @RequestBody SettingsDto settingsDto) {
        if (!configService.checkAvailability()) {
            return ResponseEntity.status(HttpStatus.SERVICE_UNAVAILABLE).body("Configuration component is not available");
        }

        programService.updateComponentSettings(configId, componentId, settingsDto);
        return ResponseEntity.ok("Component settings updated successfully");
    }
}
