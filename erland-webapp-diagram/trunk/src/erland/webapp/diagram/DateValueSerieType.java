package erland.webapp.diagram;

public class DateValueSerieType {
    private DateValueSerieType() {};
    public static final DateValueSerieType YEARLY = new DateValueSerieType();
    public static final DateValueSerieType MONTHLY = new DateValueSerieType();
    public static final DateValueSerieType QUARTERLY = new DateValueSerieType();
    public static final DateValueSerieType WEEKLY = new DateValueSerieType();
    public static final DateValueSerieType ALL = new DateValueSerieType();
    public static final DateValueSerieType get(String type) {
        if(type!=null)  {
            if(type.equalsIgnoreCase("yearly")) {
                return YEARLY;
            }else if(type.equalsIgnoreCase("quarterly")) {
                return QUARTERLY;
            }else if(type.equalsIgnoreCase("montly")) {
                return MONTHLY;
            }else if(type.equalsIgnoreCase("weekly")) {
                return WEEKLY;
            }
        }
        return ALL;
    }
}
