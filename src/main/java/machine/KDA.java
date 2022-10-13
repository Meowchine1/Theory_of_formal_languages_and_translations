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
import java.util.*;
import java.util.stream.Collectors;

public class KDA extends Machine {
    protected static Condition actualCondition;
    private HashMap<Condition, HashMap<Value, Condition>> transitions;

    public KDA(File fileInPath, File fileOutPath) {
        super(fileInPath, fileOutPath);
    }

    public KDA() {
    }


    public KDA translateKNDAtoKDA(KNDA knda) {

        if (knda.canExecute){
            transitions = new HashMap<>();

            LinkedHashSet<Condition> multipleConditions = new LinkedHashSet<>();
            MultipleCondition started = new MultipleCondition();
            started.addCondition(conditions.stream().filter(Condition::isStarted).findFirst().orElse(null));
            multipleConditions.add(started);
            values = values
                    .stream()
                    .filter(x -> !x.getValue().equals("e"))
                    .collect(Collectors.toCollection(ArrayList::new));

            conditions = new ArrayList<>();
            conditions.add(started);
               // while change exists
            while(multipleConditions.size() > 0){

                LinkedHashSet<Condition> newMultipleConditionsCollection = new LinkedHashSet<>();

                    for(Condition multipleCondition: multipleConditions){
                        HashMap<Value, Condition> unitTransition = new HashMap<>();

                        for(Value value: values) {

                            MultipleCondition newMultiplecondition = new MultipleCondition();

                            for (Condition condition : multipleCondition.getConditions()) {
                                knda.transitionForKDA(condition, value.getValue(), newMultiplecondition);
                            }
                               if( conditions.stream().anyMatch(x -> x.getConditions().equals(newMultiplecondition.getConditions()))){

                                  Condition existingCondition = conditions // get already existing condition
                                           .stream()
                                           .filter(x ->
                                                   x.getConditions()
                                                           .equals(newMultiplecondition.getConditions()))
                                          .findAny()
                                          .orElse(null);
                                   unitTransition.put(value, existingCondition);
                               }
                               else{
                                   newMultipleConditionsCollection.add(newMultiplecondition);// add new condition
                                   unitTransition.put(value, newMultiplecondition);
                                   conditions.add(newMultiplecondition);
                               }

                        }
                        transitions.put(multipleCondition, unitTransition);
                    }

                multipleConditions = newMultipleConditionsCollection;

            }
        }
        return this;
    }

    public void printTranslationResult(){
        System.out.print("             ");
        values.forEach(c -> {
            if(!c.getValue().equals("e")){
                System.out.print(c.getValue() + "     ");
            }
        });
        System.out.print("\n");

        for(Condition condition : conditions){
            System.out.print("[");
            for(Condition innerCondition : condition.getConditions()){
                if(innerCondition.isStarted()){
                    System.out.print(innerCondition.getName() + "^, " );
                }
                else if (innerCondition.isEnded()){
                    System.out.print(innerCondition.getName() + "*, ");
                }
                else {
                    System.out.print(innerCondition.getName() + ", ");

                }
            }
            System.out.print("]  ");

            for(Value value: values)
            {
                for (Map.Entry<Condition, HashMap<Value, Condition>> entry : transitions.entrySet()){
                    if(entry.getKey().equals(condition)){

                        for (Map.Entry<Value, Condition> innerEntry : entry.getValue().entrySet()){
                            if(innerEntry.getKey().equals(value)){

                                for(Condition elem : innerEntry.getValue().getConditions()){

                                        System.out.print(elem.getName() + ",");

                                }
                                System.out.print(" | ");
                            }
                        }
                    }
                }
            }
            System.out.println();
        }
    }
    protected void setActualCondition(Condition actualCondition) {
        KDA.actualCondition = actualCondition;
    }

