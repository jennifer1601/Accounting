
import java.sql.*;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.table.DefaultTableModel;

public class viewGL extends javax.swing.JFrame {

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

        query = "SELECT journal_date, journal_code, journal_desc, journal_debit, journal_credit FROM journal WHERE " + (String) jComboBox1.getSelectedItem() + "= '" + jTextField1.getText() + "' AND MONTH(journal_date) >= '" + jTextField2.getText() + "' AND MONTH(journal_date) <= '" + jTextField5.getText() + "'";

        System.out.println(query);
        Statement st;
        ResultSet rs;
        try {
            st = connection.createStatement();
            rs = st.executeQuery(query);
            getJournal journal;
            while (rs.next()) {
                journal = new getJournal(
                        rs.getString("journal_date"),
                        rs.getString("journal_code"),
                        rs.getString("journal_desc"),
                        rs.getInt("journal_debit"),
                        rs.getInt("journal_credit"));
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
        Object[] row = new Object[6];
        for (int i = 0; i < list.size(); i++) {
            row[0] = list.get(i).getJournal_date();
            row[1] = list.get(i).getJournal_code();
            row[2] = list.get(i).getJournal_desc();
            row[3] = list.get(i).getJournal_debit();
            row[4] = list.get(i).getJournal_credit();
            model.addRow(row);
        }
    }

    public void Show_Balance() {
        int credit = 0;
        int debit = 0;
        int dif = 0;
        int totaldebit = 0;
        int totalcredit = 0;
        for (int i = 1; i < jTable1.getRowCount(); i++) {
            jTable1.setValueAt((int) jTable1.getValueAt(0, 3) - (int) jTable1.getValueAt(0, 4), 0, 5);
            debit = (int) jTable1.getValueAt(i, 3);
            credit = (int) jTable1.getValueAt(i, 4);
            dif = debit - credit + (int) jTable1.getValueAt(i - 1, 5);
            jTable1.setValueAt(dif, i, 5);
        }
        for (int i = 0; i < jTable1.getRowCount(); i++) {
            totaldebit += Integer.parseInt((String.valueOf(jTable1.getValueAt(i, 3))));
            totalcredit += Integer.parseInt((String.valueOf(jTable1.getValueAt(i, 4))));
            jTextField3.setText(Integer.toString(totaldebit));
            jTextField4.setText(Integer.toString(totalcredit));
        }
    }

    public void Show_Prev() {
        DefaultTableModel model = (DefaultTableModel) jTable1.getModel();
        int debit = 0;
        int credit = 0;
        Statement st = null;
        ResultSet rs = null;
        Connection conn = getConnection();
        String query = "SELECT sum(journal_debit) as journal_debit FROM journal where " + (String) jComboBox1.getSelectedItem() + "= '" + jTextField1.getText() + "'  AND MONTH(journal_date) < '" + jTextField2.getText() + "'";
        System.out.println(query);
        try {
            st = conn.createStatement();
            rs = st.executeQuery(query);
            while (rs.next()) {
                debit = rs.getInt("journal_debit");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        String query1 = "SELECT sum(journal_credit) as journal_credit FROM journal where " + (String) jComboBox1.getSelectedItem() + "= '" + jTextField1.getText() + "'  AND MONTH(journal_date) < '" + jTextField2.getText() + "'";
        System.out.println(query1);
        try {
            st = conn.createStatement();
            rs = st.executeQuery(query1);
            while (rs.next()) {
                credit = rs.getInt("journal_credit");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        int prev = debit - credit;
        System.out.println(debit + " " + credit);
        System.out.println(prev);
        Object[]row = {"","","",prev,"",prev};
        model.addRow(row);
                
    }

    public viewGL() {
        initComponents();
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jScrollPane1 = new javax.swing.JScrollPane();
        jTable1 = new javax.swing.JTable();
        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        jComboBox1 = new javax.swing.JComboBox<>();
        jTextField1 = new javax.swing.JTextField();
        jButton1 = new javax.swing.JButton();
        jLabel3 = new javax.swing.JLabel();
        jTextField2 = new javax.swing.JTextField();
        jTextField3 = new javax.swing.JTextField();
        jTextField4 = new javax.swing.JTextField();
        jLabel4 = new javax.swing.JLabel();
        jTextField5 = new javax.swing.JTextField();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);

        jTable1.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Date", "Journal Code", "Description", "Debit", "Credit", "Balance"
            }
        ) {
            Class[] types = new Class [] {
                java.lang.String.class, java.lang.String.class, java.lang.String.class, java.lang.Integer.class, java.lang.Integer.class, java.lang.Integer.class
            };

            public Class getColumnClass(int columnIndex) {
                return types [columnIndex];
            }
        });
        jScrollPane1.setViewportView(jTable1);

        jLabel1.setFont(new java.awt.Font("Tahoma", 1, 36)); // NOI18N
        jLabel1.setText("General Ledger");

        jLabel2.setText("Search by :");

        jComboBox1.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Chart_No", "Chart_Name" }));

        jButton1.setText("Search");
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });

        jLabel3.setText("Month :");

        jTextField3.setHorizontalAlignment(javax.swing.JTextField.RIGHT);

        jTextField4.setHorizontalAlignment(javax.swing.JTextField.RIGHT);

        jLabel4.setText("TO");

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jScrollPane1)
                    .addComponent(jLabel1)
                    .addGroup(layout.createSequentialGroup()
                        .addGap(13, 13, 13)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(jLabel3)
                            .addComponent(jLabel2))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(jComboBox1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(jTextField1, javax.swing.GroupLayout.PREFERRED_SIZE, 194, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(jButton1))
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(jTextField2, javax.swing.GroupLayout.PREFERRED_SIZE, 159, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(jLabel4)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(jTextField5, javax.swing.GroupLayout.PREFERRED_SIZE, 193, javax.swing.GroupLayout.PREFERRED_SIZE)))))
                .addContainerGap())
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addContainerGap(522, Short.MAX_VALUE)
                .addComponent(jTextField3, javax.swing.GroupLayout.PREFERRED_SIZE, 161, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jTextField4, javax.swing.GroupLayout.PREFERRED_SIZE, 165, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(188, 188, 188))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel1)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel2)
                    .addComponent(jComboBox1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jTextField1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jButton1))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel3)
                    .addComponent(jTextField2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel4)
                    .addComponent(jTextField5, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 219, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jTextField3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jTextField4, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        pack();
        setLocationRelativeTo(null);
    }// </editor-fold>//GEN-END:initComponents

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
        Show_Prev();
        Show_In_JTable();
        Show_Balance();
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
    private javax.swing.JComboBox<String> jComboBox1;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JTable jTable1;
    private javax.swing.JTextField jTextField1;
    private javax.swing.JTextField jTextField2;
    private javax.swing.JTextField jTextField3;
    private javax.swing.JTextField jTextField4;
    private javax.swing.JTextField jTextField5;
    // End of variables declaration//GEN-END:variables
}
