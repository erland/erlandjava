package erland.webapp.common;

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

    private DataSource getPool(WebAppEnvironmentInterface environment)
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
        Connection conn = null;
        try {
            conn = getPool(environment).getConnection();
            return select(conn,template);
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        } finally {
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
        Connection conn = null;
        try {
            conn = getPool(environment).getConnection();
            return delete(conn,entity);
        } catch (SQLException e) {
            e.printStackTrace();  //To change body of catch statement use Options | File Templates.
        } finally {
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
        Connection conn = null;
        try {
            conn = getPool(environment).getConnection();
            return select(conn,filter);
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
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
}
