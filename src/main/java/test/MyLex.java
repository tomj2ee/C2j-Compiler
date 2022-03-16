package test;

import java.util.ArrayList;
import java.util.List;

import static test.MyToken.*;

public class MyLex {


    private String buffer;
    private int curIndex = -1;
    private int eIndex = -1;
    private String yytext;
    private int tokenType = -1;


    public List<MyToken> runLexer() {
        List<MyToken> allToken = new ArrayList<>();
        while (!match(MyToken.EOI)) {
            advance();
            System.out.println("Token: " + token() + " ,Symbol: " + yytext);
            MyToken m = new MyToken();
            m.tokenType = tokenType;
            m.text = yytext;
            allToken.add(m);
        }
        return allToken;
    }

    private String token() {
        return tokenType >= 0 ? tokenType + "" : "";
    }

    public boolean match(int b) {
        return tokenType == b;
    }

    private char getNextChar() {
        if (curIndex < eIndex - 1) {
            curIndex++;
            return buffer.charAt(curIndex);
        }
        curIndex = eIndex;
        return '\0';
    }

    public void advance() {

        while (true) {
            char c = getNextChar();
            if (c == '\0') {
                tokenType = EOI;
                yytext = "EOI";
                return ;
            } else if (c == ';') {
                tokenType = SEMI;
                yytext = ";";
                break;
            } else if (c == '+') {
                tokenType = PLUS;
                yytext = "+";
                break;
            } else if (c == '-') {
                tokenType = SUB;
                yytext = "-";
                break;
            } else if (c == '*') {
                tokenType = TIMES;
                yytext = "*";
                break;
            } else if (c == '/') {
                tokenType = DIV;
                yytext = "/";
                break;
            } else if (c == '(') {
                tokenType = LP;
                yytext = "(";
                break;
            } else if (c == ')') {
                tokenType = RP;
                yytext = ")";
                break;
            } else if (c == ' ' || c == '\r' || c == '\n') {
                tokenType = -1;
                yytext = "";
            } else {
                yytext = "";
                tokenType = NUM_OR_ID;
                for (int i = curIndex; i < eIndex; i++) {
                    c = buffer.charAt(i);
                    if (isNumOrAlpha(c)) {
                        curIndex = i;
                        yytext = yytext + c;
                    } else {
                        break;
                    }
                }
                break;
            }
        }
    }

    private boolean isNumOrAlpha(char c) {
        return Character.isDigit(c) || Character.isAlphabetic(c);
    }

    public void setInput(String s) {
        this.buffer = s;
        this.curIndex = -1;
        this.eIndex = s.length();
    }

    public static void main(String[] args) {
        MyLex lex = new MyLex();
        lex.setInput("( 21 + 45) * 60 +350 /34;");
        lex.runLexer();
    }

}
