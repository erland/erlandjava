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
import erland.webapp.common.ServletParameterHelper;
import erland.webapp.tvguide.entity.channel.Channel;
import erland.webapp.tvguide.entity.Service;
import erland.webapp.tvguide.entity.movie.Movie;
import erland.webapp.tvguide.entity.subscription.Subscription;
import erland.webapp.tvguide.entity.favorite.Favorite;
import erland.webapp.tvguide.entity.program.Program;
import erland.webapp.tvguide.logic.service.ServiceHelper;
import erland.webapp.tvguide.logic.movie.MovieHelper;
import erland.webapp.tvguide.fb.program.ProgramPB;
import erland.webapp.tvguide.fb.program.SearchProgramFB;
import erland.util.StringUtil;

import java.util.*;
import java.io.StringReader;
import java.io.Reader;
import java.text.SimpleDateFormat;
import java.text.DateFormat;
import java.text.ParseException;
import java.lang.reflect.InvocationTargetException;

import org.dom4j.io.SAXReader;
import org.dom4j.Document;
import org.dom4j.Element;
import org.dom4j.DocumentException;
import org.apache.struts.action.ActionForward;
import org.apache.commons.beanutils.PropertyUtils;

public class ProgramHelper {
    private static DateFormat DATE_FORMAT = new SimpleDateFormat("yyyyMMddHHmmss Z");
    public static void loadPrograms(WebAppEnvironmentInterface environment,boolean forced) {
        MovieHelper.loadMovies(environment);
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
        cal.add(Calendar.DATE,-1);
        if(channel.getService()!=null && channel.getService().intValue()!=0 && (forced || channel.getCacheDate()==null || cal.getTime().after(channel.getCacheDate()))) {
            String data = ServiceHelper.getServiceData(environment,channel.getService(),channel.getServiceParameters());
            Reader reader = new StringReader(data);
            SAXReader saxReader = new SAXReader(false);

            try {
                Document document = saxReader.read(reader);

                if (document != null) {
                    Set reviewCategories = getReviewCategoriesSet(channel.getReviewCategories());
                    Set noReviewCategories = getReviewCategoriesSet(channel.getNoReviewCategories());
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
                            Integer year = StringUtil.asInteger(element.elementText("date"),null);
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
                                if(channel.getReviewAvailable().booleanValue() && isReviewCategory(reviewCategories,noReviewCategories,element.selectNodes("category"))) {
                                    Movie movie = MovieHelper.getMovie(environment,title,year);
                                    program.setReview(movie.getReview());
                                    program.setReviewLink(movie.getLink());
                                }else {
                                    program.setReview(null);
                                    program.setReviewLink(null);
                                }
                                environment.getEntityStorageFactory().getStorage("tvguide-program").store(program);
                            }else {
                                Program program = (Program) environment.getEntityFactory().create("tvguide-program");
                                program.setStart(startTime);
                                program.setStop(stopTime);
                                program.setName(title);
                                program.setDescription(desc);
                                program.setChannel(channel.getId());
                                if(channel.getReviewAvailable().booleanValue()) {
                                    Movie movie = MovieHelper.getMovie(environment,title,year);
                                    program.setReview(movie.getReview());
                                    program.setReviewLink(movie.getLink());
                                }else {
                                    program.setReview(null);
                                }
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

    private static Collection getFavoriteChannels(WebAppEnvironmentInterface environment, String username) {
        QueryFilter filter = new QueryFilter("allforuser");
        filter.setAttribute("username", username);
        EntityInterface[] entities = environment.getEntityStorageFactory().getStorage("tvguide-favorite").search(filter);
        Collection channels = new ArrayList();
        for (int i = 0; i < entities.length; i++) {
            Favorite favorite = (Favorite) entities[i];
            channels.add(favorite.getChannel());
        }
        return channels;
    }

    private static Collection getSubscriptions(WebAppEnvironmentInterface environment, String username) {
        QueryFilter filter = new QueryFilter("allforuser");
        filter.setAttribute("username", username);
        EntityInterface[] entities = environment.getEntityStorageFactory().getStorage("tvguide-subscription").search(filter);
        Collection subscriptions = new ArrayList();
        for (int i = 0; i < entities.length; i++) {
            Subscription subscription = (Subscription) entities[i];
            subscriptions.add(subscription);
        }
        return subscriptions;
    }

    private static Map getChannelMap(WebAppEnvironmentInterface environment, Collection channels) {
        QueryFilter filter = new QueryFilter("allinlist");
        filter.setAttribute("channel", channels);
        EntityInterface[] entities = environment.getEntityStorageFactory().getStorage("tvguide-channel").search(filter);
        Map channelMap = new HashMap();
        for (int i = 0; i < entities.length; i++) {
            Channel channel = (Channel) entities[i];
            channelMap.put(channel.getId(), channel);
        }
        return channelMap;
    }
    public static ProgramPB[] getSubscribedPrograms(WebAppEnvironmentInterface environment, String username, ActionForward viewForward) {
        Collection channels = getFavoriteChannels(environment,username);
        if(channels.size()>0) {
            loadPrograms(environment,false);
            Map channelMap = getChannelMap(environment, channels);
            Collection subscriptions = getSubscriptions(environment,username);
            if(subscriptions.size()>0) {
                Date date = new Date();

                QueryFilter filter = new QueryFilter("allinlistaftertime");
                filter.setAttribute("channel", channels);
                return getSubscribedPrograms(environment,username,filter,subscriptions,channelMap,date, viewForward);
            }
        }
        return new ProgramPB[0];
    }

    public static ProgramPB[] getSubscribedPrograms(WebAppEnvironmentInterface environment, String username, Date date, ActionForward viewForward) {
        Collection channels = getFavoriteChannels(environment,username);
        if(channels.size()>0) {
            loadPrograms(environment,false);
            Map channelMap = getChannelMap(environment, channels);
            Collection subscriptions = getSubscriptions(environment,username);
            if(subscriptions.size()>0) {
                QueryFilter filter = new QueryFilter("allinlistfordateaftertime");
                filter.setAttribute("channel", channels);
                return getSubscribedPrograms(environment,username,filter,subscriptions,channelMap,date, viewForward);
            }
        }
        return new ProgramPB[0];
    }

    public static ProgramPB[] getSubscriptionPrograms(WebAppEnvironmentInterface environment, String username, Subscription subscription) {
        Collection channels = getFavoriteChannels(environment,username);
        if(channels.size()>0) {
            loadPrograms(environment,false);
            Map channelMap = getChannelMap(environment, channels);
            Collection subscriptions = new ArrayList();
            subscriptions.add(subscription);

            Date date = new Date();

            QueryFilter filter = new QueryFilter("allinlistaftertime");
            filter.setAttribute("channel", channels);
            return getSubscribedPrograms(environment,username,filter,subscriptions,channelMap,date, null);
        }
        return new ProgramPB[0];
    }

    public static ProgramPB[] getAllPrograms(WebAppEnvironmentInterface environment, String username, Date date, Integer dateOffset, ActionForward subscriptionForward) {

        Collection channels = getFavoriteChannels(environment,username);
        if(channels.size()>0) {
            loadPrograms(environment,false);
            Map channelMap = getChannelMap(environment, channels);

            QueryFilter filter = new QueryFilter("allinlistfordateaftertime");
            filter.setAttribute("channel",channels);
            return getAllPrograms(environment,username,filter,channelMap,date,dateOffset,subscriptionForward);
        }
        return new ProgramPB[0];
    }

    public static ProgramPB[] getChannelPrograms(WebAppEnvironmentInterface environment, String username, Channel channel, Date date, ActionForward newSubscriptionForward) {
        loadPrograms(environment,false);
        Map channelMap = new HashMap();
        channelMap.put(channel.getId(),channel);
        QueryFilter filter = new QueryFilter("allforchannelanddateaftertime");
        filter.setAttribute("channel",channel.getId());
        return getAllPrograms(environment,username,filter,channelMap,date,null,newSubscriptionForward);
    }

    private static ProgramPB[] getAllPrograms(WebAppEnvironmentInterface environment, String username, QueryFilter filter, Map channelMap, Date date, Integer dateOffset, ActionForward newSubscriptionForward) {
        Date currentDate = new Date();
        if(date==null) {
            date = currentDate;
        }
        if(!sameDay(currentDate,date) && currentDate.before(date)) {
            currentDate = date;
        }
        if(dateOffset!=null && dateOffset.intValue()!=0) {
            Calendar cal = Calendar.getInstance();
            cal.setTime(date);
            cal.add(Calendar.DATE,dateOffset.intValue());
            date = cal.getTime();
        }

        filter.setAttribute("time",date);
        EntityInterface[] entities = environment.getEntityStorageFactory().getStorage("tvguide-program").search(filter);
        ProgramPB[] programsPB = new ProgramPB[entities.length];
        Map parameters = new HashMap();
        parameters.put("user",username);
        for (int i = 0; i < entities.length; i++) {
            Program entity = (Program) entities[i];
            programsPB[i] = new ProgramPB();
            try {
                PropertyUtils.copyProperties(programsPB[i],entity);
            } catch (IllegalAccessException e) {
            } catch (InvocationTargetException e) {
            } catch (NoSuchMethodException e) {
            }
            Channel ch = (Channel) channelMap.get(entity.getChannel());
            programsPB[i].setChannelName(ch.getName());
            programsPB[i].setChannelLogo(ch.getLogo());
            programsPB[i].setChannelLink(ch.getLink());

            if(programsPB[i].getStart().before(currentDate)) {
                programsPB[i].setStarted(Boolean.TRUE);
            }else {
                programsPB[i].setStarted(Boolean.FALSE);
            }
            if(sameDay(programsPB[i].getStart(),date)) {
                programsPB[i].setStartSameDay(Boolean.TRUE);
            }else {
                programsPB[i].setStartSameDay(Boolean.FALSE);
            }
            if(newSubscriptionForward!=null) {
                parameters.put("programName",programsPB[i].getName());
                programsPB[i].setNewSubscriptionLink(ServletParameterHelper.replaceDynamicParameters(newSubscriptionForward.getPath(),parameters));
            }
        }
        return programsPB;
    }

    private static ProgramPB[] getSubscribedPrograms(WebAppEnvironmentInterface environment, String username, QueryFilter filter, Collection subscriptions, Map channelMap, Date date, ActionForward viewSubscriptionForward) {
        filter.setAttribute("time",date);
        EntityInterface[] entities = environment.getEntityStorageFactory().getStorage("tvguide-program").search(filter);
        Collection programCollection = new ArrayList();
        Map parameters = new HashMap();
        parameters.put("user",username);
        for (int i = 0; i < entities.length; i++) {
            Program entity = (Program) entities[i];
            for (Iterator iterator = subscriptions.iterator(); iterator.hasNext();) {
                Subscription subscription = (Subscription) iterator.next();
                String pattern = subscription.getPattern();
                if (entity.getName().matches(pattern)) {
                    ProgramPB programPB;
                    programPB = new ProgramPB();
                    try {
                        PropertyUtils.copyProperties(programPB, entity);
                    } catch (IllegalAccessException e) {
                    } catch (InvocationTargetException e) {
                    } catch (NoSuchMethodException e) {
                    }
                    Channel ch = (Channel) channelMap.get(entity.getChannel());
                    programPB.setChannelName(ch.getName());
                    programPB.setChannelLogo(ch.getLogo());
                    programPB.setChannelLink(ch.getLink());

                    if (programPB.getStart().before(date)) {
                        programPB.setStarted(Boolean.TRUE);
                    } else {
                        programPB.setStarted(Boolean.FALSE);
                    }
                    if (sameDay(programPB.getStart(), date)) {
                        programPB.setStartSameDay(Boolean.TRUE);
                    } else {
                        programPB.setStartSameDay(Boolean.FALSE);
                    }
                    if(viewSubscriptionForward!=null) {
                        parameters.put("subscription",subscription.getId());
                        programPB.setViewSubscriptionLink(ServletParameterHelper.replaceDynamicParameters(viewSubscriptionForward.getPath(),parameters));
                    }
                    programCollection.add(programPB);
                    break;
                }
            }
        }
        return (ProgramPB[]) programCollection.toArray(new ProgramPB[0]);
    }
    private static boolean sameDay(Date date1, Date date2) {
        Calendar c1 = Calendar.getInstance();
        c1.setTime(date1);
        Calendar c2 = Calendar.getInstance();
        c2.setTime(date2);
        return c1.get(Calendar.YEAR) == c2.get(Calendar.YEAR) && c1.get(Calendar.MONTH) == c2.get(Calendar.MONTH) && c1.get(Calendar.DATE) == c2.get(Calendar.DATE);
    }
    private static Set getReviewCategoriesSet(String reviewCategories) {
        if(StringUtil.asNull(reviewCategories)==null) {
            return null;
        }
        Set result = new HashSet();
        StringTokenizer tokens = new StringTokenizer(reviewCategories,",");
        while(tokens.hasMoreTokens()) {
            String token = tokens.nextToken();
            result.add(token.trim().toLowerCase());
        }
        return result;
    }

    private static boolean isReviewCategory(Set reviewCategories,Set noReviewCategories, List categoryElements) {
        if(noReviewCategories!=null && noReviewCategories.size()>0) {
            for (int i = 0; i < categoryElements.size(); i++) {
                Element element = (Element) categoryElements.get(i);
                String category = element.getText();
                category = category.trim().toLowerCase();
                if(noReviewCategories.contains(category)) {
                    return false;
                }
            }
        }
        if(reviewCategories==null||reviewCategories.size()==0) {
            return true;
        }
        for (int i = 0; i < categoryElements.size(); i++) {
            Element element = (Element) categoryElements.get(i);
            String category = element.getText();
            category = category.trim().toLowerCase();
            if(reviewCategories.contains(category.trim().toLowerCase())) {
                return true;
            }
        }
        return false;
    }
}
