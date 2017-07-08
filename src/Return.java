
import java.sql.*;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

public class Return extends javax.swing.JPanel {

    static final String JDBC_DRIVER = "com.mysql.jdbc.Driver";
    static final String DB_URL = "jdbc:mysql://localhost/abc";
    static final String USER = "root";
    static final String PASS = "abc";

    String product_code = "";

    public Return() {
        initComponents();
        try {
            SelectedCombo();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

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

    public void AutoCode() throws SQLException {
        Connection conn = getConnection();
        String journal_code = "";
        String jCode = "";
        String query = "SELECT max(journal_code) as journal_code FROM journal";
        int nextC = 0;
        Statement st = null;
        ResultSet rs = null;
        boolean isNull = false;
        try {
            st = conn.createStatement();
            rs = st.executeQuery(query);
            while (rs.next()) {
                journal_code = rs.getString("journal_code");
            }
            try {
                nextC = Integer.parseInt(journal_code.substring(1)) + 1;
                jCode = Integer.toString(nextC);
            } catch (NullPointerException e) {
                isNull = true;
            }
            if (isNull == true) {
                jTextField1.setText("J01");
                isNull = false;
            } else if (nextC < 10) {
                jTextField2.setText("J0" + jCode);
            } else {
                jTextField2.setText("J" + jCode);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        rs.close();
        st.close();
        conn.close();
    }

    public void SelectedCombo() throws SQLException {
        jComboBox3.removeAllItems();
        Statement st = null;
        ResultSet rs = null;
        Connection conn = getConnection();
        if ((String) jComboBox1.getSelectedItem() == "Sales") {
            jTextField1.setText("S");
            String query1 = "SELECT chart_name FROM chart WHERE chart_no LIKE '103%' OR chart_no = '1010' OR chart_no = '1020'";
            try {
                st = conn.createStatement();
                rs = st.executeQuery(query1);
                while (rs.next()) {
                    String chart_name = rs.getString("chart_name");
                    jComboBox3.addItem(chart_name);
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        if ((String) jComboBox1.getSelectedItem() == "Purchase") {
            jTextField1.setText("P");
            String query1 = "SELECT chart_name FROM chart WHERE chart_no LIKE '202%' OR chart_no = '1010' OR chart_no = '1020'";
            try {
                st = conn.createStatement();
                rs = st.executeQuery(query1);
                while (rs.next()) {
                    String chart_name = rs.getString("chart_name");
                    jComboBox3.addItem(chart_name);
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        String query = "SELECT product_name FROM inventory";
        try {
            st = conn.createStatement();
            rs = st.executeQuery(query);
            while (rs.next()) {
                String product_name = rs.getString("product_name");
                jComboBox2.addItem(product_name);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        rs.close();
        st.close();
        conn.close();
    }

    public void Show_Journal() {
        String chart_no = "";
        int purchase_price = 0;
        int sales_price = 0;
        Statement st = null;
        ResultSet rs = null;
        DefaultTableModel model = (DefaultTableModel) jTable1.getModel();
        Connection conn = getConnection();
        String query = "SELECT chart_no FROM chart where chart_name = '" + (String) jComboBox3.getSelectedItem() + "'";
        if ((String) jComboBox1.getSelectedItem() == "Purchase") {
            String query2 = "SELECT product_code FROM inventory WHERE product_name = '" + (String) jComboBox2.getSelectedItem() + "'";
            try {
                st = conn.createStatement();
                rs = st.executeQuery(query2);
                while (rs.next()) {
                    product_code = rs.getString("product_code");
                    System.out.println(product_code);
                }
                String query1 = "SELECT purchase_price FROM purchasedetail WHERE product_code = '" + product_code + "' AND purchase_no = '" + jTextField1.getText() + "'";
                rs = st.executeQuery(query1);
                System.out.println(query1);
                while (rs.next()) {
                    purchase_price = rs.getInt("purchase_price");
                    System.out.println(purchase_price);
                }
                rs = st.executeQuery(query);
                while (rs.next()) {
                    chart_no = rs.getString("chart_no");
                    System.out.println(chart_no);
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
            purchase_price = Integer.parseInt(jTextField5.getText()) * purchase_price;
            Object[] row = {chart_no, (String) jComboBox3.getSelectedItem(), purchase_price, "0"};
            model.addRow(row);
            Object[] row1 = {"5130", "Purchase Return", "0", purchase_price};
            model.addRow(row1);
        }
        if ((String) jComboBox1.getSelectedItem() == "Sales") {
            String query2 = "SELECT product_code FROM inventory WHERE product_name = '" + (String) jComboBox2.getSelectedItem() + "'";
            try {
                st = conn.createStatement();
                rs = st.executeQuery(query2);
                while (rs.next()) {
                    product_code = rs.getString("product_code");
                    System.out.println(product_code);
                }
                String query1 = "SELECT sales_price FROM salesdetail WHERE product_code = '" + product_code + "' AND sales_no = '" + jTextField1.getText() + "'";
                rs = st.executeQuery(query1);
                System.out.println(query1);
                while (rs.next()) {
                    sales_price = rs.getInt("sales_price");
                    System.out.println(sales_price);
                }
                rs = st.executeQuery(query);
                while (rs.next()) {
                    chart_no = rs.getString("chart_no");
                    System.out.println(chart_no);
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
            sales_price = Integer.parseInt(jTextField5.getText()) * sales_price;
            Object[] row = {chart_no, (String) jComboBox3.getSelectedItem(), "0", sales_price};
            model.addRow(row);
            Object[] row1 = {"4030", "Sales Return", sales_price, "0"};
            model.addRow(row1);
        }

    }

    public void UpdateInventory() throws SQLException {
        Connection conn = getConnection();
        Statement st = null;
        ResultSet rs = null;
        int product_qty = 0;
        int difQty = 0;

        if ((String) jComboBox1.getSelectedItem() == "Sales") {
            //        untuk nambah product_qty (SALES RETURN)
            difQty = Integer.parseInt(jTextField5.getText());
            String query = "SELECT product_qty FROM inventory WHERE product_code = '" + product_code + "'";
            System.out.println(query);
            try {
                st = conn.createStatement();
                rs = st.executeQuery(query);
                while (rs.next()) {
                    product_qty = rs.getInt("product_qty");
                }
                product_qty += difQty;
                String query1 = "UPDATE inventory SET product_qty  = '" + product_qty + "' WHERE product_code ='" + product_code + "'";
                System.out.println(query1);
                st.executeUpdate(query1);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        if ((String) jComboBox1.getSelectedItem() == "Purchase") {
            //        untuk kurang product_qty (PURCHASE RETURN)
            difQty = Integer.parseInt(jTextField5.getText());
            String query = "SELECT product_qty FROM inventory WHERE product_code = '" + product_code + "'";
            System.out.println(query);
            try {
                st = conn.createStatement();
                rs = st.executeQuery(query);
                while (rs.next()) {
                    product_qty = rs.getInt("product_qty");
                }
                product_qty -= difQty;
                String query1 = "UPDATE inventory SET product_qty  = '" + product_qty + "' WHERE product_code ='" + product_code + "'";
                System.out.println(query1);
                st.executeUpdate(query1);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    public void AddStatement() throws SQLException {
        Connection conn = getConnection();
        Statement st = null;
        String date = jTextField3.getText();
        st = conn.createStatement();
//        for add journal table code
        for (int i = 0; i < jTable1.getRowCount(); i++) {
            String journal_code = jTextField2.getText();
            String chart_no = (String) jTable1.getValueAt(i, 0);
            String chart_name = (String) jTable1.getValueAt(i, 1);
            Object debit = jTable1.getValueAt(i, 2);
            Object credit = jTable1.getValueAt(i, 3);
            String journal_desc = jTextField4.getText();
            String query = "INSERT INTO Journal VALUES("
                    + "'" + journal_code + "',"
                    + "'" + chart_no + "',"
                    + "'" + chart_name + "',"
                    + "'" + debit + "',"
                    + "'" + credit + "',"
                    + "'" + journal_desc + "',"
                    + "'" + date + "')";
            System.out.print(query);
            try {
                st.executeUpdate(query);

            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jLabel1 = new javax.swing.JLabel();
        jComboBox1 = new javax.swing.JComboBox<>();
        jLabel2 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        jTextField1 = new javax.swing.JTextField();
        jLabel4 = new javax.swing.JLabel();
        jComboBox2 = new javax.swing.JComboBox<>();
        jLabel5 = new javax.swing.JLabel();
        jComboBox3 = new javax.swing.JComboBox<>();
        jLabel6 = new javax.swing.JLabel();
        jTextField2 = new javax.swing.JTextField();
        jScrollPane1 = new javax.swing.JScrollPane();
        jTable1 = new javax.swing.JTable();
        jLabel7 = new javax.swing.JLabel();
        jTextField3 = new javax.swing.JTextField();
        jButton1 = new javax.swing.JButton();
        jButton2 = new javax.swing.JButton();
        jLabel8 = new javax.swing.JLabel();
        jTextField4 = new javax.swing.JTextField();
        jLabel9 = new javax.swing.JLabel();
        jTextField5 = new javax.swing.JTextField();

        jLabel1.setFont(new java.awt.Font("Tahoma", 1, 36)); // NOI18N
        jLabel1.setText("Return");

        jComboBox1.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Sales", "Purchase" }));
        jComboBox1.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                jComboBox1ItemStateChanged(evt);
            }
        });

        jLabel2.setText("Type :");

        jLabel3.setText("Code :");

        jLabel4.setText("Product :");

        jLabel5.setText("Deduct :");

        jLabel6.setText("Journal Code :");

        jTable1.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Chart No", "Chart Name", "Debit", "Credit"
            }
        ) {
            Class[] types = new Class [] {
                java.lang.String.class, java.lang.String.class, java.lang.Integer.class, java.lang.Integer.class
            };

            public Class getColumnClass(int columnIndex) {
                return types [columnIndex];
            }
        });
        jScrollPane1.setViewportView(jTable1);

        jLabel7.setText("Date :");

        jButton1.setText("View");
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });

        jButton2.setText("Add");
        jButton2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton2ActionPerformed(evt);
            }
        });

        jLabel8.setText("Description :");

        jLabel9.setText("Qty :");

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGap(24, 24, 24)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                            .addGroup(javax.swing.GroupLayout.Alignment.LEADING, layout.createSequentialGroup()
                                .addComponent(jLabel6)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(jTextField2, javax.swing.GroupLayout.PREFERRED_SIZE, 59, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(18, 18, 18)
                                .addComponent(jButton1, javax.swing.GroupLayout.PREFERRED_SIZE, 73, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(jLabel8)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(jTextField4, javax.swing.GroupLayout.PREFERRED_SIZE, 207, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(jButton2))
                            .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 555, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGroup(javax.swing.GroupLayout.Alignment.LEADING, layout.createSequentialGroup()
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                    .addComponent(jLabel5)
                                    .addComponent(jLabel2)
                                    .addComponent(jLabel4))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                                    .addGroup(layout.createSequentialGroup()
                                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                            .addComponent(jComboBox1, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                            .addComponent(jComboBox2, javax.swing.GroupLayout.PREFERRED_SIZE, 119, javax.swing.GroupLayout.PREFERRED_SIZE))
                                        .addGap(154, 154, 154)
                                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                            .addComponent(jLabel3)
                                            .addComponent(jLabel7)))
                                    .addGroup(layout.createSequentialGroup()
                                        .addComponent(jComboBox3, javax.swing.GroupLayout.PREFERRED_SIZE, 154, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                        .addComponent(jLabel9)))
                                .addGap(12, 12, 12)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jTextField3)
                                    .addComponent(jTextField1)
                                    .addComponent(jTextField5)))))
                    .addGroup(layout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(jLabel1)))
                .addContainerGap(24, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(5, 5, 5)
                .addComponent(jLabel1)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jComboBox1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel2)
                    .addComponent(jLabel3)
                    .addComponent(jTextField1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel4)
                    .addComponent(jComboBox2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel7)
                    .addComponent(jTextField3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel9)
                    .addComponent(jTextField5, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel5)
                    .addComponent(jComboBox3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(47, 47, 47)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel6)
                    .addComponent(jTextField2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jButton1))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 107, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGap(27, 27, 27)
                        .addComponent(jButton2))
                    .addGroup(layout.createSequentialGroup()
                        .addGap(15, 15, 15)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel8)
                            .addComponent(jTextField4, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))))
                .addContainerGap(24, Short.MAX_VALUE))
        );
    }// </editor-fold>//GEN-END:initComponents

    private void jComboBox1ItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_jComboBox1ItemStateChanged
        jComboBox2.removeAllItems();
        try {
            SelectedCombo();
        } catch (SQLException ex) {
            Logger.getLogger(Return.class.getName()).log(Level.SEVERE, null, ex);
        }
    }//GEN-LAST:event_jComboBox1ItemStateChanged

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
        Show_Journal();
        jTextField4.setText((String) jComboBox1.getSelectedItem() + " Return " + jTextField1.getText());
    }//GEN-LAST:event_jButton1ActionPerformed

    private void jButton2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton2ActionPerformed
        try {
            AddStatement();
            UpdateInventory();
        } catch (SQLException ex) {
            Logger.getLogger(Return.class.getName()).log(Level.SEVERE, null, ex);
        }
    }//GEN-LAST:event_jButton2ActionPerformed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton jButton1;
    private javax.swing.JButton jButton2;
    private javax.swing.JComboBox<String> jComboBox1;
    private javax.swing.JComboBox<String> jComboBox2;
    private javax.swing.JComboBox<String> jComboBox3;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JTable jTable1;
    private javax.swing.JTextField jTextField1;
    private javax.swing.JTextField jTextField2;
    private javax.swing.JTextField jTextField3;
    private javax.swing.JTextField jTextField4;
    private javax.swing.JTextField jTextField5;
    // End of variables declaration//GEN-END:variables
}
