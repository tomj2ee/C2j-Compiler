package fms;

public class TableFMS  implements FMS {

    private final int ASCII_COUNT=128;
    private  final int STATA_COUNT=6;
    private int[][] fmsTable=new int[STATA_COUNT][ASCII_COUNT];
    private  boolean [] accept=new boolean[]{false,true,true ,false,true,false};


    public  TableFMS(){
        for(int i=0;i<STATA_COUNT;i++){
            for(int j=0;j<ASCII_COUNT;j++){
                fmsTable[i][j]=FMS.STATE_FAILURE;
            }
        }
        initForNumber(0,1);
        initForNumber(1,1);
        initForNumber(2,2);
        initForNumber(3,2);
        initForNumber(4,4);
        initForNumber(5,4);
        fmsTable[0]['0']=3;
        fmsTable[1]['.']=2;
        fmsTable[1]['e']=5;
        fmsTable[2]['e']=5;

    }

    private  void initForNumber(int row,int val){
        for(int i=0;i<10;i++){
            fmsTable[row]['0'+i]=val;
        }
    }





    @Override
    public int yy_next(int state, byte yyLook) {
        if(state==FMS.STATE_FAILURE || yyLook>=ASCII_COUNT){
            return  FMS.STATE_FAILURE;
        }
        return fmsTable[state][yyLook];
    }

    @Override
    public boolean isAcceptState(int state) {
        if(state!=FMS.STATE_FAILURE){
            return  accept[state];
        }
        return false;
    }
}
