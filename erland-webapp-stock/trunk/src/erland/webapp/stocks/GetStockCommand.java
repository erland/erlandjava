package erland.webapp.stocks;

import erland.webapp.common.CommandInterface;
import erland.webapp.common.WebAppEnvironmentInterface;
import erland.webapp.diagram.DateValueSeriesContainerInterface;

import javax.servlet.http.HttpServletRequest;
import java.util.StringTokenizer;
import java.util.Vector;
import java.util.Iterator;

public class GetStockCommand implements CommandInterface, DateValueSeriesContainerInterface {
    private Vector stocks;
    private WebAppEnvironmentInterface environment;

    public void init(WebAppEnvironmentInterface environment) {
        this.environment = environment;
    }

    public String execute(HttpServletRequest request) {
        stocks = new Vector();
        String fondListaString = request.getParameter("stocks");
        String broker = request.getParameter("broker");
        StringTokenizer fondLista = new StringTokenizer(fondListaString,",");
        while(broker!=null && fondLista.hasMoreTokens()) {
            String current = fondLista.nextToken().trim();
            stocks.addElement(StockStorage.getInstance(environment).getStock(broker, current).getRates());
        }
        return null;
    }

    public Iterator getSeries() {
        return stocks.iterator();
    }
}
