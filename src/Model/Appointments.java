package Model;

import java.time.LocalDate;
import java.time.LocalDateTime;

/**
 * Model for Appointments class
 * @author Adam Rutland-Ruiz
 */
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

    /**
     * Appointment Constructor
     * @param aptID
     * @param title
     * @param description
     * @param location
     * @param contactName
     * @param type
     * @param startDate
     * @param startTime
     * @param endDate
     * @param endTime
     * @param customerID
     * @param userID
     * @param contactID
     */
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

    /**
     * Appointment Constructor for alerts
     * @param aptID
     * @param startDate
     * @param startTime
     */
    public Appointments(int aptID, LocalDate startDate, LocalDateTime startTime){
        this.aptID=aptID;
        this.startDate=startDate;
        this.startTime=startTime;
    }

    /**
     * getter for Appointment ID
     * @return
     */
    public int getAptID() {
        return this.aptID;
    }

    /**
     * Getter for Appointment Title
     * @return
     */
    public String getTitle(){
        return this.title;
    }

    /**
     * Getter for appointment contact name
     * @return
     */
    public String getContactName() {
        return this.contactName;
    }

    /**
     * Getter for appointment description
     * @return
     */
    public String getDescription() {
        return this.description;
    }

    /**
     * Getter for appointment location
     * @return
     */
    public String getLocation() {
        return this.location;
    }

    /**
     * Getter for appointment type
     * @return
     */
    public String getType() {
        return this.type;
    }

    /**
     * Getter for the appointment Start Date
     * @return
     */
    public LocalDate getStartDate() {
        return this.startDate;
    }

    /**
     * Getter for the appointment End Date
     * @return
     */
    public LocalDate getEndDate() {
        return this.endDate;
    }

    /**
     * Getter for the Appointment Start Time
     * @return
     */
    public LocalDateTime getStartTime() {
        return this.startTime;
    }

    /**
     * Getter for the appointment End Time
     * @return
     */
    public LocalDateTime getEndTime() {
        return this.endTime;
    }

    /**
     * Getter for appointment Customer ID
     * @return
     */
    public int getCustomerID() {
        return this.customerID;
    }

    /**
     * Getter for appointment User ID
     * @return
     */
    public int getUserID() {
        return this.userID;
    }

    /**
     * Getter for appointment contact ID
     * @return
     */
    public int getContactID() {
        return this.contactID;
    }

}
