package searchEngineApp.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import searchEngineApp.entity.Field;

public interface FieldRepo extends JpaRepository<Field, Integer> {
}
