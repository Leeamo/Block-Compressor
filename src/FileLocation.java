import java.io.Serializable;

public class FileLocation implements Serializable {

    
    String fileName; 
    
    int offSet; // multiple of BlkSize;

    public FileLocation(String fileName, int offSet) {
        this.fileName = fileName;
        this.offSet = offSet;
    }
}
