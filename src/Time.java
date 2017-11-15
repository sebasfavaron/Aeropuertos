package src;

/**
 * Created by Sebas on 10/26/17.
 */
public class Time {
    private int weekDay;
    private int hour;
    private int minute;
    public Time(int hour, int minute) {
        while (minute > 60){
            minute -= 60;
            hour += 1;
        }
        this.hour = hour;
        this.minute = minute;
    }
    public Time(int weekDay, int hour, int minute) {
        while (minute > 60){
            minute -= 60;
            hour += 1;
        }
        while (hour > 24){
            hour -= 24;
            if (weekDay < 7)
                weekDay += 1;
        }
        if(weekDay > 7)
            weekDay = weekDay % 8;
        this.weekDay = weekDay;
        this.hour = hour;
        this.minute = minute;
    }
    public Time(Time time) {
        this.weekDay = time.weekDay;
        this.hour = time.hour;
        this.minute = time.minute;
    }

    @Override
    public String toString() {
        return hour+":"+minute;
    }

    public int getWeekDay() {
        return weekDay;
    }

    public int getHour() {
        return hour;
    }

    public int getMinute() {
        return minute;
    }

    /**
     * Calculates the time difference in minutes
     * @param other
     * @return time difference in minutes
     */
    public int difference(Time other) {
        int dDay = weekDay - other.weekDay;
        int dHour = hour - other.hour;
        int dMinute = minute - other.minute;
        if (other.weekDay==this.weekDay) {
            if (this.hour == other.hour) {
                if (this.minute == other.minute) {
                    return 0;
                }else if (this.minute>other.minute){
                    return 7*24*60-dMinute;
                }else {
                    return dMinute;
                }
            }else if(this.hour<other.hour){
                if (other.minute<this.minute){
                    return (other.hour-this.hour-1)*60+60-this.minute+other.minute;
                }else if (other.minute==this.minute){
                    return (other.hour-this.hour)*60;
                }else {
                    return (other.hour-this.hour-1)*60+60-this.minute+other.minute;
                }
            }else {
                if (this.minute==other.minute){
                    return 7*24*60-this.hour-other.hour;
                }else {
                    return 7*24*60-(this.hour-other.hour-1)+60-(60-other.minute+this.minute);
                }
            }
        }else if (other.weekDay<this.weekDay){
            return 7*24*60-(this.hour-other.hour-1)*60-(60-other.minute+this.minute);
        }
        /*
        if (dDay<0){
            dDay+=7;
        }
        return dDay * 24 * 60 + dHour * 60 + dMinute;
        */
        return dDay * 24 * 60 + dHour * 60 + dMinute;
    }

    public int getVal(){
        return weekDay * 24 * 60 + hour * 60 + minute;
    }
}
