package erland.webapp.gallery.act.loader;

import erland.util.ObjectStorageInterface;
import erland.webapp.gallery.entity.gallery.picture.Picture;
import erland.webapp.gallery.fb.gallery.picture.PicturePB;
import erland.webapp.common.image.JPEGMetadataHandler;
import erland.webapp.common.image.FileMetadataHandler;
import erland.webapp.common.EntityInterface;
import org.apache.commons.beanutils.PropertyUtils;

import java.lang.reflect.InvocationTargetException;

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

public class PictureParameterStorage implements ObjectStorageInterface {
    private EntityInterface entity;
    private Object bean;
    private JPEGMetadataHandler jpegHandler;
    private FileMetadataHandler fileHandler;
    private String filename;
    public PictureParameterStorage(String filename, EntityInterface entity, Object bean, JPEGMetadataHandler jpegHandler, FileMetadataHandler fileHandler) {
        this.entity = entity;
        this.bean = bean;
        this.jpegHandler = jpegHandler;
        this.fileHandler = fileHandler;
        this.filename = filename;
    }
    public Object get(String name) {
        if(entity!=null && name.startsWith("entity.")) {
            try {
                return PropertyUtils.getProperty(entity,name.substring(7));
            } catch (IllegalAccessException e) {
            } catch (InvocationTargetException e) {
            } catch (NoSuchMethodException e) {
            }
        }else if(bean!=null && name.startsWith("bean.")) {
            try {
                return PropertyUtils.getProperty(bean,name.substring(5));
            } catch (IllegalAccessException e) {
            } catch (InvocationTargetException e) {
            } catch (NoSuchMethodException e) {
            }
        }else if(jpegHandler!=null && name.startsWith("metadata.")) {
            if(!filename.equals(jpegHandler.getFilename())) {
                jpegHandler.load(filename);
            }
            return jpegHandler.getValue(name.substring(9));
        }else if(fileHandler!=null && name.startsWith("file.")) {
            if(!filename.equals(fileHandler.getFilename())) {
                fileHandler.load(filename);
            }
            return fileHandler.getValue(name.substring(5));
        }
        return null;
    }

    public void set(String name, Object value) {
        // Not needed
    }

    public void delete(String name) {
        // Not needed
    }
}
