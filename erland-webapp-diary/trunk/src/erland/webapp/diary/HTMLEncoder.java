package erland.webapp.diary;

import java.util.Vector;

public class HTMLEncoder {
    private static Vector replaceRoutines = new Vector();
    public static String encode(String str) {
        for (int i = 0; i < replaceRoutines.size(); i++) {
            StringReplaceInterface replaceRoutine = (StringReplaceInterface) replaceRoutines.elementAt(i);
            str = replaceRoutine.replace(str);
        }
        return str;
    }
    public static void addReplaceRoutine(StringReplaceInterface replaceRoutine) {
        replaceRoutines.add(replaceRoutine);
    }
}
