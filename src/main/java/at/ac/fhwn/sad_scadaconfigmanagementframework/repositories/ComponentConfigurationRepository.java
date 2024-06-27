package at.ac.fhwn.sad_scadaconfigmanagementframework.repositories;

import at.ac.fhwn.sad_scadaconfigmanagementframework.entities.ComponentConfiguration;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.Optional;

public interface ComponentConfigurationRepository extends JpaRepository<ComponentConfiguration, Long> {
    Optional<ComponentConfiguration> findByConfigIdAndComponentId(Long configId, Long componentId);
}
