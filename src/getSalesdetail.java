public class getSalesdetail {
    private String sales_no;
    private String product_code;
    private int sales_qty;
    private int sales_price;
    private int sales_subtotal;

    public getSalesdetail(String sales_no, String product_code, int sales_qty, int sales_price, int sales_subtotal) {
        this.sales_no = sales_no;
        this.product_code = product_code;
        this.sales_qty = sales_qty;
        this.sales_price = sales_price;
        this.sales_subtotal = sales_subtotal;
    }

    public String getSales_no() {
        return sales_no;
    }

    public String getProduct_code() {
        return product_code;
    }

    public int getSales_qty() {
        return sales_qty;
    }

    public int getSales_price() {
        return sales_price;
    }

    public int getSales_subtotal() {
        return sales_subtotal;
    }   
}
