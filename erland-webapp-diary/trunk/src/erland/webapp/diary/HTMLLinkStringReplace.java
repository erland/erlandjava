package erland.webapp.diary;

import erland.util.Log;

public class HTMLLinkStringReplace implements StringReplaceInterface {
    public String replace(String str) {
        Log.println(this,"replacing links with "+getClass().getName());
        Log.println(this,"Before: "+str);
        str = str.replaceAll("\\[img=([^\\]]*),([^\\]]*)\\]","<a href=\"$2\" target=\"_blank\"><img src=\"$1\" border=\"0\"></img></a>");
        Log.println(this,"After : "+str);
        Log.println(this,"Before: "+str);
        str = str.replaceAll("\\[img=([^\\]]*)\\]","<img src=\"$1\"></img>");
        Log.println(this,"After : "+str);
        Log.println(this,"Before: "+str);
        str = str.replaceAll("\\[link=([^\\]]*),([^\\]]*)\\]","<a class=\"link\" href=\"$2\" target=\"_blank\">$1</a>");
        Log.println(this,"After : "+str);
        return str;
    }
}
