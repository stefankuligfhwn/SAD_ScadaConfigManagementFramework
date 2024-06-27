package at.ac.fhwn.sad_scadaconfigmanagementframework.controller;

import at.ac.fhwn.sad_scadaconfigmanagementframework.DTOs.ProgramDto;
import at.ac.fhwn.sad_scadaconfigmanagementframework.services.ConfigurationComponentService;
import at.ac.fhwn.sad_scadaconfigmanagementframework.services.ProgramService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/programs")
public class ProgramController {

    private final ConfigurationComponentService configService;
    private final ProgramService programService;

    public ProgramController(ConfigurationComponentService configService, ProgramService programService) {
        this.configService = configService;
        this.programService = programService;
    }

    @PostMapping("/full")
    public ResponseEntity<String> addFullProgram(@RequestBody ProgramDto programDto) {
        ResponseEntity<String> response = configService.sendFullProgramConfiguration(programDto);
        if (response.getStatusCode() == HttpStatus.CREATED) {
            programService.addFullProgram(programDto);
        }
        return response;
    }
}