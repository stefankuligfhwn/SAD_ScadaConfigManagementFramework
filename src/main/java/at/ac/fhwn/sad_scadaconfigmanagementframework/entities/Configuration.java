package at.ac.fhwn.sad_scadaconfigmanagementframework.entities;

import javax.persistence.*;
import java.util.List;



@Entity
public class Configuration {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String configName;
    private boolean active;

    @ManyToOne
    @JoinColumn(name = "program_id")
    private Program program;

    // Konstruktoren, Getter und Setter
}