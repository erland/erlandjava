package erland.webapp.diary.act.account;

import erland.webapp.common.act.BaseAction;
import erland.webapp.common.EntityInterface;
import erland.webapp.common.QueryFilter;
import erland.webapp.diary.fb.account.SelectUserFB;
import erland.webapp.diary.fb.diary.DiaryFB;
import erland.webapp.diary.entity.account.UserAccount;
import erland.webapp.diary.entity.diary.Diary;
import erland.webapp.gallery.fb.gallery.MenuItemPB;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.commons.beanutils.PropertyUtils;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Arrays;
import java.lang.reflect.InvocationTargetException;

/*
 * Copyright (C) 2004 Erland Isaksson (erland_i@hotmail.com)
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

public class LoadOfficialMenuAction extends LoadMenuAction {
    protected String getDiaryQueryFilter() {
        return "allofficialforuser";
    }

    protected String getGalleryQueryFilter() {
        return "allofficialforuser";
    }
}