    protected String getActualConditionString(){
        return "Actual condition is ==> " + actualCondition.getName();
    }
    @Override
    public void work(File wordFile){
        readWord(wordFile);
        executeWord();
    }
    @Override
    protected void readWord(File wordFile){
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
                       setActualCondition(ZeroCondition.getInstance());
                   }

               }
            }
        }
        catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }

    @Override
    protected void changeActualCondition(String word){
// если состояние 0  не по значению а по ссылке на нулевое состоние.
        Value value  = getValueByName(word);

        for (Map.Entry<Condition, HashMap<Value, Condition>> entry : transitions.entrySet()) {
            Condition main_condition = entry.getKey();
            if(actualCondition.equals(main_condition)){
                HashMap<Value, Condition> unit_transition = new HashMap<>();
                for (Map.Entry<Value, Condition> innerEntry : entry.getValue().entrySet()) {
                    if(innerEntry.getKey().equals(value)){
                        if(innerEntry.getValue().getClass().equals(ZeroCondition.class))
                        {

                        }
                        else{
                            setActualCondition(innerEntry.getValue());
                            break;
                        }

                    }
                }
                break;
            }

        }

    }

    @Override
    protected void executeWord(){
            if(actualCondition.equals(ZeroCondition.getInstance())){
                try {
                    FileWriter writer = new FileWriter(fileOutPath, true);
                    writer.write(getActualConditionString() + "\nFailed initialization\n ");
                    System.out.print(getActualConditionString() + "\nFailed initialization\n ");
                }
                catch (IOException e) {
                    throw new RuntimeException(e);
                }
            }
            else{

                try {
                    FileWriter writer = new FileWriter(fileOutPath, true);
                    for(String word: words){

                        changeActualCondition(word);
                        System.out.println("[" + word + "] ---> " + getActualConditionString());
                        writer.write(getActualConditionString());
                    }
                    if(actualCondition.isEnded()){
                        writer.write(getActualConditionString() + "\nGood job\n ");
                        System.out.print(getActualConditionString() + "\nGood job\n ");
                    }
                    else{
                        writer.write("\nFailed: last condition is not ended\n ");
                        System.out.print("\nFailed: last condition is not ended\n ");
                        setActualCondition(ZeroCondition.getInstance());
                    }

                }
                catch (IOException e) {
                    throw new RuntimeException(e);
                }
            }


    }

    @Override
    public void print(){
            System.out.print("      ");
            values.forEach(c -> {
                System.out.print(c.getValue() + "     ");
            });

            System.out.print("\n");

            for (Map.Entry<Condition, HashMap<Value, Condition>> entry : transitions.entrySet()) {
                Condition main_condition = entry.getKey();
                HashMap<Value, Condition> unit_transition = new HashMap<>();
                System.out.print(main_condition.getName() + "   :");

                for (Map.Entry<Value, Condition> innerEntry : entry.getValue().entrySet()) {
                    System.out.print(innerEntry.getValue().getName() + ";    ");
                }
                System.out.print("\n");
            }
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
                    if(valueDoesNotExists(values_line[i])){
                        Value value = new Value(values_line[i]);
                        values.add(value);
                    }
                    else {
                        setActualCondition(ZeroCondition.getInstance());
                        break;
                    }

                }

                HashMap<Condition, HashMap<Value, String>> intermediate_transition = new HashMap<>();
                HashMap<Value, String> unitTransition;
                for (int i = 0; i < condN; i++) { // обработка * ^
                    UsualCondition condition;

                    String unit = scanner.nextLine();
                    String[] line = unit.replaceAll(" ", "").split(";");
                    boolean isEnded_condition = line[0].contains("*");
                    boolean isStarted_condition = line[0].contains("^");
                    String conditionName = line[0].replaceAll(conditionNameRegex, "");
                    if(conditionDoesNotExists(conditionName)){
                        condition = new UsualCondition
                                (conditionName, isEnded_condition, isStarted_condition);
                        conditions.add(condition);
                    }
                    else{
                        setActualCondition(ZeroCondition.getInstance());
                        break;
                    }

                    unitTransition = new HashMap<>(); // тк во время считывания строк автомата не все состояния
                    // определены определяю промкжуточный словарь, где состояние храниться в виде строки

                    for (int j = 0; j < valuesN; j++) {  // для каждого значения состояние
                        Value value = values.get(j);     // ключ
                        int j_for_line = j + 1;
                        unitTransition.put(value, line[j_for_line]);
                    }
                    intermediate_transition.put(condition, unitTransition);
                }

                if(endedAndStartedConditionsExist()){
                    for (Map.Entry<Condition, HashMap<Value, String>> entry :
                            intermediate_transition.entrySet()) {
                        Condition main_condition = entry.getKey();

                        if(main_condition.isStarted()){
                            setActualCondition(main_condition);
                        }

                        HashMap<Value, Condition> unit_transition = new HashMap<>();
                        for (Map.Entry<Value, String> innerEntry : entry.getValue().entrySet()) {
                            Value value = innerEntry.getKey();
                            String condition = innerEntry.getValue();
                            if (condition.equals("0")) {
                                unit_transition.put(value, ZeroCondition.getInstance());
                            } else {
                                Condition unitCondition = getConditionByName(condition);
                                unit_transition.put(value,unitCondition);
                            }

                        }
                        transitions.put(main_condition, unit_transition);
                    }
                }
                else{
                    setActualCondition(ZeroCondition.getInstance());
                    break;
                }


            }
        }
        catch (FileNotFoundException e) {
            e.printStackTrace();
        }

    }

}
