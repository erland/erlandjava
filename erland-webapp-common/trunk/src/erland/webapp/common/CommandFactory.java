package erland.webapp.common;

import org.apache.commons.logging.LogFactory;
import org.apache.commons.logging.Log;

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

public class CommandFactory implements CommandFactoryInterface {
    /** Logging instance */
    private static Log LOG = LogFactory.getLog(CommandFactory.class);
    private WebAppEnvironmentInterface environment;
    public CommandFactory(WebAppEnvironmentInterface environment) {
        this.environment = environment;
    }
    public CommandInterface create(String cmd) {
        String clsName = environment.getResources().getParameter("commands." + cmd + ".class");
        if (clsName == null || clsName.length()==0) {
            clsName = environment.getResources().getParameter("commands.default.class");
        }
        if(clsName != null && clsName.length()>0) {
            try {
                Class cls = Class.forName(clsName);
                CommandInterface command = (CommandInterface)cls.newInstance();
                command.init(environment);
                return command;
            } catch (ClassNotFoundException e) {
                LOG.error("Unable to create command instance",e);
            } catch (InstantiationException e) {
                LOG.error("Unable to create command instance",e);
            } catch (IllegalAccessException e) {
                LOG.error("Unable to create command instance",e);
            } catch (ClassCastException e) {
                LOG.error("Command instance does not implement CommandInterface",e);
            }
        }
        return null;
    }
}
