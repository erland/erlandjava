package erland.webapp.diagram;

import erland.util.Log;

import java.util.Date;
import java.util.Iterator;
import java.util.Vector;
import java.awt.*;
import java.text.NumberFormat;
import java.text.DecimalFormat;

public class DateValueDiagram {
    private double maxValue;
    private Date minDate;
    private Date maxDate;
    private Vector plots;
    private final int LABEL_WIDTH=50;
    private final int LABEL_MARK_SIZE = 4;
    private final int LABEL_BORDER = 5;
    public class PlotData {
        private String name;
        private Vector data;
        public PlotData(String name, Vector data) {
            this.name = name;
            this.data = data;
        }

        public String getName() {
            return name;
        }

        public Vector getData() {
            return data;
        }
    }
    public DateValueDiagram() {
        maxValue=0;
        minDate=new Date();
        maxDate=new Date(0);
        plots = new Vector();
    }

    private Vector makeVector(Iterator it) {
        Vector vector = new Vector();
        while(it.hasNext()) {
            vector.addElement(it.next());
        }
        return vector;
    }
    public void addPlotData(String name, Iterator dateValueIterator) {
        Vector dateValues = makeVector(dateValueIterator);
        plots.addElement(new PlotData(name,dateValues));
        Iterator it = dateValues.iterator();
        int size=0;
        double maxRate = 0;
        while(it.hasNext()) {
            DateValueInterface dateValue = (DateValueInterface) it.next();
            double kurs = dateValue.getValue();
            if(kurs>maxRate) {
                maxRate=kurs;
            }
            if(size==0) {
                if(minDate.getTime()>dateValue.getDate().getTime()) {
                    minDate = dateValue.getDate();
                }
            }else if(!it.hasNext()) {
                if(maxDate.getTime()<dateValue.getDate().getTime()) {
                    maxDate = dateValue.getDate();
                }
            }
            size++;
        }
        if(maxValue<maxRate) {
            maxValue = maxRate;
        }
    }

