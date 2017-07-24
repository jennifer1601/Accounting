
import java.sql.*;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.table.DefaultTableModel;

public class viewPurchase extends javax.swing.JFrame {

    static final String JDBC_DRIVER = "con.mysql.jdbc.Driver";
    static final String DB_URL = "jdbc:mysql://localhost/abc";
    static final String USER = "root";
    static final String PASS = "abc";

    boolean search = false;
    boolean search1 = false;

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

    public ArrayList<getPurchase> getPurchaseList() {
        String query = "";
        ArrayList<getPurchase> purchaseList = new ArrayList<getPurchase>();
        Connection connection = getConnection();
        if (search == false) {
            query = "SELECT * FROM Purchase Order by purchase_no";
        }
        if (search == true) {
            query = "SELECT * FROM Purchase WHERE purchase_" + (String) jComboBox1.getSelectedItem() + " = '" + jTextField1.getText() + "'";

        }
        Statement st;
        ResultSet rs;
        try {
            st = connection.createStatement();
            rs = st.executeQuery(query);
            System.out.println(query);
            getPurchase purchase;
            while (rs.next()) {
                purchase = new getPurchase(rs.getString("purchase_no"),
                        rs.getString("purchase_date"),
                        rs.getString("vendor_code"),
                        rs.getInt("purchase_total"));
                purchaseList.add(purchase);

            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return purchaseList;
    }

    public void Show_In_JTable() {
        ArrayList<getPurchase> list = getPurchaseList();
        DefaultTableModel model = (DefaultTableModel) jTable1.getModel();
        model.setRowCount(0);
        Object[] row = new Object[5];
        for (int i = 0; i < list.size(); i++) {
            row[0] = list.get(i).getPurchase_no();
            row[1] = list.get(i).getPurchase_date();
            row[2] = list.get(i).getVendor_code();
            row[4] = list.get(i).getPurchase_total();
            model.addRow(row);
        }
        Show_Name();
    }

    public void Show_Name() {
        String vendor_name = "";
        String product_name = "";
        Connection connection = getConnection();
        for (int i = 0; i < jTable1.getRowCount(); i++) {
            String query = "SELECT vendor_name FROM vendor WHERE vendor_id = '" + jTable1.getValueAt(i, 2) + "'";
            Statement st;
            ResultSet rs;
            try {
                st = connection.createStatement();
                rs = st.executeQuery(query);
                while (rs.next()) {
                    vendor_name = rs.getString("vendor_name");
                }
                jTable1.setValueAt(vendor_name, i, 3);
            } catch (Exception e) {
                e.printStackTrace();
            }

        }
        for (int i = 0; i < jTable2.getRowCount(); i++) {
            String query = "SELECT product_name FROM inventory WHERE product_code = '" + jTable2.getValueAt(i, 1) + "'";
            Statement st;
            ResultSet rs;
            try {
                st = connection.createStatement();
                rs = st.executeQuery(query);
                while (rs.next()) {
                    product_name = rs.getString("product_name");
                }
                jTable2.setValueAt(product_name, i, 2);
            } catch (Exception e) {
                e.printStackTrace();
            }

        }
    }
    
    public ArrayList<getPurchaseDetail> getPurchaseDetailList() {
        String query = "";
        ArrayList<getPurchaseDetail> PurchaseDetailList = new ArrayList<getPurchaseDetail>();
        Connection connection = getConnection();
        if (search1 == false) {
            query = "SELECT * FROM PurchaseDetail Order by purchase_no";
        }
        if (search1 == true) {
            String code = (String) jTable1.getModel().getValueAt(jTable1.getSelectedRow(), 0);
            query = "SELECT * FROM purchasedetail WHERE purchase_no = '" + code + "'";

        }
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
                        rs.getInt("purchase_subtotal"));
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
            row[3] = list.get(i).getPurchase_qty();
            row[4] = list.get(i).getPurchase_price();
            row[5] = list.get(i).getPurchase_subtotal();
            model.addRow(row);
        }
        Show_Name();
    }

