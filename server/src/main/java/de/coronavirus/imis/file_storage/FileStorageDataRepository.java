package de.coronavirus.imis.file_storage;

import de.coronavirus.imis.domain.TestReport;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

/***
 * Repository for file storage.
 */
@Repository
public interface FileStorageDataRepository
        extends CrudRepository<TestReport, Long> {}