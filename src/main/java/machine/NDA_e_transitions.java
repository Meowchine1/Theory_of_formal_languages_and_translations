package machine;

import conditions.Condition;
import conditions.UsualCondition;
import conditions.ZeroCondition;
import values.Value;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.*;

public class NDA_e_transitions extends Machine {
    protected boolean canExecute = true;
    protected HashMap<Condition, HashMap<Value, LinkedHashSet<Condition>>> transitions;
    protected LinkedHashSet<Condition> actualConditions = new LinkedHashSet<>();
    public NDA_e_transitions(File fileInPath, File fileOutPath) {
        super(fileInPath, fileOutPath);
    }

    public void TranslateToKNDA(){
        KNDA knda = new KNDA();
        knda.FromEtoKNDA(this);

    }

    @Override
    public void readTxt() {

        try {
            Scanner scanner = new Scanner(fileInPath);
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

                            HashMap<Value, LinkedHashSet<Condition>> unit_transition = new HashMap<>();
                            for (Map.Entry<Value, String> innerEntry : entry.getValue().entrySet()) {

                                Value value = innerEntry.getKey();
                                String[] conditionsLine = innerEntry.getValue().replaceAll(" ", "").split(",");

                                LinkedHashSet<Condition> unitCondition = new LinkedHashSet<>();
                                for(int i  = 0; i < conditionsLine.length; i++ ){

                                    if (conditionsLine[i].equals("0")) {
                                        unitCondition.add(ZeroCondition.getInstance());
                                    } else {
                                        unitCondition.add(getConditionByName(conditionsLine[i]));

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
            System.out.print("      ");
            values.forEach(c -> {
                System.out.print(c.getValue() + "     ");
            });

            System.out.print("\n");
            for (Map.Entry<Condition, HashMap<Value, LinkedHashSet<Condition>>> entry : transitions.entrySet()) {
                Condition main_condition = entry.getKey();
                System.out.print(main_condition.getName() + "    ");
                for(Value value : values){
                    for (Map.Entry<Value, LinkedHashSet<Condition>> innerEntry : entry.getValue().entrySet()) {
                        if(innerEntry.getKey().equals(value)){
                            for(Condition elem : innerEntry.getValue()){
                                if(elem.equals(ZeroCondition.getInstance())){
                                    System.out.print("  0  ");
                                }
                                else{
                                System.out.print(elem.getName() + ",");
                            }
                        }
                            System.out.print(" ; ");
                    }
                }
            }
                System.out.println();
        }
    }

    @Override
    public void work(File wordFile) {
        readWord(wordFile);
        executeWord();
    }

    @Override
    protected void readWord(File wordFile) {
        try{
            Scanner scanner = new Scanner(wordFile);
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

    @Override
    protected void executeWord() {
        if(canExecute) {
                LinkedHashSet<Condition> newConditions = new LinkedHashSet<>();

            for(Condition condition: actualConditions) {
                CL(condition, newConditions);
            }
            actualConditions.addAll(newConditions);
                for (int j = 0; j < words.size(); j++) {

                    System.out.print("шаг" + (j+1) +" word= " + words.get(j) + " ---> ");

                    for(Condition condition: actualConditions){
                        if (condition.equals(ZeroCondition.getInstance())) {}
                        else{
                            transition(condition, newConditions);
                        }
                    }
                    actualConditions.addAll(newConditions);

                    for(Condition condition: actualConditions){
                        if (condition.equals(ZeroCondition.getInstance())) {}
                        else{
                            CL(condition, newConditions);
                        }
                    }
                    actualConditions.addAll(newConditions);
                    for(Condition elem : newConditions){
                        System.out.print(elem.getName() + ",");
                    }

                    System.out.println();
                    actualConditions = new LinkedHashSet<>(newConditions);
                    newConditions.clear();
                }

                if(actualConditions.stream().anyMatch(Condition::isEnded)){
                    System.out.print("Слово сработало успешно");
                }
                else{
                    System.out.print("Слово не привело к конечному состоянию. Оно не сработало");
                }
        }
        else{
            System.out.println("Error word command");
        }
    }

    protected void transition(Condition actualCondition, LinkedHashSet<Condition> newConditions){
        for (Map.Entry<Condition, HashMap<Value, LinkedHashSet<Condition>>>
                entry : transitions.entrySet()) {
            Condition main_condition = entry.getKey();
            if (main_condition.equals(actualCondition)) {

                for (Map.Entry<Value, LinkedHashSet<Condition>> innerEntry : entry.getValue().entrySet()) {
                    if (!innerEntry.getKey().equals(getValueByName("e"))) {
                        for (Condition cond :  innerEntry.getValue()) {
                            if(!cond.equals(ZeroCondition.getInstance()))
                            {
                                newConditions.add(cond);
                            }
                        }
                    }
                }
            }
        }
    }
    protected void CL(Condition condition, LinkedHashSet<Condition> newConditions){

        for (Map.Entry<Condition, HashMap<Value, LinkedHashSet<Condition>>>
                entry : transitions.entrySet()) {
            Condition main_condition = entry.getKey();
            if (main_condition.equals(condition)) {

                for (Map.Entry<Value, LinkedHashSet<Condition>> innerEntry : entry.getValue().entrySet()) {
                    if (innerEntry.getKey().equals(getValueByName("e"))) {
                        for (Condition cond :  innerEntry.getValue()) {
                            if(!cond.equals(ZeroCondition.getInstance()))
                            {
                                newConditions.add(cond);
                                CL(cond, newConditions);

                            }
                        }
                    }
                }
            }
        }
    }
    // для стартового значения сразу ищем замыкание по e
    // а только потом берем переходы по значению
    // для каждого перехода находим замыкание по е
}
