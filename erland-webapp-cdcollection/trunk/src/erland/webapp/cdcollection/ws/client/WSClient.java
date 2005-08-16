package erland.webapp.cdcollection.ws.client;

import com.antelmann.cddb.*;

import java.net.URL;
import java.io.InputStreamReader;
import java.io.BufferedReader;

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

/**
 * This is just a simple test class which reads the local cdrom drive and lookup
 * the disk in freeDB and imports into a new media/disc
 */
public class WSClient {
    public static void main(String[] args) {
        String url = null;
        if(args.length<1) {
            System.out.println("Parameters: <importDisc|importMedia> <hostname> <username> <password> [collectionId]");
            return;
        }else if(args[0].equals("importMedia")) {
            if(args.length<3) {
                System.out.println("Parameters: importMedia <hostname> <username> <password>");
                return;
            }
            url = "http://"+args[1]+"/cdcollection/services/CDCollection?method=importMedia&username="+args[2]+"&password="+args[3];
        }else if(args[0].equals("importDisc")) {
            if(args.length<4) {
                System.out.println("Parameters: importDisc <hostname> <username> <password> <collectionId>");
                return;
            }
            url = "http://"+args[1]+"/cdcollection/services/CDCollection?method=importDisc&username="+args[2]+"&password="+args[3]+"&mediaId="+args[4];
        }else {
            System.out.println("Parameters: <importDisc|importMedia> <hostname> <username> <password> <collectionId>");
            return;
        }

        try {
            FreeDB freedb = new FreeDB();
            CDDBRecord[] records = freedb.queryCD(new CDID(new CDDriveWin()));
            CDInfo cd = null;
            for(int i=0;i<records.length;i++) {
                if(records[i].isExactMatch()) {
                    CDID cdid = new CDID(new CDDriveWin());
                    cd = freedb.readCDInfo(records[i]);
                }
            }
            if(cd==null) {
                System.out.println("Not found");
                return;
            }else {
                if(cd instanceof CDDBEntry) {
                    CDDBEntry entry = (CDDBEntry)cd;
                    Composition composition = entry.extractComposition(false);
                    Artist artist = entry.extractCDArtist();
                    System.out.println(artist.getName()+" : "+composition.getTitle());
                    Track[] tracks = entry.extractTracks(false);
                    for(int i=0;i<tracks.length;i++) {
                        System.out.println(tracks[i].getTrackNo()+": "+tracks[i].getTitle()+" "+(tracks[i].getLength()/60)+(tracks[i].getLength()%60<10?":0":":")+(tracks[i].getLength()%60));
                    }
                    System.out.println("\nPress enter to continue");
                    if(System.in.read()=='\r') {
                        System.out.println("Importing by using: "+url+"&category="+entry.getCDDBRecord().getCategory()+"&discId="+entry.getCDDBRecord().getDiscID());
                        BufferedReader reader = new BufferedReader(new InputStreamReader(new URL(url+"&category="+entry.getCDDBRecord().getCategory()+"&discId="+entry.getCDDBRecord().getDiscID()).openStream()));
                        int value = reader.read();
                        while(value>=0) {
                            System.out.print((char)value);
                            value=reader.read();
                        }
                        System.out.println("Import finished!");
                    }else {
                        System.out.println("Exiting without save");
                    }
                }
            }
        }
        catch(java.io.IOException e) {
            e.printStackTrace();
        } catch (XmcdFormatException e) {
            e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
        } catch (CDDBProtocolException e) {
            e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
        }
    }
}