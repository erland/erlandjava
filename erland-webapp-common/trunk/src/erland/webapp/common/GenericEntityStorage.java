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

import java.sql.*;
import java.lang.reflect.InvocationTargetException;
import java.util.*;
import java.util.Date;
import java.net.URLEncoder;
import java.net.URLDecoder;
import java.io.UnsupportedEncodingException;

import org.apache.commons.beanutils.PropertyUtils;

public class GenericEntityStorage extends EntityStorage {
    protected EntityInterface select(Connection conn, EntityInterface template) throws SQLException {
        try {
            EntityInterface entity = getEnvironment().getEntityFactory().create(getEntityName());
            Set fields = PropertyUtils.describe(entity).keySet();
            StringBuffer sb = new StringBuffer("select ");
            boolean bFirst = true;
            int noOfFields = 0;
            for (Iterator it=fields.iterator(); it.hasNext();) {
                String field = (String)it.next();
                String dbField = getColumnName(field);
                if(dbField!=null) {
                    if(!bFirst) {
                        sb.append(",");
                    }
                    bFirst = false;
                    sb.append(dbField);
                    noOfFields++;
                }
            }
            String table = getTableName();
            sb.append(" from "+table+" where ");
            PreparedStatement stmt = makeWhereStatement(conn,sb,fields,template,1);
            Log.println(this,"Created prepared statement: "+stmt);
            if(stmt!=null && noOfFields>0) {
                ResultSet rs = stmt.executeQuery();
                if(rs.next()) {
                    if(entity instanceof EntityReadUpdateInterface) {
                        ((EntityReadUpdateInterface)entity).preReadUpdate();
                    }
                    for (Iterator it=fields.iterator(); it.hasNext();) {
                        String field = (String) it.next();
                        if(isFieldGetFromInput(field)) {
                            PropertyUtils.setProperty(entity,field,PropertyUtils.getProperty(template,(String)getAttributeName(field)));
                        }else {
                            updateField(rs,field,getColumnName(field),getFieldType(field),entity);
                        }
                    }
                    if(entity instanceof EntityReadUpdateInterface) {
                        ((EntityReadUpdateInterface)entity).postReadUpdate();
                    }
                    return entity;
                }
            }
        } catch (IllegalAccessException e) {
            e.printStackTrace();  //To change body of catch statement use Options | File Templates.
        } catch (NoSuchMethodException e) {
            e.printStackTrace();  //To change body of catch statement use Options | File Templates.
        } catch (InvocationTargetException e) {
            e.printStackTrace();  //To change body of catch statement use Options | File Templates.
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();  //To change body of catch statement use Options | File Templates.
        }
        return null;
    }

    private String getTableName() {
        String table = getEnvironment().getResources().getParameter("entities."+getEntityName()+"."+getResourceName()+".table");
        return table;
    }

    private String getColumnName(String field) {
        String dbField = getEnvironment().getResources().getParameter("entities."+getEntityName()+"."+getResourceName()+".fields."+field+".column");
        return dbField;
    }

