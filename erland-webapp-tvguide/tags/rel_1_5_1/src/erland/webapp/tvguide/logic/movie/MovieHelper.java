package erland.webapp.tvguide.logic.movie;

import erland.webapp.common.WebAppEnvironmentInterface;
import erland.webapp.common.QueryFilter;
import erland.webapp.common.EntityInterface;
import erland.webapp.tvguide.entity.movie.Movie;
import erland.util.StringUtil;

import java.net.URL;
import java.net.URLEncoder;
import java.net.URLConnection;
import java.io.*;
import java.util.regex.Pattern;
import java.util.regex.Matcher;
import java.util.Date;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;

import org.apache.commons.logging.LogFactory;
import org.apache.commons.logging.Log;

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

public class MovieHelper {
    /** Logging instance */
    private static Log LOG = LogFactory.getLog(MovieHelper.class);
    private static Map reviews = new HashMap();
    private static final String LINK_PREFIX = "http://www.imdb.com/title/";

    public static void loadMovies(WebAppEnvironmentInterface environment) {
        synchronized(reviews) {
            reviews.clear();
            EntityInterface[] entities = environment.getEntityStorageFactory().getStorage("tvguide-movie").search(new QueryFilter("all"));
            for (int i = 0; i < entities.length; i++) {
                Movie entity = (Movie) entities[i];
                if(StringUtil.asNull(entity.getLink())!=null) {
                    entity.setLink(LINK_PREFIX+entity.getLink()+"/");
                }
                reviews.put(entity.getTitle(),entity);
            }
        }
    }

    public static void refreshMovie(WebAppEnvironmentInterface environment, String title, String id) {
        title = title.toLowerCase();
        String data =null;
        if(StringUtil.asNull(id)!=null) {
            data = getTitlePage(id);
        }
        String poster = null;
        if(data!=null) {
            poster = getMoviePoster(data);
        }
        storePoster(environment, title, poster);
        int review = 0;
        if(data!=null) {
            review = getMovieReview(data);
        }
        Movie movieEntity = (Movie) environment.getEntityFactory().create("tvguide-movie");
        movieEntity.setTitle(title.toLowerCase());
        if(data!=null) {
            movieEntity.setReview(new Integer(review));
            movieEntity.setLink(id);
            storeMovie(environment,movieEntity.getTitle(),movieEntity);
        }else {
            storeMovie(environment,movieEntity.getTitle(),null);
        }
   }

    private static void storePoster(WebAppEnvironmentInterface environment, String title, String poster) {
        String cacheDir = StringUtil.asNull(environment.getConfigurableResources().getParameter("cover.cache"));
        if(cacheDir!=null) {
            if(poster!=null) {
                try {
                    LOG.debug("Loading poster: "+poster);
                    InputStream input = new BufferedInputStream(new URL(poster).openStream());
                    String title2 = title.toLowerCase().replaceAll(":","-");
                    String filename = title2.toLowerCase().replaceAll("/"," ");
                    OutputStream output = new BufferedOutputStream(new FileOutputStream(cacheDir+"/"+filename+".jpg"));

                    int d = input.read();
                    while(d>=0) {
                        output.write(d);
                        d = input.read();
                    }
                    input.close();
                    output.close();
                } catch (IOException e) {
                    e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
                }
            }else {
                String title2 = title.toLowerCase().replaceAll(":","-");
                String filename = title2.toLowerCase().replaceAll("/"," ");
                File file = new File(cacheDir+"/"+filename+".jpg");
                file.delete();
            }
        }
    }
    public static Movie getMovie(WebAppEnvironmentInterface environment, String title) {
        Movie movieEntity = null;
        synchronized(reviews) {
            movieEntity = (Movie) reviews.get(title.toLowerCase());
        }
        if(movieEntity==null) {
            Movie template = (Movie) environment.getEntityFactory().create("tvguide-movie");
            template.setTitle(title.toLowerCase());
            movieEntity = (Movie) environment.getEntityStorageFactory().getStorage("tvguide-movie").load(template);
            if(movieEntity!=null) {
                if(StringUtil.asNull(movieEntity.getLink())!=null) {
                    movieEntity.setLink(LINK_PREFIX+movieEntity.getLink()+"/");
                }
                synchronized(reviews) {
                    reviews.put(movieEntity.getTitle(),movieEntity);
                }
            }
        }
        return movieEntity;
    }
    private static void storeMovie(WebAppEnvironmentInterface environment, String title, Movie movieEntity) {
        if(movieEntity!=null) {
            environment.getEntityStorageFactory().getStorage("tvguide-movie").store(movieEntity);
            if(StringUtil.asNull(movieEntity.getLink())!=null) {
                movieEntity.setLink(LINK_PREFIX+movieEntity.getLink()+"/");
            }
        }else {
            Movie template = (Movie) environment.getEntityFactory().create("tvguide-movie");
            template.setTitle(title);
            environment.getEntityStorageFactory().getStorage("tvguide-movie").delete(template);
        }
        synchronized(reviews) {
            if(movieEntity!=null) {
                reviews.put(title,movieEntity);
            }else {
                reviews.remove(title);
            }
        }
    }

