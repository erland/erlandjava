package erland.webapp.diagram;
/*
 * Copyright (C) 2003 Erland Isaksson (erland_i@hotmail.com)
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
