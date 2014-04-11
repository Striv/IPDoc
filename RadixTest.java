import java.util.Scanner;
import java.util.regex.*;
import java.io.*;

public class RadixTest {
    public static void main (String [] args)
    {
        try {
        Scanner sc = new Scanner(new File("ips.txt")).useDelimiter("\\Z");
        }
        catch(Exception e)
        {
            e.printStackTrace();
        }

        //while(sc.hasNext())
          //  System.out.println(makeIP(sc.next()));
        //System.out.println("Done.");
    }
    public static String makeIP (String IP)
    {
        String [] octets;
        String IP_OUT = "";
        if(IP.indexOf(".") != -1) // There is a period in the string
        {
            octets = IP.split("\\.");
            for(String i : octets)
            {
                if(i.indexOf("0x") == 0) // Hex Octet
                    i = Integer.parseInt(i.substring(2), 16) + "";
                else if(i.length() == 8) // Binary Octet
                    i = Integer.parseInt(i, 2) + "";
                else if(i.charAt(0) == '0' && Integer.parseInt(i) > 255)
                    i = Integer.parseInt(i, 8) + "";
                IP_OUT += (i + ".");
            }
            return IP_OUT.substring(0,IP_OUT.length()-2);
        }
        //else if() // Case where ip address is concatenated string
        return "UNCHANGED: " + IP;
    }

    public static boolean validIP(String IP)
    {
        String [] oc = IP.split("\\.");
        boolean p1 = false, p2 = false, p3 = false, p4 = false;
        int a = Integer.parseInt(oc[0]);
        int b = Integer.parseInt(oc[1]);
        int c = Integer.parseInt(oc[2]);
        int d = Integer.parseInt(oc[3]);
        if(a > 0 && a < 256)
            p1 = true;
        if(b >= 0 && b < 256)
            p2 = true;
        if(c >= 0 && c < 256)
            p3 = true;
        if(d >= 0 && d < 255)
            p4 = true;
        return (p1 && p2 && p3 && p4);
    }
}


