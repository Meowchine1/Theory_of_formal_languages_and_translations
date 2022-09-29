import java.io.File;
import java.io.IOException;

public class Data {


    public static final String KDA_FILEIN =  "/src/main/resources/machine/kda.txt";
    public static final String KDA_FILEOUT = "/src/main/resources/fileOut";
    public static final String NDA_FILEIN =  "/src/main/resources/machine/NDA.txt";
    public static final String NDA_FILEOUT = "/src/main/resources/fileOut";
    public static final String E_FILEN = "/src/main/resources/machine/e_transition.txt";

    public static final String E_FILEOUT = "/src/main/resources/fileOut";

    public static final String WORD = "/src/main/resources/word/slovo.txt";
    private static File directory = new File ("");
    public static final String MAIN_DIRECTORY;

    static {
        try {
            MAIN_DIRECTORY = directory.getCanonicalPath();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

}
