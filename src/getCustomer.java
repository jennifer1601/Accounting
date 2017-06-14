public class getCustomer {
    private String custCode;
    private String custName;
    private String custContact;
    private String custAddress;

    public getCustomer(String custCode, String custName, String custContact, String custAddress) {
        this.custCode = custCode;
        this.custName = custName;
        this.custContact = custContact;
        this.custAddress = custAddress;
    }

    public String getCustCode() {
        return custCode;
    }

    public void setCustCode(String custCode) {
        this.custCode = custCode;
    }

    public String getCustName() {
        return custName;
    }

    public void setCustName(String custName) {
        this.custName = custName;
    }

    public String getCustContact() {
        return custContact;
    }

    public void setCustContact(String custContact) {
        this.custContact = custContact;
    }

    public String getCustAddress() {
        return custAddress;
    }

    public void setCustAddress(String custAddress) {
        this.custAddress = custAddress;
    }
    
    
}
