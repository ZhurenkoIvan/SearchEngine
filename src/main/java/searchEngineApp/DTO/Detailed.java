package searchEngineApp.DTO;

import lombok.Data;
import searchEngineApp.ENUMS.Status;

import java.sql.Date;

@Data
public class Detailed {
    private String url;
    private String name;
    private Status status;
    private Date statusTime;
    private String error;
    private long pages;
    private long lemmas;
}
