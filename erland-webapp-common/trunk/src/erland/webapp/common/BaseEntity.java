package erland.webapp.common;
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

import org.apache.commons.beanutils.PropertyUtils;

import java.util.Map;
import java.util.Iterator;
import java.lang.reflect.InvocationTargetException;

public class BaseEntity implements EntityInterface{
    private WebAppEnvironmentInterface environment;

    public void init(WebAppEnvironmentInterface environment) {
        this.environment = environment;
    }

    public WebAppEnvironmentInterface getEnvironment() {
        return environment;
    }

    public String toString() {
        StringBuffer sb = new StringBuffer(100);
        try {
            sb.append(getClass().getName());
            Map propertyMap = PropertyUtils.describe(this);
            for(Iterator it=propertyMap.entrySet().iterator();it.hasNext();) {
                Map.Entry entry = (Map.Entry) it.next();
                sb.append(entry.getKey());
                sb.append("=");
                sb.append(entry.getValue());
                if(it.hasNext()) {
                    sb.append(",");
                }
            }
        } catch (IllegalAccessException e) {
        } catch (InvocationTargetException e) {
        } catch (NoSuchMethodException e) {
        }
        return sb.toString();
    }
}