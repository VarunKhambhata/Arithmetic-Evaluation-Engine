package ArithmeticEvaluationEngine;

import java.util.ArrayList;

class AEEvarTable {
    ArrayList<AEEvar> Variables = new ArrayList<AEEvar>();

    public void setValue(String N, Number V) {
        for(AEEvar o: Variables)
            if(o.name.equals(N))
                o.value=V;
    }

    public void chechAndADD(String V) {
        if(Variables.size()==0)
            Variables.add(new AEEvar(V));  
        else
            for(int i=0; i < Variables.size(); i++)
                if(!Variables.get(i).name.equals(V)) {
                    Variables.add(new AEEvar(V));
                    break;
                }
    }

    public Number get(String name) {
        Number n = null;
        for(AEEvar o: Variables)
            if(o.name.equals(name))
                n = o.value;
        return n;
    }
}