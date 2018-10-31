package com.company;

public class Test {
    public static void main(String[] args) {
        BrainfuckInterpreter interpreter = new BrainfuckInterpreter();
        interpreter.runProgram("++++++++++[>+++++++>++++++++++>+++>+<<<<-]>++.>+.+++++++..+++.>++.<<+++++++++++++++.>.+++.------.--------.>+.>.", "ETAOIN SHRLDU");
    }
}
//  ++++++++++[>+++++++>++++++++++>+++>+<<<<-]>++.>+.+++++++..+++.>++.<<+++++++++++++++.>.+++.------.--------.>+.>. // Hello World!\n
// [.[]]+[>.+]