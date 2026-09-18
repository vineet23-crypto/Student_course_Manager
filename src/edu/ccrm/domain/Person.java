package edu.ccrm.domain;

import java.time.LocalDate;

public abstract class Person {
    private String id;
    private String fullName;
    private String email;
    private String phoneNo;
    private LocalDate dateCreated;

    public Person(String id,String fullName,String email,String phoneNo) {
        this.id=id;
        this.fullName = fullName;
        this.email = email;
        this.phoneNo = phoneNo;
        this.dateCreated = LocalDate.now();
    }
    //abstract method for different roles iin future; future-proofing
    public abstract String getRole();
    //getters for other classes to access private fields
    public String getId() { return id; }
    public String getFullName() { return fullName; }
    public String getEmail() { return email; }
    public String getPhoneNumber() { return phoneNo; }
    public LocalDate getDateCreated() { return dateCreated; }

    public void setPhoneNumber(String phoneNumber) { this.phoneNo = phoneNumber; }
    public void setEmail(String email) { this.email = email; }
    public void setFullName(String fullName) { this.fullName = fullName; }

}
