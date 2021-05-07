import org.ini4j.Wini;

import java.io.File;
import java.io.IOException;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Stack;
/*
    Dieses Projekt ist in Zusammenarbeit mit
    Connor Breuer, Claas Diedrich, Lasse Knauff
    entstanden.
 */
public class CalcModel {
    public String ipToHex(String[] ip){
        String[] hexSegments = new String[ip.length];
        for (int i = 0; i < ip.length; i++) {
            hexSegments[i] = intToHex(Integer.parseInt(ip[i]));
        }
        return String.join(".", hexSegments);
    }
    public String ipToBin(String[] ip){
        int[] seg = ipToBinary(ip);
        int c = 0;
        StringBuilder b = new StringBuilder();
        for (int i = 0; i < seg.length; i++) {

            b.append(seg[i]);

            c++;
            if(c == 8){
                c = 0;
                if(i != seg.length-1) b.append(".");
            }
        }
        return b.toString();
    }

    public int[] ipToBinary(String[] str){
        int re[] = new int[32];
        int a, b, c, d, i, rem;
        a = b = c = d = 1;
        Stack<Integer> st = new Stack<Integer>();
        if (str != null){
            a = Integer.parseInt(str[0]);
            b = Integer.parseInt(str[1]);
            c = Integer.parseInt(str[2]);
            d = Integer.parseInt(str[3]);
        }
        for (i = 0; i <= 7; i++)
        {
            rem = a % 2;
            st.push(rem);
            a = a / 2;
        }
        for (i = 0; i <= 7; i++) {
            re[i] = st.pop();
        }
        for (i = 8; i <= 15; i++) {
            rem = b % 2;
            st.push(rem);
            b = b / 2;
        }
        for (i = 8; i <= 15; i++) {
            re[i] = st.pop();
        }
        for (i = 16; i <= 23; i++) {
            rem = c % 2;
            st.push(rem);
            c = c / 2;
        }
        for (i = 16; i <= 23; i++) {
            re[i] = st.pop();
        }
        for (i = 24; i <= 31; i++) {
            rem = d % 2;
            st.push(rem);
            d = d / 2;
        }
        for (i = 24; i <= 31; i++) {
            re[i] = st.pop();
        }
        return (re);
    }

    public int[] ipBinaryToDecimal(int[] bi){
        int[] arr = new int[4];
        int a, b, c, d, i, j;
        a = b = c = d = 0;
        j = 7;

        for (i = 0; i < 8; i++) {
            a = a + (int)(Math.pow(2, j)) * bi[i];
            j--;
        }
        j = 7;
        for (i = 8; i < 16; i++) {
            b = b + bi[i] * (int)(Math.pow(2, j));
            j--;
        }
        j = 7;
        for (i = 16; i < 24; i++) {

            c = c + bi[i] * (int)(Math.pow(2, j));
            j--;
        }
        j = 7;
        for (i = 24; i < 32; i++) {

            d = d + bi[i] * (int)(Math.pow(2, j));
            j--;
        }
        arr[0] = a; arr[1] = b; arr[2] = c; arr[3] = d;
        return arr;
    }

    private String intToHex(int intVal){
        return Integer.toHexString(intVal);
    }

    public int count(int[] ip, int ch){
        int res = 0;
        for (int i = 0; i < ip.length; i++) {
            if(ip[i] == ch){
                res++;
            };
        }
        return res;
    }

    public int calcHosts(String subnet){
        int sub = count(ipToBinary(subnet.split("\\.")), 0);
        return (int) Math.pow(2,sub) - 2;
    }

    public String[] calcAdresses(String ip, String sub){
        int i;
        int shorten = count(ipToBinary(sub.split("\\.")), 1);
        int[] ipInBinary = ipToBinary(ip.split("\\."));
        int[] ntwk = new int[32];
        int[] brd = new int[32];
        int t = 32 - shorten;
        for (i = 0; i <= (31 - t); i++) {
            ntwk[i] = ipInBinary[i];
            brd[i] = ipInBinary[i];
        }
        for (i = 31; i > (31 - t); i--) {
            ntwk[i] = 0;
        }
        for (i = 31; i > (31 - t); i--) {
            brd[i] = 1;
        }

        int[] nt = ipBinaryToDecimal(ntwk);
        int[] br = ipBinaryToDecimal(brd);

        return new String[]{
                nt[0] + "." + nt[1] + "." + nt[2] + "." + nt[3],
                br[0] + "." + br[1] + "." + br[2] + "." + br[3],
                nt[0] + "." + nt[1] + "." + nt[2] + "." + (nt[3]+1),
                br[0] + "." + br[1] + "." + br[2] + "." + (br[3]-1)


        };
    }
    /*
    * IF NOT RESERVED IT IS NULL
    *
    */
    public String reservedDescription(String ip){
        String des = null;
        try {
            Path currentRelativePath = Paths.get("");
            File f = new File(currentRelativePath.toAbsolutePath().toFile(), "src/reserved.ini");
            /*
                Ich weiß nict wie sie die API abgegeben haben wollen.
                Ich habe die Jar in Intelij als Lib hinzugefügt. Damit hat es funktioniert
             */
            Wini wini = new Wini(f);
            for(String key : wini.keySet()){
                String startIP = wini.get(key,"start", String.class);
                String endIP = wini.get(key,"end", String.class);
                String description = wini.get(key,"description", String.class);

                if(ipToLong(ip) >= ipToLong(startIP) && ipToLong(ip) <= ipToLong(endIP)){
                    des = description;
                }

            }

        } catch (IOException e) {
            e.printStackTrace();
        }
        return des;

    }
    private static long ipToLong(String ip) {
        long result = 0;
        try {
            byte[] octets = InetAddress.getByName(ip).getAddress();
            for (byte octet : octets) {
                result <<= 8;
                result |= octet & 0xff;
            }
        } catch (UnknownHostException e) {
            e.printStackTrace();
        }

        return result;
    }

}
