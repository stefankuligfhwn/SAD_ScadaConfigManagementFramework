package at.ac.fhwn.sad_scadaconfigmanagementframework.entities;

import javax.persistence.*;
import java.util.List;



@Entity
public class Program {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String programName;
    private String description;

    // Konstruktoren, Getter und Setter
}