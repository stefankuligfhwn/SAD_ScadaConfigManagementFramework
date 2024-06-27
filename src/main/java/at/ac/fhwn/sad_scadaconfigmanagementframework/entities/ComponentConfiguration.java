package at.ac.fhwn.sad_scadaconfigmanagementframework.entities;

import jakarta.persistence.*;
import org.springframework.data.annotation.Id;

@Entity
public class ComponentConfiguration {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "config_id")
    private Configuration configuration;

    @ManyToOne
    @JoinColumn(name = "component_id")
    private Component component;

    @Lob
    private String settings;

    // Konstruktoren, Getter und Setter
}
