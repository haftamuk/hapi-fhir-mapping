package et.edu.mu.cfdh.repository;


import et.edu.mu.cfdh.model.ConditionEntity;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ConditionEntityRepository extends CrudRepository<ConditionEntity, Long> {
    Iterable<ConditionEntity> findBySubject(Long subject);

}
