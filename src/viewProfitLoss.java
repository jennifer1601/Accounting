
import java.sql.*;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.table.DefaultTableModel;

public class viewProfitLoss extends javax.swing.JFrame {

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

    public ArrayList<getJournal> getJournalList() {
        String query = "";
        ArrayList<getJournal> journalList = new ArrayList<getJournal>();
        Connection connection = getConnection();
        query = "SELECT chart_name FROM journal WHERE chart_no > '4000' AND chart_no <'5000'";
        Statement st;
        ResultSet rs;
        try {
            st = connection.createStatement();
            rs = st.executeQuery(query);
            getJournal journal;
            while (rs.next()) {
                journal = new getJournal(
                        rs.getString("chart_name"));
                journalList.add(journal);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return journalList;
    }

    public void Show_In_JTable() {
        ArrayList<getJournal> list = getJournalList();
        DefaultTableModel model = (DefaultTableModel) jTable1.getModel();
        model.setRowCount(0);
        Object[] row = new Object[2];
        for (int i = 0; i < list.size(); i++) {
            row[0] = list.get(i).getChart_name();
            model.addRow(row);
        }
    }

    public ArrayList<getJournal> getJournalList1() {
        String query = "";
        ArrayList<getJournal> journalList = new ArrayList<getJournal>();
        Connection connection = getConnection();
        query = "SELECT chart_name FROM journal WHERE chart_no >= '6000' AND chart_no <'7000'";
        Statement st;
        ResultSet rs;
        try {
            st = connection.createStatement();
            rs = st.executeQuery(query);
            getJournal journal;
            while (rs.next()) {
                journal = new getJournal(
                        rs.getString("chart_name"));
                journalList.add(journal);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return journalList;
    }

    public void Show_In_JTable1() {
        ArrayList<getJournal> list = getJournalList1();
        DefaultTableModel model = (DefaultTableModel) jTable3.getModel();
        model.setRowCount(0);
        Object[] row = new Object[2];
        for (int i = 0; i < list.size(); i++) {
            row[0] = list.get(i).getChart_name();
            model.addRow(row);
        }
    }

    public void Show_Balance1() throws SQLException {
        Statement st = null;
        ResultSet rs = null;
        Connection conn = getConnection();
        for (int i = 0; i < jTable1.getRowCount(); i++) {
            int debit = 0;
            int credit = 0;
            String query = "SELECT sum(journal_debit) as journal_debit FROM journal where chart_name = '" + (String) jTable1.getValueAt(i, 0) + "' AND MONTH(journal_date) >= '" + jTextField2.getText() + "' AND MONTH(journal_date) <= '" + jTextField5.getText() + "'";
            try {
                st = conn.createStatement();
                System.out.println(query);
                rs = st.executeQuery(query);
                while (rs.next()) {
                    debit = rs.getInt("journal_debit");
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
            String query1 = "SELECT sum(journal_credit) as journal_credit FROM journal where chart_name = '" + (String) jTable1.getValueAt(i, 0) + "' AND MONTH(journal_date) >= '" + jTextField2.getText() + "' AND MONTH(journal_date) <= '" + jTextField5.getText() + "'";
            try {
                st = conn.createStatement();
                System.out.println(query1);
                rs = st.executeQuery(query1);
                while (rs.next()) {
                    credit = rs.getInt("journal_credit");
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
            int balance = debit - credit;
            balance = -1 * balance;
            jTable1.setValueAt(balance, i, 1);
        }
        for (int i = 0; i < jTable3.getRowCount(); i++) {
            int debit = 0;
            int credit = 0;
            String query = "SELECT sum(journal_debit) as journal_debit FROM journal where chart_name = '" + (String) jTable3.getValueAt(i, 0) + "' AND MONTH(journal_date) >= '" + jTextField2.getText() + "' AND MONTH(journal_date) <= '" + jTextField5.getText() + "'";
            try {
                st = conn.createStatement();
                System.out.println(query);
                rs = st.executeQuery(query);
                while (rs.next()) {
                    debit = rs.getInt("journal_debit");
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
            String query1 = "SELECT sum(journal_credit) as journal_credit FROM journal where chart_name = '" + (String) jTable3.getValueAt(i, 0) + "' AND MONTH(journal_date) >= '" + jTextField2.getText() + "' AND MONTH(journal_date) <= '" + jTextField5.getText() + "'";
            try {
                st = conn.createStatement();
                System.out.println(query1);
                rs = st.executeQuery(query1);
                while (rs.next()) {
                    credit = rs.getInt("journal_credit");
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
            int balance = debit - credit;
            jTable3.setValueAt(balance, i, 1);
        }
    }

    public ArrayList<getInventory> getInventoryList() {
        ArrayList<getInventory> inventoryList = new ArrayList<getInventory>();
        Connection connection = getConnection();
        String query = "SELECT product_code FROM inventory";
        Statement st;
        ResultSet rs;
        try {
            st = connection.createStatement();
            rs = st.executeQuery(query);
            getInventory inventory;
            while (rs.next()) {
                inventory = new getInventory(rs.getString("product_code"));
                inventoryList.add(inventory);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return inventoryList;
    }

    public void Show_BeginningInventory() {
        ArrayList<getInventory> list = getInventoryList();
        int debit = 0;
        int credit = 0;
        Statement st = null;
        ResultSet rs = null;
        Connection conn = getConnection();
        for (int i = 0; i < list.size(); i++) {
            String query = "SELECT sum(purchase_qty) as purchase_qty FROM purchasedetail where product_code = '" + list.get(i).getProductCode() + "' AND MONTH(purchase_date) < '" + jTextField2.getText() + "'";
            try {
                st = conn.createStatement();
                rs = st.executeQuery(query);
                while (rs.next()) {
                    debit = rs.getInt("purchase_qty");
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
            String query1 = "SELECT sum(sales_qty) as sales_qty FROM salesdetail where product_code = '" + list.get(i).getProductCode() + "' AND MONTH(sales_date) < '" + jTextField2.getText() + "'";
            try {
                st = conn.createStatement();
                rs = st.executeQuery(query1);
                while (rs.next()) {
                    credit = rs.getInt("sales_qty");
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
            int begin = debit - credit;

            jTable1.setValueAt(debit - credit, i, 3);
        }
    }

    public void showTotal() {
        
        for (int i = 0; i < jTable1.getModel().getRowCount(); i++) {
            int total = 0;
            total += Integer.parseInt((String.valueOf(jTable1.getValueAt(i, 1))));
            jTextField1.setText(Integer.toString(total));
        }
        for (int i = 0; i < jTable3.getModel().getRowCount(); i++) {
            int total2 = 0;
            total2 += Integer.parseInt((String.valueOf(jTable3.getValueAt(i, 1))));
            jTextField4.setText(Integer.toString(total2));
        }
    }

    public viewProfitLoss() {
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
        jLabel5 = new javax.swing.JLabel();
        jScrollPane2 = new javax.swing.JScrollPane();
        jTable2 = new javax.swing.JTable();
        jLabel6 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        jTextField1 = new javax.swing.JTextField();
        jLabel7 = new javax.swing.JLabel();
        jTextField3 = new javax.swing.JTextField();
        jScrollPane3 = new javax.swing.JScrollPane();
        jTable3 = new javax.swing.JTable();
        jLabel8 = new javax.swing.JLabel();
        jLabel9 = new javax.swing.JLabel();
        jTextField4 = new javax.swing.JTextField();
        jLabel10 = new javax.swing.JLabel();
        jTextField6 = new javax.swing.JTextField();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);

        jTable1.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Name", "Price"
            }
        ) {
            Class[] types = new Class [] {
                java.lang.String.class, java.lang.String.class
            };

            public Class getColumnClass(int columnIndex) {
                return types [columnIndex];
            }
        });
        jScrollPane1.setViewportView(jTable1);

        jLabel1.setFont(new java.awt.Font("Tahoma", 1, 36)); // NOI18N
        jLabel1.setText("Profit Loss");

        jButton1.setText("Search");
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });

        jLabel3.setText("Month :");

        jLabel4.setText("TO");

        jLabel5.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jLabel5.setText("Sales Revenue");

        jTable2.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Name", "Price"
            }
        ) {
            Class[] types = new Class [] {
                java.lang.String.class, java.lang.String.class
            };

            public Class getColumnClass(int columnIndex) {
                return types [columnIndex];
            }
        });
        jScrollPane2.setViewportView(jTable2);

        jLabel6.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jLabel6.setText("Cost of Goods Sold");

        jLabel2.setText("Total Sales Revenue :");

        jTextField1.setText("jTextField1");

        jLabel7.setText("Total COGS :");

        jTextField3.setText("jTextField3");

        jTable3.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Name", "Price"
            }
        ) {
            Class[] types = new Class [] {
                java.lang.String.class, java.lang.String.class
            };

            public Class getColumnClass(int columnIndex) {
                return types [columnIndex];
            }
        });
        jScrollPane3.setViewportView(jTable3);

        jLabel8.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jLabel8.setText("Operational Expenditure");

        jLabel9.setText("Total Operational Expenditure :");

        jTextField4.setText("jTextField4");

        jLabel10.setFont(new java.awt.Font("Tahoma", 2, 18)); // NOI18N
        jLabel10.setText("Net Profit (Loss) :");

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel1)
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(jLabel3)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(jTextField2, javax.swing.GroupLayout.PREFERRED_SIZE, 159, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(jLabel4)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(jTextField5, javax.swing.GroupLayout.PREFERRED_SIZE, 193, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(jButton1))
                            .addComponent(jLabel6)
                            .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                .addGroup(layout.createSequentialGroup()
                                    .addComponent(jLabel7)
                                    .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                    .addComponent(jTextField3, javax.swing.GroupLayout.PREFERRED_SIZE, 223, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                        .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel5)
                            .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                .addGroup(layout.createSequentialGroup()
                                    .addComponent(jLabel2)
                                    .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                    .addComponent(jTextField1, javax.swing.GroupLayout.PREFERRED_SIZE, 224, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 68, Short.MAX_VALUE)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(jLabel8)
                            .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                .addComponent(jScrollPane3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGroup(layout.createSequentialGroup()
                                    .addComponent(jLabel9)
                                    .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                    .addComponent(jTextField4, javax.swing.GroupLayout.PREFERRED_SIZE, 223, javax.swing.GroupLayout.PREFERRED_SIZE)))
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(jLabel10)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(jTextField6)))
                        .addGap(24, 24, 24))))
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
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jLabel5)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 158, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel2)
                            .addComponent(jTextField1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jLabel8)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jScrollPane3, javax.swing.GroupLayout.PREFERRED_SIZE, 158, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel9)
                            .addComponent(jTextField4, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))))
                .addGap(22, 22, 22)
                .addComponent(jLabel6)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 158, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(layout.createSequentialGroup()
                        .addGap(91, 91, 91)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel10)
                            .addComponent(jTextField6, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel7)
                    .addComponent(jTextField3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(48, Short.MAX_VALUE))
        );

        pack();
        setLocationRelativeTo(null);
    }// </editor-fold>//GEN-END:initComponents

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
        Show_In_JTable();
        Show_In_JTable1();
        try {
            Show_Balance1();
            showTotal();
        } catch (SQLException ex) {
            Logger.getLogger(viewProfitLoss.class.getName()).log(Level.SEVERE, null, ex);
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
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JScrollPane jScrollPane3;
    private javax.swing.JTable jTable1;
    private javax.swing.JTable jTable2;
    private javax.swing.JTable jTable3;
    private javax.swing.JTextField jTextField1;
    private javax.swing.JTextField jTextField2;
    private javax.swing.JTextField jTextField3;
    private javax.swing.JTextField jTextField4;
    private javax.swing.JTextField jTextField5;
    private javax.swing.JTextField jTextField6;
    // End of variables declaration//GEN-END:variables
}
