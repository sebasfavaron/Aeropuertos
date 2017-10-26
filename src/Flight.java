package src;

import java.sql.Time;
import java.util.List;

public class Flight {
    private String airline;
    private Integer number;
    private List<String> weekDay;
    private Airport departure;
    private Airport arrival;
    private src.Time departureTime;
    private src.Time duration;
    private Double price;

    public Flight(String airline, Integer number, List<String> weekDay, Airport departure, Airport arrival, src.Time departureTime, src.Time duration, Double price) {
        this.airline = airline;
        this.number = number;
        this.weekDay = weekDay;
        this.departure = departure;
        this.arrival = arrival;
        this.departureTime = departureTime;
        this.duration = duration;
        this.price = price;
    }

    public String getAirline() {
        return airline;
    }

    public void setAirline(String airline) {
        this.airline = airline;
    }

    public Integer getNumber() {
        return number;
    }

    public void setNumber(Integer number) {
        this.number = number;
    }

    public List<String> getWeekDay() {
        return weekDay;
    }

    public void setWeekDay(List<String> weekDay) {
        this.weekDay = weekDay;
    }

    public Airport getDeparture() {
        return departure;
    }

    public void setDeparture(Airport departure) {
        this.departure = departure;
    }

    public Airport getArrival() {
        return arrival;
    }

    public void setArrival(Airport arrival) {
        this.arrival = arrival;
    }

    public src.Time getDepartureTime() {
        return departureTime;
    }

    public void setDepartureTime(src.Time departureTime) {
        this.departureTime = departureTime;
    }

    public src.Time getDuration() {
        return duration;
    }

    public void setDuration(src.Time duration) {
        this.duration = duration;
    }

    public Double getPrice() {
        return price;
    }

    public void setPrice(Double price) {
        this.price = price;
    }

    public boolean equals(Object o) {
        if(o == null)
            return false;
        if(!(o instanceof Flight))
            return false;
        Flight fl = (Flight)o;
        return this.airline.equals(fl.airline) && this.number.equals(fl.number);
    }

    public String toString() {
        return airline + "#" + number.toString();
    }

}
