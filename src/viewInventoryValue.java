
import java.sql.*;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.table.DefaultTableModel;

public class viewInventoryValue extends javax.swing.JFrame {

    static final String JDBC_DRIVER = "con.mysql.jdbc.Driver";
    static final String DB_URL = "jdbc:mysql://localhost/abc";
    static final String USER = "root";
    static final String PASS = "abc";

    boolean search = false;

    public Connection getConnection() {
        Connection conn;
        try {
            conn = DriverManager.getConnection(DB_URL, USER, PASS);
            return conn;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public ArrayList<getInventory> getInventoryList() {
        ArrayList<getInventory> inventoryList = new ArrayList<getInventory>();
        Connection connection = getConnection();
        String query = "SELECT * FROM inventory";
        Statement st;
        ResultSet rs;
        try {
            st = connection.createStatement();
            rs = st.executeQuery(query);
            getInventory inventory;
            while (rs.next()) {
                inventory = new getInventory(rs.getString("product_code"),
                        rs.getString("product_name"),
                        rs.getInt("product_qty"),
                        rs.getInt("product_price"));
                inventoryList.add(inventory);

            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return inventoryList;
    }

    public void Show_In_JTable() {
        ArrayList<getInventory> list = getInventoryList();
        DefaultTableModel model = (DefaultTableModel) jTable1.getModel();
        model.setRowCount(0);
        Object[] row = new Object[10];
        for (int i = 0; i < list.size(); i++) {
            row[0] = list.get(i).getProductCode();
            row[1] = list.get(i).getProductName();
            row[8] = list.get(i).getProductQty();
            row[2] = list.get(i).getProductPrice();
            model.addRow(row);
        }
    }

    public void Show_In_Out() throws SQLException {
        int sales_qty = 0;
        int purchase_qty = 0;
        Statement st = null;
        ResultSet rs = null;
        Connection conn = getConnection();
        for (int i = 0; i < jTable1.getRowCount(); i++) {

            String query = "SELECT sum(purchase_qty) as purchase_qty FROM purchasedetail where product_code = '" + (String) jTable1.getValueAt(i, 0) + "' AND MONTH(purchase_date) <= '" + jTextField2.getText() + "' AND MONTH(purchase_date) <= '" + jTextField5.getText() + "'";
            try {
                st = conn.createStatement();
                rs = st.executeQuery(query);
                while (rs.next()) {
                    purchase_qty = rs.getInt("purchase_qty");
                }
            } catch (Exception e) {
                e.printStackTrace();
            }

            String query1 = "SELECT sum(sales_qty) as sales_qty FROM salesdetail where product_code = '" + (String) jTable1.getValueAt(i, 0) + "' AND MONTH(sales_date) <= '" + jTextField2.getText() + "' AND MONTH(sales_date) <= '" + jTextField5.getText() + "'";
            try {
                st = conn.createStatement();
                rs = st.executeQuery(query1);
                while (rs.next()) {
                    sales_qty = rs.getInt("sales_qty");
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
            jTable1.setValueAt(purchase_qty, i, 4);
            jTable1.setValueAt(sales_qty, i, 6);
            int inValue = purchase_qty * (int) jTable1.getValueAt(i, 2);
            jTable1.setValueAt(inValue, i, 5);
            int outValue = sales_qty * (int) jTable1.getValueAt(i, 2);
            jTable1.setValueAt(outValue, i, 7);
            jTable1.setValueAt((int) jTable1.getValueAt(i, 8) * (int) jTable1.getValueAt(i, 2), i, 9);
        }
        rs.close();
        st.close();
        conn.close();
    }

    public void Show_Opening() {
        int debit = 0;
        int credit = 0;
        Statement st = null;
        ResultSet rs = null;
        Connection conn = getConnection();
        for (int i = 0; i < jTable1.getRowCount(); i++) {
            String query = "SELECT sum(purchase_qty) as purchase_qty FROM purchasedetail where product_code = '" + (String) jTable1.getValueAt(i, 0) + "' AND MONTH(purchase_date) < '" + jTextField2.getText() + "'";
            try {
                st = conn.createStatement();
                rs = st.executeQuery(query);
                while (rs.next()) {
                    debit = rs.getInt("purchase_qty");
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
            String query1 = "SELECT sum(sales_qty) as sales_qty FROM salesdetail where product_code = '" + (String) jTable1.getValueAt(i, 0) + "' AND MONTH(sales_date) < '" + jTextField2.getText() + "'";
            try {
                st = conn.createStatement();
                rs = st.executeQuery(query1);
                while (rs.next()) {
                    credit = rs.getInt("sales_qty");
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
            System.out.println(debit + " " + credit);
            jTable1.setValueAt(debit - credit, i, 3);
        }
    }

    public ArrayList<getPurchaseDetail> getPurchaseDetailList() {
        String query = "";
        ArrayList<getPurchaseDetail> PurchaseDetailList = new ArrayList<getPurchaseDetail>();
        Connection connection = getConnection();
        query = "SELECT * FROM PurchaseDetail WHERE MONTH(purchase_date) <= '" + jTextField2.getText() + "' AND MONTH(purchase_date) <= '" + jTextField5.getText() + "' Order by purchase_no ";
        Statement st;
        ResultSet rs;
        try {
            st = connection.createStatement();
            rs = st.executeQuery(query);
            System.out.println(query);
            getPurchaseDetail purchasedetail;
            while (rs.next()) {
                purchasedetail = new getPurchaseDetail(rs.getString("purchase_no"),
                        rs.getString("product_code"),
                        rs.getInt("purchase_qty"),
                        rs.getInt("purchase_price"),
                        rs.getInt("purchase_subtotal"),
                        rs.getString("purchase_date"));
                PurchaseDetailList.add(purchasedetail);

            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return PurchaseDetailList;
    }

    public void Show_In_JTable1() {
        ArrayList<getPurchaseDetail> list = getPurchaseDetailList();
        DefaultTableModel model = (DefaultTableModel) jTable2.getModel();
        model.setRowCount(0);
        Object[] row = new Object[6];
        for (int i = 0; i < list.size(); i++) {
            row[0] = list.get(i).getPurchase_no();
            row[1] = list.get(i).getProduct_code();
            row[2] = list.get(i).getPurchase_qty();
            row[3] = list.get(i).getPurchase_price();
            row[4] = list.get(i).getPurchase_subtotal();
            row[5] = list.get(i).getPurchase_date();
            model.addRow(row);
        }
    }

    public void Show_Price() {
        double avg = 0;
        int pricetotal = 0;
        for (int i = 0; i < jTable1.getRowCount(); i++) {
            String product = (String) jTable1.getValueAt(i, 0);
            System.out.println(product);
            for (int j = 0; j < jTable2.getRowCount(); j++) {
                String p = (String) jTable2.getValueAt(j, 1);
                if (p.equals(product)) {
                    pricetotal += (int) jTable2.getValueAt(j, 4);
                    jTable1.setValueAt(pricetotal, i, 2);
                }
            }

        }
    }

    public viewInventoryValue() {
        initComponents();
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jScrollPane1 = new javax.swing.JScrollPane();
        jTable1 = new javax.swing.JTable();
        jLabel1 = new javax.swing.JLabel();
        jButton1 = new javax.swing.JButton();
        jLabel3 = new javax.swing.JLabel();
        jTextField2 = new javax.swing.JTextField();
        jLabel4 = new javax.swing.JLabel();
        jTextField5 = new javax.swing.JTextField();
        jScrollPane2 = new javax.swing.JScrollPane();
        jTable2 = new javax.swing.JTable();
        jLabel2 = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);

        jTable1.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Product Code", "Product Name", "Price", "Opening", "In", "Value", "Out", "Value", "Ending", "Value"
            }
        ) {
            Class[] types = new Class [] {
                java.lang.String.class, java.lang.String.class, java.lang.Integer.class, java.lang.Integer.class, java.lang.Integer.class, java.lang.Integer.class, java.lang.Integer.class, java.lang.Integer.class, java.lang.Integer.class, java.lang.Integer.class
            };

            public Class getColumnClass(int columnIndex) {
                return types [columnIndex];
            }
        });
        jScrollPane1.setViewportView(jTable1);

        jLabel1.setFont(new java.awt.Font("Tahoma", 1, 36)); // NOI18N
        jLabel1.setText("Inventory Value");

        jButton1.setText("Search");
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });

        jLabel3.setText("Month :");

        jLabel4.setText("TO");

        jTable2.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Purchase No", "Product Code", "Qty", "Price", "SubTotal", "Date"
            }
        ) {
            Class[] types = new Class [] {
                java.lang.String.class, java.lang.String.class, java.lang.Integer.class, java.lang.Integer.class, java.lang.Integer.class, java.lang.String.class
            };

            public Class getColumnClass(int columnIndex) {
                return types [columnIndex];
            }
        });
        jScrollPane2.setViewportView(jTable2);

        jLabel2.setFont(new java.awt.Font("Tahoma", 1, 18)); // NOI18N
        jLabel2.setText("Detail");

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 1019, Short.MAX_VALUE)
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel1)
                            .addGroup(layout.createSequentialGroup()
                                .addGap(13, 13, 13)
                                .addComponent(jLabel3)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(jTextField2, javax.swing.GroupLayout.PREFERRED_SIZE, 131, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(jLabel4)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(jTextField5, javax.swing.GroupLayout.PREFERRED_SIZE, 141, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(jButton1))
                            .addComponent(jLabel2)
                            .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 694, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(0, 0, Short.MAX_VALUE)))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel1)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jButton1)
                    .addComponent(jLabel3)
                    .addComponent(jTextField2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel4)
                    .addComponent(jTextField5, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 213, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(25, 25, 25)
                .addComponent(jLabel2)
                .addGap(18, 18, 18)
                .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 166, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(89, Short.MAX_VALUE))
        );

        pack();
        setLocationRelativeTo(null);
    }// </editor-fold>//GEN-END:initComponents

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
        Show_In_JTable();
        Show_In_JTable1();
        try {
            Show_In_Out();
            Show_Opening();
            Show_Price();
        } catch (SQLException ex) {
            Logger.getLogger(viewInventoryValue.class.getName()).log(Level.SEVERE, null, ex);
        }
    }//GEN-LAST:event_jButton1ActionPerformed

    public static void main(String args[]) {
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new viewGL().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton jButton1;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JTable jTable1;
    private javax.swing.JTable jTable2;
    private javax.swing.JTextField jTextField2;
    private javax.swing.JTextField jTextField5;
    // End of variables declaration//GEN-END:variables
}
