package Model;

import java.time.LocalDate;
import java.time.LocalDateTime;

public class Appointments {
    private int aptID;
    private String title;
    private String description;
    private String location;
    private String contactName;
    private String type;
    private LocalDate startDate;
    private LocalDateTime startTime;
    private LocalDate endDate;
    private LocalDateTime endTime;
    private int customerID;
    private int userID;
    private int contactID;

    public Appointments(int aptID, String title, String description, String location, String contactName, String type, LocalDate startDate, LocalDateTime startTime, LocalDate endDate, LocalDateTime endTime, int customerID, int userID, int contactID) {
    this.aptID=aptID;
    this.title=title;
    this.description=description;
    this.location=location;
    this.contactName=contactName;
    this.type=type;
    this.startDate=startDate;
    this.startTime=startTime;
    this.endDate=endDate;
    this.endTime=endTime;
    this.customerID=customerID;
    this.userID=userID;
    this.contactID=contactID;
    }
    public Appointments(int aptID, LocalDate startDate, LocalDateTime startTime){
        this.aptID=aptID;
        this.startDate=startDate;
        this.startTime=startTime;
    }
    public int getAppointmentID() {
        return aptID;
    }
    public String getAptTitle(){
        return title;
    }
    public String getContactName() {
        return contactName;
    }
    public String getDescription() {
        return this.description;
    }
    public String getLocation() {
        return this.location;
    }
    public String getType() {
        return this.type;
    }
    public LocalDate getStartDate() {
        return startDate;
    }
    public LocalDate getEndDate() {
        return endDate;
    }
    public LocalDateTime getStartTime() {
        return startTime;
    }
    public LocalDateTime getEndTime() {
        return endTime;
    }
    public int getCustomerID() {
        return this.customerID;
    }
    public int getUserID() {
        return userID;
    }
    public int getContactID() {
        return contactID;
    }

}
