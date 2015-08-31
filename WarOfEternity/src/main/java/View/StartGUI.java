package View;

import Characters.EnemiesController;
import Characters.Merchant;
import Characters.MerchantController;
import Characters.Player;
import GameFileConfiguration.MainFolderConfig;
import GameFileConfiguration.ResourceFolderConfig;
import GameFileConfiguration.SaveFolderConfig;
import Items.ItemController;
import Map.MapController;
import Serialization.SaveGameData;
import java.awt.HeadlessException;
import javax.swing.ImageIcon;
import javax.swing.JOptionPane;
import javax.swing.UnsupportedLookAndFeelException;

/**
 * Starting GUI is the first form that pops up when the user is starting the 
 * application. Here the user can select to load a saved game, create a new one,
 * save a game, resume to the game and also exit the game. This form can be
 * also accessed while in the main game form by pressing the ESC button.
 * 
 * @author Vasilis Triantaris
 */
public class StartGUI extends javax.swing.JFrame {
    
    public Player player;
    public MapController mc;    
    public ItemController ic;
    public MerchantController merc;
    public EnemiesController ec;

    public StartGUI(boolean inGame, Player playerObj, EnemiesController ecObj, MerchantController merContr, MapController mcObj, ItemController icObj) {
        initComponents();

        String projectFold = System.getProperty("user.dir");
        ImageIcon icon = new ImageIcon(projectFold+"\\src\\main\\java\\ApplicationImages\\bgImg.png"); 
        jLabel1.setIcon(icon);
        
        MainFolderConfig mfc = new MainFolderConfig();
        SaveFolderConfig sfc = new SaveFolderConfig();
        ResourceFolderConfig rfcm = new ResourceFolderConfig();
        
        this.player = playerObj;
        this.mc = mcObj;    
        this.ic = icObj;
        this.merc = merContr;
        this.ec = ecObj;
    
        //If the game is in progress (the player pressed the ESC button)
        if(inGame){
            jButton3.setEnabled(true);
            jButton4.setEnabled(true); 
        }

    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jButton1 = new javax.swing.JButton();
        jButton2 = new javax.swing.JButton();
        jButton3 = new javax.swing.JButton();
        jButton4 = new javax.swing.JButton();
        jButton5 = new javax.swing.JButton();
        jLabel1 = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setTitle("War Of Eternity : Options");
        setMaximumSize(new java.awt.Dimension(400, 400));
        setMinimumSize(new java.awt.Dimension(400, 400));
        setPreferredSize(new java.awt.Dimension(400, 400));
        setResizable(false);
        addWindowListener(new java.awt.event.WindowAdapter() {
            public void windowClosing(java.awt.event.WindowEvent evt) {
                formWindowClosing(evt);
            }
        });
        getContentPane().setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jButton1.setFont(new java.awt.Font("Adobe Garamond Pro", 0, 24)); // NOI18N
        jButton1.setText("New Game");
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });
        getContentPane().add(jButton1, new org.netbeans.lib.awtextra.AbsoluteConstraints(80, 50, 250, 40));

        jButton2.setFont(new java.awt.Font("Adobe Garamond Pro", 0, 24)); // NOI18N
        jButton2.setText("Load Game");
        jButton2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton2ActionPerformed(evt);
            }
        });
        getContentPane().add(jButton2, new org.netbeans.lib.awtextra.AbsoluteConstraints(80, 110, 250, 40));

        jButton3.setFont(new java.awt.Font("Adobe Garamond Pro", 0, 24)); // NOI18N
        jButton3.setText("Save Game");
        jButton3.setEnabled(false);
        jButton3.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton3ActionPerformed(evt);
            }
        });
        getContentPane().add(jButton3, new org.netbeans.lib.awtextra.AbsoluteConstraints(80, 170, 250, 40));

        jButton4.setFont(new java.awt.Font("Adobe Garamond Pro", 0, 24)); // NOI18N
        jButton4.setText("Resume Game");
        jButton4.setEnabled(false);
        jButton4.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton4ActionPerformed(evt);
            }
        });
        getContentPane().add(jButton4, new org.netbeans.lib.awtextra.AbsoluteConstraints(80, 230, 250, 40));

        jButton5.setFont(new java.awt.Font("Adobe Garamond Pro", 0, 24)); // NOI18N
        jButton5.setText("Exit Game");
        jButton5.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton5ActionPerformed(evt);
            }
        });
        getContentPane().add(jButton5, new org.netbeans.lib.awtextra.AbsoluteConstraints(80, 290, 250, 40));

        jLabel1.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));
        getContentPane().add(jLabel1, new org.netbeans.lib.awtextra.AbsoluteConstraints(220, 200, 270, 260));
    }// </editor-fold>//GEN-END:initComponents

    //Method for "Exit Game" button
    private void jButton5ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton5ActionPerformed
        int confirmed = JOptionPane.showConfirmDialog(null, 
            "Are you sure you want to exit the game?", "Exit Game Message Box",
            JOptionPane.YES_NO_OPTION);

        if (confirmed == JOptionPane.YES_OPTION) {
            System.exit(0);
        }
    }//GEN-LAST:event_jButton5ActionPerformed

    //Event that occurres whenever the form is about to close
    private void formWindowClosing(java.awt.event.WindowEvent evt) {//GEN-FIRST:event_formWindowClosing
        int confirmed = JOptionPane.showConfirmDialog(null, 
            "Are you sure you want to exit the game?", "Exit Game Message Box",
            JOptionPane.YES_NO_OPTION);

        if (confirmed == JOptionPane.YES_OPTION) {
            System.exit(0);
        }
    }//GEN-LAST:event_formWindowClosing

    //Method for button "New Game"
    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
        NewGameForm ngf = new NewGameForm();
        ngf.setVisible(true);
        this.setVisible(false);
    }//GEN-LAST:event_jButton1ActionPerformed

    //Method for the "Resume Game" button
    private void jButton4ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton4ActionPerformed
        this.setVisible(false);
    }//GEN-LAST:event_jButton4ActionPerformed

    //Method for the "Save Game" button
    private void jButton3ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton3ActionPerformed
        if(this.player != null){
            try{
                //SaveGameData sgd = new SaveGameData(this.player, this.ec, this.breeMerchant, this.mc, this.ic);
                SaveGameData sgd = new SaveGameData(this.player, this.mc, this.ic);
                sgd.SavePlayerData();
                JOptionPane.showMessageDialog(this, "The game has been saved!", "Save Message Prompt",
                    JOptionPane.OK_OPTION);
            }
            catch(HeadlessException ex){
                JOptionPane.showMessageDialog(this, "An error occurred while trying to save your data!\n"
                    + "Please try again later, or try to reboot the game!", "Error Message Prompt",
                    JOptionPane.OK_OPTION);
            }
        }
        else{
            JOptionPane.showMessageDialog(this, "An error occurred while trying to save your data!\n"
                    + "Please try again later, or try to reboot the game!", "Error Message Prompt",
                    JOptionPane.OK_OPTION);
        }
    }//GEN-LAST:event_jButton3ActionPerformed

    //Method for the "Load Game" button
    private void jButton2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton2ActionPerformed
        LoadGameForm lgf = new LoadGameForm();
        lgf.setVisible(true);
    }//GEN-LAST:event_jButton2ActionPerformed

    
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
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(StartGUI.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(StartGUI.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>
        catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(StartGUI.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>
        catch (UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(StartGUI.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>
        
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                
                new StartGUI(false, null, null, null, null, null).setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton jButton1;
    private javax.swing.JButton jButton2;
    private javax.swing.JButton jButton3;
    private javax.swing.JButton jButton4;
    private javax.swing.JButton jButton5;
    private javax.swing.JLabel jLabel1;
    // End of variables declaration//GEN-END:variables
}
