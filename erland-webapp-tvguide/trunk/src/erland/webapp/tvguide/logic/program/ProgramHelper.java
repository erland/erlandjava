package erland.webapp.tvguide.logic.program;

/*
 * Copyright (C) 2005 Erland Isaksson (erland_i@hotmail.com)
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

import erland.webapp.common.QueryFilter;
import erland.webapp.common.EntityInterface;
import erland.webapp.common.WebAppEnvironmentInterface;
import erland.webapp.tvguide.entity.channel.Channel;
import erland.webapp.tvguide.entity.Service;
import erland.webapp.tvguide.entity.program.Program;
import erland.webapp.tvguide.logic.service.ServiceHelper;

import java.util.List;
import java.util.Iterator;
import java.util.Date;
import java.util.Calendar;
import java.io.StringReader;
import java.io.Reader;
import java.text.SimpleDateFormat;
import java.text.DateFormat;
import java.text.ParseException;

import org.dom4j.io.SAXReader;
import org.dom4j.Document;
import org.dom4j.Element;
import org.dom4j.DocumentException;

public class ProgramHelper {
    private static DateFormat DATE_FORMAT = new SimpleDateFormat("yyyyMMddHHmmss Z");
    public static void loadPrograms(WebAppEnvironmentInterface environment,boolean forced) {
        QueryFilter filter = new QueryFilter("all");
        EntityInterface[] entities = environment.getEntityStorageFactory().getStorage("tvguide-channel").search(filter);
        for (int i = 0; i < entities.length; i++) {
            Channel channel = (Channel) entities[i];
            loadPrograms(environment,channel,forced);
        }
    }
    private static void loadPrograms(WebAppEnvironmentInterface environment, Channel channel,boolean forced) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(new Date());
        cal.add(Calendar.HOUR_OF_DAY,-1);
        if(channel.getService()!=null && channel.getService().intValue()!=0 && (forced || channel.getCacheDate()==null || cal.getTime().after(channel.getCacheDate()))) {
            String data = ServiceHelper.getServiceData(environment,channel.getService(),channel.getServiceParameters());
            Reader reader = new StringReader(data);
            SAXReader saxReader = new SAXReader();

            try {
                Document document = saxReader.read(reader);

                if (document != null) {
                    List list = document.selectNodes( "/tv/programme[@channel='"+channel.getTag()+"']" );
                    for (Iterator iter = list.iterator(); iter.hasNext(); ) {
                        try {
                            Element element = (Element) iter.next();
                            String start = element.attributeValue("start");
                            Date startTime = DATE_FORMAT.parse(start);
                            String stop = element.attributeValue("stop");
                            Date stopTime = DATE_FORMAT.parse(stop);
                            String title = element.elementText("title");
                            String desc = element.elementText("desc");
                            QueryFilter filter = new QueryFilter("allforchannelandstarttime");
                            filter.setAttribute("channel",channel.getId());
                            filter.setAttribute("starttime",startTime);
                            EntityInterface[] programs = environment.getEntityStorageFactory().getStorage("tvguide-program").search(filter);
                            if(programs.length>0) {
                                Program program = (Program) programs[0];
                                program.setStart(startTime);
                                program.setStop(stopTime);
                                program.setName(title);
                                program.setDescription(desc);
                                program.setChannel(channel.getId());
                                environment.getEntityStorageFactory().getStorage("tvguide-program").store(program);
                            }else {
                                Program program = (Program) environment.getEntityFactory().create("tvguide-program");
                                program.setStart(startTime);
                                program.setStop(stopTime);
                                program.setName(title);
                                program.setDescription(desc);
                                program.setChannel(channel.getId());
                                environment.getEntityStorageFactory().getStorage("tvguide-program").store(program);
                            }
                        } catch (ParseException e) {
                            e.printStackTrace();
                        }
                    }
                    channel.setCacheDate(new Date());
                    environment.getEntityStorageFactory().getStorage("tvguide-channel").store(channel);

                }
            } catch (DocumentException e) {
                e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
            }

        }
    }
}
