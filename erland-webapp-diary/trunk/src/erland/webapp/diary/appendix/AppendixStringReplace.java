package erland.webapp.diary.appendix;

import erland.webapp.common.WebAppEnvironmentInterface;
import erland.webapp.common.EntityInterface;
import erland.webapp.common.QueryFilter;
import erland.webapp.diary.appendix.AppendixEntry;
import erland.webapp.diary.StringReplaceInterface;
import erland.util.Log;

public class AppendixStringReplace implements StringReplaceInterface {
    private WebAppEnvironmentInterface environment;

    public AppendixStringReplace(WebAppEnvironmentInterface environment) {
        this.environment = environment;
    }
    public String replace(String str) {
        Log.println(this,"replacing appendix entries with "+getClass().getName());
        EntityInterface[] entities = environment.getEntityStorageFactory().getStorage("appendixentry").search(new QueryFilter("all"));
        for (int i = 0; i < entities.length; i++) {
            AppendixEntry entry = (AppendixEntry) entities[i];
            Log.println(this,"replacing appendix entry "+entry.getName());
            if(entry.getDescription()!=null && entry.getDescription().length()>0) {
                Log.println(this,"Before:"+str);
                str = doReplace(str,"(?i)(\\b\\w*"+entry.getName()+"\\w*\\b)",entry.getDescription());
                Log.println(this,"After :"+str);
            }
        }
        return str;
    }
    private String doReplace(String data, String replace, String with) {
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
