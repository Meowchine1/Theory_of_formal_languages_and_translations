package machine;

import conditions.Condition;
import conditions.MultipleCondition;
import conditions.UsualCondition;
import conditions.ZeroCondition;
import values.Value;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;
// несколько начальных состояний распараллеливание сразу
public class KNDA extends Machine {

    private boolean canExecute = true;

    private ArrayList<Condition> actualConditions = new ArrayList<>();
    private HashMap<Condition, HashMap<Value, Condition>> transitions; //вынести в базовый класс


    public KNDA(String fileInPath, String fileOutPath) {
        super(fileInPath, fileOutPath);
    }

    @Override
    public Value getValueByName(String name) {
        return super.getValueByName(name);
    }

    @Override
    public Condition getConditionByName(String name) {
        return super.getConditionByName(name);
    }

    @Override
    protected boolean valueDoesNotExists(String valueName) {
        return super.valueDoesNotExists(valueName);
    }

    @Override
    protected boolean conditionDoesNotExists(String conditionName) {
        return super.conditionDoesNotExists(conditionName);
    }

    @Override
    protected boolean endedAndStartedConditionsExist() {
        return super.endedAndStartedConditionsExist();
    }

    @Override
    public void readTxt() {

        try {
            Scanner scanner = new Scanner(new File(fileInPath));
            String conditionNameRegex = "[*,^]";
            while (scanner.hasNextLine()) {
                String[] numbers = scanner.nextLine().replaceAll(" ", "").split(";");
                int valuesN = Integer.parseInt(numbers[0]);
                int condN = Integer.parseInt(numbers[1]);

                values = new ArrayList<>(valuesN);
                conditions = new ArrayList<>(condN);
                transitions = new HashMap<>();
                String[] values_line = scanner.nextLine().replaceAll(" ", "").split(";");
                for (int i = 0; i < valuesN; i++) {
                    if (valueDoesNotExists(values_line[i])) {
                        Value value = new Value(values_line[i]);
                        values.add(value);
                    } else {
                        canExecute = false;
                        break;
                    }

                }

                if (canExecute) {
                    HashMap<Condition, HashMap<Value, String>> intermediate_transition = new HashMap<>();
                    HashMap<Value, String> unitTransition;
                    for (int i = 0; i < condN; i++) { // обработка * ^
                        UsualCondition condition;

                        String unit = scanner.nextLine();
                        String[] line = unit.replaceAll(" ", "").split(";");
                        boolean isEnded_condition = line[0].contains("*");
                        boolean isStarted_condition = line[0].contains("^");
                        String conditionName = line[0].replaceAll(conditionNameRegex, "");
                        if (conditionDoesNotExists(conditionName)) {
                            condition = new UsualCondition
                                    (conditionName, isEnded_condition, isStarted_condition);
                            conditions.add(condition);
                        } else {
                            canExecute = false;
                            break;
                        }

                        if(canExecute)
                        {
                            unitTransition = new HashMap<>(); // тк во время считывания строк автомата не все состояния
                            // определены определяю промкжуточный словарь, где набор состояние храниться в виде строки

                            for (int j = 0; j < valuesN; j++) {  // для каждого значения состояние
                                Value value = values.get(j);     // ключ
                                int j_for_line = j + 1;
                                unitTransition.put(value, line[j_for_line]);
                            }
                            intermediate_transition.put(condition, unitTransition);
                        }
                        else{
                            break;
                        }


                    }

                    if (endedAndStartedConditionsExist() & canExecute) {
                        for (Map.Entry<Condition, HashMap<Value, String>> entry :
                                intermediate_transition.entrySet()) {
                            Condition main_condition = entry.getKey();

                            if (main_condition.isStarted()) {
                                actualConditions.add(main_condition);
                            }

                            HashMap<Value, Condition> unit_transition = new HashMap<>();
                            for (Map.Entry<Value, String> innerEntry : entry.getValue().entrySet()) {

                                Value value = innerEntry.getKey();
                                String[] conditionsLine = innerEntry.getValue().replaceAll(" ", "").split(",");

                                MultipleCondition unitCondition = new MultipleCondition();
                                for(int i  = 0; i < conditionsLine.length; i++ ){

                                    if (conditionsLine[i].equals("0")) {
                                        unit_transition.put(value, ZeroCondition.getInstance());
                                    } else {
                                         unitCondition.AddCondition(getConditionByName(conditionsLine[i]));

                                    }
                                }
                                unit_transition.put(value, unitCondition);

                            }
                            transitions.put(main_condition, unit_transition);
                        }
                    } else {
                        canExecute = false;
                        break;
                    }
                }
            }
        }
        catch(FileNotFoundException e){
                e.printStackTrace();
            }
    }

