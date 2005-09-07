/*
 * Copyright (C) 2005 Erland Isaksson (erland_i@hotmail.com)
 *
 * This program is free software; you can redistribute it and/or
 * modify it under the terms of the GNU General Public License
 * as published by the Free Software Foundation; either version 2
 * of the License, or (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program; if not, write to the Free Software
 * Foundation, Inc., 59 Temple Place - Suite 330, Boston, MA  02111-1307, USA.
 *
 */
package erland.webapp.stocks.bl.entity;

import java.util.Map;
import java.util.HashMap;
import java.util.Iterator;

/**
 * Represents a stock account value
 */
public class StockAccountValue {
    private double value;
    private double noOfStocks;
    private double rate;
    private Map statistics = new HashMap();

    public double getValue() {
        return value;
    }

    public void setValue(double value) {
        this.value = value;
    }

    public double getNoOfStocks() {
        return noOfStocks;
    }

    public void setNoOfStocks(double noOfStocks) {
        this.noOfStocks = noOfStocks;
    }

    public double getRate() {
        return rate;
    }

    public void setRate(double rate) {
        this.rate = rate;
    }

    public int[] getStatisticYears() {
        int[] years = new int[statistics.size()];
        Iterator it = statistics.keySet().iterator();
        int i=0;
        while (it.hasNext()) {
            Long year = (Long)it.next();
            years[i++]=year.intValue();

        }
        return years;
    }

    public void setStatistic(int year, double statistic) {
        statistics.put(new Long(year),new Double(statistic));
    }

    public double getStatistic(int year) {
        Double statistic = (Double) statistics.get(new Long(year));
        return statistic!=null?statistic.doubleValue():0;
    }
}
