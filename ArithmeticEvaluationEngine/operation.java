package ArithmeticEvaluationEngine;

import java.util.Stack;

@SuppressWarnings("unchecked")
class operation {
    int Operand1, Operand2;     //the adderss for data on operand stack
    char opr, priority;
    Class cls;

    operation(Class cls) {
        this.cls = cls;
    }

    operation(operation Org) {
        this.opr = Org.opr;
        this.priority = Org.priority;
        this.Operand1 = Org.Operand1;
        this.Operand2 = Org.Operand2;
        this.cls = Org.cls;
    }

    void setOperation(char operation, int Operand1, int Operand2) {
        setOperation(operation,Operand1,Operand2,'a');
    }

    void setOperation(char operation, int Operand1, int Operand2, char priority) {
        opr=operation;
        this.Operand1=Operand1;
        this.Operand2=Operand2;
        if(priority=='a')
            if(opr=='^')
                this.priority='T';
            else if(opr=='*' || opr=='/' || opr=='%')
                this.priority='H';
            else //if opr=='+' || opr=='-'
                this.priority='L';
        else    
            this.priority=priority;
        // if(T.trace)
        //     System.out.print(this.priority);
    }

    void calculate(Stack S) {
        Number Ans = null;         //temporary variable to hold answer
        if( S.get(Operand1).getClass().getSimpleName().equals("String") ) {
            //Generate null variable exception
			//System.out.println("variable: "+S.get(Operand1)+" has null value");
        }
        if( S.get(Operand1).getClass().getSimpleName().equals("String") ) {
            //Generate null variable exception
			//System.out.println("variable: "+S.get(Operand2)+" has null value");
        }
        if(S.get(Operand1) instanceof evaluate) {
            evaluate e=(evaluate)S.get(Operand1); 
            e.calc();
            S.set(Operand1,e.Value);
        }
        if(S.get(Operand2) instanceof evaluate) {
            evaluate e=(evaluate)S.get(Operand2); 
            e.calc();
            S.set(Operand2,e.Value);
        }
        switch(opr) {    // get both operand from stack and perfrom given operation
            case '+':   Ans = add(S, Operand1, Operand2); break;
            case '-':   Ans = subtract(S, Operand1, Operand2); break;
            case '*':   Ans = multiply(S, Operand1, Operand2); break;
            case '/':   Ans = divide(S, Operand1, Operand2); break;
            case '^':   Ans = power(S, Operand1, Operand2); break;
            case '%':   Ans = modulo(S, Operand1, Operand2); break;
        }
        
            // T x = new T();
            // String dt=x.getClass().getSimpleName();
            // if(dt.equals("Integer"))    S.set(Operand1, (int)Ans);
            // else if(dt.equals("Float"))    S.set(Operand1, (float)Ans);
            // else if(dt.equals("Short"))    S.set(Operand1, (short)Ans);
            // else if(dt.equals("Byte"))    S.set(Operand1, (byte)Ans);
        S.set(Operand1,Ans);    // update the ans to the address of 1st operand
        S.remove(Operand2);     // remove the second operand from the stack
        
        if(T.trace) System.out.println(S);  //for debuging
    }

    void drop() { //if an oerand is removed from the stack, this will drop the address of the operand by 1
        Operand1 -= 1;
        Operand2 -= 1;
    }

    private Number add(Stack S,int idx1, int idx2) {
        Number A= null;
        if( cls == Integer.class )
            A = (Integer)S.get(idx1) + (Integer)S.get(idx2);
        else if( cls == Double.class )
            A = (Double)S.get(idx1) + (Double)S.get(idx2);
        else if( cls == Float.class )
            A = (Float)S.get(idx1) + (Float)S.get(idx2);
        else if( cls == Short.class )
            A = (Short)S.get(idx1) + (Short)S.get(idx2);
        else if( cls == Byte.class )
            A = (Byte)S.get(idx1) + (Byte)S.get(idx2);
        return A;
    }

    private Number subtract(Stack S,int idx1, int idx2) {
        Number A= null;
        if( cls == Integer.class )
            A = (Integer)S.get(idx1) - (Integer)S.get(idx2);
        else if( cls == Double.class )
            A = (Double)S.get(idx1) - (Double)S.get(idx2);
        else if( cls == Float.class )
            A = (Float)S.get(idx1) - (Float)S.get(idx2);
        else if( cls == Short.class )
            A = (Short)S.get(idx1) - (Short)S.get(idx2);
        else if( cls == Byte.class )
            A = (Byte)S.get(idx1) - (Byte)S.get(idx2);
        return A;
    }

    private Number multiply(Stack S,int idx1, int idx2) {
        Number A= null;
        if( cls == Integer.class )
            A = (Integer)S.get(idx1) * (Integer)S.get(idx2);
        else if( cls == Double.class )
            A = (Double)S.get(idx1) * (Double)S.get(idx2);
        else if( cls == Float.class )
            A = (Float)S.get(idx1) * (Float)S.get(idx2);
        else if( cls == Short.class )
            A = (Short)S.get(idx1) * (Short)S.get(idx2);
        else if( cls == Byte.class )
            A = (Byte)S.get(idx1) * (Byte)S.get(idx2);
        return A;
    }

    private Number divide(Stack S,int idx1, int idx2) {
        Number A= null;
        if( cls == Integer.class )
            A = (Integer)S.get(idx1) / (Integer)S.get(idx2);
        else if( cls == Double.class )
            A = (Double)S.get(idx1) / (Double)S.get(idx2);
        else if( cls == Float.class )
            A = (Float)S.get(idx1) / (Float)S.get(idx2);
        else if( cls == Short.class )
            A = (Short)S.get(idx1) / (Short)S.get(idx2);
        else if( cls == Byte.class )
            A = (Byte)S.get(idx1) / (Byte)S.get(idx2);
        return A;
    }

    private Number modulo(Stack S,int idx1, int idx2) {
        Number A= null;
        if( cls == Integer.class )
            A = (Integer)S.get(idx1) % (Integer)S.get(idx2);
        else if( cls == Double.class )
            A = (Double)S.get(idx1) % (Double)S.get(idx2);
        else if( cls == Float.class )
            A = (Float)S.get(idx1) % (Float)S.get(idx2);
        else if( cls == Short.class )
            A = (Short)S.get(idx1) % (Short)S.get(idx2);
        else if( cls == Byte.class )
            A = (Byte)S.get(idx1) % (Byte)S.get(idx2);
        return A;
    }

    private Number power(Stack S,int idx1, int idx2) {
        Number A= null;
        if( cls == Integer.class )
            A =  (int)Math.pow((Integer)S.get(idx1), (Integer)S.get(idx2));
        else if( cls == Double.class )
            A = Math.pow((Double)S.get(idx1), (Double)S.get(idx2));
        else if( cls == Float.class )
            A = (float)Math.pow((Float)S.get(idx1), (Float)S.get(idx2));
        else if( cls == Short.class )
            A = (short)Math.pow((Short)S.get(idx1), (Short)S.get(idx2));
        else if( cls == Byte.class )
            A = (byte)Math.pow((Byte)S.get(idx1), (Byte)S.get(idx2));
        return A;
    }
}