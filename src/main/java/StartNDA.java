import machine.Machine;

import java.io.File;

public class StartNDA {

    public static void main(String[] args) {

        FileReader reader = new FileReader(new File(Data.MAIN_DIRECTORY + Data.NDA_FILEIN),
                new File(Data.MAIN_DIRECTORY + Data.NDA_FILEOUT));

        Machine machine = reader.defineMachine();

        machine.readTxt();

        machine.print();
        machine.work(new File(Data.MAIN_DIRECTORY + Data.WORD));


    }

}
