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

import erland.util.Log;
import org.apache.commons.beanutils.PropertyUtils;

import java.lang.reflect.InvocationTargetException;
import java.io.File;
import java.io.FileFilter;
import java.util.*;

public class FileEntityStorage implements EntityStorageInterface {
    private WebAppEnvironmentInterface environment;
    private String entityName;
    private final FileDirectoryFilter directoryFilter = new FileDirectoryFilter();
    private final FileOrderByDate orderByDate = new FileOrderByDate();

    private class FileOrderByDate implements Comparator {
        public int compare(Object o1, Object o2) {
            long l1 = ((File)o1).lastModified();
            long l2 = ((File)o2).lastModified();
            if(l1<l2) {
                return 1;
            }else if(l1==l2) {
                return 0;
            }else {
                return -1;
            }
        }
    }
    private class FileExtensionFilter implements FileFilter {
        private HashSet extensions;
        public FileExtensionFilter (String listString) {
            StringTokenizer st = new StringTokenizer(listString, ",");
            extensions = new HashSet();
            while (st.hasMoreTokens() ) {
                extensions.add( st.nextToken().toLowerCase() );
            }
        }
        public boolean accept(File f) {
            String name = f.getName();
            int index = name.lastIndexOf('.');
            String ext = null;
            if (index != -1) {
                ext = name.substring(index).toLowerCase();
            }
            if (ext == null) {
                return false;
            }else {
                return (extensions.contains(ext));
            }
        }
    };
    private class FileDirectoryFilter implements FileFilter {
        public boolean accept(File f) {
            return f.isDirectory();
        }
    };
    private class FileFileFilter implements FileFilter {
        public boolean accept(File f) {
            return f.isFile();
        }
    };
    private class FileMultipleAndFilter implements FileFilter {
        private FileFilter filter1;
        private FileFilter filter2;
        public FileMultipleAndFilter(FileFilter filter1, FileFilter filter2) {
            this.filter1 = filter1;
            this.filter2 = filter2;
        }
        public boolean accept(File f) {
            return filter1.accept(f) && filter2.accept(f);
        }
    };

    public void init(WebAppEnvironmentInterface environment) {
        this.environment = environment;
    }

    public void setEntityName(String entityName) {
        this.entityName = entityName;
    }

    public String getEntityName() {
        return entityName;
    }

    public EntityInterface load(EntityInterface template) {
        EntityInterface entity = null;
        try {
            Log.println(this,"load called");
            Set fields = PropertyUtils.describe(template).keySet();
            File file = null;
            EntityInterface temp = environment.getEntityFactory().create(entityName);
            PropertyUtils.copyProperties(temp,template);
            if(temp instanceof EntityReadUpdateInterface) {
                ((EntityReadUpdateInterface)temp).preReadUpdate();
            }
            for(Iterator it=fields.iterator();it.hasNext();) {
                String field = (String) it.next();
                if(isFieldIdentity(field)) {
                    Log.println(this, "Loading file "+(String)PropertyUtils.getProperty(template,field));
                    file = new File((String)PropertyUtils.getProperty(temp,field));
                    Log.println(this, "Loaded file "+file+" and "+(file!=null?file.exists():false));
                    break;
                }
            }
            String directory = null;
            for(Iterator it=fields.iterator();it.hasNext();) {
                String field = (String) it.next();
                String attributeName = getAttributeName(field);
                if(attributeName!=null && attributeName.equalsIgnoreCase("directory")) {
                    directory = (String)PropertyUtils.getProperty(template,"directory");
                    break;
                }
            }
            if(file!=null && file.exists()) {
                entity = environment.getEntityFactory().create(entityName);
                PropertyUtils.copyProperties(entity,template);
                if(entity instanceof EntityReadUpdateInterface) {
                    ((EntityReadUpdateInterface)entity).preReadUpdate();
                }
                for(Iterator it=fields.iterator();it.hasNext();) {
                    String field = (String) it.next();
                    if(isFieldGetFromInput(field)) {
                        PropertyUtils.setProperty(entity,field,PropertyUtils.getProperty(template,(String)getAttributeName(field)));
                    }else {
                        updateField(file, directory, field,(String)getAttributeName(field),(String)getType(field),entity);
                    }
                }
                if(entity instanceof EntityReadUpdateInterface) {
                    ((EntityReadUpdateInterface)entity).postReadUpdate();
                }
                Log.println(this,"Read entry: "+entity);
            }else {
                Log.println(this,"File does not exist");
                entity = null;
            }
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
        }
        return entity;
    }

