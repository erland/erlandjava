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

import erland.webapp.common.EntityInterface;
import erland.webapp.common.WebAppEnvironmentInterface;
import erland.util.Log;

import javax.sql.DataSource;
import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.sql.Connection;
import java.sql.SQLException;

public abstract class EntityStorage implements EntityStorageInterface {
    private WebAppEnvironmentInterface environment;
    private DataSource pool;
    private String entityName;

    private synchronized DataSource getPool(WebAppEnvironmentInterface environment)
    {
        if(pool==null) {
          try {
            Context env = (Context) new InitialContext().lookup("java:comp/env");
            String resourceName = "entities."+getEntityName()+"."+getResourceName()+".datasource";
            Log.println(this,"Getting name of pool from: "+resourceName);
            String resource = environment.getResources().getParameter(resourceName);
            Log.println(this,"Looking up pool "+resource);
            pool = (DataSource) env.lookup(resource);
            Log.println(this,"Got pool: "+pool);
          } catch (NamingException e) {
              e.printStackTrace();
            return null;
          }
        }
        return pool;
    }

    public void init(WebAppEnvironmentInterface environment) {
        this.environment = environment;
    }

    public EntityInterface load(EntityInterface template) {
        long startTime = System.currentTimeMillis();
        Connection conn = null;
        try {
            conn = getPool(environment).getConnection();
            return select(conn,template);
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        } finally {
            if(Log.isEnabled(this)) Log.println(this,"load: "+template+" ("+(System.currentTimeMillis()-startTime)+" ms)");
            if(conn!=null) {
                try {
                    conn.close();
                } catch (SQLException e) {
                    e.printStackTrace();  //To change body of catch statement use Options | File Templates.
                }
            }
        }
    }

    public boolean store(EntityInterface entity) {
        long startTime = System.currentTimeMillis();
        Connection conn = null;
        try {
            conn = getPool(environment).getConnection();
            if(select(conn,entity)!=null) {
                return update(conn,entity);
            }else {
                return insert(conn,entity);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            if(Log.isEnabled(this)) Log.println(this,"store: "+entity+" ("+(System.currentTimeMillis()-startTime)+" ms)");
            if(conn!=null) {
                try {
                    conn.close();
                } catch (SQLException e) {
                    e.printStackTrace();  //To change body of catch statement use Options | File Templates.
                }
            }
        }
        return false;
    }

    public boolean delete(EntityInterface entity) {
        long startTime = System.currentTimeMillis();
        Connection conn = null;
        try {
            conn = getPool(environment).getConnection();
            return delete(conn,entity);
        } catch (SQLException e) {
            e.printStackTrace();  //To change body of catch statement use Options | File Templates.
        } finally {
            if(Log.isEnabled(this)) Log.println(this,"delete: "+entity+" ("+(System.currentTimeMillis()-startTime)+" ms)");
            if(conn!=null) {
                try {
                    conn.close();
                } catch (SQLException e) {
                    e.printStackTrace();  //To change body of catch statement use Options | File Templates.
                }
            }
        }
        return false;
    }

    public boolean delete(FilterInterface filter) {
        long startTime = System.currentTimeMillis();
        Connection conn = null;
        try {
            conn = getPool(environment).getConnection();
            return delete(conn,filter);
        } catch (SQLException e) {
            e.printStackTrace();  //To change body of catch statement use Options | File Templates.
        } finally {
            if(Log.isEnabled(this)) Log.println(this,"delete: "+filter+" ("+(System.currentTimeMillis()-startTime)+" ms)");
            if(conn!=null) {
                try {
                    conn.close();
                } catch (SQLException e) {
                    e.printStackTrace();  //To change body of catch statement use Options | File Templates.
                }
            }
        }
        return false;
    }

    public EntityInterface[] search(FilterInterface filter) {
        long startTime = System.currentTimeMillis();
        Connection conn = null;
        try {
            conn = getPool(environment).getConnection();
            return select(conn,filter);
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            if(Log.isEnabled(this)) Log.println(this,"search: "+filter+" ("+(System.currentTimeMillis()-startTime)+" ms)");
            if(conn!=null) {
                try {
                    conn.close();
                } catch (SQLException e) {
                    e.printStackTrace();  //To change body of catch statement use Options | File Templates.
                }
            }
        }
        return new EntityInterface[0];
    }

    public boolean update(FilterInterface filter, EntityInterface entity) {
        long startTime = System.currentTimeMillis();
        Connection conn = null;
        try {
            conn = getPool(environment).getConnection();
            return update(conn,filter,entity);
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            if(Log.isEnabled(this)) Log.println(this,"update: "+filter+" "+entity+" ("+(System.currentTimeMillis()-startTime)+" ms)");
            if(conn!=null) {
                try {
                    conn.close();
                } catch (SQLException e) {
                    e.printStackTrace();  //To change body of catch statement use Options | File Templates.
                }
            }
        }
        return false;
    }

    protected WebAppEnvironmentInterface getEnvironment() {
        return this.environment;
    }

    public void setEntityName(String entityName) {
        this.entityName = entityName;
    }
    protected String getEntityName() {
        return entityName;
    }

    protected String getResourceName() {
        return environment.getResources().getParameter("entities."+getEntityName()+".storage.name");
    }

    protected abstract EntityInterface select(Connection conn, EntityInterface template) throws SQLException;

    protected abstract EntityInterface[] select(Connection conn, FilterInterface filter) throws SQLException;

    protected abstract boolean insert(Connection conn, EntityInterface entity) throws SQLException;

    protected abstract boolean update(Connection conn, EntityInterface entity) throws SQLException;

    protected abstract boolean delete(Connection conn, EntityInterface entity) throws SQLException;

    protected abstract boolean update(Connection conn, FilterInterface filter, EntityInterface entity) throws SQLException;

    protected abstract boolean delete(Connection conn, FilterInterface filter) throws SQLException;
}
