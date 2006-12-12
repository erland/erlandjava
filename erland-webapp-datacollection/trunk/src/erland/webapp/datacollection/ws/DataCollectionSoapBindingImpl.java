package erland.webapp.datacollection.ws;

import erland.util.StringUtil;
import erland.webapp.datacollection.entity.collection.Collection;
import erland.webapp.datacollection.entity.account.UserAccount;
import erland.webapp.datacollection.entity.entry.Entry;
import erland.webapp.datacollection.entity.entry.data.Data;
import erland.webapp.datacollection.fb.account.AccountFB;
import erland.webapp.datacollection.fb.account.AccountPB;
import erland.webapp.datacollection.logic.entry.EntryHelper;
import erland.webapp.common.act.WebAppEnvironmentPlugin;
import erland.webapp.common.QueryFilter;
import erland.webapp.common.EntityInterface;
import erland.webapp.common.WebAppEnvironmentInterface;
import erland.webapp.usermgmt.User;
import erland.webapp.usermgmt.UserApplicationRole;

import javax.servlet.http.HttpServletRequest;
import java.net.URL;
import java.net.URLConnection;
import java.io.*;
import java.util.*;
import java.rmi.RemoteException;
import java.text.ParseException;
import java.lang.reflect.InvocationTargetException;

import org.apache.axis.MessageContext;
import org.apache.commons.beanutils.PropertyUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.dom4j.io.SAXReader;
import org.dom4j.Document;
import org.dom4j.Element;
import org.dom4j.DocumentException;
import org.dom4j.Node;

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

public class DataCollectionSoapBindingImpl implements erland.webapp.datacollection.ws.DataCollection {
    /**
     * Logging instance
     */
    private static Log LOG = LogFactory.getLog(DataCollectionSoapBindingImpl.class);

    public String apiVersion() throws RemoteException {
        return "1.0";
    }

    public java.lang.String getEntries(java.lang.String application) throws java.rmi.RemoteException {
        MessageContext context = MessageContext.getCurrentContext();
        HttpServletRequest request = (HttpServletRequest) context.getProperty("transport.http.servletRequest");
        try {
            String port = "";
            if (request.getServerPort() != 80) {
                port = ":" + request.getServerPort();
            }
            BufferedReader reader = new BufferedReader(new InputStreamReader(new URL("http://" + request.getServerName() + port + request.getContextPath() + "/do/ws/getallentries?application=" + StringUtil.asEmpty(application)).openStream()));
            StringBuffer sb = new StringBuffer(100000);
            int character = reader.read();
            while (character >= 0) {
                sb.append((char) character);
                character = reader.read();
            }
            return sb.toString();
        } catch (IOException e) {
            e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
            throw new RemoteException("Unable to load data", e);
        }
    }

    public java.lang.String getEntry(int entryId) throws java.rmi.RemoteException {
        MessageContext context = MessageContext.getCurrentContext();
        HttpServletRequest request = (HttpServletRequest) context.getProperty("transport.http.servletRequest");
        try {
            String port = "";
            if (request.getServerPort() != 80) {
                port = ":" + request.getServerPort();
            }
            BufferedReader reader = new BufferedReader(new InputStreamReader(new URL("http://" + request.getServerName() + port + request.getContextPath() + "/do/ws/getentry?idDisplay=" + entryId).openStream()));
            StringBuffer sb = new StringBuffer(100000);
            int character = reader.read();
            while (character >= 0) {
                sb.append((char) character);
                character = reader.read();
            }
            return sb.toString();
        } catch (IOException e) {
            e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
            throw new java.rmi.RemoteException("Unable to load data", e);
        }
    }

