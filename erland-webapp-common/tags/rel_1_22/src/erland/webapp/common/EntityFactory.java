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

import erland.webapp.common.WebAppEnvironmentInterface;
import erland.webapp.common.EntityInterface;
import org.apache.commons.logging.LogFactory;
import org.apache.commons.logging.Log;

public class EntityFactory implements EntityFactoryInterface {
    /** Logging instance */
    private static Log LOG = LogFactory.getLog(EntityFactory.class);
    private WebAppEnvironmentInterface environment;
    public EntityFactory(WebAppEnvironmentInterface environment) {
        this.environment = environment;
    }
    public EntityInterface create(String entityName) {
        String clsName = environment.getResources().getParameter("entities."+entityName+".class");
        if (clsName != null && clsName.length()>0) {
            try {
                EntityInterface entity = (EntityInterface)Class.forName(clsName).newInstance();
                entity.init(environment);
                return entity;
            } catch (ClassNotFoundException e) {
                LOG.error("Unable to create entity instance",e);
            } catch (InstantiationException e) {
                LOG.error("Unable to create entity instance",e);
            } catch (IllegalAccessException e) {
                LOG.error("Unable to create entity instance",e);
            } catch (ClassCastException e) {
                LOG.error("Entity does not implement EntityInterface",e);
            }
        }
        return null;
    }
}
