public class cust {

    private String customerCode;
    private String customerName;
    private String customerContact;
    private String customerAddress;

    public cust(String cCode, String cName, String cContact, String cAddress) {
        this.customerContact = cContact;
        this.customerCode = cCode;
        this.customerName = cName;
        this.customerAddress = cAddress;
    }

    public String getCustomerCode() {
        return customerCode;
    }

    public String getCustomerName() {
        return customerName;
    }

    public String getCustomerContact() {
        return customerContact;
    }

    public String getCustomerAddress() {
        return customerAddress;
    }
}
