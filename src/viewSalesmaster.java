
import java.sql.*;
import java.util.ArrayList;
import javax.swing.table.DefaultTableModel;

public class viewSalesmaster extends javax.swing.JFrame {

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

    public ArrayList<getSalesmaster> getSalesmasterList() {
        String query = "";
        ArrayList<getSalesmaster> salesList = new ArrayList<getSalesmaster>();
        Connection connection = getConnection();
        if (search == false) {
            query = "SELECT * FROM Salesmaster Order by sales_no";
        }
        if (search == true) {
            query = "SELECT * FROM Salesmaster WHERE sales_" + (String) jComboBox1.getSelectedItem() + " = '" + jTextField1.getText() + "'";

        }
        Statement st;
        ResultSet rs;
        try {
            st = connection.createStatement();
            rs = st.executeQuery(query);
            System.out.println(query);
            getSalesmaster sales;
            while (rs.next()) {
                sales = new getSalesmaster(rs.getString("sales_no"),
                        rs.getString("sales_date"),
                        rs.getString("cust_code"),
                        rs.getInt("sales_total"));
                salesList.add(sales);

            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return salesList;
    }

    public void Show_In_JTable() {
        ArrayList<getSalesmaster> list = getSalesmasterList();
        DefaultTableModel model = (DefaultTableModel) jTable1.getModel();
        model.setRowCount(0);
        Object[] row = new Object[5];
        for (int i = 0; i < list.size(); i++) {
            row[0] = list.get(i).getSales_no();
            row[1] = list.get(i).getSales_date();
            row[2] = list.get(i).getCust_code();
            row[4] = list.get(i).getSales_total();
            model.addRow(row);
        }
        Show_Name();
    }

    public void Show_Name() {
        String cust_name = "";
        String product_name = "";
        Connection connection = getConnection();
        for (int i = 0; i < jTable1.getRowCount(); i++) {
            String query = "SELECT cust_name FROM customer WHERE cust_code = '" + jTable1.getValueAt(i, 2) + "'";
            Statement st;
            ResultSet rs;
            try {
                st = connection.createStatement();
                rs = st.executeQuery(query);
                while (rs.next()) {
                    cust_name = rs.getString("cust_name");
                }
                jTable1.setValueAt(cust_name, i, 3);
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

    public ArrayList<getSalesdetail> getSalesdetailList() {
        String query = "";
        ArrayList<getSalesdetail> salesdetailList = new ArrayList<getSalesdetail>();
        Connection connection = getConnection();
        if (search1 == false) {
            query = "SELECT * FROM Salesdetail Order by sales_no";
        }
        if (search1 == true) {
            String code = (String) jTable1.getModel().getValueAt(jTable1.getSelectedRow(), 0);
            query = "SELECT * FROM Salesdetail WHERE sales_no = '" + code + "'";

        }
        Statement st;
        ResultSet rs;
        try {
            st = connection.createStatement();
            rs = st.executeQuery(query);
            System.out.println(query);
            getSalesdetail salesdetail;
            while (rs.next()) {
                salesdetail = new getSalesdetail(rs.getString("sales_no"),
                        rs.getString("product_code"),
                        rs.getInt("sales_qty"),
                        rs.getInt("sales_price"),
                        rs.getInt("sales_subtotal"));
                salesdetailList.add(salesdetail);

            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return salesdetailList;
    }

    public void Show_In_JTable1() {
        ArrayList<getSalesdetail> list = getSalesdetailList();
        DefaultTableModel model = (DefaultTableModel) jTable2.getModel();
        model.setRowCount(0);
        Object[] row = new Object[6];
        for (int i = 0; i < list.size(); i++) {
            row[0] = list.get(i).getSales_no();
            row[1] = list.get(i).getProduct_code();
            row[3] = list.get(i).getSales_qty();
            row[4] = list.get(i).getSales_price();
            row[5] = list.get(i).getSales_subtotal();
            model.addRow(row);
        }
        Show_Name();
    }

    public viewSalesmaster() {
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
        jScrollPane2 = new javax.swing.JScrollPane();
        jTable2 = new javax.swing.JTable();
        jLabel3 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        jButton2 = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);

        jLabel1.setFont(new java.awt.Font("Tahoma", 1, 36)); // NOI18N
        jLabel1.setText("Sales");

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
                "Sales No", "Date", "Cust Code", "Cust Name", "Total"
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

        jTable2.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Sales No", "Product Code", "Product Name", "Price", "Subtotal"
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

        jButton2.setText("View");
        jButton2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton2ActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jScrollPane2, javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 834, Short.MAX_VALUE)
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
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 37, Short.MAX_VALUE)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel3, javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jLabel4, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 24, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jButton2, javax.swing.GroupLayout.Alignment.TRAILING))
                .addGap(18, 18, 18)
                .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 189, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
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
        getSalesmasterList();
        Show_In_JTable();
        search = false;
    }//GEN-LAST:event_jButton1ActionPerformed

    private void jButton2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton2ActionPerformed
        DefaultTableModel model = (DefaultTableModel) jTable2.getModel();
        while (model.getRowCount() > 0) {
            model.removeRow(0);
        }
        search1 = true;
        Show_In_JTable1();
        search = false;
        Show_Name();
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
