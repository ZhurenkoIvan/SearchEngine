package searchEngineApp.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import searchEngineApp.entity.Lemma;

public interface LemmaRepo extends JpaRepository<Lemma, Integer> {
}