    protected EntityInterface[] select(Connection conn, FilterInterface filter) throws SQLException {
        try {
            Log.println(this,"select called");
            if(filter instanceof QueryFilter) {
                EntityInterface template = getEnvironment().getEntityFactory().create(getEntityName());
                Set fields = PropertyUtils.describe(template).keySet();
                Log.println(this,"Get class "+template.getClass().getName());
                Log.println(this,"Number of fields="+fields.size());
                StringBuffer sb = null;
                String completeQueryString = getEnvironment().getResources().getParameter("entities."+getEntityName()+"."+getResourceName()+".queries."+((QueryFilter)filter).getQueryName()+".completequerystring");
                String dynamicQueryString = getEnvironment().getResources().getParameter("entities."+getEntityName()+"."+getResourceName()+".queries."+((QueryFilter)filter).getQueryName()+".dynamicquerystring");
                boolean bDynamicQuery = false;
                if(dynamicQueryString!=null && dynamicQueryString.equalsIgnoreCase("true")) {
                    bDynamicQuery = true;
                }
                if(completeQueryString==null || completeQueryString.length()==0) {
                    sb = new StringBuffer("select ");
                    String unique = getEnvironment().getResources().getParameter("entities."+getEntityName()+"."+getResourceName()+".queries."+((QueryFilter)filter).getQueryName()+".unique");
                    if(unique!=null && unique.equalsIgnoreCase("true")) {
                        sb.append(" distinct ");
                    }
                    boolean bFirst = true;
                    for(Iterator it=fields.iterator();it.hasNext();) {
                        String field = (String)it.next();
                        String dbField = getColumnName(field);
                        Log.println(this,"dbField for "+field+" = "+dbField);
                        if(dbField!=null) {
                            if(!bFirst) {
                                sb.append(",");
                            }
                            bFirst = false;
                            sb.append(dbField);
                        }
                    }
                    String table = getTableName();
                    sb.append(" from "+table);
                    Log.println(this,"Get queryString "+"entities."+getEntityName()+"."+getResourceName()+".queries."+((QueryFilter)filter).getQueryName()+".querystring");
                    String queryString = getEnvironment().getResources().getParameter("entities."+getEntityName()+"."+getResourceName()+".queries."+((QueryFilter)filter).getQueryName()+".querystring");
                    Log.println(this,"queryString="+queryString);
                    if(queryString!=null) {
                        if(queryString.length()>0) {
                            sb.append(" where "+queryString);
                            updateDynamicQuery(0,sb,(QueryFilter)filter);
                        }
                    }else {
                        sb=null;
                    }
                }else {
                    sb = new StringBuffer(completeQueryString);
                    updateDynamicQuery(0,sb,(QueryFilter)filter);
                }
                if(sb!=null) {
                    String orderString = null;
                    if(((QueryFilter)filter).getOrderName()!=null) {
                        orderString = getEnvironment().getResources().getParameter("entities."+getEntityName()+"."+getResourceName()+".queries."+((QueryFilter)filter).getQueryName()+".orderstrings."+((QueryFilter)filter).getOrderName());
                    }else {
                        orderString = getEnvironment().getResources().getParameter("entities."+getEntityName()+"."+getResourceName()+".queries."+((QueryFilter)filter).getQueryName()+".orderstring");
                    }
                    if(orderString!=null && orderString.length()>0) {
                        sb.append(" "+orderString);
                    }
                    Log.println(this,"select: "+sb.toString());
                    PreparedStatement stmt = conn.prepareStatement(sb.toString());
                    Log.println(this,"Created prepared statement: "+stmt);
                    boolean bLastParameter = false;
                    for(int fieldNo=1,queryFieldNo=1;!bLastParameter;queryFieldNo++) {
                        Log.println(this,"Get queryAttr "+"entities."+getEntityName()+"."+getResourceName()+".queries."+((QueryFilter)filter).getQueryName()+".fields."+queryFieldNo+".field");
                        String queryAttr = getEnvironment().getResources().getParameter("entities."+getEntityName()+"."+getResourceName()+".queries."+((QueryFilter)filter).getQueryName()+".fields."+queryFieldNo+".field");
                        Log.println(this,"Get queryAttr "+"entities."+getEntityName()+"."+getResourceName()+".queries."+((QueryFilter)filter).getQueryName()+".fields."+queryFieldNo+".type");
                        String queryType = getEnvironment().getResources().getParameter("entities."+getEntityName()+"."+getResourceName()+".queries."+((QueryFilter)filter).getQueryName()+".fields."+queryFieldNo+".type");
                        Log.println(this,"queryAttr="+queryAttr);
                        if(queryAttr!=null) {
                            Object queryAttrValue = ((QueryFilter)filter).getAttribute(queryAttr);
                            if(queryAttrValue instanceof Collection) {
                                for(Iterator it=((Collection)queryAttrValue).iterator();it.hasNext();) {
                                    updateStatement(stmt,it.next(),queryType,fieldNo++);
                                }
                            }else {
                                updateStatement(stmt,queryAttrValue,queryType,fieldNo++);
                            }
                        }else {
                            bLastParameter = true;
                        }
                    }
                    ResultSet rs = stmt.executeQuery();
                    Collection result = new ArrayList();
                    Map dbFields = new HashMap();
                    Map types = new HashMap();
                    Map attributes = new HashMap();
                    Set fromInput = new HashSet();
                    for(Iterator it=fields.iterator();it.hasNext();) {
                        String field = (String) it.next();
                        dbFields.put(field,getColumnName(field));
                        types.put(field,getFieldType(field));
                        String name = getAttributeName(field);
                        if(name!=null && name.length()>0) {
                            attributes.put(field,name);
                        }
                        if(isFieldGetFromInput(field)) {
                            fromInput.add(field);
                        }
                    }
                    while(rs.next()) {
                        EntityInterface entity = getEnvironment().getEntityFactory().create(getEntityName());
                        if(entity instanceof EntityReadUpdateInterface) {
                            ((EntityReadUpdateInterface)entity).preReadUpdate();
                        }
                        for(Iterator it=fields.iterator();it.hasNext();) {
                            String field = (String)it.next();
                            if(fromInput.contains(field)) {
                                PropertyUtils.setProperty(entity,field,((QueryFilter)filter).getAttribute((String)attributes.get(field)));
                            }else {
                                updateField(rs,field,(String)dbFields.get(field),(String)types.get(field),entity);
                            }
                        }
                        if(entity instanceof EntityReadUpdateInterface) {
                            ((EntityReadUpdateInterface)entity).postReadUpdate();
                        }
                        Log.println(this,"Read entry: "+entity);
                        result.add(entity);
                    }
                    return (EntityInterface[])result.toArray(new EntityInterface[0]);
                }
            }
        } catch (IllegalAccessException e) {
            e.printStackTrace();  //To change body of catch statement use Options | File Templates.
        } catch (NoSuchMethodException e) {
            e.printStackTrace();  //To change body of catch statement use Options | File Templates.
        } catch (InvocationTargetException e) {
            e.printStackTrace();  //To change body of catch statement use Options | File Templates.
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();  //To change body of catch statement use Options | File Templates.
        }
        return new EntityInterface[0];
    }

