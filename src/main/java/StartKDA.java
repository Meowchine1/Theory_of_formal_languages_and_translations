import machine.Machine;

import java.io.File;

public class StartKDA {

    public static void main(String[] args) {

        FileReader reader = new FileReader(new File(Data.MAIN_DIRECTORY + Data.KDA_FILEIN), new File(Data.MAIN_DIRECTORY + Data.KDA_FILEOUT));

        Machine machine = reader.defineMachine();

        machine.readTxt();

        machine.print();
        machine.work(new File(Data.MAIN_DIRECTORY + Data.WORD));

    }

}
