package searchEngineApp.DTO;

import lombok.Data;

@Data
public class Total {
    private int sites;
    private int pages;
    private int lemmas;
    private boolean isIndexing;
}
