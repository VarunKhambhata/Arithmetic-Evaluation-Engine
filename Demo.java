import ArithmeticEvaluationEngine.*;

import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;
import java.util.Scanner;
import javax.swing.*;
import javax.swing.text.FlowView;
import java.awt.Color;

public class Demo
{
    public static void main(String[] args) 
    {
        try 
        {
            String eq = new String();
            JFrame F = new JFrame();
            JDialog d = new JDialog(F,"Enter Equation",true);
            d.setLayout(new FlowLayout(FlowLayout.CENTER));
            d.add(new JLabel("Enter an arithmetic expression with any variable/s:"));
            JButton b=new JButton("OK");
            JTextField T = new JTextField(25);     
            JLabel l = new JLabel("eg:- 10*a+(5^b-c)");
            l.setForeground(Color.GRAY);
            b.addActionListener(new ActionListener()
                        {
                            public void actionPerformed(ActionEvent ae)    
                            {   
                                if(!T.getText().equals(""))
                                    d.setVisible(false);
                            }
                        }
            );                        
            d.add(T);
            d.add(l);
            d.add(b);
            d.setSize(320,120);
            d.setLocationRelativeTo(null);
            d.setVisible(true);
            eq =  T.getText();
            ArithmeticEvaluationEngine ae = new ArithmeticEvaluationEngine(eq, Integer.class);            
            F.setLayout(new FlowLayout());
            F.setSize(330,390);
            F.setLocationRelativeTo(null);
            JPanel m = new JPanel();
            JPanel n = new JPanel();
            ArrayList<JTextField> INP = new ArrayList<JTextField>();
            m.add(new JLabel("Equation: "+eq));
            m.setPreferredSize(new Dimension(400,80));
            n.setPreferredSize(new Dimension(400,80));
            F.add(m);
            ArrayList<String> vars = ae.getVariables();
            for(String o:vars)
            {
                JPanel P = new JPanel();
                P.setLayout(new FlowLayout());
                P.add(new JLabel(o));
                JTextField tf = new JTextField();
                tf.setText("0");
                tf.setPreferredSize(new Dimension(60,20));
                tf.setName(o);
                INP.add(tf);
                P.add(tf);        
                P.setPreferredSize(new Dimension(200,30));
                F.add(P);
            }  
            m =new JPanel();
            JLabel ans = new JLabel();
            JButton calc = new JButton("Calculate");
            m.add(calc);
            n.add(ans);
            m.setPreferredSize(new Dimension(200,50));
            F.add(m);
            F.add(n);
            F.setDefaultCloseOperation(3); 
            F.setVisible(true); 
            calc.addActionListener(
                new ActionListener()
                {  
                    public void actionPerformed(ActionEvent e)
                    {  
                          for(JTextField f : INP)  
                          {
                                ae.setVariable(f.getName(), Integer.parseInt(f.getText()));
                          }
                          ans.setText("Answer: "+ae.Value().toString());
                    }  
                });  
                                 
        } catch (Exception e) 
        {
            System.out.println(e);
        }    
    }
}