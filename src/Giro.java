
import java.sql.*;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.table.DefaultTableModel;

public class Giro extends javax.swing.JPanel {
    
    static final String JDBC_DRIVER = "con.mysql.jdbc.Driver";
    static final String DB_URL = "jdbc:mysql://localhost/abc";
    static final String USER = "root";
    static final String PASS = "abc";
    
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
    
    public Giro() {
        initComponents();
        Show_In_JTable();
        try {
            AutoCode();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    
    public ArrayList<getGiro> getGiroList() {
        ArrayList<getGiro> giroList = new ArrayList<getGiro>();
        Connection connection = getConnection();
        String query = "SELECT * FROM giro";
        Statement st;
        ResultSet rs;
        try {
            st = connection.createStatement();
            rs = st.executeQuery(query);
            getGiro giro;
            while (rs.next()) {
                giro = new getGiro(rs.getString("giro_code"),
                        rs.getString("giro_date"),
                        rs.getString("giro_type"),
                        rs.getString("giro_status"),
                        rs.getInt("giro_price"));
                giroList.add(giro);
                
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return giroList;
    }
    
    public void Show_In_JTable() {
        ArrayList<getGiro> list = getGiroList();
        DefaultTableModel model = (DefaultTableModel) jTable1.getModel();
        model.setRowCount(0);
        Object[] row = new Object[5];
        for (int i = 0; i < list.size(); i++) {
            row[0] = list.get(i).getGiro_Code();
            row[1] = list.get(i).getGiro_Date();
            row[2] = list.get(i).getGiro_Type();
            row[3] = list.get(i).getGiro_Status();
            row[4] = list.get(i).getGiro_Price();
            model.addRow(row);
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
                jTextField5.setText("J01");
                isNull = false;
            } else if (nextC < 10) {
                jTextField5.setText("J0" + jCode);
            } else {
                jTextField5.setText("J" + jCode);
            }
            
        } catch (Exception e) {
            e.printStackTrace();
        }
        rs.close();
        st.close();
        conn.close();
    }
    
    public void DeleteStatement() throws SQLException {
        String code = (String) jTable1.getModel().getValueAt(jTable1.getSelectedRow(), 0);
        System.out.println(code);
        Connection conn = getConnection();
        Statement st = null;
        st = conn.createStatement();
        String query = "DELETE FROM giro WHERE giro_code = '" + code + "'";
        try {
            st.executeUpdate(query);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    
    public void Show_Journal() {
        String chart_no = "", vendor = "";
        DefaultTableModel model2 = (DefaultTableModel) jTable2.getModel();
        String type;
        type = (String) jTable1.getValueAt(jTable1.getSelectedRow(), 2);
        if (type.equals("Sales")) {
            int price = (int) jTable1.getValueAt(jTable1.getSelectedRow(), 4);
            Object[] row = {"1020", "Bank", price, "0"};
            model2.addRow(row);
            Object[] row1 = {"1070", "Giro Receivable", "0", price};
            model2.addRow(row1);
        }
        if (type.equals("Purchase")) {
            int price = (int) jTable1.getValueAt(jTable1.getSelectedRow(), 4);
            Object[] row1 = {"2030", "Giro Payable", price, "0"};
            model2.addRow(row1);
            Object[] row = {"1020", "Bank", "0", price};
            model2.addRow(row);
        }
    }
    
    public void AddStatement() throws SQLException {
        String chart_no, chart_name = "";
        int debit,credit;
        String journal_code = jTextField5.getText();
        String journal_desc = jTextField1.getText();
        String date = (String) jTable1.getModel().getValueAt(jTable1.getSelectedRow(), 1);
        Connection conn = getConnection();
        Statement st = null;
        for (int i = 0; i < jTable2.getRowCount(); i++) {
            chart_no = (String) jTable2.getValueAt(i, 0);
            chart_name = (String) jTable2.getValueAt(i, 1);
            debit = Integer.parseInt((String.valueOf(jTable2.getValueAt(i, 2))));
            credit = Integer.parseInt((String.valueOf(jTable2.getValueAt(i, 3))));
            System.out.println(chart_no + " " + chart_name + " " + debit + " " + credit + " " + date);
            st = conn.createStatement();
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
    
    public void UpdateStatus() throws SQLException {
        String code = (String) jTable1.getModel().getValueAt(jTable1.getSelectedRow(), 0);
        Connection conn = getConnection();
        Statement st = null;
        st = conn.createStatement();
        String query = "UPDATE `giro` SET giro_status = 'Paid' WHERE giro_code ='" + code + "'";
        System.out.println(query);
        try {
            st.executeUpdate(query);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jScrollPane1 = new javax.swing.JScrollPane();
        jTable1 = new javax.swing.JTable();
        jButton2 = new javax.swing.JButton();
        jButton1 = new javax.swing.JButton();
        jButton3 = new javax.swing.JButton();
        jLabel6 = new javax.swing.JLabel();
        jScrollPane2 = new javax.swing.JScrollPane();
        jTable2 = new javax.swing.JTable();
        jLabel7 = new javax.swing.JLabel();
        jTextField5 = new javax.swing.JTextField();
        jButton4 = new javax.swing.JButton();
        jLabel1 = new javax.swing.JLabel();
        jTextField1 = new javax.swing.JTextField();

        jTable1.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Code", "Date", "Type", "Status", "Price"
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

        jButton2.setText("Add");
        jButton2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton2ActionPerformed(evt);
            }
        });

        jButton1.setText("Delete");
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });

        jButton3.setText("Update");
        jButton3.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton3ActionPerformed(evt);
            }
        });

        jLabel6.setFont(new java.awt.Font("Tahoma", 1, 36)); // NOI18N
        jLabel6.setText("Giro");

        jTable2.setModel(new javax.swing.table.DefaultTableModel(
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
        jScrollPane2.setViewportView(jTable2);

        jLabel7.setText("Journal Code :");

        jButton4.setText("View");
        jButton4.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton4ActionPerformed(evt);
            }
        });

