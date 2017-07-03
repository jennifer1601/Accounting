public class getGiro {
    private String Giro_Code;
    private String Giro_Date;
    private String Giro_Type;
    private String Giro_Status;
    private int Giro_Price;

    public getGiro(String Giro_Code, String Giro_Date, String Giro_Type, String Giro_Status, int Giro_Price) {
        this.Giro_Code = Giro_Code;
        this.Giro_Date = Giro_Date;
        this.Giro_Type = Giro_Type;
        this.Giro_Status = Giro_Status;
        this.Giro_Price = Giro_Price;
    }

    public String getGiro_Code() {
        return Giro_Code;
    }

    public String getGiro_Date() {
        return Giro_Date;
    }

    public String getGiro_Type() {
        return Giro_Type;
    }

    public String getGiro_Status() {
        return Giro_Status;
    }

    public int getGiro_Price() {
        return Giro_Price;
    }


}
