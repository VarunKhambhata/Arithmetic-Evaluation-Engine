package ArithmeticEvaluationEngine;

import java.util.Scanner;
import java.util.ArrayList;

//A port that is used by source class to use the whole engine and get value instanteously
@SuppressWarnings("unchecked")
public class ArithmeticEvaluationEngine {   
    private evaluate e;
    private AEEvarTable varList;

    public ArithmeticEvaluationEngine(String Equation, Class c) throws Exception {
        varList = new AEEvarTable();
        e = new evaluate(Equation, c, varList);
    }

    public void setVariable(String s, Number n) {
        varList.setValue(s,n);
    }
    
    public ArrayList<String> getVariables() {
        ArrayList<String> ret = new ArrayList<String>();
        for(AEEvar o: varList.Variables)
            ret.add(o.name);
        return ret;
    }

    public Number Value() {
        e.calc();
        return e.Value;
    }

    public static Number calculate(String Equation) {
        try {
            AEEvarTable varList = new AEEvarTable();
            evaluate e = new evaluate(Equation, Double.class, varList);
            e.calc();
            return e.Value;	
        }
        catch(Exception e) {
            return null;
        }
    }

    public static void main(String arg[]) {
        Scanner sc = new Scanner(System.in);
        if(arg.length > 0)
            if(arg[0].equals("-trace"))
                T.trace=true;
        System.out.print("Equation: ");
        try { System.out.println( "ans: "+ ArithmeticEvaluationEngine.calculate(sc.next()));  }
        catch(Exception e) { System.out.println(e); }
    }
}