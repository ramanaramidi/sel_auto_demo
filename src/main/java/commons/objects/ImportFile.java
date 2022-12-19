package commons.objects;

import commons.constants.Constants;

public class ImportFile {
    public String importId;
    public String processId;
    public String importStatus;
    public String importFile;
    public String importFilePath;

    public ImportFile(String importId,String fileName){
        this.importId = importId;
        this.importFilePath = Constants.SECTOR_IMPORT_DATA;
        this.importFile = fileName;
    }
    public ImportFile(String importId,String fileName,String path){
        this.importId = importId;
        this.importFilePath = path;
        this.importFile = fileName;
    }

}