    public static Movie getMovie(WebAppEnvironmentInterface environment, String title, Integer year) {
        Movie movieEntity = getMovie(environment, title);
        if(movieEntity == null) {
            int review = 0;
            String data = getSearchResultPage(title);
            for(int i=0;data==null && i<3;i++) {
                // Sleep for 10 seconds and retry
                try {
                    Thread.sleep(10000);
                } catch (InterruptedException e) {
                }
                data = getSearchResultPage(title);
            }
            if(data!=null && isResultPage(data)) {
                data = getTitlePage(data,year);
            }
            if(data==null) {
                data = getHalfTitlePage(title,year,'-');
            }
            if(data==null) {
                data = getHalfTitlePage(title,year,':');
            }
            String link = null;
            if(data!=null) {
                review = getMovieReview(data);
                link = getMovieId(data);
                String poster = getMoviePoster(data);
                storePoster(environment, title, poster);
            }

            movieEntity = (Movie) environment.getEntityFactory().create("tvguide-movie");
            movieEntity.setTitle(title.toLowerCase());
            movieEntity.setReview(new Integer(review));
            movieEntity.setLink(link);
            storeMovie(environment, movieEntity.getTitle(), movieEntity);
        }
        return movieEntity;
    }

    private static int getMovieReview(String data) {
        int review=0;
        int pos = data.indexOf("goldstar.gif");
        while(pos>=0) {
            review++;
            pos = data.indexOf("goldstar.gif",pos+1);
        }
        return review;
    }
    private static String getHalfTitlePage(String title, Integer year, char ch) {
        String data = null;
        int pos = title.indexOf(ch);
        if(pos>0) {
            String subtitle = title.substring(0,pos).trim();
            data = getSearchResultPage(subtitle);
            if(data!=null && isResultPage(data)) {
                data = getTitlePage(data,year);
            }
            if(data==null && pos<title.length()-1) {
                subtitle = title.substring(pos+1).trim();
            }
            data = getSearchResultPage(subtitle);
            if(data!=null && isResultPage(data)) {
                data = getTitlePage(data,year);
            }
        }
        return data;
    }
    private static boolean isResultPage(String data) {
        return data.indexOf("IMDb title search")>=0;
    }
    private static String getMovieId(String data) {
        Matcher m = Pattern.compile("/title/(.*?)/").matcher(new StringBuffer(data));
        if(m.find()) {
            return m.group(1);
        }
        return null;
    }
    private static String getMoviePoster(String data) {
        Matcher m = Pattern.compile("name=\"poster\".*?src=\"(.*?)\"").matcher(new StringBuffer(data));
        if(m.find()) {
            String poster = m.group(1);
            if(!poster.endsWith("npa.jpg")) {
                return poster;
            }
        }
        return null;
    }

