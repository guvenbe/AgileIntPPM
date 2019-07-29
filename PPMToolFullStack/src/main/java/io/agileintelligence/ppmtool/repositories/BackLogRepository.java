package io.agileintelligence.ppmtool.repositories;

import io.agileintelligence.ppmtool.domain.Backlog;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BackLogRepository extends CrudRepository<Backlog,Long> {

    Backlog findByProjectIdentifier(String Identifier);
}
