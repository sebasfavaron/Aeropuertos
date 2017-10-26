package src;

import java.util.*;

/**
 * Marca los dias de la semana y hora en que sale un avion (la hora es la misma para todos los dias)
 */
public class FlightDates {
    private Integer LUN = 0, MAR = 1, MIE = 2, JUE = 3, VIE = 4, SAB = 5, DOM = 6;
    private int hour;
    private int minute;
    private List<Integer> days;
    public FlightDates(List<String> days, int hour, int minute) {
        this.hour = hour;
        this.minute = minute;
        // Usar List tiene la desventaja de que te pueden pasar dias duplicados.
        // Sin embargo, como mucho la hace mas lenta, el resultado es el mismo que si no hubieran duplicados.
        this.days = new ArrayList<>();
        for(String day : days) {
            switch (day) {
                case "LUN": this.days.add(LUN); break;
                case "MAR": this.days.add(MAR); break;
                case "MIE": this.days.add(MIE); break;
                case "JUE": this.days.add(JUE); break;
                case "VIE": this.days.add(VIE); break;
                case "SAB": this.days.add(SAB); break;
                case "DOM": this.days.add(DOM); break;
            }
        }
    }

    public double minus(FlightDates other) {
        return 0;
    }
}
