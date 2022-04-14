package searchEngineApp.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import searchEngineApp.entity.Site;

public interface SiteRepo extends JpaRepository<Site, Integer> {
    public Site findByUrl(String url);
}
