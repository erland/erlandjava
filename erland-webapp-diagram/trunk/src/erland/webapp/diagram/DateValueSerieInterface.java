package erland.webapp.diagram;

import java.util.Iterator;
import java.util.Date;

public interface DateValueSerieInterface {
    public String getName();
    public Iterator getSerie(DateValueSerieType type);
    public int size();
    public DateValueInterface getDateValue(int pos);
    public int indexOf(Date date);
    public int indexOf(Date date, int startPos);
}
