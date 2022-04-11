package searchEngineApp.DTO;

import lombok.Data;

import java.util.List;
@Data
public class Statistics {
    private Total total;
    private List<Detailed> detailed;
}
