package searchEngineApp.DTO;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Data
public class SiteDTO {
    private String url;
    private String name;
}
