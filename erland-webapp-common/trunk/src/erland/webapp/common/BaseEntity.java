package erland.webapp.common;

import org.apache.commons.beanutils.PropertyUtils;

import java.util.Map;
import java.util.Iterator;
import java.lang.reflect.InvocationTargetException;

public class BaseEntity implements EntityInterface{
    private WebAppEnvironmentInterface environment;

    public void init(WebAppEnvironmentInterface environment) {
        this.environment = environment;
    }

    public WebAppEnvironmentInterface getEnvironment() {
        return environment;
    }

    public String toString() {
        StringBuffer sb = new StringBuffer(100);
        try {
            sb.append(getClass().getName());
            Map propertyMap = PropertyUtils.describe(this);
            for(Iterator it=propertyMap.entrySet().iterator();it.hasNext();) {
                Map.Entry entry = (Map.Entry) it.next();
                sb.append(entry.getKey());
                sb.append("=");
                sb.append(entry.getValue());
                if(it.hasNext()) {
                    sb.append(",");
                }
            }
        } catch (IllegalAccessException e) {
        } catch (InvocationTargetException e) {
        } catch (NoSuchMethodException e) {
        }
        return sb.toString();
    }
}