package com.williams_sample_test_code.demo;
import com.williams_sample_test_code.repository.CompaniesRecordRepository;
import com.williams_sample_test_code.exception.FileTypeNotMatchException;
import com.williams_sample_test_code.model.CompaniesRecords;
import com.williams_sample_test_code.service.CompaniesRecordService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.web.multipart.MultipartFile;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.List;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;
@ExtendWith(MockitoExtension.class)

@SpringBootTest
public class CompaniesRecordServiceTest {
    @Mock
    private CompaniesRecordRepository companiesRecordRepository;

    @InjectMocks
    private CompaniesRecordService companiesRecordService;

    @Test
    void testCompanyRecordsAdding() {
        CompaniesRecords companiesRecords = new CompaniesRecords();
        when(companiesRecordRepository.save(companiesRecords)).thenReturn(companiesRecords);

        CompaniesRecords result = companiesRecordService.CompanyRecordsAdding(companiesRecords);

        assertNotNull(result);
    }


    @Test
    void testValidateFileType_invalid() {
        MultipartFile file = new MockMultipartFile("test.pdf", "content".getBytes(StandardCharsets.UTF_8));

        assertThrows(FileTypeNotMatchException.class, () -> companiesRecordService.validateFileType(file));
    }

    @Test
    void testFileReadByLine() throws IOException {
        MultipartFile file = new MockMultipartFile("test.txt", "content".getBytes(StandardCharsets.UTF_8));

        List<String> result = companiesRecordService.FileReadByLine(file);

        assertNotNull(result);
    }

    @Test
    void testCompanyNameSetting() {
        CompaniesRecords currentCompanyRecord = new CompaniesRecords();
        String line = "Company LTD";

        companiesRecordService.CompanyNameSetting(line, currentCompanyRecord);

        assertNotNull(currentCompanyRecord.getCompanyName());
        assertNotNull(currentCompanyRecord.getUniqueIdentifier());
    }

    @Test
    void testProcessCompanyNumberLine() {
        CompaniesRecords currentCompanyRecord = new CompaniesRecords();
        String number = "12345";

        companiesRecordService.processCompanyNumberLine(number, currentCompanyRecord);

        assertNotNull(currentCompanyRecord.getCompanyNumber());
    }

    @Test
    void testProcessEventTypeLine() {
        CompaniesRecords currentCompanyRecord = new CompaniesRecords();
        String event = "\"Event Type\"";

        companiesRecordService.processEventTypeLine(event, currentCompanyRecord);

        assertNotNull(currentCompanyRecord.getEventType());
        // Add more assertions based on your specific requirements
    }

    @Test
    void testSettingEventDate() {
        CompaniesRecords currentCompanyRecord = new CompaniesRecords();
        String date = "01/01/2022";

        companiesRecordService.SettingEventDate(date, currentCompanyRecord);

        assertNotNull(currentCompanyRecord.getEventDate());
    }

    @Test
    void testValidateEventType() {
        String event = "\"Event Type\"";

        String result = companiesRecordService.ValidateEventType(event);

        assertNotNull(result);
    }

    @Test
    void testNumberForm_valid() {
        String number = "12345";

        boolean result = companiesRecordService.NumberForm(number);

        assertTrue(result);
    }

    @Test
    void testNumberForm_invalid() {
        String number = "abc";

        boolean result = companiesRecordService.NumberForm(number);

        assertFalse(result);
    }

    @Test
    void testDateValidation_valid() {
        String date = "01/01/2022";

        boolean result = companiesRecordService.DateValidation(date);

        assertTrue(result);
    }

    @Test
    void testDateValidation_invalid() {
        String date = "2022-01-01";

        boolean result = companiesRecordService.DateValidation(date);

        assertFalse(result);
    }
}
