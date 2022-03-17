package fms;

import input.Input;

public class FiniteStateMachine {

    private  int yystate=0;
    private  int yylastaccept=FMS.STATE_FAILURE;
    private  int yyprev=FMS.STATE_FAILURE;
    private int yynstate=FMS.STATE_FAILURE;
    private  boolean yyanchor=false;
    private  byte yyLook= 0;
    private Input input=new Input();
    private  TableFMS fms=new TableFMS();
    private  boolean endOfReads=false;

    public  FiniteStateMachine(){
        input.newFile();
        input.inputAdvance();
        //input.pushask(1);
        //input.markStart();
    }

}
