import java.util.Scanner;
import java.util.regex.*;
import java.util.ArrayList;
import java.io.*;

public class RadixTest {
    public static void main (String [] args)
    {
        try {
            Scanner sc = new Scanner(new FileReader("ips.txt")).useDelimiter("\\Z");
            ArrayList<String> ipList = new ArrayList<String>();
            //-------------------------------------------- 
            // Hex Strings (Dot Separated)
            String re1 = "0x([0-9a-fA-F]{1,2})\\.0x([0-9a-fA-F]{1,2})\\.0x([0-9a-fA-F]{1,2})\\.0x([0-9a-fA-F]{1,2})";
            // Binary String (Dot Separated)
            String re2 = "([0-1]{4,8})\\.([0-1]{4,8})\\.([0-1]{4,8})\\.([0-1]{4,8})";
            // Octal String (Dot Separated)
            String re3 = "0[0-7]{3}\\.0[0-7]{3}\\.0[0-7]{3}\\.0[0-7]{3}";
            // Decimal Strings (Dot Separated)
            String re4 = "([0-9]{1,3})\\.([0-9]{1,3})\\.([0-9]{1,3})\\.([0-9]{1,3})";
            // Binary Concatenated
            String re5 = "([0-1]{31,32})";
            // Hexadecimal String Concatenated
            String re6 = "0x([0-9a-fA-F]{8})";
            // Octal String Concatenated
            String re7 = "([0-7]{12})";
            String [] rex = {re1, re2, re3, re4, re5, re6, re7};
            //-------------------------------------------- 
            String file = sc.next();
            for(String re : rex)
            {
                parseFile(file, re, ipList);
            }
            System.out.println(ipList);
            
        }
        catch(Exception e)
        {
            e.printStackTrace();
        }
    }

    public static void parseFile(String fileContents, String re, ArrayList<String> ipList)
    {
        String in = fileContents;
        //--------------------------------------------         
        while(!"".equals(in))
        {
            Pattern p = Pattern.compile(re,Pattern.CASE_INSENSITIVE | Pattern.DOTALL);
            Matcher m = p.matcher(in);
        
            if (m.find())
            {
                String testIP = makeIP(in.substring(m.start(),m.end()));
                //System.out.println("Testing: " + testIP);
                System.out.println(in.substring(m.start(),m.end()) + "\t\t\t-->\t\t\t" + testIP);
                if(validIP(testIP))
                {
                    ipList.add(testIP);    
                }
                in = in.substring(m.end()+1);
            }
            else
                in = "";
        }
        //--------------------------------------------
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
                else if(i.charAt(0) == '0' && i.length() == 4)
                    i = Integer.parseInt(i, 8) + "";
                IP_OUT += (i + ".");
            }
            return IP_OUT.substring(0,IP_OUT.length()-1);
        }
        else // Case where ip address is concatenated string
        {
            if (IP.indexOf("0x") == 0)
            {
                IP_OUT += "0x" + IP.substring(2,4) + ".";
                IP_OUT += "0x" + IP.substring(4,6) + ".";
                IP_OUT += "0x" + IP.substring(6,8) + ".";
                IP_OUT += "0x" + IP.substring(8,10);
            }
            else if(IP.length() == 31)
            {
                IP_OUT += "0" + IP.substring(0,7) + ".";
                IP_OUT += IP.substring(7,15) + ".";
                IP_OUT += IP.substring(15,16) + ".";
                IP_OUT += IP.substring(23,31);                
            }
            else if(IP.length() == 32)
            {
                IP_OUT += IP.substring(0,8) + ".";
                IP_OUT += IP.substring(8,16) + ".";
                IP_OUT += IP.substring(16,24) + ".";
                IP_OUT += IP.substring(24,32);                
            }
            else if(IP.length() == 12)
            {
                IP_OUT += "0" + IP.substring(0,3) + ".";
                IP_OUT += "0" + IP.substring(3,6) + ".";
                IP_OUT += "0" + IP.substring(6,9) + ".";
                IP_OUT += "0" + IP.substring(9,12);
            }
            return makeIP(IP_OUT);
        }
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