    protected boolean insert(Connection conn, EntityInterface entity) throws SQLException {
        try {
            Set fields = PropertyUtils.describe(entity).keySet();
            String table = getTableName();
            StringBuffer sb = new StringBuffer("insert into "+table+" (");
            boolean bFirst = true;
            int noOfFields = 0;
            List autoKeys = new ArrayList();
            for(Iterator it=fields.iterator();it.hasNext();) {
                String field = (String)it.next();
                String dbField = getColumnName(field);
                if(dbField!=null && (!isFieldAuto(field)||PropertyUtils.getProperty(entity,field)!=null)) {
                    if(!bFirst) {
                        sb.append(",");
                    }
                    bFirst = false;
                    sb.append(dbField);
                    noOfFields++;
                }else if(dbField!=null && isFieldAuto(field)) {
                    autoKeys.add(dbField);
                }
            }
            sb.append(") values (");
            for(int i=0;i<noOfFields;i++) {
                if(i>0) {
                    sb.append(",");
                }
                sb.append("?");
            }
            sb.append(")");
            if(noOfFields>0) {
                Log.println(this,"insert:"+sb.toString());
                PreparedStatement stmt = conn.prepareStatement(sb.toString(),(String[])autoKeys.toArray(new String[0]));
                Log.println(this,"Created prepared statement: "+stmt);
                boolean bUseMySQLOldMethod = false;
                if(stmt==null) {
                    bUseMySQLOldMethod = true;
                    stmt = conn.prepareStatement(sb.toString());
                    Log.println(this,"Created prepared statement(For old MySQL method): "+stmt);
                }
                int fieldNo = 1;
                for(Iterator it=fields.iterator();it.hasNext();) {
                    String field = (String)it.next();
                    String type = getFieldType(field);
                    Object value = PropertyUtils.getProperty(entity,field);
                    if(type!=null && (!isFieldAuto(field)||value!=null)) {
                        updateStatement(stmt,value,type,fieldNo);
                        fieldNo++;
                    }
                }
                if(stmt.executeUpdate()>0) {
                    ResultSet rs = null;
                    if(!bUseMySQLOldMethod) {
                        rs = stmt.getGeneratedKeys();
                    }else {
                        PreparedStatement autoStmt = conn.prepareStatement("SELECT LAST_INSERT_ID()");
                        rs = autoStmt.executeQuery();
                    }
                    if(rs.next()) {
                        fieldNo=1;
                        for (Iterator it=fields.iterator(); it.hasNext();) {
                            String field = (String) it.next();
                            if(isFieldAuto(field)&&PropertyUtils.getProperty(entity,field)==null) {
                                updateField(rs,field,fieldNo,entity);
                                fieldNo++;
                            }
                        }
                    }
                }
            }
        } catch (IllegalAccessException e) {
            e.printStackTrace();  //To change body of catch statement use Options | File Templates.
        } catch (NoSuchMethodException e) {
            e.printStackTrace();  //To change body of catch statement use Options | File Templates.
        } catch (InvocationTargetException e) {
            e.printStackTrace();  //To change body of catch statement use Options | File Templates.
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();  //To change body of catch statement use Options | File Templates.
        }
        return false;
    }