    public viewPurchase() {
        initComponents();
        Show_In_JTable();
        Show_In_JTable1();
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        jComboBox1 = new javax.swing.JComboBox<>();
        jTextField1 = new javax.swing.JTextField();
        jButton1 = new javax.swing.JButton();
        jScrollPane1 = new javax.swing.JScrollPane();
        jTable1 = new javax.swing.JTable();
        jButton2 = new javax.swing.JButton();
        jScrollPane2 = new javax.swing.JScrollPane();
        jTable2 = new javax.swing.JTable();
        jLabel3 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);

        jLabel1.setFont(new java.awt.Font("Tahoma", 1, 36)); // NOI18N
        jLabel1.setText("Purchase");

        jLabel2.setText("Search by :");

        jComboBox1.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Date", "No" }));

        jButton1.setText("Search");
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });

        jTable1.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Purchase No", "Date", "Vendor Code", "Vendor Name", "Total"
            }
        ) {
            Class[] types = new Class [] {
                java.lang.String.class, java.lang.String.class, java.lang.String.class, java.lang.String.class, java.lang.Integer.class
            };

            public Class getColumnClass(int columnIndex) {
                return types [columnIndex];
            }
        });
        jScrollPane1.setViewportView(jTable1);

        jButton2.setText("View");
        jButton2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton2ActionPerformed(evt);
            }
        });

        jTable2.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Purchase No", "Product Code", "Product Name", "Price", "Subtotal"
            }
        ) {
            Class[] types = new Class [] {
                java.lang.String.class, java.lang.String.class, java.lang.String.class, java.lang.Integer.class, java.lang.Integer.class
            };

            public Class getColumnClass(int columnIndex) {
                return types [columnIndex];
            }
        });
        jScrollPane2.setViewportView(jTable2);

        jLabel3.setFont(new java.awt.Font("Tahoma", 1, 18)); // NOI18N
        jLabel3.setText("Detail");

        jLabel4.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 834, Short.MAX_VALUE)
                    .addComponent(jScrollPane2, javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(layout.createSequentialGroup()
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                    .addComponent(jLabel1)
                                    .addGroup(layout.createSequentialGroup()
                                        .addGap(15, 15, 15)
                                        .addComponent(jLabel2)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                        .addComponent(jComboBox1, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(jTextField1, javax.swing.GroupLayout.PREFERRED_SIZE, 189, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(jButton1))
                            .addGroup(layout.createSequentialGroup()
                                .addGap(31, 31, 31)
                                .addComponent(jLabel3)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(jLabel4, javax.swing.GroupLayout.PREFERRED_SIZE, 42, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(jButton2)))
                        .addGap(0, 0, Short.MAX_VALUE)))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel1)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel2)
                    .addComponent(jComboBox1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jTextField1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jButton1))
                .addGap(18, 18, 18)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 173, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(37, 37, 37)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jLabel3)
                    .addComponent(jLabel4, javax.swing.GroupLayout.PREFERRED_SIZE, 24, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jButton2))
                .addGap(18, 18, 18)
                .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 189, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(93, Short.MAX_VALUE))
        );

        pack();
        setLocationRelativeTo(null);
    }// </editor-fold>//GEN-END:initComponents

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
        DefaultTableModel model = (DefaultTableModel) jTable1.getModel();
        while (model.getRowCount() > 0) {
            model.removeRow(0);
        }
        search = true;
        getPurchaseList();
        Show_In_JTable();
        Show_Name();
        search = false;
    }//GEN-LAST:event_jButton1ActionPerformed

    private void jButton2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton2ActionPerformed
        DefaultTableModel model = (DefaultTableModel) jTable2.getModel();
        while (model.getRowCount() > 0) {
            model.removeRow(0);
        }
        search1 = true;
        Show_In_JTable1();
        Show_Name();
        search = false;
        jLabel4.setText((String) jTable1.getModel().getValueAt(jTable1.getSelectedRow(), 0));
    }//GEN-LAST:event_jButton2ActionPerformed

    public static void main(String args[]) {
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new viewPurchase().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton jButton1;
    private javax.swing.JButton jButton2;
    private javax.swing.JComboBox<String> jComboBox1;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JTable jTable1;
    private javax.swing.JTable jTable2;
    private javax.swing.JTextField jTextField1;
    // End of variables declaration//GEN-END:variables
}
