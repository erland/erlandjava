package erland.webapp.gallery;

import erland.util.Log;

public class HTMLBasicStringReplace implements StringReplaceInterface {
    public String replace(String str) {
        Log.println(this,"replacing HTML tags with "+getClass().getName());
        str = str.replaceAll("<","&lt");
        str = str.replaceAll(">","&gt");
        str = str.replaceAll("\n","<BR>");
        return str;
    }
}
