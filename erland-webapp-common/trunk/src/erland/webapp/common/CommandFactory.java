package erland.webapp.common;

public class CommandFactory implements CommandFactoryInterface {
    private WebAppEnvironmentInterface environment;
    public CommandFactory(WebAppEnvironmentInterface environment) {
        this.environment = environment;
    }
    public CommandInterface create(String cmd) {
        String clsName = environment.getResources().getParameter("commands." + cmd + ".class");
        if (clsName == null || clsName.length()==0) {
            clsName = environment.getResources().getParameter("commands.default.class");
        }
        if(clsName != null && clsName.length()>0) {
            try {
                Class cls = Class.forName(clsName);
                CommandInterface command = (CommandInterface)cls.newInstance();
                command.init(environment);
                return command;
            } catch (ClassNotFoundException e) {
                e.printStackTrace();
            } catch (InstantiationException e) {
                e.printStackTrace();
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            } catch (ClassCastException e) {
                e.printStackTrace();
            }
        }
        return null;
    }
}
