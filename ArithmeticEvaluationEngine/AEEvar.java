package ArithmeticEvaluationEngine;

class AEEvar {
    String name;
    Number value;

    AEEvar(String n) {
        name = n;
    }

    public String toString() {
        return name+": "+value;
    }
}