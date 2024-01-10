package com.williams_sample_test_code.service_interface.impl;
import com.williams_sample_test_code.data_access.CompaniesRecordRepository;
import com.williams_sample_test_code.exception_handeller.FileTypeNotMatchException;
import com.williams_sample_test_code.model_entity.CompaniesRecords;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.List;
import java.util.UUID;
import static com.williams_sample_test_code.util.EventTypes.getEventTypeCodeByDescription;


  @Service
  @Slf4j
    public class CompaniesRecordService {

  @Autowired
    private CompaniesRecordRepository companiesRecordRepository;

   public CompaniesRecords CompanyRecordsAdding(CompaniesRecords companiesRecords) {
        log.info("Moving company Records int DB");
        return companiesRecordRepository.save(companiesRecords);
    }

    public CompaniesRecords FileProcessing(MultipartFile file) throws IOException, FileTypeNotMatchException {
        validateFileType(file);

        List<String> readingLines = FileReadByLine(file);
        CompaniesRecords presentCompanyRecords = null;

        for (String readLines : readingLines) {
            if (readLines.trim().isEmpty()) {
                continue;
            }

            if (presentCompanyRecords == null) {
                presentCompanyRecords = new CompaniesRecords();
            }

            if (readLines.toUpperCase().contains("LTD")&& readLines.length() < 100) {
                CompanyNameSetting(readLines, presentCompanyRecords);

            } else if (readLines.toUpperCase().contains("LIMITED")) {
                CompanyNameSetting(readLines, presentCompanyRecords);

            } else if (NumberForm(readLines.trim())) {
                processCompanyNumberLine(readLines, presentCompanyRecords);


            } else if (readLines.trim().matches("\".*\"")) {
                processEventTypeLine(readLines, presentCompanyRecords);

            } else if (DateValidation(readLines.trim())) {
                SettingEventDate(readLines, presentCompanyRecords);
                CompanyRecordsAdding(presentCompanyRecords);
                presentCompanyRecords = null;
            }
        }

        return presentCompanyRecords;
    }


    public void validateFileType(MultipartFile file) throws FileTypeNotMatchException {
        if (!file.getOriginalFilename().toLowerCase().endsWith(".txt")) {
            throw new FileTypeNotMatchException("Invalid File Type. Upload a .txt File.");
        }
    }
    //---------->reads the file by line
    public List<String> FileReadByLine(MultipartFile file) {
        try {
            return org.apache.commons.io.IOUtils.readLines(file.getInputStream(), StandardCharsets.UTF_8);

        } catch (IOException e) {
            throw new RuntimeException("File Reading Error: " + e.getMessage(), e);

        }

    }

    public void CompanyNameSetting(String line, CompaniesRecords currentCompanyRecord) {
        currentCompanyRecord.setCompanyName(line.trim());
        currentCompanyRecord.setUniqueIdentifier(UUID.randomUUID().toString());
    }

    public void processCompanyNumberLine(String number, CompaniesRecords currentCompanyRecord) {
        log.info("Providing company Number");
        currentCompanyRecord.setCompanyNumber(number.trim());
    }

    public void processEventTypeLine(String event, CompaniesRecords currentCompanyRecord) {
        String eventType = ValidateEventType(event);
        String eventCode = getEventTypeCodeByDescription(eventType);
        currentCompanyRecord.setEventType(eventCode);
    }

    public void SettingEventDate(String date, CompaniesRecords currentCompaniesRecord) {
        log.info("Providing the Event Date");
        currentCompaniesRecord.setEventDate(date.trim());
    }
//------------------> if string enclosed in " "then it consider as eventType
public String ValidateEventType(String event) {

        return event.replaceAll(".*\"(.*?)\".*","$1" );
    }

//---------->checking is this numbers or not in the text file if present then consider as CompanyNumber
public boolean NumberForm(String number) {

        return number.matches("\\d+");
    }
//--------> Date validating
public boolean DateValidation(String date) {
       log.info("Date format validating");
        return date.matches("\\d{2}/\\d{2}/\\d{4}");
    }
}