        jLabel1.setText("Description :");

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(10, 10, 10)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jLabel7)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jTextField5, javax.swing.GroupLayout.PREFERRED_SIZE, 74, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jButton4, javax.swing.GroupLayout.PREFERRED_SIZE, 71, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(0, 0, Short.MAX_VALUE))
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                                .addGap(0, 0, Short.MAX_VALUE)
                                .addComponent(jButton3)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(jButton1))
                            .addComponent(jScrollPane2, javax.swing.GroupLayout.DEFAULT_SIZE, 598, Short.MAX_VALUE)
                            .addComponent(jScrollPane1))
                        .addContainerGap())))
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jButton2, javax.swing.GroupLayout.PREFERRED_SIZE, 71, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
            .addGroup(layout.createSequentialGroup()
                .addComponent(jLabel6, javax.swing.GroupLayout.PREFERRED_SIZE, 85, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, Short.MAX_VALUE))
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel1)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jTextField1, javax.swing.GroupLayout.PREFERRED_SIZE, 208, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(jLabel6)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 109, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jButton1)
                    .addComponent(jButton3))
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel7)
                    .addComponent(jTextField5, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jButton4))
                .addGap(18, 18, 18)
                .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 134, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel1)
                    .addComponent(jTextField1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jButton2)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
    }// </editor-fold>//GEN-END:initComponents

    private void jButton2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton2ActionPerformed
        try {
            AddStatement();
            UpdateStatus();
        } catch (SQLException ex) {
            Logger.getLogger(Giro.class.getName()).log(Level.SEVERE, null, ex);
        }
        Show_In_JTable();
        try {
            AutoCode();
        } catch (SQLException ex) {
            Logger.getLogger(Giro.class.getName()).log(Level.SEVERE, null, ex);
        }
    }//GEN-LAST:event_jButton2ActionPerformed

    private void jButton3ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton3ActionPerformed
        new updateGiro().setVisible(true);
    }//GEN-LAST:event_jButton3ActionPerformed

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
        try {
            DeleteStatement();
        } catch (SQLException ex) {
            Logger.getLogger(Giro.class.getName()).log(Level.SEVERE, null, ex);
        }
        Show_In_JTable();
    }//GEN-LAST:event_jButton1ActionPerformed

    private void jButton4ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton4ActionPerformed
        DefaultTableModel model = (DefaultTableModel) jTable2.getModel();
        while (model.getRowCount() > 0) {
            model.removeRow(0);
        }
        jTextField1.setText("Giro " + (String) jTable1.getModel().getValueAt(jTable1.getSelectedRow(), 0));
        Show_Journal();
    }//GEN-LAST:event_jButton4ActionPerformed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton jButton1;
    private javax.swing.JButton jButton2;
    private javax.swing.JButton jButton3;
    private javax.swing.JButton jButton4;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JTable jTable1;
    private javax.swing.JTable jTable2;
    private javax.swing.JTextField jTextField1;
    private javax.swing.JTextField jTextField5;
    // End of variables declaration//GEN-END:variables
}
