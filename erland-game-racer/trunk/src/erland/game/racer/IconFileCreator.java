package erland.game.racer;

/**
 * Utility class that can be used to create the mapicon part of a new empty configuration file
 */
public class IconFileCreator {
    /**
     * Prints the mapicon part with specified number of icons of a new empty configuration file
     * @param args image starticonno startimageno nooficons
     */
    public static void main(String args[])
    {
        if(args.length!=4) {
            System.err.println("Incorrect arguments, syntas is:");
            System.err.println("<image> <starticonno> <startimageno> <nooficons>");
            return;
        }
        String image = args[0];
        int startIconNo = 0;
        int startImageNo = 0;
        int noOfIcons = 0;
        try {
            startIconNo = Integer.valueOf(args[1]).intValue();
            startImageNo = Integer.valueOf(args[2]).intValue();
            noOfIcons = Integer.valueOf(args[3]).intValue();
        } catch (NumberFormatException e) {
            System.err.println("Incorrect arguments, syntas is:");
            System.err.println("<image> <starticonno> <startimageno> <nooficons>");
            return;
        }

        for(int i=0;i<noOfIcons;i++) {
            System.out.println("<mapicon>");
            System.out.println("<name>mapicon"+(startIconNo+i)+"</name>");
            System.out.println("<data>");
            System.out.println("<mapicondata>");
            System.out.println("<data>");
            System.out.println("<blockdata>");
            System.out.println("<block>");
            System.out.println("<name>block0</name>");
            System.out.println("<data>");
            System.out.println("<parameters>");
            System.out.println("<class>erland.game.racer.BlockBitmap</class>");
            System.out.println("<x>0</x>");
            System.out.println("<y>0</y>");
            System.out.println("<image>"+image+"</image>");
            System.out.println("<subimage>"+(startImageNo+i)+"</subimage>");
            System.out.println("</parameters>");
            System.out.println("</data>");
            System.out.println("</block>");
            System.out.println("</blockdata>");
            System.out.println("</data>");
            System.out.println("</mapicondata>");
            System.out.println("</data>");
            System.out.println("</mapicon>");
        }
    }
}