    public java.lang.String getCollections(java.lang.String username, java.lang.String application) throws java.rmi.RemoteException {
        MessageContext context = MessageContext.getCurrentContext();
        HttpServletRequest request = (HttpServletRequest) context.getProperty("transport.http.servletRequest");
        try {
            String port = "";
            if (request.getServerPort() != 80) {
                port = ":" + request.getServerPort();
            }
            BufferedReader reader = new BufferedReader(new InputStreamReader(new URL("http://" + request.getServerName() + port + request.getContextPath() + "/do/ws/getallcollections?user=" + StringUtil.asEmpty(username) + "&application=" + StringUtil.asEmpty(application)).openStream()));
            StringBuffer sb = new StringBuffer(100000);
            int character = reader.read();
            while (character >= 0) {
                sb.append((char) character);
                character = reader.read();
            }
            return sb.toString();
        } catch (IOException e) {
            e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
            throw new RemoteException("Unable to load data", e);
        }
    }

    public java.lang.String getCollection(int collectionId) throws java.rmi.RemoteException {
        MessageContext context = MessageContext.getCurrentContext();
        HttpServletRequest request = (HttpServletRequest) context.getProperty("transport.http.servletRequest");
        try {
            String port = "";
            if (request.getServerPort() != 80) {
                port = ":" + request.getServerPort();
            }
            BufferedReader reader = new BufferedReader(new InputStreamReader(new URL("http://" + request.getServerName() + port + request.getContextPath() + "/do/ws/getcollection?collectionDisplay=" + collectionId).openStream()));
            StringBuffer sb = new StringBuffer(100000);
            int character = reader.read();
            while (character >= 0) {
                sb.append((char) character);
                character = reader.read();
            }
            return sb.toString();
        } catch (IOException e) {
            e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
            throw new RemoteException("Unable to load data", e);
        }
    }

    public java.lang.String registerUser(java.lang.String username, java.lang.String password, java.lang.String firstName, java.lang.String lastName, java.lang.String eMail) throws java.rmi.RemoteException {
        MessageContext context = MessageContext.getCurrentContext();
        HttpServletRequest request = (HttpServletRequest) context.getProperty("transport.http.servletRequest");
        if (checkUser(username)) {
            User template = (User) WebAppEnvironmentPlugin.getEnvironment().getEntityFactory().create("usermgmt-user");
            template.setUsername(username);
            template.setFirstName(firstName);
            template.setLastName(lastName);
            template.setPassword(password);
            WebAppEnvironmentPlugin.getEnvironment().getEntityStorageFactory().getStorage("usermgmt-user").store(template);
            UserApplicationRole templateRole = (UserApplicationRole) WebAppEnvironmentPlugin.getEnvironment().getEntityFactory().create("usermgmt-userapplicationrole");
            templateRole.setUsername(username);
            templateRole.setApplication("datacollection");
            templateRole.setRole("user");
            WebAppEnvironmentPlugin.getEnvironment().getEntityStorageFactory().getStorage("usermgmt-userapplicationrole").store(templateRole);
            UserAccount templateAccount = (UserAccount) WebAppEnvironmentPlugin.getEnvironment().getEntityFactory().create("datacollection-useraccount");
            templateAccount.setUsername(username);
            templateAccount.setWelcomeText("Welcome to my data collection");
            templateAccount.setDescription("Data collection for " + firstName + " " + lastName);
            templateAccount.setOfficial(Boolean.FALSE);
            WebAppEnvironmentPlugin.getEnvironment().getEntityStorageFactory().getStorage("datacollection-useraccount").store(templateAccount);
            return "";
        } else {
            throw new RemoteException("Username is already taken");
        }
    }

    public java.lang.String loginUser(java.lang.String username, java.lang.String password) throws java.rmi.RemoteException {
        MessageContext context = MessageContext.getCurrentContext();
        HttpServletRequest request = (HttpServletRequest) context.getProperty("transport.http.servletRequest");
        if (validateUser(username,password,"user")) {
            return "";
        } else {
            throw new RemoteException("Invalid username or password");
        }
    }

