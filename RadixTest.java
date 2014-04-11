import java.util.Scanner;
import java.io.FileReader;

public class RadixTest {
    public static void main (String [] args)
    {
        Scanner sc = new Scanner(new FileReader("ips.txt"));
        while(sc.hasNext())
            System.out.println(makeIP(sc.nextLine()));
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
        return "UNCHANGED: " + IP;
    }

}


