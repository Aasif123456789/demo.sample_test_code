package com.williams_sample_test_code.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;

import lombok.NoArgsConstructor;
import org.antlr.v4.runtime.misc.NotNull;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "companies_records")
public class CompaniesRecords {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @Column(name = "company_name")
    private String companyName;
    @NotNull
    @Column(name = "company_number")
    private String companyNumber;
    @NotNull
    @Column(name = "event_type")
    private String eventType;
    @NotNull
    @Column(name = "event_date")
    private String eventDate;
    @NotNull
    @Column(name = "unique_identifier")
    private String uniqueIdentifier;


    public void setId(Long id) {
        this.id = id;
    }

    public void setCompanyName(String companyName) {
        this.companyName = companyName;
    }

    public void setCompanyNumber(String companyNumber) {
        this.companyNumber = companyNumber;
    }

    public void setEventType(String eventType) {
        this.eventType = eventType;
    }

    public void setEventDate(String eventDate) {
        this.eventDate = eventDate;
    }

    public void setUniqueIdentifier(String uniqueIdentifier) {
        this.uniqueIdentifier = uniqueIdentifier;
    }



    public Long getId() {
        return id;
    }

    public String getCompanyName() {
        return companyName;
    }

    public String getCompanyNumber() {
        return companyNumber;
    }

    public String getEventType() {
        return eventType;
    }

    public String getEventDate() {
        return eventDate;
    }

    public String getUniqueIdentifier() {
        return uniqueIdentifier;
    }

}
