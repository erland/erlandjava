package erland.webapp.common.act;

import org.apache.struts.action.PlugIn;
import org.apache.struts.action.ActionServlet;
import org.apache.struts.config.ModuleConfig;

import javax.servlet.ServletException;
import java.util.*;
import java.io.Reader;
import java.io.File;
import java.io.FileFilter;

import erland.util.StringUtil;
import erland.util.Log;

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

public class ManagedDirPlugin extends BaseTaskPlugin {

    private class ManagedDir{
        private String dir;
        private long size;
        public ManagedDir(String dir, long size) {
            this.dir = dir;
            this.size = size;
        }

        public String getDir() {
            return dir;
        }

        public long getSize() {
            return size;
        }
    }

    public void init(ActionServlet actionServlet, ModuleConfig moduleConfig) throws ServletException {
        super.init(actionServlet, moduleConfig);
        addTask(this,new MainTask());
    }
    private class MainTask implements Runnable {
        public void run() {
            String cacheDirectories = WebAppEnvironmentPlugin.getEnvironment().getConfigurableResources().getParameter("manageddirectories");
            Log.println(this,"Got directories: "+cacheDirectories);
            if(StringUtil.asNull(cacheDirectories)!=null) {
                StringTokenizer tokens = new StringTokenizer(cacheDirectories,",");
                List dirList = new ArrayList();
                while(tokens.hasMoreElements()) {
                    String token = (String) tokens.nextElement();
                    StringTokenizer subTokens = new StringTokenizer(token,"=");
                    if(subTokens.hasMoreElements()) {
                        String dir = (String) subTokens.nextElement();
                        if(subTokens.hasMoreElements()) {
                            long size = StringUtil.asLong((String) subTokens.nextElement(),new Long(0)).longValue();
                            if(size>0) {
                                Log.println(this,"Managing directory: "+dir+" Size:"+size+" bytes");
                                dirList.add(new ManagedDir(dir,size));
                            }
                        }
                    }
                }
                if(dirList.size()>0) {
                    addTask(this,new ManagerTask((ManagedDir[])dirList.toArray(new ManagedDir[0])));
                }
            }
        }
    }
    private class ManagerTask implements Runnable {
        private ManagedDir[] directories;
        public ManagerTask(ManagedDir[] directories) {
            this.directories = directories;
        }
        public void run() {
            while (true) {
                for (int i = 0; i < directories.length; i++) {
                    ManagedDir dir = directories[i];
                    manageCacheDir(dir.getDir(),dir.getSize());
                }
                try {
                    Thread.sleep(300000);
                } catch (InterruptedException e) {
                }
            }
        }

        private void manageCacheDir(String dir, long size) {
            File file = new File(dir);
            long currentSize = 0;
            File[] files = file.listFiles();
            if(files!=null) {
                for (int i = 0; i < files.length; i++) {
                    if(!files[i].isDirectory()) {
                        currentSize += files[i].length();
                    }
                }
                if(currentSize>size) {
                    Arrays.sort(files, new Comparator() {
                        public int compare(Object o1, Object o2) {
                            long diff = ((File)o1).lastModified()-((File)o2).lastModified();
                            if(diff<0) {
                                return -1;
                            }else if(diff>0) {
                                return 1;
                            }else {
                                return 0;
                            }
                        }
                    });
                    int filesDeleted = 0;
                    for (int i = 0; i < files.length && currentSize>size; i++) {
                        File f = files[i];
                        if(!f.isDirectory()) {
                            if(f.delete()) {
                                filesDeleted++;
                                currentSize-=f.length();
                            }

                        }
                        files[i] = null;
                    }
                    Log.println(this,"Deleted "+filesDeleted+ " files from dir: "+dir);

                }
            }
            file = null;
        }
    }
}