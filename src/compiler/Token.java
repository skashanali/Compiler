
package compiler;

public final class Token {
    
    String CP;
    String VP;
    int Line_Number;
    
    public void setValues(String CP,String VP,int Line_Number){
        this.CP = CP;
        this.VP = VP;
        this.Line_Number = Line_Number;
    }
}