    public java.lang.String addDataEntry(java.lang.String username, java.lang.String password, java.lang.String application, int collectionId, int overwrite, java.lang.String data) throws java.rmi.RemoteException {
        try {
            MessageContext context = MessageContext.getCurrentContext();
            HttpServletRequest request = (HttpServletRequest) context.getProperty("transport.http.servletRequest");
            username = StringUtil.asNull(username);
            boolean anonymous = false;
            if (username == null || validateUser(username, password, "user")) {
                if (username == null) {
                    if (StringUtil.asNull(application) == null) {
                        throw new RemoteException("Application must be specified");
                    }
                    if (checkUser(application)) {
                        throw new RemoteException("Anonymous upload for " + application + " is not allowed");
                    }
                    UserAccount account = (UserAccount) WebAppEnvironmentPlugin.getEnvironment().getEntityFactory().create("datacollection-useraccount");
                    account.setUsername(application);
                    account = (UserAccount) WebAppEnvironmentPlugin.getEnvironment().getEntityStorageFactory().getStorage("datacollection-useraccount").load(account);
                    if(account==null || !account.getAnonymous().booleanValue()) {
                        throw new RemoteException("Anonymous upload for " + application + " is not allowed");
                    }
                    username = application;
                    anonymous = true;
                }
                Collection template = (Collection) WebAppEnvironmentPlugin.getEnvironment().getEntityFactory().create("datacollection-collection");
                template.setId(collectionId);
                Collection collection = (Collection) WebAppEnvironmentPlugin.getEnvironment().getEntityStorageFactory().getStorage("datacollection-collection").load(template);
                if(collection!=null && !collection.getUsername().equals(username)) {
                    throw new RemoteException("Incorrect collection identifier");
                }
                if (collection == null) {
                    if (collectionId != 0) {
                        throw new RemoteException("Invalid collection");
                    }
                    QueryFilter filter = new QueryFilter("allforuserandapplication");
                    filter.setAttribute("username", username);
                    filter.setAttribute("application", application);
                    EntityInterface[] entities = WebAppEnvironmentPlugin.getEnvironment().getEntityStorageFactory().getStorage("datacollection-collection").search(filter);
                    if (entities.length > 0) {
                        collection = (Collection) entities[0];
                    } else {
                        LOG.info("Got no hit for " + username + ", " + application);
                        collection = (Collection) WebAppEnvironmentPlugin.getEnvironment().getEntityFactory().create("datacollection-collection");
                        collection.setUsername(username);
                        collection.setDescription("Collection for " + application);
                        collection.setTitle(application);
                        collection.setApplication(application);
                        collection.setOfficial(Boolean.TRUE);
                        if (!WebAppEnvironmentPlugin.getEnvironment().getEntityStorageFactory().getStorage("datacollection-collection").store(collection)) {
                            throw new RemoteException("Unable to create new collection for " + application);
                        }
                    }
                }
                if (!collection.getUsername().equals(username)) {
                    throw new RemoteException("Collection doesnt match username");
                }
                LOG.debug(data);
                StringReader reader = new StringReader(data);
                if (!handleAddDataEntry(collection.getId(), reader, anonymous, overwrite!=0)) {
                    throw new RemoteException("Error creating entry");
                }
                return "";
            } else {
                throw new RemoteException("Invalid username or password");
            }
        } catch (RemoteException e) {
            throw e;
        }
    }

    private boolean validateUser(String username, String password, String role) {
        User user = (User) WebAppEnvironmentPlugin.getEnvironment().getEntityFactory().create("usermgmt-user");
        user.setUsername(username);
        user.setPassword(password);
        if (user.login("datacollection")) {
            if (role != null && user.hasRole(role)) {
                return true;
            } else {
                return true;
            }
        } else {

        }
        return false;
    }

    private boolean checkUser(String username) {
        User template = (User) WebAppEnvironmentPlugin.getEnvironment().getEntityFactory().create("usermgmt-user");
        template.setUsername(username);
        User user = (User) WebAppEnvironmentPlugin.getEnvironment().getEntityStorageFactory().getStorage("usermgmt-user").load(template);
        return user == null;
    }

