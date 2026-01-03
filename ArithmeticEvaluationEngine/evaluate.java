package ArithmeticEvaluationEngine;
import java.util.Stack;	
import java.util.ArrayList;

@SuppressWarnings("unchecked")
class evaluate {
    Stack eqS = new Stack();
    Stack eqS_clone;
    StringBuilder eq;           //the original equation
    int opSize, order[];
    operation OP[];
    Class TYPEclass;
    Number Value;
    AEEvarTable varList;
    ArrayList<Integer> varLocOnEqStack;

    evaluate(String equation, Class c, AEEvarTable vList) throws Exception {
        // System.out.println("new equation created");
        eq = new StringBuilder().append(equation);
        TYPEclass = c;
        varList = vList;
        varLocOnEqStack = new ArrayList<Integer>();
        buildStack();
    }  

    static char getClosedBracketOf(char x) {
        char c=' ';
        switch(x)
        {
            case '(':   c=')'; break;
            case '[':   c=']'; break;
            case '{':   c='}'; break;
        }
        return c;
    }

    void buildStack() throws Exception {
        char c;
        StringBuilder pack = new StringBuilder(15);
        StringBuilder operationPCK = new StringBuilder(100);
        boolean cutDecimal=false;
        for(int index=0; index<eq.length(); index++) {
            c = eq.charAt(index);            
            // System.out.println("c: "+c);
            if( c=='(' || c=='{' || c=='[' ) {   //sub equation start
                int I=index, depth=0;
                boolean loop=true;
                try {
                    while(loop) {    //loop till the curret sub equation ends
                        if( eq.charAt(I) == getClosedBracketOf(c) ) {
                            if( depth==0 )  loop=false;
                            else {   depth--; I++;   }
                        }
                        else {
                            I++;
                            if(eq.charAt(I) == c )
                                depth++;
                        }
                    }
                    // System.out.println("subequation");
                    eqS.push( new evaluate(eq.substring(index+1, I),TYPEclass, varList)    ); //add sub-equation evaluate object to the operand stack
                    index=I;
                }
                catch(StringIndexOutOfBoundsException e){
                    throw new Exception("Improper Brackets Formation between character position: "+index+" and "+I);
                }
            }
            else if(!(c=='+' || c=='*' || c=='/' || c=='-' || c=='^'||c=='%')) { //oprand
                //if the data type i sinteger,short,byte and a decimal occurs in oprand, then skip the decimal value------------
                  if(c == '.' && (TYPEclass == Integer.class || TYPEclass == Short.class || TYPEclass == Byte.class))
                    cutDecimal=true;
                //-------------------------------------------------------------------------------------------------------------
                
                //check if brackets are closed properly
                if( c == ']' || c == '}' || c ==')') {
                    throw new Exception("Unrequired Bracket at character position: "+index);
                }
                pack.append(c);   
            }
            
            else {   //Operator occurs     
                //cut the decimal value-----------------------------
                    if(cutDecimal) {
                        cutDecimal=false;   
                        for(int i=0;i<pack.length();i++)
                            if(pack.charAt(i)=='.') {
                                pack.setLength(i);
                                break;
                            }
                    }
                //---------------------------------------------------

                //if negative number (-n) is written wihtout brackets. example:- 5*-2 || 4--1
				if(c=='-' &&  (eq.charAt(index-1)=='+' || eq.charAt(index-1)=='*' || eq.charAt(index-1)=='/' || eq.charAt(index-1)=='-' || eq.charAt(index-1)=='^'||eq.charAt(index-1)=='%')) {
					try {   parseAndPushTO_eqS(pack.toString());    } //add oprand to stack
					catch(Exception e) {   throw new Exception(pack+" is not an operand");    }
                    
                    pack.delete(0,pack.length());  //clear string buffer
                    pack.append(c);	
				}
				else { //operator			
					try {   parseAndPushTO_eqS(pack.toString());    }  //add oprand to stack
					catch(Exception e) {   throw new Exception(pack+" is not an operand");    }
                    
					pack.delete(0,pack.length()); //clear string buffer
					operationPCK.append(c);
				}
            }
        }
        try {
            //cut the decimal value-----------------------------
                if(cutDecimal) {
                    cutDecimal=false;   
                    for(int i=0;i<pack.length();i++)
                        if(pack.charAt(i)=='.')
                        {   pack.setLength(i);break;    }
                }
            //---------------------------------------------------
            parseAndPushTO_eqS(pack.toString());   //add last oprand to stack	
        }	 			
		catch(Exception e) {
            throw new Exception(pack+" is not an operand");
        }


        opSize=eqS.size()-1;       //for every n operand, there is n-1 operaator
        //if(T.trace) System.out.println("op size: "+opSize);
        OP = new operation[opSize]; 
        for(int i=0; i < opSize; i++)   //initialize the OP stack
            OP[i] = new operation(TYPEclass);
        for(int i=0; i < operationPCK.length(); i++)   //set OP stack
            OP[i].setOperation(operationPCK.charAt(i), i, i+1);


        order = new int[opSize];

        //********************** set order for execution ************************************
            int x = 0;
            // TOP priority pass-------------------------------------
                for(int i = 0; i < opSize; i++)
                    if( OP[i].priority == 'T' ) {
                        order[x] = i;
                        x++;
                    }
            //-------------------------------------------------------
            // HIGH priority pass-------------------------------------
                    for(int i = 0; i < opSize; i++)
                        if( OP[i].priority == 'H' ) {
                            order[x] = i;
                            x++;
                        }
            //--------------------------------------------------------
            // LOW priority pass-------------------------------------
                    for(int i = 0; i <opSize; i++)
                        if( OP[i].priority == 'L' ) {
                            order[x]=i;
                            x++;
                        }
            //-------------------------------------------------------
        //***********************************************************************************

        if( T.trace ) {
            // System.out.println();
            // for(int i = 0; i < opSize; i++)
            //     System.out.print(OP[i].opr);
            // System.out.println();
            // for(int i = 0; i < opSize; i++)
            //     System.out.print(order[i]+", ");
            System.out.println("\nV******************V");
            System.out.println("op size: "+opSize);
            System.out.println(eqS);
            System.out.println(operationPCK);
            for(int i = 0; i < opSize; i++)     System.out.print(order[i]+", ");
            System.out.println("\n^******************^");
        }
        
    
        // System.out.println("Equation built");
        // System.out.println(eqS);
        // System.out.println(operationPCK);
    }

