package searchEngineApp.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import searchEngineApp.entity.Page;

public interface PageRepo extends JpaRepository<Page, Integer> {
}
