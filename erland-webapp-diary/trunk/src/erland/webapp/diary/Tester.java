package erland.webapp.diary;

import erland.util.ParameterStorageTree;
import erland.util.FileStorage;

public class Tester {
    public static void main(String[] args) {
        String str = "Jag provar jagMedan [img=med.gif] igen [img=12.gif]ciklider och [img=1.gif][r]";
        //str = str.replaceAll("\\[img=([a-zA-Z0-9./:_-]*)\\]","KALLE");
        str = replace(str,"(igen)","[img=med.gif]");
        str = replace(str,"(?i)(\\b\\w*med\\w*\\b)","[img=testing.gif]");
        //str = str.replaceAll("(med)","[img=testing.gif]");
        //str = str.replaceAll("\\[img=([^\\]]*)\\]","<img src=\"$1\"></img>");
        System.out.println(str);

        //System.out.println(new ParameterStorageTree(new FileStorage("resources.xml")).getParameter("testing.kalle.name"));
    }
    public static String replace(String data, String replace, String with) {
        StringBuffer sb = new StringBuffer(data);
        int startPos = 0;
        while(startPos>=0 && startPos<sb.length()) {
            int pos = sb.indexOf("[",startPos);
            if(pos>=0) {
                String str = sb.substring(startPos,pos);
                str = str.replaceAll(replace,with);
                sb.replace(startPos,pos,str);
                startPos = sb.indexOf("]",pos);
            }else {
                String str = sb.substring(startPos);
                str = str.replaceAll(replace,with);
                sb.replace(startPos,sb.length(),str);
                startPos = sb.length();
            }
            if(startPos>=0) {
                startPos++;
            }
        }

        return sb.toString();
    }
}
