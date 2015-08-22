
package View;

import Serialization.LoadGameData;
import java.awt.Window;
import javax.swing.ButtonGroup;
import javax.swing.ImageIcon;
import javax.swing.JOptionPane;
import javax.swing.UnsupportedLookAndFeelException;

/**
 * GUI form for creating a new game process. In this form there is a player name
 * area to which the user must type the desired player name which will also be
 * the name of the save file, a terms and agreement area, a back button and a 
 * start game button.
 * 
 * @author Thomas Liakos
 */
public class NewGameForm extends javax.swing.JFrame {

   
    private final String projectFold;
    private final ButtonGroup group;
    private String playerClass;
    
    public NewGameForm() {
        initComponents();
        this.projectFold = System.getProperty("user.dir");
   
        //Makes the three radio button a group
        this.group = new ButtonGroup();
        this.group.add(jRadioButton1);
        this.group.add(jRadioButton2);
        this.group.add(jRadioButton3);
        
        this.SetFormDataIcons();
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jLabel1 = new javax.swing.JLabel();
        jTextField1 = new javax.swing.JTextField();
        jScrollPane1 = new javax.swing.JScrollPane();
        jTextArea1 = new javax.swing.JTextArea();
        jCheckBox1 = new javax.swing.JCheckBox();
        jButton1 = new javax.swing.JButton();
        jButton2 = new javax.swing.JButton();
        jRadioButton1 = new javax.swing.JRadioButton();
        jRadioButton2 = new javax.swing.JRadioButton();
        jRadioButton3 = new javax.swing.JRadioButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setTitle("War Of Eternity : New Game");
        setResizable(false);
        addWindowListener(new java.awt.event.WindowAdapter() {
            public void windowClosing(java.awt.event.WindowEvent evt) {
                formWindowClosing(evt);
            }
        });

        jLabel1.setFont(new java.awt.Font("Adobe Garamond Pro", 0, 15)); // NOI18N
        jLabel1.setText("Enter Player Name : ");

        jTextArea1.setEditable(false);
        jTextArea1.setColumns(20);
        jTextArea1.setLineWrap(true);
        jTextArea1.setRows(5);
        jTextArea1.setText("Important - Please read the following terms and conditions carefully. These terms constitute a legally binding agreement and by using this game application, you are indicated that you acknowledge and agree to be bound by these terms.\nThis text based game is property of the technological institute of serres and by submitting - agreeing with the terms and conditions you are bound to follow those terms. The basic terms are the following : A) The user must be at least eighteen years old (18), and that you have read and understood the content of this terms. B) If you agree to the terms but your age is between thirteen years old and eighteen years old (13 - 18), you represent that your leagal guardians has review the whole text of terms and conditions. C) If you are not at least thirteen years old and your guardians did or did not review this terms and license agreement then the technological institute of serres doeas not take any responsibility for any of the kids actions. We strongly recommend that If you do not agree with all of these Terms, or you are not at least thirteen (13) years of age, or you are between the ages of thirteen (13) and eighteen (18) and your legal guardian does not agree with all of these Terms, please do not access / aggree with the terms. \n\n1. LICENSE TO USE THE SERVICES\n\n(a) Subject to your compliance with these Terms, the technological institute of serres grants you a limited, non-exclusive, non-transferable, non-sublicense-able, revocable license to access and use the game solely for your personal non-commercial entertainment purposes. You may not use the game for any other purpose, commercial or otherwise.\n(b) You agree not to: (i) use the game for any purpose other than as expressly permitted by these Terms; (ii) copy (except to execute games on your mobile phone for your personal non-commercial entertainment use), adapt, merge, modify, prepare any derivative works of, reverse engineer, disassemble, decompile, license, sell, stream, distribute or otherwise transfer, or otherwise exploit any materials (including any software) or any portion thereof, provided through the Site or Services; or (iii) otherwise violate these Terms, including the Code of Conduct described in Section 2 below. Any use of the Site or Services in violation of these limitations will be considered a breach of these Terms, is cause for immediate suspension and/or termination under Section 11(b) of these Terms, and/or may result in legal action against you.\n\n2. RESTRICTIONS AND CODE OF CONDUCT\n\nYou agree that you will not (the following restrictions are referred to collectively as the \"Code of Conduct\"\n(a) cheat or use, develop or distribute automation software programs (\"bots\"), \"macro\" software programs or other “cheat utility” software programs or applications which are designed to modify the Services or Machine Zone experience;\n(b) exploit, distribute or publicly inform other users of any game error, miscue or bug which gives an unintended advantage or allows impersonation of another person;\n(c) disrupt, attempt to, or otherwise assist in the disruption of (i) any computer or network used to provide or support the game or (ii) any other user's experience;\n(d) upload files that contain viruses, Trojan horses, worms, time bombs, corrupted files or data, or any other similar software or programs that may damage the operation of the Services or other users' computers;\n(e) promote or encourage any illegal activity including, without limitation, hacking, cracking or distribution of counterfeit software, or cheats or hacks for the Services;\n(f) publish, post, upload, transmit, distribute or disseminate User Content (as defined in Section 8(a) below) that is harmful to minors, otherwise harmful, abusive, vulgar, harassing, sexually explicit, sexually provocative, pornographic, defamatory, libelous, obscene, infringing, embarrassing, unwanted, invasive of another's right of privacy or publicity (including distributing another user’s personal information if he or she has not made such information public on the host site (if applicable)), hateful, racist, homophobic, bigoted, or otherwise offensive or objectionable;\n(g) submit material that is copyrighted, protected by trade secret or otherwise subject to third party intellectual property or proprietary rights, including privacy and publicity right, unless you are the owner of such rights or have permission from their rightful owner to post the material and to grant Machine Zone all of the license rights granted herein;\n(h) restrict or inhibit any other user from using and enjoying the Services (such as, but not limited to, disrupting the flow of chat in chat rooms with vulgar language, abusiveness, hitting the return key repeatedly, inputting excessively large images so the screen goes by too fast to read, use of excessive SHOUTING (all caps) in an attempt to disturb other users, \"spamming,\" or flooding (continuous posting repetitive text);\n(i) promote, encourage, or otherwise advocate the use of illegal drugs;\n(j) violate any other terms governing the access or use of the Services (including any end user license, code of conduct or other terms of use/service or guidelines from any technologiacal institute of serres or third party web site through which the game are available);\n(k) publish falsehoods or misrepresentations that could damage Machine Zone or any third party;\n(l) post advertisements or solicitations of business, advertise or offer to sell or buy any goods or services for any purpose through the Services, unless the Services on which you are playing specifically and expressly allow such messages and provided further that offers to buy or sell in-game items, if expressly permitted by the technologiacal institute of serres, are limited to the forum or channel specified by Machine Zone for such offers;\n(m) collect (in an automated manner or otherwise) personal information (including attempting to obtain password or account information) about other users without their written consent, or impersonate or create a false identity (such as a celebrity, web site administrator or a Machine Zone representative);\n(n) remove or obscure any proprietary notices on the game;\n(o) use the Services (including bulletin boards and other communications services) or your account in any manner other than for personal communication as an individual user (e.g. sending surveys, contests, pyramid schemes, chain letters, junk email, spam or any duplicative or unsolicited messages);\n(p) sublease your account or offer “free space” on or other access to your account to third parties;\n(q) improperly use support channels or complaint buttons to make false reports to Machine Zone;\n(r) use the Services for fraudulent transactions or for any purpose that violates any applicable local, state, national, or foreign laws, regulations, or treaties; or\n(s) otherwise create liability for Machine Zone.\nAs between the parties, technologiacal institute of serres shall own all title, ownership rights, and intellectual property rights in and to the Service, and any copies or portions thereof. In the event User submits any content, data or information to the game, User represents and warrants that User the full right and authority to do so (without any infringement of, or conflict with, the rights of any third party).");
        jScrollPane1.setViewportView(jTextArea1);

        jCheckBox1.setFont(new java.awt.Font("Adobe Garamond Pro", 0, 15)); // NOI18N
        jCheckBox1.setText("I agree with the terms and conditions");

        jButton1.setFont(new java.awt.Font("Adobe Garamond Pro", 0, 16)); // NOI18N
        jButton1.setText("Start Game");
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });

        jButton2.setFont(new java.awt.Font("Adobe Garamond Pro", 0, 16)); // NOI18N
        jButton2.setText("< Back");
        jButton2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton2ActionPerformed(evt);
            }
        });

        jRadioButton1.setText("Warrior");
        jRadioButton1.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jRadioButton1MouseClicked(evt);
            }
        });

        jRadioButton2.setText("Rogue");
        jRadioButton2.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jRadioButton2MouseClicked(evt);
            }
        });

        jRadioButton3.setText("Mage");
        jRadioButton3.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jRadioButton3MouseClicked(evt);
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(40, 40, 40)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jCheckBox1)
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(jLabel1)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(jTextField1, javax.swing.GroupLayout.PREFERRED_SIZE, 187, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 624, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(18, 18, 18))
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jButton2)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(jButton1)
                        .addGap(86, 86, 86)))
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jRadioButton1, javax.swing.GroupLayout.PREFERRED_SIZE, 189, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jRadioButton2, javax.swing.GroupLayout.PREFERRED_SIZE, 183, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jRadioButton3, javax.swing.GroupLayout.PREFERRED_SIZE, 179, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(20, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(15, 15, 15)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel1)
                    .addComponent(jTextField1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jRadioButton1, javax.swing.GroupLayout.PREFERRED_SIZE, 149, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 11, Short.MAX_VALUE)
                        .addComponent(jRadioButton2, javax.swing.GroupLayout.PREFERRED_SIZE, 143, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(5, 5, 5)
                        .addComponent(jRadioButton3, javax.swing.GroupLayout.PREFERRED_SIZE, 149, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(layout.createSequentialGroup()
                        .addGap(0, 0, Short.MAX_VALUE)
                        .addComponent(jCheckBox1)
                        .addGap(18, 18, 18)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jButton1)
                            .addComponent(jButton2)))
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 376, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(0, 0, Short.MAX_VALUE)))
                .addGap(25, 25, 25))
        );

        pack();
        setLocationRelativeTo(null);
    }// </editor-fold>//GEN-END:initComponents

    //Method for button "< Back"
    private void jButton2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton2ActionPerformed
        StartGUI sgui = new StartGUI(false, null, null, null, null, null);
        sgui.setVisible(true);
        this.setVisible(false);
    }//GEN-LAST:event_jButton2ActionPerformed

    //Method for button "Start Game"
    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
        String playerName = jTextField1.getText().trim();
 
        if(jCheckBox1.isSelected()){
            if(!playerName.isEmpty()){
                if(!playerClass.equals("")){
                    LoadGameData lgd = new LoadGameData();
                
                    if(!lgd.PlayerNameExistsAsASaveFile(playerName)){
                        MainGame mg = new MainGame(playerName, true, null, playerClass);
                    
                        //Closes all window forms
                        System.gc();
                        for (Window window : Window.getWindows()) {
                            window.dispose();
                        }
                        
                        JOptionPane.showMessageDialog(null, 
                          "The year is seven hundred and twenty two (722) when war started at\n"
                        + "the lands of Yeress, which later is know as the war of eternity.\n" 
                        + "The kingdom of Aldor, the kingdom of Ash'vor, the kingdom of Tan'leri\n "
                        + "and the clan of the water mages all combined to face the imminent threat\n"
                        + "that came from the north, the necromancers.\n\n" 
                        + "While the religion of all the tribes and kingdoms of Yeress is with the \n" 
                        + "order of edernium, a order of gods which sole purpose is to preserve \n"
                        + "balance and peace in the realms of men, the necromancers on the other \n"
                        + "hand believe only in the power of death and conjuring. Legend says that \n"
                        + "the necromancers conjured a monster from the depths of the great jade sea\n"
                        + "to fight by their side and flip the scales of balance that the order of \n" 
                        + " edernium has fought for. \n\n"
                        + "The war ended with its consequences sons and daughters have been slain \n"
                        + "in this war, the cemeteries and graveyards were filled by the corpes of \n"
                        + "the soldiers and the clan of the water mages vanished. Even though the \n"
                        + "armies of Yeress managed to push the necromancers back confinting them\n"
                        + " at the farther north part of the land the cost was too much and the war \n"
                        + "was practicly lost. The people started to recover and also lost their \n"
                        + "faith in the order for abandoning them in their finest our. Now six \n"
                        + "hundred years have passed since the war of eternity that struck the \n"
                        + "lands of Yeress.\n\n" 
                        + "While all those memories pass through your mind, you sudenly wake up\n"
                        + " with no memory how you got here...", "The Begining...", WIDTH, null);

                        mg.setVisible(true);
                    }
                    else{
                        JOptionPane.showMessageDialog(this, "The entered player name is already been used by\n"
                            + "another player!", "Message Prompt", JOptionPane.OK_OPTION);
                    } 
                }
                else{
                    JOptionPane.showMessageDialog(this, "You need to select a player class before \n"
                        + "creating a new game !", "Message Prompt", JOptionPane.OK_OPTION);
                }
            }
            else{
                JOptionPane.showMessageDialog(this, "You must first enter a desired player name \n"
                        + "in order to start a new game !", "Message Prompt", JOptionPane.OK_OPTION);
            }
        }
        else{
            JOptionPane.showMessageDialog(this, "You must first agree with the terms and conditions\n"
                    + "in order to start a new game !", "Message Prompt", JOptionPane.OK_OPTION);
        }
    }//GEN-LAST:event_jButton1ActionPerformed

    private void formWindowClosing(java.awt.event.WindowEvent evt) {//GEN-FIRST:event_formWindowClosing
        StartGUI sgui = new StartGUI(false, null, null, null, null, null);
        sgui.setVisible(true);
        this.setVisible(false);
    }//GEN-LAST:event_formWindowClosing

    private void jRadioButton1MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jRadioButton1MouseClicked
        jRadioButton1.setText("<html><font color=\"green\"><b>Warrior</b></font></html>");
        jRadioButton2.setText("<html><font color=\"black\">Rogue</font></html>");
        jRadioButton3.setText("<html><font color=\"black\">Mage</font></html>");
        
        this.playerClass = "warrior";
    }//GEN-LAST:event_jRadioButton1MouseClicked

    private void jRadioButton2MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jRadioButton2MouseClicked
        jRadioButton2.setText("<html><font color=\"green\"><b>Rogue</b></font></html>");
        jRadioButton3.setText("<html><font color=\"black\">Mage</font></html>");
        jRadioButton1.setText("<html><font color=\"black\">Warrior</font></html>");
        
        this.playerClass = "rogue";
    }//GEN-LAST:event_jRadioButton2MouseClicked

    private void jRadioButton3MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jRadioButton3MouseClicked
        jRadioButton3.setText("<html><font color=\"green\"><b>Mage</b></font></html>");
        jRadioButton1.setText("<html><font color=\"black\">Warrior</font></html>");
        jRadioButton2.setText("<html><font color=\"black\">Rogue</font></html>");
        
        this.playerClass = "mage";
    }//GEN-LAST:event_jRadioButton3MouseClicked

    private void SetFormDataIcons(){
    
        ImageIcon icon = new ImageIcon(this.projectFold+"\\src\\main\\java\\ApplicationImages\\warrioricon.png"); 
        jRadioButton1.setIcon(icon);
        
        icon = new ImageIcon(this.projectFold+"\\src\\main\\java\\ApplicationImages\\rogue.png"); 
        jRadioButton2.setIcon(icon);

        icon = new ImageIcon(this.projectFold+"\\src\\main\\java\\ApplicationImages\\mageicon.png"); 
        jRadioButton3.setIcon(icon);

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
            java.util.logging.Logger.getLogger(NewGameForm.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>
        
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new NewGameForm().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton jButton1;
    private javax.swing.JButton jButton2;
    private javax.swing.JCheckBox jCheckBox1;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JRadioButton jRadioButton1;
    private javax.swing.JRadioButton jRadioButton2;
    private javax.swing.JRadioButton jRadioButton3;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JTextArea jTextArea1;
    private javax.swing.JTextField jTextField1;
    // End of variables declaration//GEN-END:variables
}
