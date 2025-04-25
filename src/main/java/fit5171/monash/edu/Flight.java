package fit5171.monash.edu;

import java.sql.Timestamp;
import java.text.SimpleDateFormat;

public class Flight {
    private int flightID;
    private String departTo;
    private String departFrom;
    private String code;
    private String company;
    private Timestamp dateFrom;
    private Timestamp dateTo;
    Airplane airplane;

    public Flight() {}

    public Flight(int flightID, String departTo, String departFrom, String code, String company,
                  Timestamp dateFrom, Timestamp dateTo, Airplane airplane) {
        setFlightID(flightID);
        setDepartTo(departTo);
        setDepartFrom(departFrom);
        setCode(code);
        setCompany(company);
        setDateFrom(dateFrom);
        setDateTo(dateTo);
        setAirplane(airplane);
    }

    public int getFlightID() {
        return flightID;
    }

    public void setFlightID(int flightID) {
        if (flightID <= 0) throw new IllegalArgumentException("Flight ID must be positive.");
        this.flightID = flightID;
    }

    public String getDepartTo() {
        return departTo;
    }

    public void setDepartTo(String departTo) {
        if (departTo == null || departTo.isBlank())
            throw new IllegalArgumentException("Destination city is required.");
        this.departTo = departTo;
    }

    public String getDepartFrom() {
        return departFrom;
    }

    public void setDepartFrom(String departFrom) {
        if (departFrom == null || departFrom.isBlank())
            throw new IllegalArgumentException("Departure city is required.");
        this.departFrom = departFrom;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        if (code == null || code.isBlank())
            throw new IllegalArgumentException("Flight code is required.");
        this.code = code;
    }

    public String getCompany() {
        return company;
    }

    public void setCompany(String company) {
        if (company == null || company.isBlank())
            throw new IllegalArgumentException("Company name is required.");
        this.company = company;
    }

    public Timestamp getDateFrom() {
        return dateFrom;
    }

    public void setDateFrom(Timestamp dateFrom) {
        if (dateFrom == null)
            throw new IllegalArgumentException("dateFrom is required.");
        if (!isValidDateFormat(dateFrom)) {
            throw new IllegalArgumentException("dateFrom must be in format YYYY-MM-DD HH:MM:SS");
        }
        this.dateFrom = dateFrom;
    }

    public Timestamp getDateTo() {
        return dateTo;
    }

    public void setDateTo(Timestamp dateTo) {
        if (dateTo == null)
            throw new IllegalArgumentException("dateTo is required.");
        if (!isValidDateFormat(dateTo)) {
            throw new IllegalArgumentException("dateTo must be in format YYYY-MM-DD HH:MM:SS");
        }
        this.dateTo = dateTo;
    }

    public Airplane getAirplane() {
        return airplane;
    }

    public void setAirplane(Airplane airplane) {
        if (airplane == null)
            throw new IllegalArgumentException("Airplane is required.");
        this.airplane = airplane;
    }

    private boolean isValidDateFormat(Timestamp timestamp) {
        try {
            String format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(timestamp);
            // further parsing to verify format correctness
            new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse(format);
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    @Override
    public String toString() {
        return "Flight{" +
                "flightID=" + flightID +
                ", departFrom='" + departFrom + '\'' +
                ", departTo='" + departTo + '\'' +
                ", code='" + code + '\'' +
                ", company='" + company + '\'' +
                ", dateFrom=" + dateFrom +
                ", dateTo=" + dateTo +
                ", airplaneID=" + (airplane != null ? airplane.getAirplaneID() : "null") +
                '}';
    }
}