    private String getFieldType(String field) {
        String type = getEnvironment().getResources().getParameter("entities."+getEntityName()+"."+getResourceName()+".fields."+field+".type");
        return type;
    }

    private boolean isFieldAuto(String field) {
        String auto = getEnvironment().getResources().getParameter("entities."+getEntityName()+"."+getResourceName()+".fields."+field+".auto");
        if(auto!=null && auto.equalsIgnoreCase("true")) {
            return true;
        }else {
            return false;
        }
    }

    protected boolean update(Connection conn, EntityInterface entity) throws SQLException {
        try {
            StringBuffer sb = new StringBuffer("update");
            String table = getTableName();
            sb.append(" "+table+" set ");
            Set fields = PropertyUtils.describe(entity).keySet();
            boolean bFirst = true;
            int noOfFields = 0;
            for (Iterator it = fields.iterator();it.hasNext();) {
                String field = (String)it.next();
                String dbField = getColumnName(field);
                if(dbField!=null) {
                    if(!bFirst) {
                        sb.append(", ");
                    }
                    bFirst = false;
                    noOfFields++;
                    sb.append(dbField+"=?");
                }
            }
            sb.append(" where ");
            PreparedStatement stmt = makeWhereStatement(conn,sb,fields,entity,noOfFields+1);
            Log.println(this,"Created prepared statement: "+stmt);
            if(stmt!=null && noOfFields>0) {
                int fieldNo=1;
                for (Iterator it = fields.iterator();it.hasNext();) {
                    String field = (String)it.next();
                    String type = getFieldType(field);
                    if(type!=null) {
                        updateStatement(stmt,PropertyUtils.getProperty(entity,field),type,fieldNo);
                        fieldNo++;
                    }
                }
                return stmt.execute();
            }
        } catch (IllegalAccessException e) {
            e.printStackTrace();  //To change body of catch statement use Options | File Templates.
        } catch (NoSuchMethodException e) {
            e.printStackTrace();  //To change body of catch statement use Options | File Templates.
        } catch (InvocationTargetException e) {
            e.printStackTrace();  //To change body of catch statement use Options | File Templates.
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();  //To change body of catch statement use Options | File Templates.
        }
        return false;
    }

