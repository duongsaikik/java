/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package library;

import DAO.BlackListDAO;
import DAO.ViolationDAO;
import DataHelper.Notify;
import DataHelper.Validator;
import java.util.List;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author Acer
 */
public class BlackList extends javax.swing.JPanel {
    private DefaultTableModel table;
    /**
     * Creates new form BlackList
     */
    public BlackList() {
        initComponents();
        initTable();
        loadData();
        loadVio();
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jLabel1 = new javax.swing.JLabel();
        txtreader = new javax.swing.JTextField();
        jLabel2 = new javax.swing.JLabel();
        cbVT = new javax.swing.JComboBox<>();
        btnadd = new javax.swing.JButton();
        jScrollPane1 = new javax.swing.JScrollPane();
        tableBlackList = new javax.swing.JTable();
        jButton1 = new javax.swing.JButton();

        setBackground(new java.awt.Color(204, 204, 255));

        jLabel1.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        jLabel1.setForeground(new java.awt.Color(0, 51, 204));
        jLabel1.setText("Mã đọc giả");

        txtreader.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));

        jLabel2.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        jLabel2.setForeground(new java.awt.Color(0, 51, 204));
        jLabel2.setText("Loại vi phạm");

        btnadd.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        btnadd.setForeground(new java.awt.Color(255, 0, 102));
        btnadd.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Image/them.png"))); // NOI18N
        btnadd.setText("Thêm");
        btnadd.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        btnadd.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnaddActionPerformed(evt);
            }
        });

        tableBlackList.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null}
            },
            new String [] {
                "Title 1", "Title 2", "Title 3", "Title 4"
            }
        ));
        jScrollPane1.setViewportView(tableBlackList);

        jButton1.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        jButton1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Image/reset.png"))); // NOI18N
        jButton1.setText("Reset");
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 77, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(txtreader, javax.swing.GroupLayout.PREFERRED_SIZE, 103, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(24, 24, 24)
                .addComponent(jLabel2)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(cbVT, javax.swing.GroupLayout.PREFERRED_SIZE, 122, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(btnadd)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jButton1)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
            .addComponent(jScrollPane1, javax.swing.GroupLayout.Alignment.TRAILING)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel1)
                    .addComponent(txtreader, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel2)
                    .addComponent(cbVT, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnadd)
                    .addComponent(jButton1))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 294, Short.MAX_VALUE))
        );
    }// </editor-fold>//GEN-END:initComponents

    private void btnaddActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnaddActionPerformed
        // TODO add your handling code here:
        StringBuilder sb=new StringBuilder();
        Validator.CheckText(txtreader, sb, "Chưa nhập tên danh mục");
         if(sb.length()>0)
            {
                Notify.ChickenMessageDialog(this, sb.toString(), "Warning");
                return;
            }
        try {
          Model.BlackList s=new Model.BlackList();
          BlackListDAO st=new BlackListDAO();
          Model.Violation vio=st.FinById(cbVT.getSelectedItem().toString());
          if(vio!=null)
          {
              s.setReaderID(txtreader.getText());
              s.setVTcateid(vio.getId());
          }
          if(st.insertBlack(s))
          {
              Notify.ChickenMessageDialog(this, "Thêm danh sách đen thành công", "Thông báo");
              loadData();
          }
          else{
              Notify.ChickenErrorMessageDialog(this, "Đã xảy ra lỗi vui lòng xem lại", "Cảnh báo");
          }
        } catch (Exception e) {
            e.printStackTrace();//in ra chi tiết lỗi
            Notify.ChickenErrorMessageDialog(this, e.getMessage(), "Lỗi");
        }
    }//GEN-LAST:event_btnaddActionPerformed

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
        // TODO add your handling code here:
        cbVT.removeAllItems();
        loadVio();
        loadData();
    }//GEN-LAST:event_jButton1ActionPerformed
    private void initTable()
    {
        table=new DefaultTableModel();
        table.setColumnIdentifiers(new String[]{"ID","Mã đọc giả","Mã loại vi phạm"});//set danh sách cột trong table
        tableBlackList.setModel(table);
    }
    public void loadData()
    {
        try {
            BlackListDAO sf=new  BlackListDAO();
            List<Model.BlackList> list=sf.FindBlackList();
            table.setRowCount(0);//trc khi cap nhâp xoa hết data r update lại
            for(Model.BlackList s:list)
            {
                table.addRow(new Object[]{
                    s.getId(),s.getReaderID(),s.getVTcateid()
                });
            }
            table.fireTableDataChanged();//cập nhập hiển thị dữ liệu
        } catch (Exception e) {
        }
    }       
    public void loadVio()
    {
        try{
            ViolationDAO vio=new ViolationDAO();
            List<Model.Violation> list=vio.FindViolation();
            for(Model.Violation v:list)
            {
                cbVT.addItem(v.getName());
            }
        }catch(Exception e)
        {
            
        }
    }
    private static String id;
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnadd;
    private javax.swing.JComboBox<String> cbVT;
    private javax.swing.JButton jButton1;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JTable tableBlackList;
    private javax.swing.JTextField txtreader;
    // End of variables declaration//GEN-END:variables
}
