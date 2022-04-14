package searchEngineApp.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.transaction.annotation.Transactional;
import searchEngineApp.entity.Lemma;

import java.util.List;
import java.util.Set;

public interface LemmaRepo extends JpaRepository<Lemma, Integer> {
    @Transactional
    void deleteAllBySiteId(int siteId);
    long countBySiteId(int siteId);
}