    protected boolean update(Connection conn, FilterInterface filter, EntityInterface entity) throws SQLException {
        try {
            if(filter instanceof QueryFilter) {
                StringBuffer sb = new StringBuffer("update");
                String table = getTableName();
                sb.append(" "+table+" set ");
                Set fields = PropertyUtils.describe(entity).keySet();
                boolean bFirst = true;
                int noOfFields = 0;
                for (Iterator it = fields.iterator();it.hasNext();) {
                    String field = (String)it.next();
                    if(PropertyUtils.getProperty(entity,field)!=null) {
                        String dbField = getColumnName(field);
                        if(dbField!=null) {
                            if(!bFirst) {
                                sb.append(", ");
                            }
                            bFirst = false;
                            noOfFields++;
                            sb.append(dbField+"=?");
                        }
                    }
                }
                Log.println(this,"Get queryString "+"entities."+getEntityName()+"."+getResourceName()+".queries."+((QueryFilter)filter).getQueryName()+".querystring");
                String queryString = getEnvironment().getResources().getParameter("entities."+getEntityName()+"."+getResourceName()+".queries."+((QueryFilter)filter).getQueryName()+".querystring");
                Log.println(this,"queryString="+queryString);
                if(queryString!=null) {
                    if(queryString.length()>0) {
                        sb.append(" where "+queryString);
                        updateDynamicQuery(0,sb,(QueryFilter)filter);
                    }
                }else {
                    sb=null;
                }
                if(sb!=null) {
                    Log.println(this,"update: "+sb.toString());
                    PreparedStatement stmt = conn.prepareStatement(sb.toString());
                    Log.println(this,"Created prepared statement: "+stmt);
                    if(stmt!=null && noOfFields>0) {
                        int fieldNo=1;
                        for (Iterator it = fields.iterator();it.hasNext();) {
                            String field = (String)it.next();
                            String type = getFieldType(field);
                            Object value = PropertyUtils.getProperty(entity,field);
                            if(type!=null && value!=null) {
                                updateStatement(stmt,value,type,fieldNo);
                                fieldNo++;
                            }
                        }
                        boolean bLastParameter = false;
                        for(int queryFieldNo=1;!bLastParameter;queryFieldNo++) {
                            Log.println(this,"Get queryAttr "+"entities."+getEntityName()+"."+getResourceName()+".queries."+((QueryFilter)filter).getQueryName()+".fields."+queryFieldNo+".field");
                            String queryAttr = getEnvironment().getResources().getParameter("entities."+getEntityName()+"."+getResourceName()+".queries."+((QueryFilter)filter).getQueryName()+".fields."+queryFieldNo+".field");
                            Log.println(this,"Get queryAttr "+"entities."+getEntityName()+"."+getResourceName()+".queries."+((QueryFilter)filter).getQueryName()+".fields."+queryFieldNo+".type");
                            String queryType = getEnvironment().getResources().getParameter("entities."+getEntityName()+"."+getResourceName()+".queries."+((QueryFilter)filter).getQueryName()+".fields."+queryFieldNo+".type");
                            Log.println(this,"queryAttr="+queryAttr);
                            if(queryAttr!=null) {
                                Object queryAttrValue = ((QueryFilter)filter).getAttribute(queryAttr);
                                if(queryAttrValue instanceof Collection) {
                                    for(Iterator it=((Collection)queryAttrValue).iterator();it.hasNext();) {
                                        updateStatement(stmt,it.next(),queryType,fieldNo++);
                                    }
                                }else {
                                    updateStatement(stmt,queryAttrValue,queryType,fieldNo++);
                                }
                            }else {
                                bLastParameter = true;
                            }
                        }
                        return stmt.execute();
                    }
                }

            }
        }catch (IllegalAccessException e) {
            e.printStackTrace();  //To change body of catch statement use Options | File Templates.
        } catch (NoSuchMethodException e) {
            e.printStackTrace();  //To change body of catch statement use Options | File Templates.
        } catch (InvocationTargetException e) {
            e.printStackTrace();  //To change body of catch statement use Options | File Templates.
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();  //To change body of catch statement use Options | File Templates.
        }
        return false;
    }
    protected boolean delete(Connection conn, EntityInterface entity) throws SQLException {
        try {
            StringBuffer sb = new StringBuffer("delete");
            String table = getTableName();
            sb.append(" from "+table+" where ");
            Set fields = PropertyUtils.describe(entity).keySet();
            PreparedStatement stmt = makeWhereStatement(conn,sb,fields,entity,1);
            Log.println(this,"Created prepared statement: "+stmt);
            if(stmt!=null) {
                return stmt.execute();
            }
        } catch (IllegalAccessException e) {
            e.printStackTrace();  //To change body of catch statement use Options | File Templates.
        } catch (InvocationTargetException e) {
            e.printStackTrace();  //To change body of catch statement use Options | File Templates.
        } catch (NoSuchMethodException e) {
            e.printStackTrace();  //To change body of catch statement use Options | File Templates.
        }
        return false;
    }

