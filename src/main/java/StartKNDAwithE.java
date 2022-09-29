import machine.Machine;

import java.io.File;
import java.io.IOException;

public class StartKNDAwithE {

    public static void main(String[] args) throws IOException {


        FileReader reader = new FileReader(new File(Data.MAIN_DIRECTORY + Data.E_FILEN), new File(Data.MAIN_DIRECTORY + Data.E_FILEOUT));
        Machine machine = reader.defineMachine();
        machine.readTxt();
        machine.print();
        machine.work(new File(Data.MAIN_DIRECTORY + Data.WORD));

    }

}