    public boolean store(EntityInterface entity) {
        //TODO: Not implemented yet
        return false;
    }

    public boolean delete(EntityInterface entity) {
        //TODO: Not implemented yet
        return false;
    }

    public EntityInterface[] search(FilterInterface filter) {
        EntityInterface[] entities=new EntityInterface[0];
        try {
            Log.println(this,"search called");
            if(filter instanceof QueryFilter) {
                EntityInterface entity=environment.getEntityFactory().create(entityName);
                Set fields = PropertyUtils.describe(entity).keySet();
                Map attributeNames = new HashMap();
                Map types = new HashMap();
                for(Iterator it=fields.iterator();it.hasNext();) {
                    String field = (String) it.next();
                    String attributeName = getAttributeName(field);
                    if(attributeName!=null) {
                        attributeNames.put(field,attributeName);
                        types.put(field,getType(field));
                    }
                }

                String directory = (String)((QueryFilter)filter).getAttribute("directory");
                Log.println(this,"directory="+directory);
                if(directory!=null && directory.length()>0) {
                    File dir = new File(directory);
                    if(dir.exists() && dir.isDirectory()) {
                        String extensions = (String)((QueryFilter)filter).getAttribute("extensions");
                        FileFilter fileFilter=null;
                        if(extensions!=null && extensions.length()>0) {
                            fileFilter= new FileExtensionFilter(extensions);
                        }
                        Log.println(this,"extensions="+extensions);
                        Boolean tree =  (Boolean)((QueryFilter)filter).getAttribute("tree");
                        if(tree==null) {
                            tree = Boolean.FALSE;
                        }
                        Log.println(this,"tree="+tree);

                        Boolean directoriesOnly = (Boolean) ((QueryFilter)filter).getAttribute("directoriesonly");
                        Log.println(this,"directoriesonly="+directoriesOnly);
                        if(directoriesOnly!=null && directoriesOnly.booleanValue()) {
                            if(fileFilter!=null) {
                                fileFilter = new FileMultipleAndFilter(fileFilter,new FileDirectoryFilter());
                            }else {
                                fileFilter = new FileDirectoryFilter();
                            }
                        }else {
                            if(fileFilter!=null) {
                                fileFilter = new FileMultipleAndFilter(fileFilter,new FileFileFilter());
                            }else {
                                fileFilter = new FileFileFilter();
                            }
                        }
                        File[] files = getFilesInDir(dir,fileFilter,tree.booleanValue());
                        Log.println(this,"Got "+(files.length)+" files/directories");
                        Arrays.sort(files,orderByDate);
                        entities = new EntityInterface[files.length];
                        for (int i = 0; i < files.length; i++) {
                            File file = files[i];
                            entity= environment.getEntityFactory().create(entityName);
                            if(entity instanceof EntityReadUpdateInterface) {
                                ((EntityReadUpdateInterface)entity).preReadUpdate();
                            }
                            for(Iterator it=fields.iterator();it.hasNext();) {
                                String field = (String) it.next();
                                if(isFieldGetFromInput(field)) {
                                    PropertyUtils.setProperty(entity,field,((QueryFilter)filter).getAttribute((String)attributeNames.get(field)));
                                }else {
                                    updateField(file, directory, field,(String)attributeNames.get(field),(String)types.get(field),entity);
                                }
                            }
                            if(entity instanceof EntityReadUpdateInterface) {
                                ((EntityReadUpdateInterface)entity).postReadUpdate();
                            }
                            entities[i]=entity;
                            Log.println(this,"Read entry: "+entity);
                        }
                    }else {
                        Log.println(this,"directory does not exist");
                    }
                }
            }
        } catch (IllegalAccessException e) {
            e.printStackTrace();  //To change body of catch statement use Options | File Templates.
        } catch (InvocationTargetException e) {
            e.printStackTrace();  //To change body of catch statement use Options | File Templates.
        } catch (NoSuchMethodException e) {
            e.printStackTrace();  //To change body of catch statement use Options | File Templates.
        }
        return entities;
    }

    private boolean isFieldIdentity(String field) {
        String identity = environment.getResources().getParameter("entities."+getEntityName()+"."+getResourceName()+".fields."+field+".identity");
        if(identity!=null && identity.equalsIgnoreCase("true")) {
            return true;
        }else {
            return false;
        }
    }

