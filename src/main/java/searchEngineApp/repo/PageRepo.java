package searchEngineApp.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.transaction.annotation.Transactional;
import searchEngineApp.entity.Page;

import java.util.List;

public interface PageRepo extends JpaRepository<Page, Integer> {
    public List<Page> findAllBySiteId(int siteId);
    public long countBySiteId(int siteId);
    @Transactional
    void deleteAllBySiteId(int siteId);
}
