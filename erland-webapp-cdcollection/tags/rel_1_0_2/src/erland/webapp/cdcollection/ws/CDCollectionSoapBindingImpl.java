package erland.webapp.cdcollection.ws;

import erland.util.StringUtil;
import erland.webapp.cdcollection.logic.media.MediaHelper;
import erland.webapp.cdcollection.logic.media.disc.DiscHelper;
import erland.webapp.cdcollection.entity.collection.CollectionMedia;
import erland.webapp.cdcollection.entity.collection.Collection;
import erland.webapp.common.act.WebAppEnvironmentPlugin;
import erland.webapp.usermgmt.User;

import javax.servlet.http.HttpServletRequest;
import java.net.URL;
import java.io.InputStreamReader;
import java.io.BufferedReader;
import java.io.IOException;
import java.rmi.RemoteException;

import org.apache.axis.MessageContext;
import org.apache.commons.beanutils.PropertyUtils;

/*
 * Copyright (C) 2003-2004 Erland Isaksson (erland_i@hotmail.com)
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

public class CDCollectionSoapBindingImpl implements erland.webapp.cdcollection.ws.CDCollection{
    public int importMedia(java.lang.String username, java.lang.String password, java.lang.String category, java.lang.String discId) throws java.rmi.RemoteException {
        try {
            if(!validateUser(username,password,"manager")) {
                throw new java.rmi.RemoteException("Invalid username or password");
            }
            int mediaId = MediaHelper.importMedia(category,discId);
            return mediaId;
        } catch (IOException e) {
            e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
            throw new java.rmi.RemoteException("An error occured while importing",e);
        }
    }

    public int importDisc(java.lang.String username, java.lang.String password, int mediaId, java.lang.String category, java.lang.String discId) throws java.rmi.RemoteException {
        try {
            if(!validateUser(username,password,"manager")) {
                throw new java.rmi.RemoteException("Invalid username or password");
            }
            int result = DiscHelper.importDiscToMedia(new Integer(mediaId),category,discId);
            return result;
        } catch (IOException e) {
            e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
            throw new java.rmi.RemoteException("An error occured while importing",e);
        }
    }

    public boolean addMediaToCollection(java.lang.String username, java.lang.String password, int collectionId, int mediaId) throws java.rmi.RemoteException {
        if(!validateUser(username,password,null)) {
            throw new java.rmi.RemoteException("Invalid username or password");
        }
        Collection template = (Collection) WebAppEnvironmentPlugin.getEnvironment().getEntityFactory().create("cdcollection-collection");
        template.setId(new Integer(collectionId));
        Collection collection = (Collection) WebAppEnvironmentPlugin.getEnvironment().getEntityStorageFactory().getStorage("cdcollection-collection").load(template);
        if(collection==null) {
            throw new java.rmi.RemoteException("Invalid collection id");
        }
        if(!collection.getUsername().equals(username)) {
            throw new java.rmi.RemoteException("Invalid username or password");
        }
        CollectionMedia entity = (CollectionMedia) WebAppEnvironmentPlugin.getEnvironment().getEntityFactory().create("cdcollection-collectionmedia");
        entity.setCollectionId(new Integer(collectionId));
        entity.setMediaId(new Integer(mediaId));

        return WebAppEnvironmentPlugin.getEnvironment().getEntityStorageFactory().getStorage("cdcollection-collectionmedia").store(entity);
    }

    public java.lang.String getMedias() throws java.rmi.RemoteException {
        MessageContext context = MessageContext.getCurrentContext();
        HttpServletRequest request = (HttpServletRequest) context.getProperty("transport.http.servletRequest");
        try {
            String port = "";
            if(request.getServerPort()!=80) {
                port=":"+request.getServerPort();
            }
            BufferedReader reader = new BufferedReader(new InputStreamReader(new URL("http://"+request.getServerName()+port+request.getContextPath()+"/do/ws/getallmedias").openStream()));
            StringBuffer sb = new StringBuffer(100000);
            int character = reader.read();
            while(character>=0) {
                sb.append((char)character);
                character = reader.read();
            }
            return sb.toString();
        } catch (IOException e) {
            e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
            throw new java.rmi.RemoteException("Unable to load data",e);
        }
    }

    public java.lang.String getMedia(int mediaId) throws java.rmi.RemoteException {
        MessageContext context = MessageContext.getCurrentContext();
        HttpServletRequest request = (HttpServletRequest) context.getProperty("transport.http.servletRequest");
        try {
            String port = "";
            if(request.getServerPort()!=80) {
                port=":"+request.getServerPort();
            }
            BufferedReader reader = new BufferedReader(new InputStreamReader(new URL("http://"+request.getServerName()+port+request.getContextPath()+"/do/ws/getmedia?idDisplay="+mediaId).openStream()));
            StringBuffer sb = new StringBuffer(100000);
            int character = reader.read();
            while(character>=0) {
                sb.append((char)character);
                character = reader.read();
            }
            return sb.toString();
        } catch (IOException e) {
            e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
            throw new java.rmi.RemoteException("Unable to load data",e);
        }
    }

    public java.lang.String getCollections(java.lang.String username, java.lang.String language) throws java.rmi.RemoteException {
        MessageContext context = MessageContext.getCurrentContext();
        HttpServletRequest request = (HttpServletRequest) context.getProperty("transport.http.servletRequest");
        try {
            if(StringUtil.asNull(language)!=null) {
                language = "&language="+language;
            }
            String port = "";
            if(request.getServerPort()!=80) {
                port=":"+request.getServerPort();
            }
            BufferedReader reader = new BufferedReader(new InputStreamReader(new URL("http://"+request.getServerName()+port+request.getContextPath()+"/do/ws/getallcollections?user="+StringUtil.asEmpty(username)+StringUtil.asEmpty(language)).openStream()));
            StringBuffer sb = new StringBuffer(100000);
            int character = reader.read();
            while(character>=0) {
                sb.append((char)character);
                character = reader.read();
            }
            return sb.toString();
        } catch (IOException e) {
            e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
            throw new java.rmi.RemoteException("Unable to load data",e);
        }
    }

    public java.lang.String getCollection(int collectionId, java.lang.String language) throws java.rmi.RemoteException {
        MessageContext context = MessageContext.getCurrentContext();
        HttpServletRequest request = (HttpServletRequest) context.getProperty("transport.http.servletRequest");
        try {
            if(StringUtil.asNull(language)!=null) {
                language = "?language="+language;
            }
            String port = "";
            if(request.getServerPort()!=80) {
                port=":"+request.getServerPort();
            }
            BufferedReader reader = new BufferedReader(new InputStreamReader(new URL("http://"+request.getServerName()+port+request.getContextPath()+"/do/ws/getcollection?collectionDisplay="+collectionId+StringUtil.asEmpty(language)).openStream()));
            StringBuffer sb = new StringBuffer(100000);
            int character = reader.read();
            while(character>=0) {
                sb.append((char)character);
                character = reader.read();
            }
            return sb.toString();
        } catch (IOException e) {
            e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
            throw new java.rmi.RemoteException("Unable to load data",e);
        }
    }

    private boolean validateUser(String username, String password,String role) {
        User user = (User)WebAppEnvironmentPlugin.getEnvironment().getEntityFactory().create("usermgmt-user");
        user.setUsername(username);
        user.setPassword(password);
        if(user.login("cdcollection")) {
            if(role!=null && user.hasRole(role)) {
                return true;
            }else {
                return true;
            }
        }
        return false;
    }
}
