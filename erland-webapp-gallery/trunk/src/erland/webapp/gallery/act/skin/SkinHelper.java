package erland.webapp.gallery.act.skin;

import erland.webapp.gallery.fb.skin.SkinFB;
import erland.webapp.gallery.entity.skin.Skin;
import erland.webapp.common.act.WebAppEnvironmentPlugin;
import erland.util.StringUtil;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.action.ActionForward;

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

public class SkinHelper {
    public static SkinFB loadSkin(ActionMapping mapping, String skin) {
        SkinFB pbSkin = new SkinFB();
        pbSkin.setId(skin);
        ActionForward forward = mapping.findForward("skin-layout");
        if(forward!=null) {
            pbSkin.setLayout(forward.getPath());
        }
        forward = mapping.findForward("skin-layoutSingle");
        if(forward!=null) {
            pbSkin.setLayoutSingle(forward.getPath());
        }
        forward = mapping.findForward("skin-header");
        if(forward!=null) {
            pbSkin.setHeader(forward.getPath());
        }
        forward = mapping.findForward("skin-menu");
        if(forward!=null) {
            pbSkin.setMenu(forward.getPath());
        }
        forward = mapping.findForward("skin-search");
        if(forward!=null) {
            pbSkin.setSearch(forward.getPath());
        }
        forward = mapping.findForward("skin-viewpicture");
        if(forward!=null) {
            pbSkin.setViewPicture(forward.getPath());
        }
        forward = mapping.findForward("skin-viewpictures");
        if(forward!=null) {
            pbSkin.setViewPictures(forward.getPath());
        }
        pbSkin.setStylesheet(null);
        if(skin!=null && skin.length()>0) {
            Skin template = (Skin) WebAppEnvironmentPlugin.getEnvironment().getEntityFactory().create("gallery-skin");
            template.setId(skin);
            Skin skinEntity = (Skin) WebAppEnvironmentPlugin.getEnvironment().getEntityStorageFactory().getStorage("gallery-skin").load(template);
            if(skinEntity!=null) {
                if(StringUtil.asNull(skinEntity.getLayout())!=null) {
                    pbSkin.setLayout(skinEntity.getLayout());
                }
                if(StringUtil.asNull(skinEntity.getLayoutSingle())!=null) {
                    pbSkin.setLayoutSingle(skinEntity.getLayoutSingle());
                }
                if(StringUtil.asNull(skinEntity.getHeader())!=null) {
                    pbSkin.setHeader(skinEntity.getHeader());
                }
                if(StringUtil.asNull(skinEntity.getMenu())!=null) {
                    pbSkin.setMenu(skinEntity.getMenu());
                }
                if(StringUtil.asNull(skinEntity.getSearch())!=null) {
                    pbSkin.setSearch(skinEntity.getSearch());
                }
                if(StringUtil.asNull(skinEntity.getViewPicture())!=null) {
                    pbSkin.setViewPicture(skinEntity.getViewPicture());
                }
                if(StringUtil.asNull(skinEntity.getViewPictures())!=null) {
                    pbSkin.setViewPictures(skinEntity.getViewPictures());
                }
                if(StringUtil.asNull(skinEntity.getStylesheet())!=null) {
                    pbSkin.setStylesheet(skinEntity.getStylesheet());
                }
            }
        }
        return pbSkin;
   }
}