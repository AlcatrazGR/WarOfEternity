package View;

import Serialization.LoadGameData;
import java.awt.Window;
import java.io.IOException;
import java.util.List;
import javax.swing.DefaultListModel;
import javax.swing.ImageIcon;
import javax.swing.JOptionPane;
import javax.swing.UnsupportedLookAndFeelException;

/**
 * GUI form for loading the game. It consists a list which shows all the saved games
 * and buttons for loading the game, deleting a saved game and canceling the
 * process.
 * 
 * @author Vasilis Triantaris
 */
public class LoadGameForm extends javax.swing.JFrame {

    public LoadGameForm() {
        initComponents();
        
        String projectFold = System.getProperty("user.dir");
        ImageIcon icon = new ImageIcon(projectFold+"\\src\\main\\java\\ApplicationImages\\swordShield.png"); 
        jLabel1.setIcon(icon);
        
        this.SetListModel();
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jScrollPane1 = new javax.swing.JScrollPane();
        jList1 = new javax.swing.JList();
        jButton1 = new javax.swing.JButton();
        jButton2 = new javax.swing.JButton();
        jButton3 = new javax.swing.JButton();
        jLabel1 = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setTitle("War Of Eternity : Load - Delete Game");
        setLocationByPlatform(true);
        setMaximumSize(null);
        setResizable(false);
        addWindowListener(new java.awt.event.WindowAdapter() {
            public void windowClosing(java.awt.event.WindowEvent evt) {
                formWindowClosing(evt);
            }
        });
        getContentPane().setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jScrollPane1.setViewportView(jList1);

        getContentPane().add(jScrollPane1, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 220, 370));

        jButton1.setFont(new java.awt.Font("Adobe Garamond Pro", 0, 17)); // NOI18N
        jButton1.setText("Load Game File");
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });
        getContentPane().add(jButton1, new org.netbeans.lib.awtextra.AbsoluteConstraints(230, 230, 220, 30));

        jButton2.setFont(new java.awt.Font("Adobe Garamond Pro", 0, 17)); // NOI18N
        jButton2.setText("Delete Game File");
        jButton2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton2ActionPerformed(evt);
            }
        });
        getContentPane().add(jButton2, new org.netbeans.lib.awtextra.AbsoluteConstraints(230, 270, 220, 30));

        jButton3.setFont(new java.awt.Font("Adobe Garamond Pro", 0, 17)); // NOI18N
        jButton3.setText("Cancel");
        jButton3.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton3ActionPerformed(evt);
            }
        });
        getContentPane().add(jButton3, new org.netbeans.lib.awtextra.AbsoluteConstraints(230, 310, 220, 30));
        getContentPane().add(jLabel1, new org.netbeans.lib.awtextra.AbsoluteConstraints(270, 0, 200, 300));

        setSize(new java.awt.Dimension(486, 399));
        setLocationRelativeTo(null);
    }// </editor-fold>//GEN-END:initComponents

    //Method for "Cancel Button"
    private void jButton3ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton3ActionPerformed
        this.setVisible(false);
    }//GEN-LAST:event_jButton3ActionPerformed

    private void jButton2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton2ActionPerformed
        LoadGameData lgd = new LoadGameData();
        String result = lgd.DeleteSavedGame(this.jList1.getSelectedValue().toString());

        JOptionPane.showMessageDialog(this, result, "Delete Message Prompt", 
                JOptionPane.OK_OPTION);
        
        this.SetListModel();
    }//GEN-LAST:event_jButton2ActionPerformed

    //Method for button "Load Game File"
    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
        LoadGameData lgd = new LoadGameData();
     
        if(this.jList1.getSelectedValue() == null){
            JOptionPane.showMessageDialog(this, "Error Occured!, You need to select a specific\n"
                    + "file before loading...", "Errom Message Prompt", JOptionPane.ERROR_MESSAGE);
        }
        else{
            try {
                lgd.LoadGameFileData(this.jList1.getSelectedValue().toString());
                MainGame mg = new MainGame(lgd.GetPlayerObject().GetCharacterName(), false, lgd, "");

                //Closes all window forms
                System.gc();
                for (Window window : Window.getWindows()) {
                    window.dispose();
                }
            
                mg.setVisible(true);           
            } 
            catch (IOException ex) {
                JOptionPane.showMessageDialog(this, "Error occurred, while loading the save file.\n"
                    + "Please try again later!", "Load Error Message Prompt" ,JOptionPane.OK_OPTION);
            } 
            catch (ClassNotFoundException ex) {
                JOptionPane.showMessageDialog(this, "Error occurred, cant load the specific \n"
                    + "file. Please try again later or reboot the game!", "Load Error Message Prompt",
                    JOptionPane.OK_OPTION);
            }
        }
    }//GEN-LAST:event_jButton1ActionPerformed

    //Method that occurres whenever the form is about to close.
    private void formWindowClosing(java.awt.event.WindowEvent evt) {//GEN-FIRST:event_formWindowClosing
        this.setVisible(false);
    }//GEN-LAST:event_formWindowClosing

    //Method that reads all the names of saved files inside the /Serializable/Saves
    //folder and then sets the model for jList1
    private void SetListModel(){
        List<String> savedGameFiles;
        LoadGameData lgd = new LoadGameData();
        
        savedGameFiles = lgd.GetSavedFileNames();

        DefaultListModel model = new DefaultListModel();
        for(String eachFileName : savedGameFiles)
            model.addElement(eachFileName);  
        
        this.jList1.setModel(model);
    }
    
    
    public static void main(String args[]) {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException | InstantiationException | IllegalAccessException | UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(LoadGameForm.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new LoadGameForm().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton jButton1;
    private javax.swing.JButton jButton2;
    private javax.swing.JButton jButton3;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JList jList1;
    private javax.swing.JScrollPane jScrollPane1;
    // End of variables declaration//GEN-END:variables
}