    private static String getTitlePage(String data,Integer requestedYear) {
        Matcher m = Pattern.compile("IMDb title search for \"(.*?)\"").matcher(new StringBuffer(data));
        String id = null;
        Integer idYear = null;
        if(m.find()) {
            String title = m.group(1);
            Matcher mSection = Pattern.compile("<ol>(.*?)</ol>").matcher(new StringBuffer(data));
            while(id==null && mSection.find()) {
                String sectionData = mSection.group(1);
                m = Pattern.compile("href=\"/title/(.*?)/.*?>(.*?)</a>.*?\\((.*?)[^0-9]*\\)").matcher(new StringBuffer(sectionData));
                while(m.find()) {
                    String currentTitle = m.group(2);
                    Integer currentYear = StringUtil.asInteger(m.group(3),null);
                    if(equalNames(currentTitle,title)) {
                        if(idYear==null||(!idYear.equals(requestedYear) && currentYear!=null && (currentYear.equals(requestedYear) || idYear.compareTo(currentYear)<0))) {
                            id = m.group(1);
                            idYear=currentYear;
                        }
                    }
                }
                m = Pattern.compile("href=\"/title/(.*?)/.*?</a>.*?\\((.*?)[^0-9]*\\).*?(<br>)*(.*?)</li>").matcher(new StringBuffer(sectionData));
                while(m.find()) {
                    Matcher m2 = Pattern.compile("<em>\"(.*?)\"<").matcher(new StringBuffer(m.group(4)));
                    while(m2.find()) {
                        String akaTitle = m2.group(1);
                        Integer currentYear = StringUtil.asInteger(m.group(2),null);
                        if(equalNames(akaTitle,title)) {
                            if(idYear==null||(!idYear.equals(requestedYear) && currentYear!=null && (currentYear.equals(requestedYear) || idYear.compareTo(currentYear)<0))) {
                                idYear = currentYear;
                                id = m.group(1);
                            }
                        }
                    }
                }
            }
            return getTitlePage(id);
        }
        return null;
    }
    private static String getTitlePage(String id) {
        if(id!=null) {
            try {
                URL url = new URL(LINK_PREFIX+id+"/");
                BufferedReader reader = new BufferedReader(new InputStreamReader(url.openStream()));
                StringBuffer sb = new StringBuffer(10000);
                String line = reader.readLine();
                while(line!=null) {
                    sb.append(line);
                   line = reader.readLine();
                }
                reader.close();
                return sb.toString();
            } catch (IOException e) {
                e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
            }
        }
        return null;
    }
    private static String getSearchResultPage(String title) {
        try {
            URL url = new URL("http://www.imdb.com/find?q="+URLEncoder.encode(title,"UTF-8")+";s=tt;site=aka");
            BufferedReader reader = new BufferedReader(new InputStreamReader(url.openStream()));
            //BufferedReader reader = new BufferedReader(new FileReader("Z://IMDbTest.html"));
            StringBuffer sb = new StringBuffer(10000);
            String line = reader.readLine();
            while(line!=null) {
                sb.append(line);
               line = reader.readLine();
            }
            reader.close();
            return sb.toString();
        } catch (IOException e) {
            e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
        }
        return null;
    }
    private static boolean equalNames(String title1, String title2) {
        if(title1!=null && title2!=null) {
            title1 = title1.toLowerCase();
            title2 = title2.toLowerCase();
            if(title1.equalsIgnoreCase(title2)) {
                return true;
            }else {
                title1 = title1.replaceAll(": "," ");
                title1 = title1.replaceAll("- "," ");
                title1 = title1.replaceAll("  "," ");
                title1 = title1.replaceAll("  "," ");
                title2 = title2.replaceAll(": "," ");
                title2 = title2.replaceAll("- "," ");
                title2 = title2.replaceAll("  "," ");
                title2 = title2.replaceAll("  "," ");
                if(title1.equalsIgnoreCase(title2)) {
                    return true;
                }
                if(title1.startsWith("the ")) {
                    title1 = title1.substring(4)+", the";
                }
                if(title2.startsWith("the ")) {
                    title2 = title2.substring(4)+", the";
                }
                if(title1.equalsIgnoreCase(title2)) {
                    return true;
                }
                if(title1.startsWith("det ")) {
                    title1 = title1.substring(4)+", det";
                }
                if(title2.startsWith("det ")) {
                    title2 = title2.substring(4)+", det";
                }
                if(title1.equalsIgnoreCase(title2)) {
                    return true;
                }
                if(title1.startsWith("den ")) {
                    title1 = title1.substring(4)+", den";
                }
                if(title2.startsWith("den ")) {
                    title2 = title2.substring(4)+", den";
                }
                if(title1.equalsIgnoreCase(title2)) {
                    return true;
                }
                if(title1.startsWith("de ")) {
                    title1 = title1.substring(3)+", de";
                }
                if(title2.startsWith("de ")) {
                    title2 = title2.substring(3)+", de";
                }
                if(title1.equalsIgnoreCase(title2)) {
                    return true;
                }
                if(title1.startsWith("en ")) {
                    title1 = title1.substring(3)+", en";
                }
                if(title2.startsWith("en ")) {
                    title2 = title2.substring(3)+", en";
                }
                if(title1.equalsIgnoreCase(title2)) {
                    return true;
                }
            }
        }
        return false;
    }
}