    public boolean handleAddDataEntry(Integer collectionId, Reader reader, boolean anonymous, boolean overwrite) throws java.rmi.RemoteException {
        SAXReader saxReader = new SAXReader();
        try {
            Document document = saxReader.read(reader);

            if (document != null) {
                Node entryNode = document.selectSingleNode("/entry");
                Node element = entryNode.selectSingleNode("title");
                String title = element.getText();
                element = entryNode.selectSingleNode("description");
                String description = element.getText();
                element = entryNode.selectSingleNode("id");
                String uniqueId = element.getText();
                QueryFilter filter = new QueryFilter("allforcollectionanduniqueid");
                filter.setAttribute("collection", collectionId);
                filter.setAttribute("uniqueid", uniqueId);
                EntityInterface[] entities = WebAppEnvironmentPlugin.getEnvironment().getEntityStorageFactory().getStorage("datacollection-entry").search(filter);
                Integer entryId = null;
                if (entities.length == 0) {
                    if (StringUtil.asNull(title) == null || StringUtil.asNull(uniqueId) == null) {
                        throw new RemoteException("Title and uniqueid is mandatory");
                    }
                    Entry entry = (Entry) WebAppEnvironmentPlugin.getEnvironment().getEntityFactory().create("datacollection-entry");
                    entry.setTitle(title);
                    entry.setDescription(description);
                    entry.setUniqueEntryId(uniqueId);
                    entry.setCollection(collectionId);
                    entry.setLastChanged(new Date());
                    if (!WebAppEnvironmentPlugin.getEnvironment().getEntityStorageFactory().getStorage("datacollection-entry").store(entry)) {
                        throw new RemoteException("Unable to create new entry");
                    }
                    entryId = entry.getId();
                } else if(overwrite) {
                    Entry entry = ((Entry) entities[0]);
                    EntryHelper.storeHistory(WebAppEnvironmentPlugin.getEnvironment(), entry.getId());
                    entry.setTitle(title);
                    entry.setDescription(description);
                    entry.setLastChanged(new Date());
                    if (!WebAppEnvironmentPlugin.getEnvironment().getEntityStorageFactory().getStorage("datacollection-entry").store(entry)) {
                        throw new RemoteException("Unable to update existing entry");
                    }
                    entryId = entry.getId();
                } else {
                    throw new RemoteException("An entry with this unique identifier already exist, change the identifier or choose to overwrite the existing");
                }
                List list = entryNode.selectNodes("data");
                LOG.info("Found " + list.size() + " data elements");
                int i = 1;
                for (Iterator iter = list.iterator(); iter.hasNext();) {
                    Element dataElement = (Element) iter.next();
                    LOG.info("Parsing data " + i);

                    element = dataElement.selectSingleNode("type");
                    String type = element.getText();
                    element = dataElement.selectSingleNode("content");
                    String content = element.getText();

                    filter = new QueryFilter("allforentryandtype");
                    filter.setAttribute("entry", entryId);
                    filter.setAttribute("type", type);
                    EntityInterface[] dataEntities = WebAppEnvironmentPlugin.getEnvironment().getEntityStorageFactory().getStorage("datacollection-data").search(filter);
                    if (dataEntities.length > 0) {
                        Data data = (Data) dataEntities[0];
                        data.setContent(content);
                        if (!WebAppEnvironmentPlugin.getEnvironment().getEntityStorageFactory().getStorage("datacollection-data").store(data)) {
                            throw new RemoteException("Unable to update existing data element");
                        }
                    } else {
                        Data data = (Data) WebAppEnvironmentPlugin.getEnvironment().getEntityFactory().create("datacollection-data");
                        data.setEntryId(entryId);
                        data.setType(type);
                        data.setContent(content);
                        if (!WebAppEnvironmentPlugin.getEnvironment().getEntityStorageFactory().getStorage("datacollection-data").store(data)) {
                            throw new RemoteException("Unable to create new data element");
                        }
                    }
                    i++;
                }
                return true;
            }
        } catch (DocumentException e) {
            LOG.error("Error while reading xml", e);
            throw new RemoteException("Parse error", e);
        } catch (IllegalAccessException e) {
            LOG.error("Error creating history of old data", e);
            throw new RemoteException("Error creating history of old data", e);
        } catch (NoSuchMethodException e) {
            LOG.error("Error creating history of old data", e);
            throw new RemoteException("Error creating history of old data", e);
        } catch (InvocationTargetException e) {
            LOG.error("Error creating history of old data", e);
            throw new RemoteException("Error creating history of old data", e);
        }
        return false;
    }
}