    protected boolean delete(Connection conn, FilterInterface filter) throws SQLException {
        try {
            if(filter instanceof QueryFilter) {
                StringBuffer sb = new StringBuffer("delete");
                String table = getTableName();
                sb.append(" from "+table);
                Log.println(this,"Get queryString "+"entities."+getEntityName()+"."+getResourceName()+".queries."+((QueryFilter)filter).getQueryName()+".querystring");
                String queryString = getEnvironment().getResources().getParameter("entities."+getEntityName()+"."+getResourceName()+".queries."+((QueryFilter)filter).getQueryName()+".querystring");
                Log.println(this,"queryString="+queryString);
                if(queryString!=null) {
                    if(queryString.length()>0) {
                        sb.append(" where "+queryString);
                        updateDynamicQuery(0,sb,(QueryFilter)filter);
                    }
                }else {
                    sb=null;
                }
                if(sb!=null) {
                    Log.println(this,"delete: "+sb.toString());
                    PreparedStatement stmt = conn.prepareStatement(sb.toString());
                    Log.println(this,"Created prepared statement: "+stmt);
                    if(stmt!=null) {
                        boolean bLastParameter = false;
                        for(int queryFieldNo=1,fieldNo=1;!bLastParameter;queryFieldNo++) {
                            Log.println(this,"Get queryAttr "+"entities."+getEntityName()+"."+getResourceName()+".queries."+((QueryFilter)filter).getQueryName()+".fields."+queryFieldNo+".field");
                            String queryAttr = getEnvironment().getResources().getParameter("entities."+getEntityName()+"."+getResourceName()+".queries."+((QueryFilter)filter).getQueryName()+".fields."+queryFieldNo+".field");
                            Log.println(this,"Get queryAttr "+"entities."+getEntityName()+"."+getResourceName()+".queries."+((QueryFilter)filter).getQueryName()+".fields."+queryFieldNo+".type");
                            String queryType = getEnvironment().getResources().getParameter("entities."+getEntityName()+"."+getResourceName()+".queries."+((QueryFilter)filter).getQueryName()+".fields."+queryFieldNo+".type");
                            Log.println(this,"queryAttr="+queryAttr);
                            if(queryAttr!=null) {
                                Object queryAttrValue = ((QueryFilter)filter).getAttribute(queryAttr);
                                if(queryAttrValue instanceof Collection) {
                                    for(Iterator it=((Collection)queryAttrValue).iterator();it.hasNext();) {
                                        updateStatement(stmt,it.next(),queryType,fieldNo++);
                                    }
                                }else {
                                    updateStatement(stmt,queryAttrValue,queryType,fieldNo++);
                                }
                            }else {
                                bLastParameter = true;
                            }
                        }

                        return stmt.execute();
                    }
                }
            }
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();  //To change body of catch statement use Options | File Templates.
        } catch (IllegalAccessException e) {
            e.printStackTrace();  //To change body of catch statement use Options | File Templates.
        } catch (InvocationTargetException e) {
            e.printStackTrace();  //To change body of catch statement use Options | File Templates.
        } catch (NoSuchMethodException e) {
            e.printStackTrace();  //To change body of catch statement use Options | File Templates.
        }
        return false;
    }

    private PreparedStatement makeWhereStatement(Connection conn, StringBuffer sb, Set fields, EntityInterface template,int firstParameter) throws SQLException {
        try {
            boolean bFirst = true;
            for (Iterator it=fields.iterator();it.hasNext();) {
                String field = (String) it.next();
                if(isFieldIdentity(field)) {
                    if(!bFirst) {
                        sb.append(" and ");
                    }
                    bFirst = false;
                    String dbField = getColumnName(field);
                    sb.append(dbField+"=?");
                }
            }
            Log.println(this,"select: "+sb.toString());
            PreparedStatement stmt = conn.prepareStatement(sb.toString());
            int fieldNo=firstParameter;
            for (Iterator it=fields.iterator(); it.hasNext(); ) {
                String field = (String)it.next();
                if(isFieldIdentity(field)) {
                    String type = getFieldType(field);
                    if(type!=null) {
                        Object value = PropertyUtils.getProperty(template,field);
                        if(value!=null) {
                            updateStatement(stmt,value,type,fieldNo);
                            fieldNo++;
                        }else {
                            Log.println(this,"Identity field "+field+" not filled in");
                            return null;
                        }
                    }
                }
            }
            return stmt;
        } catch (IllegalAccessException e) {
            e.printStackTrace();  //To change body of catch statement use Options | File Templates.
        } catch (NoSuchMethodException e) {
            e.printStackTrace();  //To change body of catch statement use Options | File Templates.
        } catch (InvocationTargetException e) {
            e.printStackTrace();  //To change body of catch statement use Options | File Templates.
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();  //To change body of catch statement use Options | File Templates.
        }
        return null;
    }

    private boolean isFieldIdentity(String field) {
        String identity = getEnvironment().getResources().getParameter("entities."+getEntityName()+"."+getResourceName()+".fields."+field+".identity");
        if(identity!=null && identity.equalsIgnoreCase("true")) {
            return true;
        }else {
            return false;
        }
    }

