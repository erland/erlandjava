package erland.webapp.common;

import erland.webapp.common.EntityStorage;
import erland.webapp.common.EntityInterface;
import erland.webapp.common.FilterInterface;
import erland.webapp.common.QueryFilter;
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
            if(stmt!=null && noOfFields>0) {
                ResultSet rs = stmt.executeQuery();
                if(rs.next()) {
                    if(entity instanceof EntityReadUpdateInterface) {
                        ((EntityReadUpdateInterface)entity).preReadUpdate();
                    }
                    for (Iterator it=fields.iterator(); it.hasNext();) {
                        String field = (String) it.next();
                        updateField(rs,field,entity);
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
                StringBuffer sb = new StringBuffer("select ");
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
                    }
                    String orderString = getEnvironment().getResources().getParameter("entities."+getEntityName()+"."+getResourceName()+".queries."+((QueryFilter)filter).getQueryName()+".orderstring");
                    if(orderString!=null && orderString.length()>0) {
                        sb.append(" "+orderString);
                    }
                    Log.println(this,"select: "+sb.toString());
                    PreparedStatement stmt = conn.prepareStatement(sb.toString());
                    boolean bLastParameter = false;
                    for(int fieldNo=1;!bLastParameter;fieldNo++) {
                        Log.println(this,"Get queryAttr "+"entities."+getEntityName()+"."+getResourceName()+".queries."+((QueryFilter)filter).getQueryName()+".fields."+fieldNo+".field");
                        String queryAttr = getEnvironment().getResources().getParameter("entities."+getEntityName()+"."+getResourceName()+".queries."+((QueryFilter)filter).getQueryName()+".fields."+fieldNo+".field");
                        Log.println(this,"Get queryAttr "+"entities."+getEntityName()+"."+getResourceName()+".queries."+((QueryFilter)filter).getQueryName()+".fields."+fieldNo+".type");
                        String queryType = getEnvironment().getResources().getParameter("entities."+getEntityName()+"."+getResourceName()+".queries."+((QueryFilter)filter).getQueryName()+".fields."+fieldNo+".type");
                        Log.println(this,"queryAttr="+queryAttr);
                        if(queryAttr!=null) {
                            updateStatement(stmt,((QueryFilter)filter).getAttribute(queryAttr),queryType,fieldNo);
                        }else {
                            bLastParameter = true;
                        }
                    }
                    ResultSet rs = stmt.executeQuery();
                    Collection result = new ArrayList();
                    while(rs.next()) {
                        EntityInterface entity = getEnvironment().getEntityFactory().create(getEntityName());
                        if(entity instanceof EntityReadUpdateInterface) {
                            ((EntityReadUpdateInterface)entity).preReadUpdate();
                        }
                        for(Iterator it=fields.iterator();it.hasNext();) {
                            String field = (String)it.next();
                            updateField(rs,field,entity);
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
                if(dbField!=null && !isFieldAuto(field)) {
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
                int fieldNo = 1;
                for(Iterator it=fields.iterator();it.hasNext();) {
                    String field = (String)it.next();
                    String type = getFieldType(field);
                    if(type!=null && !isFieldAuto(field)) {
                        updateStatement(stmt,PropertyUtils.getProperty(entity,field),type,fieldNo);
                        fieldNo++;
                    }
                }
                if(stmt.executeUpdate()>0) {
                    ResultSet rs = stmt.getGeneratedKeys();
                    if(rs.next()) {
                        fieldNo=1;
                        for (Iterator it=fields.iterator(); it.hasNext();) {
                            String field = (String) it.next();
                            if(isFieldAuto(field)) {
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

    protected boolean delete(Connection conn, EntityInterface entity) throws SQLException {
        try {
            StringBuffer sb = new StringBuffer("delete");
            String table = getTableName();
            sb.append(" from "+table+" where ");
            Set fields = PropertyUtils.describe(entity).keySet();
            PreparedStatement stmt = makeWhereStatement(conn,sb,fields,entity,1);
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
        }else if(type.equals("Boolean")) {
            Log.println(this,"setBoolean("+fieldNo+","+value+")");
            if(value==null) {
                value = new Boolean(false);
            }
            stmt.setBoolean(fieldNo,((Boolean)value).booleanValue());
        }
    }

    protected void updateField(ResultSet rs, String field, Object object) throws SQLException, NoSuchMethodException, IllegalAccessException, InvocationTargetException, UnsupportedEncodingException {
        String dbField = getColumnName(field);
        if(dbField!=null) {
            String type = getFieldType(field);
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
        }else if(type.equals("Boolean")) {
            Boolean value = Boolean.valueOf(rs.getBoolean(fieldNo));
            Log.println(this,"entity.set"+field+"("+value+")");
            PropertyUtils.setProperty(object,field,value);
        }
    }
}
