public class getVendor {
    private String vendorId;
    private String vendorName;
    private String vendorContact;
    private String vendorAddress;

    public getVendor(String vendorId, String vendorName, String vendorContact, String vendorAddress) {
        this.vendorId = vendorId;
        this.vendorName = vendorName;
        this.vendorContact = vendorContact;
        this.vendorAddress = vendorAddress;
    }

    public String getVendorId() {
        return vendorId;
    }

    public String getVendorName() {
        return vendorName;
    }

    public String getVendorContact() {
        return vendorContact;
    }

    public String getVendorAddress() {
        return vendorAddress;
    }
    
    
}
