/*
Padilla, Neilson | Sato, Troy | Unico, Aldwin Rion | Verte, Bon Axl
O1A

Project Started: May 30,2024
Project Ended: 
OBJOPROG MA3
Lost and Found Log System

Lost and Found User Window
*/

package LostAndFoundMA3;

import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.imageio.ImageIO;
import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;

public class LFuser extends javax.swing.JFrame {

    //Fields
    private Connection con;
    private ResultSet rs;
    private PreparedStatement pstate;
    
    public LFuser() {
        initComponents();
    }
    
    //SETTERS - will be using the Claimant Inffo Interface
    public String getContNo(){
        if (!txtContNo.getText().isEmpty()){
            return txtContNo.getText();
        }
        else{
            return null;
        }
    }
    public Icon getImgIcon(){
        if (lblImage.getIcon() != null){
            return lblImage.getIcon();
        }
        else{
            return null;
        }
    }
    
    //Show on Table the Search
    public void ShowSearch(){
        //Initialize the format of the table column
        Object [] column = {"Item Type", "Brand", "Color", "Others", "Location", "Date Found"};
        DefaultTableModel model = new DefaultTableModel(column, 0);
        Table.setModel(model);
        
        try {
            con = DriverManager.getConnection("jdbc:mysql://localhost:3306/lostandfoundma3","root","");

            //Set string builder to include in the query the items that are only needed.
            StringBuilder sb = new StringBuilder();
            //Set parameter list for listing parameters that are only needed.
            List<String> params = new ArrayList<>();

            //Checks if the text field contains something
            if(!txtType.getText().isEmpty()){
                //Appends to the string builder if the text field contains something
                sb.append("ItemType =? AND ");
                //Adds to parameter list if the text field contains something
                params.add(txtType.getText());
            }

            if(!txtBrand.getText().isEmpty()){
                sb.append("Brand =? AND ");
                params.add(txtBrand.getText());
            }

            if(!txtColor.getText().isEmpty()){
                sb.append("Color =? AND ");
                params.add(txtColor.getText());
            }

            if(!txtOthers.getText().isEmpty()){
                sb.append("Others =? AND ");
                params.add(txtOthers.getText());
            }

            if(!txtLocation.getText().isEmpty()){
                sb.append("Location =? AND ");
                params.add(txtLocation.getText());
            }
            
            if(dateDateFound.getDate()!= null){
                SimpleDateFormat sdf = new SimpleDateFormat ("yyyy-MM-dd");
                String dateStr = sdf.format(dateDateFound.getDate());
                sb.append("DateSurrendered =? AND ");
                params.add(dateStr);
            }
            
            //Checks is there is something to contain in the query thgrough the string builder.
            if(sb.length() > 0){
                //After all the inclusions, remove the last 5 characters or the " AND " of the string builder.
                sb.delete(sb.length() - 5, sb.length());
            }

            //Set the query in a variable
            String finalQuery = "SELECT ItemType, Brand, Color, Others, Location, DateSurrendered FROM `lfunclaimeditems` WHERE " + sb.toString();

            //Set the connection to a prepared statement
            pstate = con.prepareStatement(finalQuery);

            //Set to the prepared statement all applicable parameters to get from the database
            for (int i = 0; i < params.size(); i++) {
                pstate.setString(i + 1, params.get(i));
            }

            //Execute the query
            rs = pstate.executeQuery();
            
            //Selects all applicaple rows, hence in a while loop.
            while(rs.next()){
                String itemType = rs.getString("ItemType");
                String brand = rs.getString("Brand");
                String color = rs.getString("Color");
                String others = rs.getString("Others");
                String location = rs.getString("Location");
                String dateSurrendered = rs.getString("DateSurrendered");

                //Add the data to the table.
                model.addRow(new Object[]{itemType, brand, color, others, location, dateSurrendered});
            }            
        }catch (SQLException ex){
            ex.printStackTrace();
        }
    }    
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel1 = new javax.swing.JPanel();
        jPanel2 = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        jPanel3 = new javax.swing.JPanel();
        jLabel3 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();
        jLabel6 = new javax.swing.JLabel();
        jLabel7 = new javax.swing.JLabel();
        txtType = new javax.swing.JTextField();
        txtColor = new javax.swing.JTextField();
        txtBrand = new javax.swing.JTextField();
        txtOthers = new javax.swing.JTextField();
        buttonSearch = new javax.swing.JButton();
        jScrollPane1 = new javax.swing.JScrollPane();
        Table = new javax.swing.JTable();
        jLabel8 = new javax.swing.JLabel();
        buttonClaim = new javax.swing.JButton();
        jLabel9 = new javax.swing.JLabel();
        dateDateFound = new com.toedter.calendar.JDateChooser();
        jLabel10 = new javax.swing.JLabel();
        txtLocation = new javax.swing.JTextField();
        jLabel11 = new javax.swing.JLabel();
        buttonClear = new javax.swing.JButton();
        jLabel12 = new javax.swing.JLabel();
        txtContNo = new javax.swing.JTextField();
        buttonExit = new javax.swing.JButton();
        jLabel13 = new javax.swing.JLabel();
        lblImage = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        jPanel1.setBackground(new java.awt.Color(0, 112, 60));

