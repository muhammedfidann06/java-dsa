package io.github.muhammedfidann06.dsa.linear;

import java.util.Arrays;
import java.util.NoSuchElementException;

/**
 * Stapel (LIFO: last in, first out) auf Basis eines Arrays.
 * push, pop und peek sind O(1) (push amortisiert).
 */
public class ArrayStack<T> {

    private Object[] data = new Object[8];
    private int size;

    public void push(T value) {
        if (size == data.length) {
            data = Arrays.copyOf(data, size * 2);
        }
        data[size++] = value;
    }

    @SuppressWarnings("unchecked")
    public T peek() {
        if (size == 0) {
            throw new NoSuchElementException("Stapel ist leer");
        }
        return (T) data[size - 1];
    }

    public T pop() {
        T value = peek();
        data[--size] = null;
        return value;
    }

    public int size() {
        return size;
    }

    public boolean isEmpty() {
        return size == 0;
    }

    /**
     * Anwendung: Sind die Klammern richtig gesetzt? Beispiel: "{[()]}" ja, "([)]" nein.
     * Jede öffnende Klammer kommt auf den Stapel, jede schließende muss zur obersten passen.
     */
    public static boolean isBalanced(String text) {
        ArrayStack<Character> stack = new ArrayStack<>();
        for (char ch : text.toCharArray()) {
            switch (ch) {
                case '(', '[', '{' -> stack.push(ch);
                case ')', ']', '}' -> {
                    if (stack.isEmpty()) {
                        return false;
                    }
                    char open = stack.pop();
                    boolean match = (open == '(' && ch == ')') || (open == '[' && ch == ']')
                            || (open == '{' && ch == '}');
                    if (!match) {
                        return false;
                    }
                }
                default -> {
                    // andere Zeichen ignorieren
                }
            }
        }
        return stack.isEmpty();
    }

    /**
     * Anwendung: Ausdruck in umgekehrter polnischer Notation berechnen.
     * Beispiel: "3 4 + 2 *" = (3 + 4) * 2 = 14
     */
    public static long evaluateRpn(String expression) {
        ArrayStack<Long> stack = new ArrayStack<>();
        for (String token : expression.trim().split("\\s+")) {
            switch (token) {
                case "+", "-", "*", "/" -> {
                    long b = stack.pop();
                    long a = stack.pop();
                    stack.push(switch (token) {
                        case "+" -> a + b;
                        case "-" -> a - b;
                        case "*" -> a * b;
                        default -> a / b;
                    });
                }
                default -> stack.push(Long.parseLong(token));
            }
        }
        if (stack.size() != 1) {
            throw new IllegalArgumentException("Ungültiger Ausdruck: " + expression);
        }
        return stack.pop();
    }
}
