package searchEngineApp.entity;

import lombok.Data;
import searchEngineApp.ENUMS.Status;

import javax.persistence.*;
import java.sql.Date;

@Entity
@Table(name = "sites")
@Data
public class Site {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    private Status status;
    private Date statusTime;
    private String lastError;
    private String url;
    private String name;
}
