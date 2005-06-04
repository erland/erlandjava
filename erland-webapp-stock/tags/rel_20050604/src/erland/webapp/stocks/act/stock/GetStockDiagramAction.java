package erland.webapp.stocks.act.stock;
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

import erland.webapp.common.act.WebAppEnvironmentPlugin;
import erland.webapp.diagram.DateValueSerieInterface;
import erland.webapp.diagram.DateValueDiagramHelper;
import erland.webapp.stocks.bl.service.StockStorageInterface;
import erland.webapp.stocks.bl.logic.stock.StockInterface;
import erland.webapp.stocks.fb.stock.SelectFB;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.*;

import org.apache.struts.action.Action;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.action.ActionForm;
import org.apache.commons.beanutils.PropertyUtils;

public class GetStockDiagramAction extends Action {
    public ActionForward execute(ActionMapping actionMapping, ActionForm actionForm, HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse) throws Exception {
        SelectFB fb = (SelectFB) actionForm;
        if(fb.getStock()!=null) {
            DateValueSerieInterface[] stocks = new DateValueSerieInterface[] {((StockStorageInterface)WebAppEnvironmentPlugin.getEnvironment().getServiceFactory().create("stock-stockstorage")).getStock(fb.getBroker(), fb.getStock()).getRates()};
            httpServletResponse.setContentType("image/jpeg");
            DateValueDiagramHelper.drawDiagram((DateValueSerieInterface[]) stocks,httpServletResponse.getOutputStream());
            SelectFB pb = new SelectFB();
            PropertyUtils.copyProperties(pb,fb);
            httpServletRequest.setAttribute("stockDiagramPB",pb);
        }
        return null;
    }
}
