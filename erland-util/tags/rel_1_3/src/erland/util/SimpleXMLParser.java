package erland.util;
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

import java.util.Map;
import java.util.HashMap;
import java.io.*;

/**
 * A simple implementation of an XML parser, this implementation does
 * not require any third party XML parser libraries
 * @author Erland Isaksson
 */
public class SimpleXMLParser implements XMLParserInterface {
    /**
     * Calculate the position of the first real character after the specified position, skipping
     * space and tab characters.
     * @param data The text string
     * @param pos The start position in the string
     * @return The position of the next real character,
     *         equal to string length if no more real character exist
     */
    protected int skipSpaces(String data, int pos)
    {
        char ch;
        try {
            ch=data.charAt(pos);
            while(Character.isSpaceChar(ch)) {
                pos++;
                ch=data.charAt(pos);
            }
        }catch(IndexOutOfBoundsException e) {
            return pos = data.length();
        }
        return pos;
    }
    public boolean parse(InputStream in, XMLParserHandlerInterface handler) {
        try {
            BufferedReader reader = new BufferedReader(new InputStreamReader(in));
            StringBuffer sb = new StringBuffer();
            for(String line = reader.readLine();line!=null;line=reader.readLine()) {
                sb.append(line);
            }
            return parse(sb.toString(),handler);
        } catch (IOException e) {
            e.printStackTrace();  //To change body of catch statement use Options | File Templates.
        }
        return false;
    }

    public boolean parse(String str,XMLParserHandlerInterface handler) {
        if(str==null||str.length()==0) {
            return false;
        }
        int pos = skipSpaces(str,0);
        int length = str.length();
        int startPos = 0;
        int endPos = 0;
        String name=null;
        String attributeName=null;
        Map attributes = null;
        boolean bCollectElement = false;
        boolean bCollectAttribute = false;
        boolean bCollectAttributeValueString = false;
        boolean bCollectCharacters = false;
        boolean bEndElementFound = false;
        boolean bSimpleEndElementFound = false;
        boolean bPreprocessorDirective = false;
        int mainStartNodes = 0;
        int startNodes = 0;
        int endNodes = 0;
        handler.startDocument();
        while(pos<length) {
            if(str.charAt(pos)=='<') {
                if(bCollectCharacters) {
                    handler.characters(str,startPos,pos-startPos);
                }
                bCollectElement = true;
                startPos = pos+1;
            }else if(str.charAt(pos)=='>') {
                if(bCollectElement) {
                    if(name==null && bSimpleEndElementFound && pos==endPos+1) {
                        name = str.substring(startPos,endPos);
                    }else if(name==null && bEndElementFound) {
                        name = str.substring(startPos+1,pos);
                    }else if(name==null) {
                        name = str.substring(startPos,pos);
                    }
                    if(!bPreprocessorDirective) {
                        if(bEndElementFound) {
                            endNodes++;
                            handler.endElement(name);
                        }else if(bSimpleEndElementFound) {
                            handler.startElement(name,attributes!=null&&attributes.size()>0?attributes:null);
                            handler.endElement(name);
                            if(startNodes==endNodes) {
                                mainStartNodes++;
                            }
                            startNodes++;
                            endNodes++;
                        }else {
                            handler.startElement(name,attributes!=null&&attributes.size()>0?attributes:null);
                            if(startNodes==endNodes) {
                                mainStartNodes++;
                            }
                            startNodes++;
                        }
                        name=null;
                        attributeName=null;
                        attributes = null;
                        bCollectCharacters = true;
                        startPos = pos+1;
                    }
                }
                bCollectElement = false;
                bEndElementFound = false;
                bSimpleEndElementFound = false;
                bPreprocessorDirective = false;
                bCollectAttribute = false;
                bCollectAttributeValueString = false;
            }else if(Character.isSpaceChar(str.charAt(pos))) {
                if(bCollectElement) {
                    if(name==null) {
                        name = str.substring(startPos,pos);
                    }
                    bCollectAttribute = true;
                    if(attributeName==null) {
                        startPos = pos+1;
                    }
                }
            }else if(str.charAt(pos)=='/') {
                if(bCollectElement && !bCollectAttribute && startPos==pos) {
                    bEndElementFound = true;
                }else if(bCollectElement && !bCollectAttribute) {
                    endPos = pos;
                    bSimpleEndElementFound = true;
                }
            }else if(str.charAt(pos)=='?') {
                if(bCollectElement && startPos==pos) {
                    bPreprocessorDirective = true;
                    bCollectElement = false;
                }
            }else if(str.charAt(pos)=='=') {
                if(bCollectAttribute && attributeName==null) {
                    attributeName = str.substring(startPos,pos);
                    startPos = pos+1;
                }
            }else if(str.charAt(pos)=='\"') {
                if(bCollectAttributeValueString && attributeName!=null) {
                    if(attributes == null) {
                        attributes = new HashMap();
                    }
                    attributes.put(attributeName,str.substring(startPos,pos));
                    attributeName=null;
                    bCollectAttributeValueString = false;
                    bCollectAttribute = false;
                }else if(bCollectAttribute && attributeName!=null && !bCollectAttributeValueString && startPos==pos) {
                    bCollectAttributeValueString = true;
                    startPos = pos+1;
                }
            }
            pos++;
        }
        handler.endDocument();
        if(startNodes==endNodes && startNodes>0 && mainStartNodes==1) {
            return true;
        }else {
            return false;
        }
    }
}