    protected void updateStatement(PreparedStatement stmt, Object value, String type, int fieldNo) throws SQLException, IllegalAccessException, NoSuchMethodException, InvocationTargetException, UnsupportedEncodingException {
        if(type==null) {
        }else if(type.equals("String")) {
            Log.println(this,"setString("+fieldNo+","+value+")");
            if(value!=null) {
                value = URLEncoder.encode((String)value,"UTF-8");
            }else {
                value="";
            }
            stmt.setString(fieldNo,(String)value);
        }else if(type.equals("Date")) {
            Log.println(this,"setDate("+fieldNo+","+value+")");
            if(value==null) {
                value = new Date();
            }
            stmt.setDate(fieldNo,new java.sql.Date(((Date)value).getTime()));
        }else if(type.equals("Time")) {
            Log.println(this,"setTime("+fieldNo+","+value+")");
            if(value==null) {
                value = new Date();
            }
            stmt.setTime(fieldNo,new java.sql.Time(((Date)value).getTime()));
        }else if(type.equals("Timestamp")) {
            Log.println(this,"setTimestamp("+fieldNo+","+value+")");
            if(value==null) {
                value = new Date();
            }
            stmt.setTimestamp(fieldNo,new java.sql.Timestamp(((Date)value).getTime()));
        }else if(type.equals("Integer")) {
            Log.println(this,"setInt("+fieldNo+","+value+")");
            if(value==null) {
                value = new Integer(0);
            }
            stmt.setInt(fieldNo,((Integer)value).intValue());
        }else if(type.equals("Long")) {
            Log.println(this,"setLong("+fieldNo+","+value+")");
            if(value==null) {
                value = new Long(0);
            }
            stmt.setLong(fieldNo,((Long)value).longValue());
        }else if(type.equals("Double")) {
            Log.println(this,"setDouble("+fieldNo+","+value+")");
            if(value==null) {
                value = new Double(0);
            }
            stmt.setDouble(fieldNo,((Double)value).doubleValue());
        }else if(type.equals("Float")) {
            Log.println(this,"setFloat("+fieldNo+","+value+")");
            if(value==null) {
                value = new Float(0);
            }
            stmt.setFloat(fieldNo,((Float)value).floatValue());
        }else if(type.equals("Boolean")) {
            Log.println(this,"setBoolean("+fieldNo+","+value+")");
            if(value==null) {
                value = new Boolean(false);
            }
            stmt.setBoolean(fieldNo,((Boolean)value).booleanValue());
        }
    }

    protected void updateField(ResultSet rs, String field, String dbField, String type, Object object) throws SQLException, NoSuchMethodException, IllegalAccessException, InvocationTargetException, UnsupportedEncodingException {
        //String dbField = getColumnName(field);
        if(dbField!=null) {
            //String type = getFieldType(field);
            if(type==null) {
            }else if(type.equals("String")) {
                String value = rs.getString(dbField);
                Log.println(this,"entity.set"+field+"("+value+")");
                if(value!=null) {
                    value = URLDecoder.decode(value,"UTF-8");
                }
                PropertyUtils.setProperty(object,field,value);
            }else if(type.equals("Date")) {
                Date value = rs.getDate(dbField);
                Log.println(this,"entity.set"+field+"("+value+")");
                PropertyUtils.setProperty(object,field,value);
            }else if(type.equals("Time")) {
                Date value = rs.getTime(dbField);
                Log.println(this,"entity.set"+field+"("+value+")");
                PropertyUtils.setProperty(object,field,value);
            }else if(type.equals("Timestamp")) {
                Date value = rs.getTimestamp(dbField);
                Log.println(this,"entity.set"+field+"("+value+")");
                PropertyUtils.setProperty(object,field,value);
            }else if(type.equals("Integer")) {
                Integer value = new Integer(rs.getInt(dbField));
                Log.println(this,"entity.set"+field+"("+value+")");
                PropertyUtils.setProperty(object,field,value);
            }else if(type.equals("Long")) {
                Long value = new Long(rs.getLong(dbField));
                Log.println(this,"entity.set"+field+"("+value+")");
                PropertyUtils.setProperty(object,field,value);
            }else if(type.equals("Double")) {
                Double value = new Double(rs.getDouble(dbField));
                Log.println(this,"entity.set"+field+"("+value+")");
                PropertyUtils.setProperty(object,field,value);
            }else if(type.equals("Float")) {
                Float value = new Float(rs.getFloat(dbField));
                Log.println(this,"entity.set"+field+"("+value+")");
                PropertyUtils.setProperty(object,field,value);
            }else if(type.equals("Boolean")) {
                Boolean value = Boolean.valueOf(rs.getBoolean(dbField));
                Log.println(this,"entity.set"+field+"("+value+")");
                PropertyUtils.setProperty(object,field,value);
            }
        }
    }

