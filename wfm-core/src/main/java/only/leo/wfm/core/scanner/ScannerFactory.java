package only.leo.wfm.core.scanner;

import only.leo.wfm.common.ProtocolType;
import only.leo.wfm.common.beans.IndexDirectory;
import only.leo.wfm.core.Pipeline;
import only.leo.wfm.core.service.FileDetectedService;
import only.leo.wfm.core.service.FileManagerService;
import only.leo.wfm.core.service.IDirectoryService;
import only.leo.wfm.core.service.IFileManagerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * @Author: LEO
 * @Date: 2021/10/19 14:00
 */
@Component
public class ScannerFactory {
    @Autowired
    private FileDetectedService fileDetectedService;
    @Autowired
    private Pipeline pipeline;
    @Autowired
    private IFileManagerService fileManagerService;
    @Autowired
    private IDirectoryService directoryService;
    public Scanner getScanner(ProtocolType protocolType, String path, IndexDirectory indexDirectory){
        Scanner scanner = null;
        switch (protocolType){
            case SMB:
                scanner = new SMBFileScanner(path,indexDirectory,pipeline,fileDetectedService,directoryService,fileManagerService);
                break;
            case FTP:
            case HTTP:
            default:
                scanner = new LocalFileScanner(path,indexDirectory,pipeline,fileDetectedService,directoryService,fileManagerService);
        }
        return scanner;
    }

}
