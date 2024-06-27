package at.ac.fhwn.sad_scadaconfigmanagementframework.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

public interface ConfigurationRepository extends JpaRepository<Configuration, Long> {
}