    private Color getColor(int plotNo) {
        switch(plotNo) {
            case 0:
                return Color.black;
            case 1:
                return Color.red;
            case 2:
                return Color.green;
            case 3:
                return Color.cyan;
            case 4:
                return Color.gray;
            case 5:
                return Color.yellow;
            case 6:
                return Color.magenta;
            case 7:
                return Color.orange;
            case 8:
                return Color.pink;
            default:
                return Color.getHSBColor((float)Math.random(),1.0f,(float)Math.random());
        }
    }
    private double getValueLabelInterval(int size, int minInterval, double maxValue) {
        Log.println(this,"getValueLabelInterval "+size+" "+minInterval+" "+maxValue);
        if(maxValue>0) {
            int noOfLabels=size/minInterval;
            if(noOfLabels>0) {
                double multiplier=1;
                while(maxValue*multiplier/noOfLabels>10) {
                    multiplier/=10.0;
                }
                while(maxValue*multiplier/noOfLabels<1) {
                    multiplier*=10.0;
                }
                double val = Math.floor(maxValue*multiplier/noOfLabels)/multiplier;
                Log.println(this,"getValueLabelInterval(...)="+val);
                return val;
            }else {
                return maxValue;
            }
        }else {
            Log.println(this,"getValueLabelInterval(...)="+size);
            return maxValue;
        }
    }
    private DateInterval getDateLabelInterval(int size,int minInterval, Date minDate, Date maxDate) {
        int noOfLabels = size/minInterval;
        Log.println(this,"noOfDateLabels = "+noOfLabels);
        return DateInterval.calculateInterval(minDate,maxDate,noOfLabels);
    }
    private int getMaxPlotLabelWidth(Graphics g, int height) {
        int width = 0;
        FontMetrics font = g.getFontMetrics();
        int y=0;
        int maxWidth=0;
        for(int i=0;i<plots.size();i++) {
            PlotData p = (PlotData) plots.elementAt(i);
            int w = font.stringWidth(p.getName());
            if(y+font.getHeight()>height) {
                width+=maxWidth+LABEL_BORDER;
                maxWidth=0;
                y=0;
            }
            y+=font.getHeight();
            if(w>maxWidth) {
                maxWidth=w;
            }
        }
        return width+maxWidth;
    }
    public void plot(Image img) {
        Graphics g = img.getGraphics();

        FontMetrics font = g.getFontMetrics();
        int labelHeight=font.getHeight();
        double oldx=0;
        double oldy=0;
        double x=0;
        double y=0;
        int top = 0;
        int bottom = img.getHeight(null)-labelHeight-LABEL_BORDER;
        int height = bottom-top;
        int left = LABEL_WIDTH;
        int right = img.getWidth(null)-getMaxPlotLabelWidth(g,height)-LABEL_BORDER;
        int width = right-left;

        double dx=(double)width/(maxDate.getTime()-minDate.getTime());
        double dy=(double)height/maxValue;
        double firstX = minDate.getTime();


        double verticalLabelInterval = getValueLabelInterval(height,25,maxValue);
        Log.println(this,"verticalLabelInterval="+verticalLabelInterval);
        DateInterval horizontalLabelInterval = getDateLabelInterval(width,60,minDate,maxDate);
        Log.println(this,"horizontalLabelInterval="+horizontalLabelInterval);
        int noOfVerticalLabels = (int)Math.floor(maxValue/verticalLabelInterval);
        Log.println(this,"noOfVerticalLabels="+noOfVerticalLabels);

        x=0;

        NumberFormat numberFormat = new DecimalFormat("0.##");
        for(int i=0;i<noOfVerticalLabels;i++) {
            y=(i+1)*verticalLabelInterval*dy;
            Log.println(this,"verticalLabel="+y);
            g.setColor(Color.lightGray);
            g.drawLine(left-LABEL_MARK_SIZE,bottom-(int)y,right,bottom-(int)y);
            g.setColor(Color.black);
            String label = numberFormat.format(y/dy);
            int labelWidth = g.getFontMetrics().stringWidth(label);
            g.drawString(label,left-labelWidth-LABEL_MARK_SIZE-LABEL_BORDER+(int)x,bottom-(int)y+4);
        }
        g.setColor(Color.lightGray);
        g.drawLine(left,top,left,bottom);

        y=img.getHeight(null);
        Date currentDate = horizontalLabelInterval.next(minDate);
        while(currentDate.getTime()<=maxDate.getTime()) {
            x=(currentDate.getTime()-minDate.getTime())*dx;
            Log.println(this,"horizontalLabel="+x);
            g.setColor(Color.lightGray);
            g.drawLine(left+(int)x,top,left+(int)x,bottom+LABEL_MARK_SIZE);
            g.setColor(Color.black);
            String label = horizontalLabelInterval.format(currentDate);
            int labelWidth = g.getFontMetrics().stringWidth(label);
            g.drawString(label,left+(int)x-labelWidth/2,(int)y);
            currentDate = horizontalLabelInterval.nextAfter(currentDate);
        }
        g.setColor(Color.lightGray);
        g.drawLine(left,bottom,right,bottom);

        x=0;
        y=0;
        int labelY=0;
        int labelX=right+LABEL_BORDER;
        int maxLabelX = 0;
        for(int i=0;i<plots.size();i++) {
            PlotData plot = (PlotData) plots.elementAt(i);
            Iterator plotIt = plot.getData().iterator();
            g.setColor(getColor(i));
            boolean bFirst = true;
            while(plotIt.hasNext()) {
                DateValueInterface dateValue = (DateValueInterface) plotIt.next();
                y = dateValue.getValue();
                x = dateValue.getDate().getTime()-firstX;
                if(bFirst) {
                    oldy=y;
                    oldx=x;
                    bFirst = false;
                }
                g.drawLine(left+(int)(oldx*dx),top+height-(int)(oldy*dy),left+(int)(x*dx),top+height-(int)(y*dy));
                oldy=y;
                oldx=x;
            }
            if(labelY+labelHeight>height) {
                labelY=labelHeight;
                labelX+=maxLabelX+LABEL_BORDER;
                maxLabelX=0;
            }else {
                labelY+=labelHeight;
                if(font.stringWidth(plot.getName())>maxLabelX) {
                    maxLabelX=font.stringWidth(plot.getName());
                }
            }
            g.drawString(plot.getName(),labelX,top+labelY);
        }
        g.dispose();
    }
}
