package at.ac.fhwn.sad_scadaconfigmanagementframework.entities;

import javax.persistence.*;
import java.util.List;


@Entity
public class Component {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String componentName;
    private String type;
    private String description;

    // Konstruktoren, Getter und Setter
}