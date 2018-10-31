package com.company;

import java.util.HashMap;
import java.util.InputMismatchException;
import java.util.Map;
import java.util.concurrent.ExecutionException;

public class BrainfuckInterpreter {
    private int MAXN = 200, MAX_OP = 300_000;
    private int current_command;
    private int inputPosition = 0;
    private int currentPosition = 0;
    private int counter = 0;
    private int[] machine;
    private byte[] byte_input;
    private HashMap<Integer, Integer> braces;

    public BrainfuckInterpreter() {
        current_command = 0;
        inputPosition = 0;
        machine = new int[MAXN];
        braces = new HashMap<>();
    }

    public static void main(String[] args) {

    }

    private void parse(String program) throws Exception {
        char[] program_chars = program.toCharArray();
        int current = 0, balance = 0;
        HashMap<Integer, Integer> prev = new HashMap<>();
        for (int i = 0; i < program_chars.length; i++) {
            if (balance == 0) {
                switch (program_chars[i]) {
                    case (']'):
                        throw new Exception("Wrong sequence of parenthesis");
                    case ('['):
                        balance++;
                        prev.put(i, -1);
                        current = i;
                        break;
                }
            } else {
                switch (program_chars[i]) {
                    case (']'):
                        balance--;
                        braces.put(i, current);
                        braces.put(current, i);
                        current = prev.get(current);
                        break;
                    case ('['):
                        balance++;
                        prev.put(i, current);
                        current = i;
                        break;
                }
            }
        }
        if (balance != 0) {
            throw new Exception("Wrong sequence of parenthesis");
        }
    }

    public void runProgram(String program, String input){
        try {
            parse(program);
            byte_input = input.getBytes();
        } catch (Exception e) {
            System.out.println("Parsing error: ".concat(e.getMessage()));
            System.exit(-1);
        }
        while ((current_command < program.length()) && (counter < MAX_OP)) {
            try {
                counter++;
                switch (program.charAt(current_command)) {
                    case ('['): {
                        if (machine[currentPosition] == 0) {
                            current_command = braces.get(current_command) + 1;
                        } else current_command++;
                        break;
                    }
                    case (']'): {
                        if (machine[currentPosition] != 0) {
                            current_command = braces.get(current_command) + 1;
                        } else current_command++;
                        break;
                    }
                    case (','): {
                        if (inputPosition >= byte_input.length) throw new InputMismatchException("Input ended");
                        machine[currentPosition] = byte_input[inputPosition++];
                        current_command++;
                        break;
                    }
                    case ('.'): {
                        if ((currentPosition < 0) || (currentPosition >= MAXN)) throw new Exception("Memory reading error");
                        System.out.println(machine[currentPosition]);
                        current_command++;
                        break;
                    }
                    case ('-'): {
                        if ((currentPosition < 0) || (currentPosition >= MAXN)) throw new Exception("Memory reading error");
                        machine[currentPosition]--;
                        current_command++;
                        break;
                    }
                    case ('+'): {
                        if ((currentPosition < 0) || (currentPosition >= MAXN)) throw new Exception("Memory reading error");
                        machine[currentPosition]++;
                        current_command++;
                        break;
                    }
                    case ('<'): {
                        currentPosition--;
                        if (currentPosition < 0) {
                            throw new Exception("Memory reading error");
                        }
                        current_command++;
                        break;
                    }
                    case ('>'): {
                        currentPosition++;
                        if (currentPosition > MAXN) {
                            throw new Exception("Memory reading error");
                        }
                        current_command++;
                        break;
                    }
                }
            } catch (Exception e) {
                System.out.println("Error on step #" + counter + " :" +e.getMessage());
                break;
            }
        }
        if (counter >= MAX_OP) {
            System.out.println("Error: too many steps");
        }
    }
}
