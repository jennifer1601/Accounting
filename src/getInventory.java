public class getInventory {
    private String productCode;
    private String productName;
    private int productQty;
    private int productPrice;

    public getInventory(String productCode, String productName, int productQty, int productPrice) {
        this.productCode = productCode;
        this.productName = productName;
        this.productQty = productQty;
        this.productPrice = productPrice;
    }

    public getInventory(String productCode) {
        this.productCode = productCode;
    }

    public String getProductCode() {
        return productCode;
    }

    public String getProductName() {
        return productName;
    }

    public int getProductQty() {
        return productQty;
    }

    public int getProductPrice() {
        return productPrice;
    }
    
    
}
