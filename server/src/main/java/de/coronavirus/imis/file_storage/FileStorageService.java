package de.coronavirus.imis.file_storage;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Optional;

import de.coronavirus.imis.domain.TestReport;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

/***
 * Service class for saving uploaded files to the file system.
 */
@Service
public class FileStorageService {

    /***
     * Path for testReports.
     */
    private String reportPath = "/reports/";

    // Instance of the data storage service.
    private final FileStorageDataService fileStorageDataService;

    @Autowired
    public FileStorageService(FileStorageDataService fileStorageDataService) {
        this.fileStorageDataService = fileStorageDataService;
    }

    /***
     * Saves a file identified by an id.
     * @param _id: The identifier of the file.
     * @param file: The binary of the file.
     * @throws Exception
     */
    void addFileById(Long _id, MultipartFile file) throws Exception {
        fileStorageDataService.createTestReport(_id, file.getBytes());
    }

    /***
     * Gets a file from the file system by an id.
     * @param _id: The identifier of the file.
     * @return The returned file.
     * @throws FileNotFoundException
     */
    TestReport getFileById(Long _id) throws FileNotFoundException {
        Optional<TestReport> optionalTestReport =
                fileStorageDataService.getTestReport(_id);


        if (optionalTestReport.isPresent()) {
            return optionalTestReport.get();
        }


        throw new FileNotFoundException(
                "The file by the identifier: " + _id + " was not found"
        );
    }

    /***
     * Replace a file identified by an id.
     * @param _id: The identifier of the file.
     * @param file: The new file.
     * @throws IOException
     */
    void replaceFileById(Long _id, MultipartFile file) throws IOException {
        fileStorageDataService.updateTestReport(_id, file.getBytes());
    }

    /***
     * Delete a file given its id.
     * @param _id: The unique identifier of a file to be deleted.
     */
    void deleteFileById(Long _id) {
        fileStorageDataService.deleteTestReport(_id);
    }
}