import machine.KNDA;
import machine.Machine;
import machine.NDA_e_transitions;

import java.io.File;

public class Translation {

    public static void main(String[] args) {

        NDA_e_transitions enda = new NDA_e_transitions(new File(Data.MAIN_DIRECTORY + Data.E_FILEN), new File(Data.MAIN_DIRECTORY + Data.E_FILEOUT));
        enda.readTxt();
        enda.print();

        System.out.println("Translation from ENDA to NDA");
        KNDA knda =  enda.translateToKNDA();
        knda.print();

        System.out.println("Translation from NDA to KDA");
        knda.translateToKDA().printTranslationResult();

    }

}
