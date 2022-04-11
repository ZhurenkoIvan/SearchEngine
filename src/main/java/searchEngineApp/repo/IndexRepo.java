package searchEngineApp.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import searchEngineApp.entity.Index;

public interface IndexRepo extends JpaRepository<Index, Integer> {
}
