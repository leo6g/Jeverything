package only.leo.wfm.common.util;

import only.leo.wfm.common.PlatformSystem;

import java.awt.*;
import java.io.File;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;

public class SystemUtil {
    public static PlatformSystem getPlatformSystem(){
        String osName = System.getProperty("os.name");
        PlatformSystem platformSystem = PlatformSystem.LINUX;
        if(osName.indexOf(PlatformSystem.WINDOWS.getFlag())!=-1) return PlatformSystem.WINDOWS;
        return platformSystem;
    }
    public static void openDIR(String path){
        switch (getPlatformSystem()){
            case WINDOWS:
                if(Desktop.isDesktopSupported()){
                    String cmd = "explorer /select, \""+path+"\"";
                    CMDExcutor._instance().exec(cmd);
                }
                break;
            case LINUX:
        }
    }
    public static void openFile(String path){
        switch (getPlatformSystem()){
            case WINDOWS:
                if(Desktop.isDesktopSupported()){
                    try {
                        Desktop.getDesktop().open(new File(path));
                    } catch (IOException e) {
                    }
                }
                break;
            case LINUX:
        }
    }
    public static void browseURI(String uriStr){
        switch (getPlatformSystem()){
            case WINDOWS:
                if(Desktop.isDesktopSupported()&&Desktop.getDesktop().isSupported(Desktop.Action.BROWSE)){
                    try {
                        URI uri = new URI(uriStr);
                        Desktop.getDesktop().browse(uri);
                    } catch (IOException | URISyntaxException e) {
                    }
                }
                break;
            case LINUX:
        }
    }
}
