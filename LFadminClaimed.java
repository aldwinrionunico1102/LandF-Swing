/*
Padilla, Neilson | Sato, Troy | Unico, Aldwin Rion | Verte, Bon Axl
O1A

Project Started: May 30,2024
Project Ended: 
OBJOPROG MA3
Lost and Found Log System

Lost and Found Admin (Claimed) Window
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
import javax.swing.ImageIcon;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;

public class LFadminClaimed extends javax.swing.JFrame {

    //Fields
    private Connection con;
    private ResultSet rs;
    private PreparedStatement pstate;
    
    //Constructor 
    public LFadminClaimed() {
        initComponents();
    }

    //Show on Table the Search
    public void ShowSearch(){
        //Initialize the format of the table column
        Object [] column = {"Control No", "Item Type", "Brand", "Color", "Others", "Location", "Date Found", "Image ID", 
            "Person Surrendered", "Claimant Name", "Claimant ID", "Claimant Designation", "Date Claimed"};
        DefaultTableModel model = new DefaultTableModel(column, 0);
        Table.setModel(model);
        
        try {
            con = DriverManager.getConnection("jdbc:mysql://localhost:3306/lostandfoundma3","root","");

            //Set string builder to include in the query the items that are only needed.
            StringBuilder sb = new StringBuilder();
            //Set parameter list for listing parameters that are only needed.
            List<String> params = new ArrayList<>();

            //Checks if the text field contains something
            if(!txtContNo.getText().isEmpty()){
                //Appends to the string builder if the text field contains something
                sb.append("ControlNo =? AND ");
                //Adds to parameter list if the text field contains something
                params.add(txtType.getText());
            }
            if (!txtContNo.getText().isEmpty()) {
            sb.append("ControlNo = ? AND ");
            params.add(txtContNo.getText());
            }
            if (!txtType.getText().isEmpty()) {
                sb.append("ItemType = ? AND ");
                params.add(txtType.getText());
            }
            if (!txtBrand.getText().isEmpty()) {
                sb.append("Brand = ? AND ");
                params.add(txtBrand.getText());
            }
            if (!txtColor.getText().isEmpty()) {
                sb.append("Color = ? AND ");
                params.add(txtColor.getText());
            }
            if (!txtOthers.getText().isEmpty()) {
                sb.append("Others = ? AND ");
                params.add(txtOthers.getText());
            }
            if (!txtLocation.getText().isEmpty()) {
                sb.append("Location = ? AND ");
                params.add(txtLocation.getText());
            }
            if (dateDateFound.getDate() != null) {
                SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
                String dateStr = sdf.format(dateDateFound.getDate());
                sb.append("DateSurrendered = ? AND ");
                params.add(dateStr);
            }
            if (!txtImage.getText().isEmpty()) {
                sb.append("ImageID = ? AND ");
                params.add(txtImage.getText());
            }
            if (!txtPSurr.getText().isEmpty()) {
                sb.append("PersonSurrendered = ? AND ");
                params.add(txtPSurr.getText());
            }
            if (!txtCName.getText().isEmpty()) {
                sb.append("ClaimantName = ? AND ");
                params.add(txtCName.getText());
            }
            if (!txtCID.getText().isEmpty()) {
                sb.append("ClaimantID = ? AND ");
                params.add(txtCID.getText());
            }
            if (!txtCDesig.getText().isEmpty()) {
                sb.append("ClaimantDesignation = ? AND ");
                params.add(txtCDesig.getText());
            }
            if (dateDateClaimed.getDate() != null) {
                SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
                String dateStr = sdf.format(dateDateClaimed.getDate());
                sb.append("DateClaimed = ? AND ");
                params.add(dateStr);
            }

            // Checks if there is something to contain in the query through the string builder.
            if (sb.length() > 0) {
                // After all the inclusions, remove the last 5 characters or the " AND " of the string builder.
                sb.delete(sb.length() - 5, sb.length());
            }

            // Set the query in a variable
            String finalQuery = "SELECT * FROM `lfclaimeditems` WHERE " + sb.toString();

            // Set the connection to a prepared statement
            pstate = con.prepareStatement(finalQuery);

            // Set to the prepared statement all applicable parameters to get from the database
            for (int i = 0; i < params.size(); i++) {
                pstate.setString(i + 1, params.get(i));
            }

            // Execute the query
            rs = pstate.executeQuery();

            // Selects all applicable rows, hence in a while loop.
            while (rs.next()) {
                // Store into a variable all data of the item.
                String ContNo = rs.getString("ControlNo");
                String type = rs.getString("ItemType");
                String brand = rs.getString("Brand");
                String color = rs.getString("Color");
                String others = rs.getString("Others");
                String location = rs.getString("Location");
                String dateSurr = rs.getString("DateSurrendered");
                String imgID = rs.getString("ImageID");
                String pSurr = rs.getString("PersonSurrendered");
                String cName = rs.getString("ClaimantName");
                String cID = rs.getString("ClaimantID");
                String cDesig = rs.getString("ClaimantDesignation");
                String dateCLM = rs.getString("DateClaimed");

                // Add the data to the table.
                model.addRow(new Object[]{ContNo, type, brand, color, others, location, dateSurr, imgID, pSurr, cName, cID, cDesig, dateCLM});
            }
        }catch (SQLException ex){
            ex.printStackTrace();
        }
    }
    
    //Show all items in `lfclaimeditems`
    public void ShowAll(){
        try {
        con = DriverManager.getConnection("jdbc:mysql://localhost:3306/lostandfoundma3", "root", "");
        //Selects all data in descending order
        String qry = "SELECT * FROM `lfclaimeditems` ORDER BY `ControlNo` DESC";
        pstate = con.prepareStatement(qry);
        rs = pstate.executeQuery();

        Object [] column = {"Control No", "Item Type", "Brand", "Color", "Others", "Location", "Date Found", "Image ID", "Person Surrendered", 
            "Claimant Name", "Claimant ID", "Claimant Designation", "Date Claimed"};
        DefaultTableModel model = new DefaultTableModel(column, 0);
        Table.setModel(model);

        while(rs.next()){
            String ContNo = rs.getString("ControlNo");
            String type = rs.getString("ItemType");
            String brand = rs.getString("Brand");
            String color = rs.getString("Color");
            String others = rs.getString("Others");
            String location = rs.getString("Location");
            String dateSurr = rs.getString("DateSurrendered");
            String imgID = rs.getString("ImageID");
            String pSurr = rs.getString("PersonSurrendered");
            String cName = rs.getString("ClaimantName");
            String cID = rs.getString("ClaimantID");
            String cDesig = rs.getString("ClaimantDesignation");
            String dateCLM = rs.getString("DateClaimed");

            model.addRow(new Object[]{ContNo, type, brand, color, others, location, dateSurr, imgID, pSurr, cName, cID, cDesig, dateCLM});
        }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
    }
    
    // Edit fields that are only changed
    public void Edit() {
        //Set the values of the text fields into variables
        int ContNo = Integer.parseInt(txtContNo.getText());
        String type = txtType.getText();
        String brand = txtBrand.getText();
        String color = txtColor.getText();
        String others = txtOthers.getText();
        String location = txtLocation.getText();

        String dateSurr = new SimpleDateFormat("yyyy-MM-dd").format(dateDateFound.getDate());
        java.sql.Date dateSURR = java.sql.Date.valueOf(dateSurr);

        String imgID = txtImage.getText();
        String pSurr = txtPSurr.getText();
        String cName = txtCName.getText();
        String cID = txtCID.getText();
        String cDesig = txtCDesig.getText();

        String dateClm = new SimpleDateFormat("yyyy-MM-dd").format(dateDateClaimed.getDate());
        java.sql.Date dateCLM = java.sql.Date.valueOf(dateClm);

        try {
            con = DriverManager.getConnection("jdbc:mysql://localhost:3306/lostandfoundma3", "root", "");
            //Find the specific row through the Control Number
            String qry = "SELECT ControlNo, ItemType, Brand, Color, Others, Location, DateSurrendered, ImageID, PersonSurrendered, "
                    + "ClaimantName, ClaimantID, ClaimantDesignation, DateClaimed FROM `lfclaimeditems` WHERE ControlNo =?";
            pstate = con.prepareStatement(qry);
            pstate.setInt(1, ContNo);
            rs = pstate.executeQuery();

            if (rs.next()) {
                //Set the values on the database into a variable
                String dbType = rs.getString("ItemType");
                String dbBrand = rs.getString("Brand");
                String dbColor = rs.getString("Color");
                String dbOthers = rs.getString("Others");
                String dbLocation = rs.getString("Location");
                java.sql.Date dbDateSurr = rs.getDate("DateSurrendered");
                String dbImgID = rs.getString("ImageID");
                String dbPSurr = rs.getString("PersonSurrendered");
                String dbCName = rs.getString("ClaimantName");
                String dbCID = rs.getString("ClaimantID");
                String dbCDesig = rs.getString("ClaimantDesignation");
                java.sql.Date dbDateClm = rs.getDate("DateClaimed");

                StringBuilder updateQuery = new StringBuilder("UPDATE `lfclaimeditems` SET ");
                //Checks if it needs update or not.
                boolean updateNeeded = false;

                //Checks if the values on the text field and the database are different, then it is subject to change
                if (!type.equals(dbType)) {
                    updateQuery.append("ItemType =?, ");
                    updateNeeded = true;
                }
                if (!brand.equals(dbBrand)) {
                    updateQuery.append("Brand =?, ");
                    updateNeeded = true;
                }
                if (!color.equals(dbColor)) {
                    updateQuery.append("Color =?, ");
                    updateNeeded = true;
                }
                if (!others.equals(dbOthers)) {
                    updateQuery.append("Others =?, ");
                    updateNeeded = true;
                }
                if (!location.equals(dbLocation)) {
                    updateQuery.append("Location =?, ");
                    updateNeeded = true;
                }
                if (!dateSURR.equals(dbDateSurr)) {
                    updateQuery.append("DateSurrendered =?, ");
                    updateNeeded = true;
                }
                if (!imgID.equals(dbImgID)) {
                    updateQuery.append("ImageID =?, ");
                    updateNeeded = true;
                }
                if (!pSurr.equals(dbPSurr)) {
                    updateQuery.append("PersonSurrendered =?, ");
                    updateNeeded = true;
                }
                if (!cName.equals(dbCName)) {
                    updateQuery.append("ClaimantName =?, ");
                    updateNeeded = true;
                }
                if (!cID.equals(dbCID)) {
                    updateQuery.append("ClaimantID =?, ");
                    updateNeeded = true;
                }
                if (!cDesig.equals(dbCDesig)) {
                    updateQuery.append("ClaimantDesignation =?, ");
                    updateNeeded = true;
                }
                if (!dateCLM.equals(dbDateClm)) {
                    updateQuery.append("DateClaimed =?, ");
                    updateNeeded = true;
                }
                if (updateNeeded) {
                    //Removes the last 2 characters, which is ", "
                    updateQuery.setLength(updateQuery.length() - 2);
                    //Attach to the end the indicator, which is the control number
                    updateQuery.append(" WHERE ControlNo =?");
                    pstate = con.prepareStatement(updateQuery.toString());

                    //Set to a prepared statement. This time it initializes an index for the parameter.
                    int paramIndex = 1;
                    if (!type.equals(dbType)) {
                        pstate.setString(paramIndex++, type);
                    }
                    if (!brand.equals(dbBrand)) {
                        pstate.setString(paramIndex++, brand);
                    }
                    if (!color.equals(dbColor)) {
                        pstate.setString(paramIndex++, color);
                    }
                    if (!others.equals(dbOthers)) {
                        pstate.setString(paramIndex++, others);
                    }
                    if (!location.equals(dbLocation)) {
                        pstate.setString(paramIndex++, location);
                    }
                    if (!dateSURR.equals(dbDateSurr)) {
                        pstate.setDate(paramIndex++, dateSURR);
                    }
                    if (!imgID.equals(dbImgID)) {
                        pstate.setString(paramIndex++, imgID);
                    }
                    if (!pSurr.equals(dbPSurr)) {
                        pstate.setString(paramIndex++, pSurr);
                    }
                    if (!cName.equals(dbCName)) {
                        pstate.setString(paramIndex++, cName);
                    }
                    if (!cID.equals(dbCID)) {
                        pstate.setString(paramIndex++, cID);
                    }
                    if (!cDesig.equals(dbCDesig)) {
                        pstate.setString(paramIndex++, cDesig);
                    }
                    if (!dateCLM.equals(dbDateClm)) {
                        pstate.setDate(paramIndex++, dateCLM);
                    }

                    pstate.setInt(paramIndex++, ContNo);
                    pstate.executeUpdate();
                    
                    //Displays confirmation
                    int ans = JOptionPane.showConfirmDialog(null, "Do you want to edit this item?", "Edit Item", JOptionPane.YES_NO_OPTION);
                    if(ans == 0){
                        //Execute query update
                        pstate.executeUpdate();

                        //Go to Edited Prompt
                        ItemEditedC Aedit = new ItemEditedC();
                        Aedit.setVisible(true);
                        dispose();
                    }
                    else {
                        //Go back to this JFrame
                        LFadminClaimed adClaim = new LFadminClaimed();
                        adClaim.setVisible(true);
                        dispose();
                    }
                }
                else{
                    JOptionPane.showMessageDialog(null, "No changes to save.", "Edit Item", JOptionPane.INFORMATION_MESSAGE);
                }
            }    
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
    }
    
    // Delete fields that are only changed
    public void Delete() {
        String ContNo = txtContNo.getText();

        //Delete the whole row
        try {
            con = DriverManager.getConnection("jdbc:mysql://localhost:3306/lostandfoundma3", "root", "");
            String qry = "DELETE FROM `lfclaimeditems` WHERE ControlNo =?";
            pstate = con.prepareStatement(qry);
            
            pstate.setString(1, ContNo);
            
            //Delete Item comfirmation prompt
            int ans = JOptionPane.showConfirmDialog(null, "Do you want to delete this item?", "Delete Item", JOptionPane.YES_NO_OPTION);
            //If confirmed, execute delete
            if(ans == 0){
                pstate.execute();
                
                //Open Item Delete prompt
                ItemDeletedC Adelete = new ItemDeletedC();
                Adelete.setVisible(true);
                dispose();
            }
            else {
                //If canceled, open again the LFadminUnclaimed 
                LFadminClaimed adClaim = new LFadminClaimed();
                adClaim.setVisible(true);
                dispose();
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
    }
    
    //Move from Claimed to Unclaimed Method
        //Add to `lfuncalimeditems`
        public void AddItem(){
            try{
                //Set variables from values on the text fields
                String ContNo = txtContNo.getText();
                String type = txtType.getText();
                String brand = txtBrand.getText();
                String color = txtColor.getText();
                String others = txtOthers.getText();
                String location = txtLocation.getText();

                String dateSurr = new SimpleDateFormat("yyyy-MM-dd").format(dateDateFound.getDate());
                java.sql.Date dateSURR = java.sql.Date.valueOf(dateSurr);

                String imgID = txtImage.getText();
                String pSurr = txtPSurr.getText();
                
                //Insert another row on the database with the said values
                con = DriverManager.getConnection("jdbc:mysql://localhost:3306/lostandfoundma3", "root", "");
                String qry = "INSERT INTO `lfunclaimeditems` (ControlNo, ItemType, Brand, Color, Others, Location, DateSurrendered, "
                        + "ImageID, PersonSurrendered) VALUES (?,?,?,?,?,?,?,?,?)";
                pstate = con.prepareStatement(qry);

                pstate.setString(1, ContNo);
                pstate.setString(2, type);
                pstate.setString(3, brand);
                pstate.setString(4, color);
                pstate.setString(5, others);
                pstate.setString(6, location);
                pstate.setDate(7, dateSURR);
                pstate.setString(8, imgID);
                pstate.setString(9, pSurr);

                pstate.execute();

            } catch (SQLException ex) {
                ex.printStackTrace();
            }
        }

        //Remove from `lfclaimeditems'
        public void RemoveItem(){
            try{
                String cNo = txtContNo.getText();

                con = DriverManager.getConnection("jdbc:mysql://localhost:3306/lostandfoundma3", "root", "");
                String qry = ("DELETE FROM `lfclaimeditems` WHERE ControlNo = ?");
                pstate = con.prepareStatement(qry);

                pstate.setString(1, cNo);

                pstate.execute();

                con.close();

            } catch (SQLException ex) {
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
        buttonSearch = new javax.swing.JButton();
        jScrollPane1 = new javax.swing.JScrollPane();
        Table = new javax.swing.JTable();
        jLabel8 = new javax.swing.JLabel();
        buttonEdit = new javax.swing.JButton();
        jLabel11 = new javax.swing.JLabel();
        buttonClear = new javax.swing.JButton();
        buttonExit = new javax.swing.JButton();
        jLabel13 = new javax.swing.JLabel();
        lblImage = new javax.swing.JLabel();
        jLabel15 = new javax.swing.JLabel();
        txtCName = new javax.swing.JTextField();
        jLabel14 = new javax.swing.JLabel();
        txtPSurr = new javax.swing.JTextField();
        jLabel16 = new javax.swing.JLabel();
        txtImage = new javax.swing.JTextField();
        jLabel9 = new javax.swing.JLabel();
        dateDateFound = new com.toedter.calendar.JDateChooser();
        jLabel10 = new javax.swing.JLabel();
        txtLocation = new javax.swing.JTextField();
        jLabel7 = new javax.swing.JLabel();
        txtOthers = new javax.swing.JTextField();
        jLabel5 = new javax.swing.JLabel();
        txtColor = new javax.swing.JTextField();
        jLabel6 = new javax.swing.JLabel();
        txtBrand = new javax.swing.JTextField();
        jLabel4 = new javax.swing.JLabel();
        txtType = new javax.swing.JTextField();
        jLabel12 = new javax.swing.JLabel();
        txtContNo = new javax.swing.JTextField();
        jLabel17 = new javax.swing.JLabel();
        jLabel18 = new javax.swing.JLabel();
        txtCID = new javax.swing.JTextField();
        jLabel19 = new javax.swing.JLabel();
        txtCDesig = new javax.swing.JTextField();
        dateDateClaimed = new com.toedter.calendar.JDateChooser();
        jLabel20 = new javax.swing.JLabel();
        ButtonDelete = new javax.swing.JButton();
        ButtonMove = new javax.swing.JButton();
        buttonShowAll = new javax.swing.JButton();

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

    buttonEdit.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
    buttonEdit.setForeground(new java.awt.Color(0, 0, 153));
    buttonEdit.setText("EDIT");
    buttonEdit.addActionListener(new java.awt.event.ActionListener() {
        public void actionPerformed(java.awt.event.ActionEvent evt) {
            buttonEditActionPerformed(evt);
        }
    });

    jLabel11.setFont(new java.awt.Font("Century Gothic", 0, 12)); // NOI18N
    jLabel11.setText("Type in all applicable fields, then click SEARCH. To display all items, click SHOW ALL. Select an item in the table before making changes (EDIT, DELETE, or MOVE).");

    buttonClear.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
    buttonClear.setForeground(new java.awt.Color(153, 0, 0));
    buttonClear.setText("Clear");
    buttonClear.addActionListener(new java.awt.event.ActionListener() {
        public void actionPerformed(java.awt.event.ActionEvent evt) {
            buttonClearActionPerformed(evt);
        }
    });

    buttonExit.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
    buttonExit.setForeground(new java.awt.Color(153, 0, 0));
    buttonExit.setText("BACK TO ADMIN MENU");
    buttonExit.addActionListener(new java.awt.event.ActionListener() {
        public void actionPerformed(java.awt.event.ActionEvent evt) {
            buttonExitActionPerformed(evt);
        }
    });

    jLabel13.setIcon(new javax.swing.ImageIcon(getClass().getResource("/LostAndFoundMA3/Images/L&F (2).png"))); // NOI18N

    lblImage.setBorder(javax.swing.BorderFactory.createEtchedBorder());
    lblImage.setPreferredSize(new java.awt.Dimension(250, 250));

    jLabel15.setFont(new java.awt.Font("Century Gothic", 0, 14)); // NOI18N
    jLabel15.setText("Claimant Name:");

    jLabel14.setFont(new java.awt.Font("Century Gothic", 0, 14)); // NOI18N
    jLabel14.setText("Person Surrendered:");

    jLabel16.setFont(new java.awt.Font("Century Gothic", 0, 14)); // NOI18N
    jLabel16.setText("Image Link:");

    jLabel9.setFont(new java.awt.Font("Century Gothic", 0, 14)); // NOI18N
    jLabel9.setText("Date Found:");

    dateDateFound.setDateFormatString("yyyy-MM-dd");

    jLabel10.setFont(new java.awt.Font("Century Gothic", 0, 14)); // NOI18N
    jLabel10.setText("Location:");

    jLabel7.setFont(new java.awt.Font("Century Gothic", 0, 14)); // NOI18N
    jLabel7.setText("Others:");

    jLabel5.setFont(new java.awt.Font("Century Gothic", 0, 14)); // NOI18N
    jLabel5.setText("Color:");

    jLabel6.setFont(new java.awt.Font("Century Gothic", 0, 14)); // NOI18N
    jLabel6.setText("Brand:");

    jLabel4.setFont(new java.awt.Font("Century Gothic", 0, 14)); // NOI18N
    jLabel4.setText("Item Type:");

    jLabel12.setFont(new java.awt.Font("Century Gothic", 0, 14)); // NOI18N
    jLabel12.setText("Control No.:");

    txtContNo.setEditable(false);

    jLabel17.setBackground(new java.awt.Color(0, 153, 51));
    jLabel17.setFont(new java.awt.Font("Century Gothic", 1, 18)); // NOI18N
    jLabel17.setText("ADMIN | CLAIMED");

    jLabel18.setFont(new java.awt.Font("Century Gothic", 0, 14)); // NOI18N
    jLabel18.setText("Claimant ID:");

    jLabel19.setFont(new java.awt.Font("Century Gothic", 0, 14)); // NOI18N
    jLabel19.setText("Claimant Designation:");

    dateDateClaimed.setDateFormatString("yyyy-MM-dd");

    jLabel20.setFont(new java.awt.Font("Century Gothic", 0, 14)); // NOI18N
    jLabel20.setText("Date Claimed:");

    ButtonDelete.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
    ButtonDelete.setForeground(new java.awt.Color(0, 0, 153));
    ButtonDelete.setText("DELETE");
    ButtonDelete.addActionListener(new java.awt.event.ActionListener() {
        public void actionPerformed(java.awt.event.ActionEvent evt) {
            ButtonDeleteActionPerformed(evt);
        }
    });

    ButtonMove.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
    ButtonMove.setForeground(new java.awt.Color(0, 0, 153));
    ButtonMove.setText("MOVE");
    ButtonMove.addActionListener(new java.awt.event.ActionListener() {
        public void actionPerformed(java.awt.event.ActionEvent evt) {
            ButtonMoveActionPerformed(evt);
        }
    });

    buttonShowAll.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
    buttonShowAll.setText("SHOW ALL");
    buttonShowAll.addActionListener(new java.awt.event.ActionListener() {
        public void actionPerformed(java.awt.event.ActionEvent evt) {
            buttonShowAllActionPerformed(evt);
        }
    });

    javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
    jPanel2.setLayout(jPanel2Layout);
    jPanel2Layout.setHorizontalGroup(
        jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
        .addGroup(jPanel2Layout.createSequentialGroup()
            .addContainerGap()
            .addComponent(jLabel13)
            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
            .addComponent(jLabel1)
            .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(jPanel2Layout.createSequentialGroup()
                    .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                    .addComponent(jPanel3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel2Layout.createSequentialGroup()
                    .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jLabel17)
                    .addContainerGap())))
        .addGroup(jPanel2Layout.createSequentialGroup()
            .addGap(31, 31, 31)
            .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(jPanel2Layout.createSequentialGroup()
                    .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addComponent(jLabel11)
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(buttonExit)
                            .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 1131, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addComponent(jLabel3)
                        .addComponent(jLabel8))
                    .addContainerGap(38, Short.MAX_VALUE))
                .addGroup(jPanel2Layout.createSequentialGroup()
                    .addGap(13, 13, 13)
                    .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                        .addGroup(jPanel2Layout.createSequentialGroup()
                            .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                .addComponent(jLabel7)
                                .addComponent(jLabel5)
                                .addComponent(jLabel6)
                                .addComponent(jLabel4)
                                .addGroup(jPanel2Layout.createSequentialGroup()
                                    .addComponent(jLabel12)
                                    .addGap(5, 5, 5)))
                            .addGap(18, 18, 18)
                            .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                .addComponent(txtBrand, javax.swing.GroupLayout.PREFERRED_SIZE, 143, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addComponent(txtType, javax.swing.GroupLayout.PREFERRED_SIZE, 143, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addComponent(txtColor, javax.swing.GroupLayout.PREFERRED_SIZE, 143, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addComponent(txtOthers, javax.swing.GroupLayout.PREFERRED_SIZE, 143, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addComponent(txtContNo, javax.swing.GroupLayout.PREFERRED_SIZE, 143, javax.swing.GroupLayout.PREFERRED_SIZE)))
                        .addGroup(jPanel2Layout.createSequentialGroup()
                            .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                .addComponent(jLabel10, javax.swing.GroupLayout.PREFERRED_SIZE, 64, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addComponent(jLabel9))
                            .addGap(18, 18, 18)
                            .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                .addComponent(txtLocation, javax.swing.GroupLayout.PREFERRED_SIZE, 143, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGroup(jPanel2Layout.createSequentialGroup()
                                    .addGap(2, 2, 2)
                                    .addComponent(dateDateFound, javax.swing.GroupLayout.PREFERRED_SIZE, 141, javax.swing.GroupLayout.PREFERRED_SIZE))))
                        .addGroup(jPanel2Layout.createSequentialGroup()
                            .addComponent(jLabel16, javax.swing.GroupLayout.PREFERRED_SIZE, 81, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGap(20, 20, 20)
                            .addComponent(txtImage, javax.swing.GroupLayout.PREFERRED_SIZE, 141, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGroup(jPanel2Layout.createSequentialGroup()
                            .addComponent(jLabel14)
                            .addGap(22, 22, 22)
                            .addComponent(txtPSurr, javax.swing.GroupLayout.PREFERRED_SIZE, 139, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(jPanel2Layout.createSequentialGroup()
                            .addGap(57, 57, 57)
                            .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                                .addGroup(jPanel2Layout.createSequentialGroup()
                                    .addComponent(jLabel18)
                                    .addGap(18, 18, 18)
                                    .addComponent(txtCID, javax.swing.GroupLayout.PREFERRED_SIZE, 139, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addGroup(jPanel2Layout.createSequentialGroup()
                                    .addComponent(jLabel15)
                                    .addGap(18, 18, 18)
                                    .addComponent(txtCName, javax.swing.GroupLayout.PREFERRED_SIZE, 139, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addGroup(jPanel2Layout.createSequentialGroup()
                                    .addComponent(jLabel19)
                                    .addGap(18, 18, 18)
                                    .addComponent(txtCDesig, javax.swing.GroupLayout.PREFERRED_SIZE, 139, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addGroup(jPanel2Layout.createSequentialGroup()
                                    .addComponent(buttonEdit, javax.swing.GroupLayout.PREFERRED_SIZE, 91, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addGap(18, 18, 18)
                                    .addComponent(ButtonDelete, javax.swing.GroupLayout.PREFERRED_SIZE, 91, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addGap(18, 18, 18)
                                    .addComponent(ButtonMove, javax.swing.GroupLayout.PREFERRED_SIZE, 91, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addGroup(jPanel2Layout.createSequentialGroup()
                                    .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                        .addComponent(jLabel20)
                                        .addGroup(jPanel2Layout.createSequentialGroup()
                                            .addComponent(buttonClear, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                            .addGap(59, 59, 59)))
                                    .addGap(18, 18, 18)
                                    .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                        .addComponent(dateDateClaimed, javax.swing.GroupLayout.PREFERRED_SIZE, 141, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addComponent(buttonSearch)))))
                        .addGroup(jPanel2Layout.createSequentialGroup()
                            .addGap(159, 159, 159)
                            .addComponent(buttonShowAll)))
                    .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(lblImage, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGap(122, 122, 122))))
    );
    jPanel2Layout.setVerticalGroup(
        jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
        .addGroup(jPanel2Layout.createSequentialGroup()
            .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(jPanel2Layout.createSequentialGroup()
                    .addGap(29, 29, 29)
                    .addComponent(jLabel1))
                .addGroup(jPanel2Layout.createSequentialGroup()
                    .addContainerGap()
                    .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(jPanel2Layout.createSequentialGroup()
                            .addComponent(jLabel17)
                            .addGap(13, 13, 13)
                            .addComponent(jPanel3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addComponent(jLabel13))))
            .addGap(24, 24, 24)
            .addComponent(jLabel8)
            .addGap(1, 1, 1)
            .addComponent(jLabel11, javax.swing.GroupLayout.PREFERRED_SIZE, 16, javax.swing.GroupLayout.PREFERRED_SIZE)
            .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(jPanel2Layout.createSequentialGroup()
                    .addGap(18, 18, 18)
                    .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(jPanel2Layout.createSequentialGroup()
                            .addGap(0, 0, Short.MAX_VALUE)
                            .addComponent(buttonShowAll)
                            .addGap(18, 18, 18)
                            .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                .addComponent(ButtonDelete)
                                .addComponent(buttonEdit)
                                .addComponent(ButtonMove))
                            .addGap(26, 26, 26))
                        .addGroup(jPanel2Layout.createSequentialGroup()
                            .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                .addGroup(jPanel2Layout.createSequentialGroup()
                                    .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                        .addComponent(txtContNo, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addComponent(jLabel12))
                                    .addGap(8, 8, 8)
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
                                    .addGap(7, 7, 7)
                                    .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                        .addComponent(jLabel10)
                                        .addComponent(txtLocation, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                                    .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                    .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                        .addComponent(dateDateFound, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addComponent(jLabel9))
                                    .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                    .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                        .addComponent(jLabel16)
                                        .addComponent(txtImage, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                                    .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                    .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                        .addComponent(jLabel14)
                                        .addComponent(txtPSurr, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                                .addGroup(jPanel2Layout.createSequentialGroup()
                                    .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                        .addComponent(jLabel15)
                                        .addComponent(txtCName, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                                    .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                    .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                        .addComponent(jLabel18)
                                        .addComponent(txtCID, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                                    .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                    .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                        .addComponent(jLabel19)
                                        .addComponent(txtCDesig, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                                    .addGap(6, 6, 6)
                                    .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                        .addComponent(dateDateClaimed, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addComponent(jLabel20))
                                    .addGap(18, 18, 18)
                                    .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                        .addComponent(buttonSearch)
                                        .addComponent(buttonClear))))
                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))))
                .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel2Layout.createSequentialGroup()
                    .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(lblImage, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGap(16, 16, 16)))
            .addComponent(jLabel3)
            .addGap(7, 7, 7)
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
            .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
            .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
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
        .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
    );
    layout.setVerticalGroup(
        layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
        .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
    );

    pack();
    }// </editor-fold>//GEN-END:initComponents

    private void buttonExitActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_buttonExitActionPerformed
        //Go to AdminMenu JFrame
        AdminMenu Amenu = new AdminMenu();
        Amenu.setVisible(true);
        dispose();
    }//GEN-LAST:event_buttonExitActionPerformed

    private void buttonClearActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_buttonClearActionPerformed
        //Clear all fields
        Table.clearSelection();
        lblImage.setIcon(null);
        txtContNo.setText("");
        txtType.setText("");
        txtBrand.setText("");
        txtColor.setText("");
        txtOthers.setText("");
        txtLocation.setText("");
        dateDateFound.setCalendar(null);
        txtImage.setText("");
        txtPSurr.setText("");
        txtCName.setText("");
        txtCID.setText("");
        txtCDesig.setText("");
        dateDateClaimed.setCalendar(null);
    }//GEN-LAST:event_buttonClearActionPerformed

    private void buttonEditActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_buttonEditActionPerformed
        //Checks if there is a row selected on the table
        if (Table.getSelectionModel().isSelectionEmpty()){
            JOptionPane.showMessageDialog(null, "Please search and select an item on the table first.", "Item Edit", JOptionPane.ERROR_MESSAGE);
        }
        else{
            //Execute method
            Edit();
        }
    }//GEN-LAST:event_buttonEditActionPerformed

    private void TableMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_TableMouseClicked
        try{
            //Initialize the row as the one selected
            int row = Table.getSelectedRow();
            if (row == -1){
                return;
            }
            
            //Initialize the variable for the row
            String contNo = (String) Table.getValueAt(row, 0);

            //Set the query
            String qry = "SELECT ControlNo, ItemType, Brand, Color, Others, Location, DateSurrendered, ImageID, PersonSurrendered, ClaimantName, "
                    + "ClaimantID, ClaimantDesignation, DateClaimed FROM `lfclaimeditems` WHERE ControlNo =?";

            try (Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/lostandfoundma3", "root", "");
                PreparedStatement pstate = con.prepareStatement(qry)){
                pstate.setString(1, contNo);

                try (ResultSet rs = pstate.executeQuery()){
                    if(rs.next()){
                        //Set the text fields into their corresponding values in the database
                        txtContNo.setText(rs.getString("ControlNo"));
                        txtType.setText(rs.getString("ItemType"));
                        txtBrand.setText(rs.getString("Brand"));
                        txtColor.setText(rs.getString("Color"));
                        txtOthers.setText(rs.getString("Others"));
                        txtLocation.setText(rs.getString("Location"));
                        dateDateFound.setDate(rs.getDate("DateSurrendered"));
                        txtImage.setText(rs.getString("ImageID"));
                        txtPSurr.setText(rs.getString("PersonSurrendered"));
                        txtCName.setText(rs.getString("ClaimantName"));
                        txtCID.setText(rs.getString("ClaimantID"));
                        txtCDesig.setText(rs.getString("ClaimantDesignation"));
                        dateDateClaimed.setDate(rs.getDate("DateClaimed"));
                        
                        //Set the url from the database into Icon for a label panel
                        String imageID = rs.getString("ImageID");
                        URL url = new URL(imageID);
                        BufferedImage image = ImageIO.read(url);
                        Image scaledImage = image.getScaledInstance(250,250, Image.SCALE_SMOOTH);
                        ImageIcon icon = new ImageIcon(scaledImage);
                        lblImage.setIcon(icon);
                    }
                } catch (MalformedURLException ex) {
                    Logger.getLogger(LFadminClaimed.class.getName()).log(Level.SEVERE, null, ex);
                } catch (IOException ex) {
                    Logger.getLogger(LFadminClaimed.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        } catch (SQLException ex) {
            Logger.getLogger(LFadminClaimed.class.getName()).log(Level.SEVERE, null, ex);
        }
    }//GEN-LAST:event_TableMouseClicked

    private void buttonSearchActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_buttonSearchActionPerformed
        //Checks if there is something on a text field
        if (txtType.getText().isEmpty() && txtBrand.getText().isEmpty() && txtColor.getText().isEmpty() && txtOthers.getText().isEmpty() && 
                txtLocation.getText().isEmpty() && dateDateFound.getDate() == null && txtPSurr.getText().isEmpty() &&
                txtCName.getText().isEmpty() && txtCID.getText().isEmpty() && txtCDesig.getText().isEmpty() &&  dateDateClaimed.getDate() == null){
            JOptionPane.showMessageDialog(null, "Please fill in at least one field.", "Item Search", JOptionPane.ERROR_MESSAGE);
        }
        else{
            //Execute the method
            ShowSearch();
            
            if(Table.getRowCount() == 0){
                JOptionPane.showMessageDialog(null, "No item found.", "Item Search", JOptionPane.ERROR_MESSAGE);
            }
        }
    }//GEN-LAST:event_buttonSearchActionPerformed

    private void ButtonDeleteActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_ButtonDeleteActionPerformed
        //Checks if all text fields have values
        if (txtType.getText().isEmpty() || txtBrand.getText().isEmpty() || txtColor.getText().isEmpty() || txtOthers.getText().isEmpty() && 
                txtLocation.getText().isEmpty() || dateDateFound.getDate() == null || txtPSurr.getText().isEmpty() ||
                txtCName.getText().isEmpty() || txtCID.getText().isEmpty() || txtCDesig.getText().isEmpty() ||  dateDateClaimed.getDate() == null){
            JOptionPane.showMessageDialog(null, "Please search and select an item on the table first.", "Item Delete", JOptionPane.ERROR_MESSAGE);
        }
        else{
            //Execute method
            Delete();
        }
    }//GEN-LAST:event_ButtonDeleteActionPerformed

    private void ButtonMoveActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_ButtonMoveActionPerformed
        //Checks if all text fields have values
        if (Table.getSelectionModel().isSelectionEmpty()){
            JOptionPane.showMessageDialog(null, "Please search and select an item on the table first.", "Item Edit", JOptionPane.ERROR_MESSAGE);
        }
        else{
            //Move item confirmation prompt
            int ans = JOptionPane.showConfirmDialog(null, "Do you want to move this claimed item to unclaimed?", "Item Move", JOptionPane.YES_NO_OPTION);
            if(ans == 0){
                //if confirmed, execute the methods
                AddItem();
                RemoveItem();
                
                //Open Item Move prompt
                ItemMovedC move = new ItemMovedC();
                move.setVisible(true);
                dispose();
            }
        }
    }//GEN-LAST:event_ButtonMoveActionPerformed

    private void buttonShowAllActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_buttonShowAllActionPerformed
        ShowAll();
    }//GEN-LAST:event_buttonShowAllActionPerformed

    public static void main(String args[]) {
        
        java.awt.EventQueue.invokeLater(() -> {
            new LFadminClaimed().setVisible(true);
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton ButtonDelete;
    private javax.swing.JButton ButtonMove;
    private javax.swing.JTable Table;
    private javax.swing.JButton buttonClear;
    private javax.swing.JButton buttonEdit;
    private javax.swing.JButton buttonExit;
    private javax.swing.JButton buttonSearch;
    private javax.swing.JButton buttonShowAll;
    private com.toedter.calendar.JDateChooser dateDateClaimed;
    private com.toedter.calendar.JDateChooser dateDateFound;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel12;
    private javax.swing.JLabel jLabel13;
    private javax.swing.JLabel jLabel14;
    private javax.swing.JLabel jLabel15;
    private javax.swing.JLabel jLabel16;
    private javax.swing.JLabel jLabel17;
    private javax.swing.JLabel jLabel18;
    private javax.swing.JLabel jLabel19;
    private javax.swing.JLabel jLabel20;
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
    private javax.swing.JTextField txtCDesig;
    private javax.swing.JTextField txtCID;
    private javax.swing.JTextField txtCName;
    private javax.swing.JTextField txtColor;
    private javax.swing.JTextField txtContNo;
    private javax.swing.JTextField txtImage;
    private javax.swing.JTextField txtLocation;
    private javax.swing.JTextField txtOthers;
    private javax.swing.JTextField txtPSurr;
    private javax.swing.JTextField txtType;
    // End of variables declaration//GEN-END:variables
}