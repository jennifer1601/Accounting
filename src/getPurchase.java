public class getPurchase {
    private String purchase_no;
    private String purchase_date;
    private String vendor_code;
    private int purchase_total;

    public getPurchase(String purchase_no, String purchase_date, String vendor_code, int purchase_total) {
        this.purchase_no = purchase_no;
        this.purchase_date = purchase_date;
        this.vendor_code = vendor_code;
        this.purchase_total = purchase_total;
    }

    public String getPurchase_no() {
        return purchase_no;
    }

    public String getPurchase_date() {
        return purchase_date;
    }

    public String getVendor_code() {
        return vendor_code;
    }

    public int getPurchase_total() {
        return purchase_total;
    }
    
    
}