    private void parseAndPushTO_eqS(String p) {
        if(p.length()==0)
            return;
        try {
            switch(TYPEclass.getSimpleName()) {
                case "Integer": eqS.push(Integer.parseInt(p));   break;
                case "Float":   eqS.push(Float.parseFloat(p));   break;
                case "Short":   eqS.push(Short.parseShort(p));   break;
                case "Byte":    eqS.push(Byte.parseByte(p));     break;
                case "Double":  eqS.push(Double.parseDouble(p)); break;
            }                
        }
        catch(NumberFormatException ne) {
            eqS.push(p);   
            varLocOnEqStack.add(eqS.size()-1);
            varList.chechAndADD(p);
        }
    }

    void calc() {
        eqS_clone = new Stack();
        eqS_clone.addAll(eqS);

        operation OP_clone[] = new operation[OP.length];
        for(int i=0;i<OP.length;i++)
            OP_clone[i] = new operation(OP[i]);

        if(eqS_clone.get(0) instanceof evaluate) {
            evaluate e=(evaluate)eqS_clone.get(0); 
            e.calc();
            eqS_clone.set(0,e.eqS_clone.get(0));
        }

        //set value to variables------------------------------
        if(varLocOnEqStack.size() > 0) {
            for( Integer i: varLocOnEqStack)
                eqS_clone.set(i, varList.get(eqS_clone.get(i).toString() ) );
        }
        //----------------------------------------------------
        //******************** Calculate In Order *******************************
        for(int i = 0, X = 0; i < opSize; i++) {
            X = order[i];
            OP_clone[X].calculate(eqS_clone);
            X++;
            while(X < opSize) {
                OP_clone[X].drop();
                X++;
            }
        }
        Value = (Number)eqS_clone.get(0);
        //***********************************************************************
    }
}