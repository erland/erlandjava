package erland.game.tileadventure;

import erland.util.*;
import erland.game.GameEnvironmentInterface;

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

public class LevelManagerHelper {
    public static StorageInterface load(ParameterValueStorageExInterface mainStorage, String parent, String group, String id) {
        StorageInterface parentStorage = mainStorage.getParameterAsStorage(parent);
        ParameterValueStorageExInterface parameters = new ParameterStorageGroupWithId(parentStorage, null, parent, group);
        return parameters.getParameterAsStorage(group + "." + id);
    }
    public static void store(ParameterValueStorageExInterface storage, String parent, String group, String id, String value) {
        StorageInterface parameterStorage = storage.getParameterAsStorage(parent);
        ParameterStorageGroupWithId parameters = null;
        StorageInterface updatedStorage = null;
        if(parameterStorage==null) {
            updatedStorage = new StringStorage();
            parameters = new ParameterStorageGroupWithId(updatedStorage,null,null,group);
        }else {
            updatedStorage = parameterStorage;
            parameters = new ParameterStorageGroupWithId(updatedStorage,null,parent,group);
        }
        parameters.setParameter(group+"."+id,value);

        if(parameterStorage!=null) {
            storage.setParameterAsStorage(parent,updatedStorage);
        }else {
            storage.setParameter(parent,updatedStorage.load());
        }
    }
}