public class getPurchaseDetail {
    private String purchase_no;
    private String product_code;
    private int purchase_qty;
    private int purchase_price;
    private int purchase_subtotal;
    private String purchase_date;

    public getPurchaseDetail(String purchase_no, String product_code, int purchase_qty, int purchase_price, int purchase_subtotal) {
        this.purchase_no = purchase_no;
        this.product_code = product_code;
        this.purchase_qty = purchase_qty;
        this.purchase_price = purchase_price;
        this.purchase_subtotal = purchase_subtotal;
    }

    public getPurchaseDetail(String purchase_no, String product_code, int purchase_qty, int purchase_price, int purchase_subtotal, String purchase_date) {
        this.purchase_no = purchase_no;
        this.product_code = product_code;
        this.purchase_qty = purchase_qty;
        this.purchase_price = purchase_price;
        this.purchase_subtotal = purchase_subtotal;
        this.purchase_date = purchase_date;
    }
    
 
    public String getPurchase_no() {
        return purchase_no;
    }

    public String getProduct_code() {
        return product_code;
    }

    public int getPurchase_qty() {
        return purchase_qty;
    }

    public int getPurchase_price() {
        return purchase_price;
    }

    public int getPurchase_subtotal() {
        return purchase_subtotal;
    }

    public String getPurchase_date() {
        return purchase_date;
    }
   
}
