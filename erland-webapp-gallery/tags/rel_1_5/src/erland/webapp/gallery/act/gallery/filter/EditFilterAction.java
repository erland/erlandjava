package erland.webapp.gallery.act.gallery.filter;

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

import erland.webapp.common.act.BaseAction;
import erland.webapp.gallery.entity.gallery.filter.Filter;
import erland.webapp.gallery.fb.gallery.filter.FilterFB;
import org.apache.commons.beanutils.PropertyUtils;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionMapping;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Arrays;
import java.awt.image.ImageFilter;

public class EditFilterAction extends BaseAction {
    protected void executeLogic(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception {
        FilterFB fb = (FilterFB) form;
        Class cls = null;
        try {
            cls = Class.forName(fb.getCls());
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        if (cls == null) {
            saveErrors(request, Arrays.asList(new String[]{"gallery.gallery.filter.edit.class-dont-exist"}));
            return;
        }
        Object obj = cls.newInstance();
        if (!(obj instanceof ImageFilter)) {
            saveErrors(request, Arrays.asList(new String[]{"gallery.gallery.filter.edit.incorrect-class"}));
            return;
        }
        Filter storage = (Filter) getEnvironment().getEntityFactory().create("gallery-filter");
        PropertyUtils.copyProperties(storage, fb);
        getEnvironment().getEntityStorageFactory().getStorage("gallery-filter").store(storage);
    }
}