    private boolean isFieldGetFromInput(String field) {
        String fromInput = environment.getResources().getParameter("entities."+getEntityName()+"."+getResourceName()+".fields."+field+".frominput");
        if(fromInput!=null && fromInput.equalsIgnoreCase("true")) {
            return true;
        }else {
            return false;
        }
    }

    private String getType(String field) {
        return environment.getResources().getParameter("entities."+getEntityName()+"."+getResourceName()+".fields."+field+".type");
    }

    private String getAttributeName(String field) {
        return environment.getResources().getParameter("entities."+getEntityName()+"."+getResourceName()+".fields."+field+".attribute");
    }

    private String getResourceName() {
        return environment.getResources().getParameter("entities."+getEntityName()+".storage.name");
    }

    public boolean update(FilterInterface filter, EntityInterface entity) {
        //TODO: Not implemented yet
        return false;
    }

    public boolean delete(FilterInterface filter) {
        //TODO: Not implemented yet
        return false;
    }
    private Object getAttribute(File file, String directory, String attribute) throws NoSuchMethodException, IllegalAccessException, InvocationTargetException {
        if(attribute.equals("lastModified")) {
            return new Long(file.lastModified());
        }else if(attribute.equals("length")) {
            return new Long(file.length());
        }else if(attribute.equals("directory")) {
            return directory;
        }else {
            try {
                return PropertyUtils.getProperty(file,attribute);
            } catch (IllegalAccessException e) {
            } catch (InvocationTargetException e) {
            } catch (NoSuchMethodException e) {
            }
        }
        return null;
    }
    private void updateField(File file, String directory, String field, String attributeName, String type, EntityInterface object) throws NoSuchMethodException, IllegalAccessException, InvocationTargetException {
        if(attributeName!=null) {
            if(type==null) {
            }else if(type.equals("String")) {
                String value = (String) getAttribute(file, directory, attributeName);
                Log.println(this,"entity.set"+field+"("+value+")");
                PropertyUtils.setProperty(object,field,value);
            }else if(type.equals("Date") || type.equals("Time") || type.equals("Timestamp")) {
                Object value = getAttribute(file, directory, attributeName);
                Date dateValue = null;
                if(value instanceof Long) {
                    dateValue = new Date(((Long)value).longValue());
                }
                Log.println(this,"entity.set"+field+"("+dateValue+")");
                PropertyUtils.setProperty(object,field,dateValue);
            }else if(type.equals("Integer")) {
                Object value = getAttribute(file, directory, attributeName);
                Integer intValue = null;
                if(value instanceof Number) {
                    intValue = new Integer((((Number)value).intValue()));
                }
                Log.println(this,"entity.set"+field+"("+intValue+")");
                PropertyUtils.setProperty(object,field,intValue);
            }else if(type.equals("Long")) {
                Object value = getAttribute(file, directory, attributeName);
                Long longValue = null;
                if(value instanceof Number) {
                    longValue = new Long((((Number)value).longValue()));
                }
                Log.println(this,"entity.set"+field+"("+longValue+")");
                PropertyUtils.setProperty(object,field,longValue);
            }else if(type.equals("Double")) {
                Object value = getAttribute(file, directory, attributeName);
                Double doubleValue = null;
                if(value instanceof Number) {
                    doubleValue = new Double((((Number)value).doubleValue()));
                }
                Log.println(this,"entity.set"+field+"("+doubleValue+")");
                PropertyUtils.setProperty(object,field,doubleValue);
            }else if(type.equals("Boolean")) {
                Object value = getAttribute(file, directory, attributeName);
                Boolean booleanValue = null;
                if(value instanceof Boolean) {
                    booleanValue = (Boolean)value;
                }
                Log.println(this,"entity.set"+field+"("+booleanValue+")");
                PropertyUtils.setProperty(object,field,booleanValue);
            }
        }
    }
    private File[] getFilesInDir(File directory, FileFilter filter, boolean includeSubDirectories) {
        List totalFiles = new ArrayList();
        if(includeSubDirectories) {
            File[] dirs = directory.listFiles(directoryFilter);
            for (int i = 0; i < dirs.length; i++) {
                File[] files = getFilesInDir(dirs[i],filter,includeSubDirectories);
                totalFiles.addAll(Arrays.asList(files));
            }
        }
        totalFiles.addAll(Arrays.asList(directory.listFiles(filter)));
        return (File[])totalFiles.toArray(new File[0]);
    }
}
