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

    public String getCustName() {
        return custName;
    }

    public String getCustContact() {
        return custContact;
    }

    public String getCustAddress() {
        return custAddress;
    }
    
}
