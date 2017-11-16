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
        while (minute >= 60){
            minute -= 60;
            hour += 1;
        }
        while (hour >= 24){
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
        return "Day: "+weekDay+", Time: "+hour+":"+minute;
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
        //nosotros somos salida del proximo y ellos son arrivo del anterior
        int dDay = weekDay - other.weekDay;
        int dHour = hour - other.hour;
        int dMinute = minute - other.minute;
        int add=0;
        if (dDay<0){
            add+=7;
        }
        if (dHour==0){
            if (dMinute == 0) {
                return (dDay+add)*60*24;
            }
        }
        if (dDay==0){
            if (dHour>0){
                if (dMinute==0){
                    return dHour*60;
                }else if (dMinute<0){
                    return (dHour-1)*60+60-other.minute+minute;
                }else{
                    return (dHour-1)*60+60-other.minute+minute;
                }
            }else if (dHour<0){
                if (dMinute==0){
                    return 7*60*24+dHour*60;
                }else if (dMinute<0){
                    return 7*60*24+dHour*60+dMinute;
                }
            }
        }
        /*
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
            if (this.hour == other.hour) {
                if (this.minute == other.minute) {
                    return dDay * 24 * 60;
                } else if (this.minute > other.minute) {
                    return dDay * 24 * 60 + dMinute;
                } else {
                    return dDay * 24 * 60 + dMinute;
                }
            }else if(this.hour<other.hour){
                if (this.minute == other.minute) {
                    return dDay * 24 * 60+dHour;
                } else if (this.minute > other.minute) {
                    return dDay * 24 * 60 + dMinute;
                } else {
                    return dDay * 24 * 60 + dMinute;
                }
            }
            //return dDay * 24 * 60 + dHour * 60 + dMinute;
        }
        /*
        if (dDay<0){
            dDay+=7;
        }
        return dDay * 24 * 60 + dHour * 60 + dMinute;
        */
        return 7*24*60-(this.hour-other.hour-1)*60-(60-other.minute+this.minute);
    }

    //No afecta al tiempo de la clase, solo devuelve la suma de este y el t2
    public Time add(Time t2) {
        // La suma es un poco confusa porque considero a this como un momento de la semana
        // y a t2 como una duracion, por eso si t2.weekDay es 1 significa que dura un dia entero
        // Asi, si sumo directo, me devuelve this + el tiempo que dure t2
        int weekD = this.weekDay + t2.weekDay;
        int hour = this.hour + t2.hour;
        int min = this.minute + t2.minute;
        if(min >= 60) {
            hour += (min/60);
            min = min % 60;
        }
        if(hour >= 24) {
            weekD += (hour/24);
            hour = hour % 24;
        }
        if(weekD >= 7) {
            weekD = weekD%7;
        }
        return new Time(weekD, hour, min);
    }

    public int getVal(){
        return weekDay * 24 * 60 + hour * 60 + minute;
    }
}
