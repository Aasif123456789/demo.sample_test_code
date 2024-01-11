package com.williams_sample_test_code.contrller;
import com.williams_sample_test_code.exception.FileTypeNotMatchException;
import com.williams_sample_test_code.model.CompaniesRecords;
import com.williams_sample_test_code.service.CompaniesRecordService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import java.io.IOException;
@Slf4j
@RestController
@RequestMapping("/baseApi")

  public class FileController {

    private final CompaniesRecordService companiesRecordService;

    @Autowired
    public FileController(CompaniesRecordService companiesRecordService) {
        this.companiesRecordService = companiesRecordService;
    }
    @PostMapping("/upload")
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseEntity<String> uploadFile(@RequestParam("file") MultipartFile file)throws IOException, FileTypeNotMatchException {
        log.info(" File Start... To Uploading");

       System.out.println(file.getContentType());
        System.out.println(file.getSize());
        System.out.println(file.isEmpty());
        System.out.println(file.getName());
        try {
            CompaniesRecords Records = companiesRecordService.FileProcessing(file);
            return new ResponseEntity<>(" File Record Successfully Uploaded In Data Base", HttpStatus.OK);

        } catch (IOException | FileTypeNotMatchException e) {
            return new ResponseEntity<>("Error processing the uploaded file", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}

