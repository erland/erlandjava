package erland.webapp.common.act;

import org.apache.struts.action.PlugIn;
import org.apache.struts.action.ActionServlet;
import org.apache.struts.config.ModuleConfig;

import javax.servlet.ServletException;
import java.util.Map;
import java.util.HashMap;
import java.util.Iterator;
import java.util.ArrayList;
import java.io.Reader;

import erland.webapp.common.ServletParameterHelper;

/*
 * Copyright (C) 2004 Erland Isaksson (erland_i@hotmail.com)
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

public class BaseTaskPlugin implements PlugIn {
    private Map tasks = new HashMap();
    private Integer priority = null;

    public void setPriority(String priority) {
        this.priority = ServletParameterHelper.asInteger(priority,null);
    }

    public void destroy() {
        synchronized(tasks) {
            for(Iterator it = tasks.entrySet().iterator();it.hasNext();) {
                Map.Entry entry = (Map.Entry) it.next();
                Thread thread = (Thread) entry.getValue();
                if(thread.isAlive()) {
                    thread.interrupt();
                }
                it.remove();
            }
        }
    }

    public void init(ActionServlet actionServlet, ModuleConfig moduleConfig) throws ServletException {
    }

    protected boolean addTask(Object id, Runnable runnable, boolean waitUntilExit) {
        Thread thread = null;
        synchronized(tasks) {
            thread = (Thread) tasks.get(id);
            if(thread!=null) {
                if(thread.isAlive()) {
                    return false;
                }else {
                    tasks.remove(id);
                }
            }
            thread = new Thread(runnable);
            tasks.put(id,thread);
            if(priority!=null) {
                thread.setPriority(priority.intValue());
            }
            thread.start();
        }
        if(waitUntilExit) {
            try {
                thread.join();
            } catch (InterruptedException e) {
                e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
            }
        }
        return true;
    }
    protected boolean addTask(Object id,Runnable runnable) {
        return addTask(id,runnable,false);
    }

    protected void removeTask(Object id) {
        synchronized(tasks) {
            Thread thread = (Thread) tasks.get(id);
            if(thread.isAlive()) {
                thread.interrupt();
            }
            tasks.remove(id);
        }
    }

    protected Object[] getActiveTasks() {
        synchronized(tasks) {
            ArrayList activeTasks = new ArrayList();
            for(Iterator it = tasks.entrySet().iterator();it.hasNext();) {
                Map.Entry entry = (Map.Entry) it.next();
                Thread thread = (Thread) entry.getValue();
                if(thread.isAlive()) {
                    activeTasks.add(entry.getKey());
                }
            }
            return activeTasks.toArray();
        }
    }
}