    protected void updateField(ResultSet rs, String field, int fieldNo, Object object) throws SQLException, NoSuchMethodException, IllegalAccessException, InvocationTargetException, UnsupportedEncodingException {
        String type = getFieldType(field);
        if(type==null) {
        }else if(type.equals("String")) {
            String value = rs.getString(fieldNo);
            Log.println(this,"entity.set"+field+"("+value+")");
            if(value!=null) {
                value = URLDecoder.decode(value,"UTF-8");
            }
            PropertyUtils.setProperty(object,field,value);
        }else if(type.equals("Date")) {
            Date value = rs.getDate(fieldNo);
            Log.println(this,"entity.set"+field+"("+value+")");
            PropertyUtils.setProperty(object,field,value);
        }else if(type.equals("Time")) {
            Date value = rs.getTime(fieldNo);
            Log.println(this,"entity.set"+field+"("+value+")");
            PropertyUtils.setProperty(object,field,value);
        }else if(type.equals("Timestamp")) {
            Date value = rs.getTimestamp(fieldNo);
            Log.println(this,"entity.set"+field+"("+value+")");
            PropertyUtils.setProperty(object,field,value);
        }else if(type.equals("Integer")) {
            Integer value = new Integer(rs.getInt(fieldNo));
            Log.println(this,"entity.set"+field+"("+value+")");
            PropertyUtils.setProperty(object,field,value);
        }else if(type.equals("Long")) {
            Long value = new Long(rs.getLong(fieldNo));
            Log.println(this,"entity.set"+field+"("+value+")");
            PropertyUtils.setProperty(object,field,value);
        }else if(type.equals("Double")) {
            Double value = new Double(rs.getDouble(fieldNo));
            Log.println(this,"entity.set"+field+"("+value+")");
            PropertyUtils.setProperty(object,field,value);
        }else if(type.equals("Float")) {
            Float value = new Float(rs.getFloat(fieldNo));
            Log.println(this,"entity.set"+field+"("+value+")");
            PropertyUtils.setProperty(object,field,value);
        }else if(type.equals("Boolean")) {
            Boolean value = Boolean.valueOf(rs.getBoolean(fieldNo));
            Log.println(this,"entity.set"+field+"("+value+")");
            PropertyUtils.setProperty(object,field,value);
        }
    }

    private void updateDynamicQuery(int initPos, StringBuffer sb, QueryFilter filter) {
        int startPos = sb.indexOf("{",initPos);
        if(startPos>=0) {
            int endPos = sb.indexOf("}",startPos+1);
            if(endPos>=0) {
                String fieldNo =  sb.substring(startPos+1,endPos);
                String field = getEnvironment().getResources().getParameter("entities."+getEntityName()+"."+getResourceName()+".queries."+((QueryFilter)filter).getQueryName()+".fields."+fieldNo+".field");
                String type = getEnvironment().getResources().getParameter("entities."+getEntityName()+"."+getResourceName()+".queries."+((QueryFilter)filter).getQueryName()+".fields."+fieldNo+".type");
                Object values = filter.getAttribute(field);
                if(values instanceof Collection) {
                    int noOfItems = ((Collection)values).size();
                    StringBuffer sbParameters = new StringBuffer();
                    for(int i=0;i<noOfItems;i++) {
                        if(i>0) {
                            sbParameters.append(",");
                        }
                        sbParameters.append("?");
                    }
                    sb.replace(startPos,endPos+1,sbParameters.toString());
                }
                updateDynamicQuery(endPos+1,sb,filter);
            }
        }
    }
    private String getAttributeName(String field) {
        return getEnvironment().getResources().getParameter("entities."+getEntityName()+"."+getResourceName()+".fields."+field+".attribute");
    }

    private boolean isFieldGetFromInput(String field) {
        String fromInput = getEnvironment().getResources().getParameter("entities."+getEntityName()+"."+getResourceName()+".fields."+field+".frominput");
        if(fromInput!=null && fromInput.equalsIgnoreCase("true")) {
            return true;
        }else {
            return false;
        }
    }
}
