import java.util.Scanner;
import java.util.regex.*;
import java.util.ArrayList;
import java.io.*;

public class RadixTest {
    public static void main (String [] args)
    {
        try {
            Scanner sc = new Scanner(new FileReader("ips.txt")).useDelimiter("\\Z");
            System.out.println(parseFile(sc.next()));
        }
        catch(Exception e)
        {
            e.printStackTrace();
        }

        //while(sc.hasNext())
          //  System.out.println(makeIP(sc.next()));
        //System.out.println("Done.");
    }

    public static ArrayList<String> parseFile(String fileContents)
    {
        ArrayList<String> ipList = new ArrayList<String>();
        String in = fileContents;
        //--------------------------------------------
        // Binary / Hexadecimal / Octal Strings (Dot Separated)
        String re1 = "([0-9a-fxA-FX]{1,8})\\.([0-9a-fxA-FX]{1,8})\\.([0-9a-fxA-FX]{1,8})\\.([0-9a-fxA-FX]{1,8})";
        // Binary Concatenated
        String re2 = "([0-1]{31,32})";
        // Hexadecimal String Concatenated
        String re3 = "0x([0-9a-fA-F]{7,8})";
        // Octal String Concatenated
        String re4 = "([0-7]{11,12})";
        
        Pattern p = Pattern.compile(re,Pattern.CASE_INSENSITIVE | Pattern.DOTALL);
        Matcher m = p.matcher(in);
        
        if (m.find())
        {
            System.out.println(in.substring(m.start(),m.end()));
            String int1=m.group(1);
            String int2=m.group(2);
            String int3=m.group(3);
            String int4=m.group(4);
            System.out.print("("+int1.toString()+")"+"("+int2.toString()+")"+"("+int3.toString()+")"+"("+int4.toString()+")"+"\n");
        }
        //--------------------------------------------
        return ipList;
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


