package harjoitustyo.sportdiary.domain;

import java.util.List;
import org.springframework.data.repository.CrudRepository;

public interface SportRepository extends CrudRepository<Sport, Long> {

    List<Sport> findAll();
    List<Sport> findByUserId(Long userid);

}