package erland.webapp.stocks;

import erland.webapp.common.WebAppEnvironmentInterface;

public class BrokerConnectionFactory implements BrokerConnectionFactoryInterface {
    private WebAppEnvironmentInterface environment;

    public BrokerConnectionFactory(WebAppEnvironmentInterface environment) {
        this.environment = environment;
    }
    public BrokerConnectionInterface create(String broker) {
        BrokerConnectionInterface brokerCls = null;
        String clsName = environment.getResources().getParameter("brokers."+broker+".connection.class");
        if(clsName!=null && clsName.length()>0) {
            try {
                brokerCls = (BrokerConnectionInterface) (Class.forName(clsName).newInstance());
                brokerCls.init(environment);
            } catch (InstantiationException e) {
                e.printStackTrace();  //To change body of catch statement use Options | File Templates.
            } catch (IllegalAccessException e) {
                e.printStackTrace();  //To change body of catch statement use Options | File Templates.
            } catch (ClassNotFoundException e) {
                e.printStackTrace();  //To change body of catch statement use Options | File Templates.
            }
        }
        return brokerCls;
    }

}
