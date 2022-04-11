package searchEngineApp.DTO;

import lombok.Data;
import searchEngineApp.ENUMS.Status;
@Data
public class Detailed {
    private String url;
    private String name;
    private Status status;
    private long statusTime;
    private String error;
    private int pages;
    private int lemmas;
}
