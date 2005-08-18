package erland.webapp.diary.act.purchase;

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
import erland.webapp.common.QueryFilter;
import erland.webapp.common.ServletParameterHelper;
import erland.webapp.common.act.BaseAction;
import erland.webapp.diary.entity.purchase.PurchaseEntry;
import erland.webapp.diary.entity.purchase.PurchasePriceGroupEntry;
import erland.webapp.diary.fb.account.SelectUserFB;
import erland.webapp.diary.fb.purchase.PurchaseEntryPB;
import erland.webapp.diary.fb.purchase.PurchasePriceGroupEntryPB;
import erland.util.KeyValue;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.action.ActionForward;
import org.apache.commons.beanutils.PropertyUtils;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.HashMap;
import java.util.Map;
import java.util.TreeMap;

public class ViewPurchaseStatsAction extends BaseAction {
    protected void executeLogic(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception {
        SelectUserFB fb = (SelectUserFB) form;
        String username = request.getRemoteUser();
        if(username==null) {
            username=fb.getUser();
        }
        QueryFilter filter = new QueryFilter("allstoresforuser");
        filter.setAttribute("username", username);
        EntityInterface[] entities = getEnvironment().getEntityStorageFactory().getStorage("diary-purchasepricegroupentry").search(filter);
        PurchasePriceGroupEntryPB[] pb = new PurchasePriceGroupEntryPB[entities.length];
        for (int i = 0; i < entities.length; i++) {
            pb[i] = new PurchasePriceGroupEntryPB();
            PropertyUtils.copyProperties(pb[i],entities[i]);
        }
        request.setAttribute("purchaseStoresEntriesPB",pb);

        filter = new QueryFilter("allyearsforuser");
        filter.setAttribute("username", username);
        entities = getEnvironment().getEntityStorageFactory().getStorage("diary-purchasepricegroupentry").search(filter);
        pb = new PurchasePriceGroupEntryPB[entities.length];
        Integer[] years = new Integer[entities.length];
        for (int i = 0; i < entities.length; i++) {
            pb[i] = new PurchasePriceGroupEntryPB();
            PropertyUtils.copyProperties(pb[i],entities[i]);
            years[i] = Integer.valueOf(((PurchasePriceGroupEntry)entities[i]).getGroup());
        }
        request.setAttribute("purchaseYearsEntriesPB",pb);

        filter = new QueryFilter("allcategoriesforyearforuser");
        filter.setAttribute("username", username);
        KeyValue[] pbYear = new KeyValue[years.length];
        for (int currentYear = 0; currentYear < years.length; currentYear++) {
            filter.setAttribute("year", years[currentYear]);
            entities = getEnvironment().getEntityStorageFactory().getStorage("diary-purchasepricegroupentry").search(filter);
            pb = new PurchasePriceGroupEntryPB[entities.length];
            for (int i = 0; i < entities.length; i++) {
                pb[i] = new PurchasePriceGroupEntryPB();
                PropertyUtils.copyProperties(pb[i],entities[i]);
            }
            pbYear[currentYear] = new KeyValue(years[currentYear],pb);
        }
        request.setAttribute("purchaseYearsCategoriesEntriesPB",pbYear);

        filter = new QueryFilter("allmonthsforuser");
        filter.setAttribute("username", username);
        entities = getEnvironment().getEntityStorageFactory().getStorage("diary-purchasepricegroupentry").search(filter);
        pb = new PurchasePriceGroupEntryPB[entities.length];
        for (int i = 0; i < entities.length; i++) {
            pb[i] = new PurchasePriceGroupEntryPB();
            PropertyUtils.copyProperties(pb[i],entities[i]);
        }
        request.setAttribute("purchaseMonthsEntriesPB",pb);

        filter = new QueryFilter("allcategoriesforuser");
        filter.setAttribute("username", username);
        entities = getEnvironment().getEntityStorageFactory().getStorage("diary-purchasepricegroupentry").search(filter);
        pb = new PurchasePriceGroupEntryPB[entities.length];
        for (int i = 0; i < entities.length; i++) {
            pb[i] = new PurchasePriceGroupEntryPB();
            PropertyUtils.copyProperties(pb[i],entities[i]);
        }
        request.setAttribute("purchaseCategoriesEntriesPB",pb);
    }
}
