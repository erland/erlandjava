package erland.webapp.diary.diary;
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

import erland.webapp.usermgmt.User;
import erland.webapp.diary.account.UserAccount;
import erland.webapp.common.CommandInterface;
import erland.webapp.common.WebAppEnvironmentInterface;

import javax.servlet.http.HttpSession;

public abstract class BaseEntryCommand implements CommandInterface {
    private WebAppEnvironmentInterface environment;
    public void init(WebAppEnvironmentInterface environment) {
        this.environment = environment;
    }

    protected WebAppEnvironmentInterface getEnvironment() {
        return environment;
    }

    protected Integer getDiary(String diary, String username, HttpSession session) {
        if(session!=null) {
            User user = (User) session.getAttribute("user");
            if(user!=null) {
                username = user.getUsername();
            }
        }

        Integer result = null;
        if(diary==null || diary.length()==0) {
            UserAccount template = (UserAccount) environment.getEntityFactory().create("diary-useraccount");
            template.setUsername(username);
            UserAccount account = (UserAccount) environment.getEntityStorageFactory().getStorage("diary-useraccount").load(template);
            if(account!=null) {
                result = account.getDefaultDiary();
            }
        }else {
            result = Integer.valueOf(diary);
        }
        return result;
    }
}
