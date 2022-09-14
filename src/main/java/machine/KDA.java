package machine;

import conditions.Condition;
import conditions.UsualCondition;
import conditions.ZeroCondition;
import values.Value;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

public class KDA extends Machine {
    
    private HashMap<Condition, HashMap<Value, Condition>> transitions;

    public KDA(String filePath) {
        super(filePath);
    }

    private static boolean valueDoesNotExists(String valueName){
        if(!values.isEmpty())
        {
            for (Value entry:values) {
                if(entry.getValue().equals(valueName)){
                    return false;

                }
            }
        }
        return true;
    }
    
    private boolean conditionDoesNotExists(String conditionName){// не может быть нулевых

        if(!conditions.isEmpty()){
            for (Condition entry:conditions) {

                if(entry.getName().equals(conditionName)){
                    return false;
                }
            }
        }
        return true;
    }
    // если нет ни одного завершающего состоояния
    //  если нет ни одного начального или начального больше одного
    // если название повторилось (для значений и состояний)
    @Override
    public void readTxt() {
        try {
            Scanner scanner = new Scanner(new File(filePath));
            String conditionNameRegex = "[*,^]";
            while (scanner.hasNextLine()) {
                String[] numbers = scanner.nextLine().replaceAll(" ", "").split(";");
                int valuesN = Integer.parseInt(numbers[0]);
                int condN = Integer.parseInt(numbers[1]);

                values = new ArrayList<>();
                conditions = new ArrayList<>();
                
                String[] values_line = scanner.nextLine().replaceAll(" ", "").split(";");
                for (int i = 0; i < valuesN; i++) {
                    if(valueDoesNotExists(values_line[i])){
                        Value value = new Value(values_line[i]);
                        values.set(i, value);
                    }
                    else {
                        setActualCondition(new ZeroCondition());
                    }

                }

                HashMap<Condition, HashMap<Value, String>> intermediate_transition = new HashMap<>();
                HashMap<Value, String> unitTransition;
                for (int i = 0; i < condN; i++) { // обработка * ^
                    String unit = scanner.nextLine();

                    String[] line = unit.replaceAll(" ", "").split(";");
                    boolean isEnded_condition = line[0].contains("*");
                    boolean isStarted_condition = line[0].contains("^");
                    String conditionName = line[0].replaceAll(conditionNameRegex, "");
                    if(conditionDoesNotExists(conditionName)){
                        UsualCondition condition = new UsualCondition
                                (conditionName, isEnded_condition, isStarted_condition);
                        conditions.set(i,condition);
                    }
                    else{
                        setActualCondition(new ZeroCondition());
                    }


                    unitTransition = new HashMap<>(); // тк во время считывания строк автомата не все состояния 
                    // определены определяю промкжуточный словарь, где состояние храниться в виде строки

                    for (int j = 0; j < valuesN; j++) {
                        Value value = values.get(j);     // ключ
                        int j_for_line = j + 1;
                        unitTransition.put(value, line[j_for_line]);
                    }
                    intermediate_transition.put(condition, unitTransition);
                }
                
                for (Map.Entry<Condition, HashMap<Value, String>> entry : 
                        intermediate_transition.entrySet()) {
                    Condition main_condition = entry.getKey();
                    HashMap<Value, Condition> unit_transition = new HashMap<>();
                    for (Map.Entry<Value, String> innerEntry : entry.getValue().entrySet()) {
                        Value value = innerEntry.getKey();
                        String condition = innerEntry.getValue();

                        if (condition.equals("0")) {
                            unit_transition.put(value, new ZeroCondition("0"));
                        } else {
                            boolean isEnded_condition = condition.contains("*");
                            boolean isStarted_condition = condition.contains("^");
                            String conditionName = condition.replaceAll(conditionNameRegex, "");
                            Condition unitCondition = new UsualCondition
                                    (conditionName, isEnded_condition, isStarted_condition);
                            unit_transition.put(value,unitCondition);
                        }
                        
                    }
                    
                    transitions.put(main_condition, unit_transition);
                }
            }
        }
        catch (FileNotFoundException e) {
            e.printStackTrace();
        }

    }
}
