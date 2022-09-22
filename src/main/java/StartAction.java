import conditions.Condition;
import conditions.UsualCondition;
import machine.Machine;
import values.Value;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class StartAction {

    public static void main(String[] args) {
        /*FileReader reader = new FileReader("C:/Users/katev/IdeaProjects/Theory_of_formal_languages_and_translations/src/main/resources/machine/kda.txt",
                "C:/Users/katev/IdeaProjects/Theory_of_formal_languages_and_translations/src/main/resources/fileOut");
        Machine machine = reader.defineMachine();
        machine.readTxt();
        machine.print();
        machine.work("C:/Users/katev/IdeaProjects/Theory_of_formal_languages_and_translations/src/main/resources/word/slovo.txt");*/
        
        
        FileReader reader = new FileReader("C:/Users/Воронина/IdeaProjects/Theory_of_formal_languages_and_translations/src/main/resources/machine/NDA.txt",
                "C:/Users/Воронина/IdeaProjects/Theory_of_formal_languages_and_translations/src/main/resources/fileOut");

        Machine machine = reader.defineMachine();

        machine.readTxt();

        machine.print();

    }

}