        jPanel2.setBackground(new java.awt.Color(255, 255, 255));

        jLabel1.setFont(new java.awt.Font("Century Gothic", 1, 36)); // NOI18N
        jLabel1.setText("LOST and FOUND");

        jPanel3.setBackground(new java.awt.Color(0, 112, 60));

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 0, Short.MAX_VALUE)
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 10, Short.MAX_VALUE)
        );

        jLabel3.setFont(new java.awt.Font("Century Gothic", 1, 18)); // NOI18N
        jLabel3.setText("Found Items");

        jLabel4.setFont(new java.awt.Font("Century Gothic", 0, 14)); // NOI18N
        jLabel4.setText("Item Type:");

        jLabel5.setFont(new java.awt.Font("Century Gothic", 0, 14)); // NOI18N
        jLabel5.setText("Color:");

        jLabel6.setFont(new java.awt.Font("Century Gothic", 0, 14)); // NOI18N
        jLabel6.setText("Brand:");

        jLabel7.setFont(new java.awt.Font("Century Gothic", 0, 14)); // NOI18N
        jLabel7.setText("Others:");

        buttonSearch.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        buttonSearch.setText("SEARCH");
        buttonSearch.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                buttonSearchActionPerformed(evt);
            }
        });

        Table.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {

            }
        )
    );
    Table.addMouseListener(new java.awt.event.MouseAdapter() {
        public void mouseClicked(java.awt.event.MouseEvent evt) {
            TableMouseClicked(evt);
        }
    });
    jScrollPane1.setViewportView(Table);

    jLabel8.setFont(new java.awt.Font("Century Gothic", 1, 18)); // NOI18N
    jLabel8.setText("SEARCH ITEM:");

    buttonClaim.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
    buttonClaim.setText("CLAIM");
    buttonClaim.addActionListener(new java.awt.event.ActionListener() {
        public void actionPerformed(java.awt.event.ActionEvent evt) {
            buttonClaimActionPerformed(evt);
        }
    });

    jLabel9.setFont(new java.awt.Font("Century Gothic", 0, 14)); // NOI18N
    jLabel9.setText("Date Found:");

    dateDateFound.setDateFormatString("yyyy-MM-dd");

    jLabel10.setFont(new java.awt.Font("Century Gothic", 0, 14)); // NOI18N
    jLabel10.setText("Location:");

    jLabel11.setFont(new java.awt.Font("Century Gothic", 0, 12)); // NOI18N
    jLabel11.setText("Type in all fields that apply.");

    buttonClear.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
    buttonClear.setForeground(new java.awt.Color(153, 0, 0));
    buttonClear.setText("Clear");
    buttonClear.addActionListener(new java.awt.event.ActionListener() {
        public void actionPerformed(java.awt.event.ActionEvent evt) {
            buttonClearActionPerformed(evt);
        }
    });

    jLabel12.setFont(new java.awt.Font("Century Gothic", 0, 14)); // NOI18N
    jLabel12.setText("Control No.:");

    txtContNo.setEditable(false);

    buttonExit.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
    buttonExit.setForeground(new java.awt.Color(153, 0, 0));
    buttonExit.setText("BACK TO MAIN MENU");
    buttonExit.addActionListener(new java.awt.event.ActionListener() {
        public void actionPerformed(java.awt.event.ActionEvent evt) {
            buttonExitActionPerformed(evt);
        }
    });

    jLabel13.setIcon(new javax.swing.ImageIcon(getClass().getResource("/LostAndFoundMA3/Images/L&F (2).png"))); // NOI18N

    lblImage.setBorder(javax.swing.BorderFactory.createEtchedBorder());
    lblImage.setPreferredSize(new java.awt.Dimension(250, 250));

    javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
    jPanel2.setLayout(jPanel2Layout);
    jPanel2Layout.setHorizontalGroup(
        jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
        .addGroup(jPanel2Layout.createSequentialGroup()
            .addContainerGap()
            .addComponent(jLabel13)
            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
            .addComponent(jLabel1)
            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
            .addComponent(jPanel3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        .addGroup(jPanel2Layout.createSequentialGroup()
            .addGap(22, 22, 22)
            .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                .addComponent(buttonExit)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel3)
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 740, javax.swing.GroupLayout.PREFERRED_SIZE)))
            .addContainerGap(29, Short.MAX_VALUE))
        .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel2Layout.createSequentialGroup()
            .addGap(86, 86, 86)
            .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                .addGroup(jPanel2Layout.createSequentialGroup()
                    .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                        .addComponent(jLabel8)
                        .addGroup(jPanel2Layout.createSequentialGroup()
                            .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                .addComponent(jLabel4)
                                .addComponent(jLabel5)
                                .addComponent(jLabel6)
                                .addComponent(jLabel7))
                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                            .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                .addComponent(txtType)
                                .addComponent(txtColor)
                                .addComponent(txtBrand)
                                .addComponent(txtOthers)))
                        .addGroup(jPanel2Layout.createSequentialGroup()
                            .addComponent(jLabel10)
                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                            .addComponent(txtLocation))
                        .addGroup(jPanel2Layout.createSequentialGroup()
                            .addComponent(jLabel9)
                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                            .addComponent(dateDateFound, javax.swing.GroupLayout.PREFERRED_SIZE, 120, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addComponent(jLabel11))
                    .addGap(8, 8, 8))
                .addGroup(javax.swing.GroupLayout.Alignment.LEADING, jPanel2Layout.createSequentialGroup()
                    .addComponent(buttonClear)
                    .addGap(72, 72, 72)
                    .addComponent(buttonSearch)))
            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(jPanel2Layout.createSequentialGroup()
                    .addComponent(jLabel12)
                    .addGap(18, 18, 18)
                    .addComponent(txtContNo, javax.swing.GroupLayout.PREFERRED_SIZE, 150, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addContainerGap())
                .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel2Layout.createSequentialGroup()
                    .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                        .addComponent(buttonClaim, javax.swing.GroupLayout.PREFERRED_SIZE, 91, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(lblImage, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGap(120, 120, 120))))
    );
    jPanel2Layout.setVerticalGroup(
        jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
        .addGroup(jPanel2Layout.createSequentialGroup()
            .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(jPanel2Layout.createSequentialGroup()
                    .addContainerGap()
                    .addComponent(jLabel13))
                .addGroup(jPanel2Layout.createSequentialGroup()
                    .addGap(29, 29, 29)
                    .addComponent(jLabel1))
                .addGroup(jPanel2Layout.createSequentialGroup()
                    .addGap(49, 49, 49)
                    .addComponent(jPanel3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
            .addGap(18, 18, 18)
            .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(jPanel2Layout.createSequentialGroup()
                    .addComponent(jLabel8)
                    .addGap(13, 13, 13)
                    .addComponent(jLabel11)
                    .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                    .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(jLabel4)
                        .addComponent(txtType, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                    .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(jLabel6)
                        .addComponent(txtBrand, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                    .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(jLabel5)
                        .addComponent(txtColor, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                    .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(jLabel7)
                        .addComponent(txtOthers, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                    .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(jLabel10)
                        .addComponent(txtLocation, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                    .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addComponent(dateDateFound, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(jLabel9))
                    .addGap(18, 18, 18)
                    .addComponent(buttonClear))
                .addGroup(jPanel2Layout.createSequentialGroup()
                    .addGap(274, 274, 274)
                    .addComponent(buttonSearch))
                .addGroup(jPanel2Layout.createSequentialGroup()
                    .addGap(6, 6, 6)
                    .addComponent(lblImage, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGap(18, 18, 18)
                    .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(jLabel12)
                        .addComponent(txtContNo, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGap(14, 14, 14)
                    .addComponent(buttonClaim)))
            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addComponent(jLabel3)
            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
            .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 270, javax.swing.GroupLayout.PREFERRED_SIZE)
            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
            .addComponent(buttonExit)
            .addContainerGap())
    );

    javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
    jPanel1.setLayout(jPanel1Layout);
    jPanel1Layout.setHorizontalGroup(
        jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
        .addGroup(jPanel1Layout.createSequentialGroup()
            .addContainerGap()
            .addComponent(jPanel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addContainerGap())
    );
    jPanel1Layout.setVerticalGroup(
        jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
        .addGroup(jPanel1Layout.createSequentialGroup()
            .addContainerGap()
            .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
            .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
    );

    javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
    getContentPane().setLayout(layout);
    layout.setHorizontalGroup(
        layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
        .addGroup(layout.createSequentialGroup()
            .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
            .addGap(0, 0, Short.MAX_VALUE))
    );
    layout.setVerticalGroup(
        layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
        .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
    );

    pack();
    }// </editor-fold>//GEN-END:initComponents

    private void buttonClearActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_buttonClearActionPerformed
        //Clear all text fields
        txtType.setText("");
        txtBrand.setText("");
        txtColor.setText("");
        txtOthers.setText("");
        dateDateFound.setCalendar(null);
        txtLocation.setText("");
        lblImage.setIcon(null);
        txtContNo.setText("");
        Table.clearSelection();
    }//GEN-LAST:event_buttonClearActionPerformed

    private void buttonClaimActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_buttonClaimActionPerformed
        //Checks if the two needed fields has a value
        if (txtContNo.getText() != null && lblImage.getIcon() != null){
            //Set into a variable
            String contNo = txtContNo.getText();
            Icon imgICON = lblImage.getIcon();
            //Call the Claimant Info JFrame
            ClaimantInfo Cinfo = new ClaimantInfo(contNo, imgICON);
            Cinfo.setVisible(true);
            dispose();
        }
        else{
            if (txtType.getText().isEmpty() && txtBrand.getText().isEmpty() && txtColor.getText().isEmpty() && txtOthers.getText().isEmpty() && 
                    txtLocation.getText().isEmpty() && dateDateFound.getDate() == null){
                JOptionPane.showMessageDialog(null, "Please fill in at least one field.", "Item Search", JOptionPane.ERROR_MESSAGE);
            } else if (Table.getSelectionModel().isSelectionEmpty()){
                JOptionPane.showMessageDialog(null, "Please select an item on the table first.", "Item Claim", JOptionPane.ERROR_MESSAGE);
            }
        }
    }//GEN-LAST:event_buttonClaimActionPerformed

    private void TableMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_TableMouseClicked
        try{
            //Initialize the row as the one selected
            int row = Table.getSelectedRow();
            if (row == -1){
                return;
            }
            //Initialize variables for all the columns of the row
            String type = (String) Table.getValueAt(row, 0);
            String brand = (String) Table.getValueAt(row, 1);
            String color = (String) Table.getValueAt(row, 2);
            String others = (String) Table.getValueAt(row, 3);
            String location = (String) Table.getValueAt(row, 4);
            String dateStr = (String) Table.getValueAt(row, 5);
            java.sql.Date dateCLM = java.sql.Date.valueOf(dateStr);
            
            //Set the query
            String qry = "SELECT ImageID, ControlNo FROM `lfunclaimeditems` WHERE ItemType = ? AND Brand = ? AND Color = ? AND Others = ? AND "
                    + "Location = ? AND DateSurrendered = ?";
            
            try (Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/lostandfoundma3", "root", "");
                 PreparedStatement pstate = con.prepareStatement(qry)){
                pstate.setString(1, type);
                pstate.setString(2, brand);
                pstate.setString(3, color);
                pstate.setString(4, others);
                pstate.setString(5, location);
                pstate.setDate(6, dateCLM);
                
                try (ResultSet rs = pstate.executeQuery()){
                    if(rs.next()){
                        //Set the url from the database into Icon for a label panel
                        String imgID = rs.getString("ImageID");
                        URL url = new URL(imgID);
                        BufferedImage image = ImageIO.read(url);
                        Image scaledImage = image.getScaledInstance(250,250, Image.SCALE_SMOOTH);
                        ImageIcon icon = new ImageIcon(scaledImage);
                        lblImage.setIcon(icon);
                        
                        //Set the Control Number
                        String cont = rs.getString("ControlNo");
                        txtContNo.setText(cont);
                    }
                } catch (MalformedURLException ex) {
                    Logger.getLogger(LFuser.class.getName()).log(Level.SEVERE, null, ex);
                } catch (IOException ex) {
                    Logger.getLogger(LFuser.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        } catch (SQLException ex) {
            Logger.getLogger(LFuser.class.getName()).log(Level.SEVERE, null, ex);
        }
    }//GEN-LAST:event_TableMouseClicked

    private void buttonSearchActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_buttonSearchActionPerformed
        if (txtType.getText().isEmpty() && txtBrand.getText().isEmpty() && txtColor.getText().isEmpty() && txtOthers.getText().isEmpty() && 
                txtLocation.getText().isEmpty() && dateDateFound.getDate() == null){
            JOptionPane.showMessageDialog(null, "Please fill in at least one field.", "Item Search", JOptionPane.ERROR_MESSAGE);
        } 
        else{
            ShowSearch();
            
            if(Table.getRowCount() == 0){
                JOptionPane.showMessageDialog(null, "No item found.", "Item Search", JOptionPane.ERROR_MESSAGE);
            }
        }
    }//GEN-LAST:event_buttonSearchActionPerformed

    private void buttonExitActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_buttonExitActionPerformed
        //Back to main menu
        MainLogin main = new MainLogin();
        main.setVisible(true);
        dispose();
    }//GEN-LAST:event_buttonExitActionPerformed

    public static void main(String args[]) {
        
        java.awt.EventQueue.invokeLater(() -> {
            new LFuser().setVisible(true);
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JTable Table;
    private javax.swing.JButton buttonClaim;
    private javax.swing.JButton buttonClear;
    private javax.swing.JButton buttonExit;
    private javax.swing.JButton buttonSearch;
    private com.toedter.calendar.JDateChooser dateDateFound;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel12;
    private javax.swing.JLabel jLabel13;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JLabel lblImage;
    private javax.swing.JTextField txtBrand;
    private javax.swing.JTextField txtColor;
    private javax.swing.JTextField txtContNo;
    private javax.swing.JTextField txtLocation;
    private javax.swing.JTextField txtOthers;
    private javax.swing.JTextField txtType;
    // End of variables declaration//GEN-END:variables
}