    @Override
    public void print() {
        try(FileWriter writer = new FileWriter(fileOutPath, false)) {
            writer.write("      ");
            System.out.print("      ");
            values.forEach(c -> {
                try {
                    writer.write(c.getValue() + "     ");
                    System.out.print(c.getValue() + "     ");
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            });

            System.out.print("\n");
            writer.write("\n");

            for (Map.Entry<Condition, HashMap<Value, Condition>> entry : transitions.entrySet()) {
                Condition main_condition = entry.getKey();
                HashMap<Value, Condition> unit_transition = new HashMap<>();
                System.out.print(main_condition.getName() + "    ");
                writer.write(main_condition.getName() + "     ");

                for(Value value : values){
                    for (Map.Entry<Value, Condition> innerEntry : entry.getValue().entrySet()) {
                        //  writer.write(innerEntry.getValue().getName() + ";    ");
                        if(innerEntry.getKey().equals(value)){
                            MultipleCondition conditions = (MultipleCondition) innerEntry.getValue(); // cast
                            for(Condition elem : conditions){
                                writer.write(elem.getName() + ",");
                                System.out.print(elem.getName() + ",");
                            }
                            writer.write(" ; ");
                            System.out.print(" ; ");
                        }

                    }

                }

                writer.write("\n");
                System.out.println();
            }

        }
        catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    protected void readWord(String wordFile) {
        try{
            Scanner scanner = new Scanner(new File(wordFile));
            int n = Integer.parseInt(scanner.nextLine());
            words = new ArrayList<>(n);
            while (scanner.hasNextLine()){
                String[] line = scanner.nextLine().replaceAll(" ", "").split(";");
                for(String word: line ){
                    if(values.stream().anyMatch(v -> v.getValue().equals(word))){
                        words.add(word);
                    }
                    else{
                        canExecute = false;
                    }

                }
            }
        }
        catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }

    private void getActualCond(int i , Condition actual ){
        while(i!= words.size()) {

            for (Map.Entry<Condition, HashMap<Value, Condition>> entry : transitions.entrySet()) {
                Condition main_condition = entry.getKey();
                if(main_condition.equals(actual)){
                    for (Value value : values) {
                        for (Map.Entry<Value, Condition> innerEntry : entry.getValue().entrySet()) {

                            if (innerEntry.getKey().equals(value)) {
                                MultipleCondition conditions = (MultipleCondition) innerEntry.getValue(); // cast
                                for (Condition elem : conditions) {
                                    System.out.print(elem.getName() + ",");
                                    getActualCond(i++, elem);
                                }

                                System.out.print(" ; ");
                            }

                        }

                    }
                }
            }


        }
    }

    @Override
    protected void executeWord() {
        if(canExecute){
            ArrayList<Condition> results = new ArrayList<>();


            String line = "";
            for(int j = 0; j < words.size(); j++){

                for(int i = 0 ; i < actualConditions.size(); i++){
                    if( actualConditions.get(i).equals(ZeroCondition.getInstance())){

                        try {
                            FileWriter writer = new FileWriter(fileOutPath, true);
                            writer.write( "\nzero\n ");
                            System.out.print("\nzero\n ");
                        }
                        catch (IOException e) {
                            throw new RuntimeException(e);
                        }

                    }
                    else {
                        getActualCond(0, actualConditions.get(i));


                    }

                }

            }

        }
        else{
            System.out.println("Error word command");
        }
    }

    @Override
    protected void changeActualCondition(String word) {
        super.changeActualCondition(word);
    }

    @Override
    public void work(String wordFile) {
        readWord(wordFile);
        executeWord();
    }

}
