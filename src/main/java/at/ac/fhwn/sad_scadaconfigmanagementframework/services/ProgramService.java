package at.ac.fhwn.sad_scadaconfigmanagementframework.services;

import at.ac.fhwn.sad_scadaconfigmanagementframework.DTOs.ComponentDto;
import at.ac.fhwn.sad_scadaconfigmanagementframework.DTOs.ConfigurationDto;
import at.ac.fhwn.sad_scadaconfigmanagementframework.DTOs.ProgramDto;
import at.ac.fhwn.sad_scadaconfigmanagementframework.entities.Component;
import at.ac.fhwn.sad_scadaconfigmanagementframework.entities.ComponentConfiguration;
import at.ac.fhwn.sad_scadaconfigmanagementframework.entities.Configuration;
import at.ac.fhwn.sad_scadaconfigmanagementframework.entities.Program;
import at.ac.fhwn.sad_scadaconfigmanagementframework.repositories.ComponentConfigurationRepository;
import at.ac.fhwn.sad_scadaconfigmanagementframework.repositories.ComponentRepository;
import at.ac.fhwn.sad_scadaconfigmanagementframework.repositories.ConfigurationRepository;
import at.ac.fhwn.sad_scadaconfigmanagementframework.repositories.ProgramRepository;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class ProgramService {

    private final ProgramRepository programRepository;
    private final ConfigurationRepository configurationRepository;
    private final ComponentRepository componentRepository;
    private final ComponentConfigurationRepository componentConfigurationRepository;
    private final ObjectMapper objectMapper;

    public ProgramService(ProgramRepository programRepository, ConfigurationRepository configurationRepository,
                          ComponentRepository componentRepository, ComponentConfigurationRepository componentConfigurationRepository,
                          ObjectMapper objectMapper) {
        this.programRepository = programRepository;
        this.configurationRepository = configurationRepository;
        this.componentRepository = componentRepository;
        this.componentConfigurationRepository = componentConfigurationRepository;
        this.objectMapper = objectMapper;
    }

    @Transactional
    public void addFullProgram(ProgramDto programDto) {
        Program program = new Program(programDto.getProgramName(), programDto.getDescription());
        programRepository.save(program);

        for (ConfigurationDto configDto : programDto.getConfigurations()) {
            Configuration config = new Configuration(configDto.getConfigName(), configDto.isActive(), program);
            configurationRepository.save(config);

            for (ComponentDto componentDto : configDto.getComponents()) {
                Component component = new Component(componentDto.getComponentName(), componentDto.getType(), componentDto.getDescription());
                componentRepository.save(component);

                try {
                    String settingsJson = objectMapper.writeValueAsString(componentDto.getSettings().getSettings());
                    ComponentConfiguration componentConfig = new ComponentConfiguration(config, component, settingsJson);
                    componentConfigurationRepository.save(componentConfig);
                } catch (JsonProcessingException e) {
                    throw new RuntimeException("Failed to serialize settings", e);
                }
            }
        }
    }

    @Transactional
    public void addComponentToConfiguration(Long configId, ComponentDto componentDto) {
        Configuration config = configurationRepository.findById(configId)
                .orElseThrow(() -> new IllegalArgumentException("Configuration not found"));

        Component component = new Component(componentDto.getComponentName(), componentDto.getType(), componentDto.getDescription());
        componentRepository.save(component);

        try {
            String settingsJson = objectMapper.writeValueAsString(componentDto.getSettings().getSettings());
            ComponentConfiguration componentConfig = new ComponentConfiguration(config, component, settingsJson);
            componentConfigurationRepository.save(componentConfig);
        } catch (JsonProcessingException e) {
            throw new RuntimeException("Failed to serialize settings", e);
        }
    }

    @Transactional
    public void updateComponentSettings(Long configId, Long componentId, SettingsDto settingsDto) {
        ComponentConfiguration componentConfig = componentConfigurationRepository.findByConfigIdAndComponentId(configId, componentId)
                .orElseThrow(() -> new IllegalArgumentException("Component configuration not found"));

        try {
            String settingsJson = objectMapper.writeValueAsString(settingsDto.getSettings());
            componentConfig.setSettings(settingsJson);
            componentConfigurationRepository.save(componentConfig);
        } catch (JsonProcessingException e) {
            throw new RuntimeException("Failed to serialize settings", e);
        }
    }
}
