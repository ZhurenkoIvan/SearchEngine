package searchEngineApp.DTO;

import lombok.Data;

@Data
public class Total {
    private int sites;
    private long pages;
    private long lemmas;
    private boolean isIndexing;
}
