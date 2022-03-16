package test;

public class MyToken {

    public static final int EOI = 0;
    public static final int SEMI = 1; //分号
    public static final int PLUS = 2; //+
    public static final int SUB = 3; //-
    public static final int TIMES = 4; // *
    public static final int DIV = 5; // /
    public static final int LP = 6; // (
    public static final int RP = 7; //  )
    public static final int NUM_OR_ID = 8;// 数字或者id

    public int tokenType;
    public String text;

    @Override
    public String toString() {
        return "MyToken{" +
                "tokenType=" + tokenType +
                ", text='" + text + '\'' +
                '}';
    }
}
