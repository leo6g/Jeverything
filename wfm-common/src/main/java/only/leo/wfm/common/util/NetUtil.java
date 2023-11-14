package only.leo.wfm.common.util;

import java.net.InetAddress;
import java.net.NetworkInterface;
import java.net.SocketException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Enumeration;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * @Author: LEO
 * @Date: 2021/9/8 11:56
 */
public class NetUtil {
    public static String[] getIntranetIps() {
        String localip = null;
        String netip = null;
        Set<String> set = new HashSet<>();
        try {
            Enumeration netInterfaces = NetworkInterface.getNetworkInterfaces();
            InetAddress ip = null;
            while (netInterfaces.hasMoreElements()) {
                NetworkInterface ni = (NetworkInterface) netInterfaces.nextElement();
                Enumeration address = ni.getInetAddresses();
                while (address.hasMoreElements()) {
                    ip = (InetAddress) address.nextElement();
                    if (ip.getHostAddress().indexOf(":") == -1) {
                        netip = ip.getHostAddress();
                        set.add(netip);
                    }
                }
            }
        } catch (SocketException e) {
            e.printStackTrace();
        }
        return set.toArray(new String[set.size()]);
    }

    public static void main(String[] args) {
        System.out.println(getIntranetIp());
    }
    public static String getIntranetIp() {
        String localip = "127.0.0.1";
        try {
            Enumeration netInterfaces = NetworkInterface.getNetworkInterfaces();
            while (netInterfaces.hasMoreElements()) {
                NetworkInterface ni = (NetworkInterface) netInterfaces.nextElement();
                Enumeration address = ni.getInetAddresses();
                while (address.hasMoreElements()) {
                    InetAddress ip = (InetAddress) address.nextElement();
                    if (ip.isLoopbackAddress() && ip.getHostAddress().indexOf(":") == -1) {
                        localip = ip.getHostAddress();
                    }
                }
            }
        } catch (SocketException e) {
            e.printStackTrace();
        }
        return  localip;
    }
}
