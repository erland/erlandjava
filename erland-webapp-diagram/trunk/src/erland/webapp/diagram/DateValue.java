package erland.webapp.diagram;

import java.util.Date;

public class DateValue implements DateValueInterface {
        private Date date;
        private double value;
        public DateValue(Date date,double value) {
            this.date = date;
            this.value = value;
        }
        public Date getDate() {
            return date;
        }
        public double getValue() {
            return value;
        }
    }
