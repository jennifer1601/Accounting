public class getSalesmaster {
    private String sales_no;
    private String sales_date;
    private String cust_code;
    private int sales_total;

    public getSalesmaster(String sales_no, String sales_date, String cust_code, int sales_total) {
        this.sales_no = sales_no;
        this.sales_date = sales_date;
        this.cust_code = cust_code;
        this.sales_total = sales_total;
    }

    public String getSales_no() {
        return sales_no;
    }

    public String getSales_date() {
        return sales_date;
    }

    public String getCust_code() {
        return cust_code;
    }

    public int getSales_total() {
        return sales_total;
    }
    
    
}
