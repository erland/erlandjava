package erland.webapp.homepage.logic.service;

/*
 * Copyright (C) 2003-2004 Erland Isaksson (erland_i@hotmail.com)
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

import erland.util.StringStorage;
import erland.util.ParameterStorageParameterString;

public class Test {
    public static void main(String[] args) {
        ServiceInterface service = new UrlReaderService();
        String data = service.execute(new ParameterStorageParameterString(new StringStorage(args[0]),null,','));
        TransformerInterface transform = new UrlXslTransformer();
        System.out.println(transform.transform(data,new ParameterStorageParameterString(new StringStorage(args[1]),null,',')));
    }
}