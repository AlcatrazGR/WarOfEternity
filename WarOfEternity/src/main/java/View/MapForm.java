
package View;

import Map.Area;
import Map.AreaConnectionMaker;
import Map.MapController;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import javax.swing.ImageIcon;
import javax.swing.JFrame;

/**
 *
 * @author Alcatraz
 * 
 */
public class MapForm extends javax.swing.JFrame implements MouseListener{
    private MapController mapContr;
    
    public MapForm(MapController mc) {
        initComponents();
        this.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        
        ImageIcon icon = new ImageIcon(System.getProperty("user.dir")+"\\src\\main\\java\\ApplicationImages\\WarOfEternityMap.jpg");
        jLabel1.setIcon(icon);
        
        this.mapContr = mc;
        
        /*
        jLabel2.addMouseListener(this);
        jLabel3.addMouseListener(this);
        jLabel4.addMouseListener(this);
        jLabel5.addMouseListener(this);
        jLabel6.addMouseListener(this);
        jLabel7.addMouseListener(this);
        jLabel8.addMouseListener(this);
        jLabel9.addMouseListener(this);
        jLabel10.addMouseListener(this);
        */
    }

    @Override
    public void mouseEntered(MouseEvent e){
        this.ShowAreaData(e.getComponent().getName());
    }
    
    @Override
    public void mouseClicked(MouseEvent me) {   
    }

    @Override
    public void mousePressed(MouseEvent me) {  
    }

    @Override
    public void mouseReleased(MouseEvent me) {  
    }

    @Override
    public void mouseExited(MouseEvent me) {  
    }
    
    //Method that from the name of the jLabel finds the object whose area name is
    //equal with the name of the jLabel.
    private void ShowAreaData(String areaName){
        String subterfs = "";
        
        for(Area eachArea : this.mapContr.GetAreasList()){
            if(eachArea.GetAreasName().equalsIgnoreCase(areaName)){
                jLabel11.setText(eachArea.GetAreasName());
                jTextArea1.setText(eachArea.GetAreaDescription());
                
                for(AreaConnectionMaker eachConnection : eachArea.GetListOfAreaConnections())
                    subterfs = eachConnection.GetNextArea().GetAreasName()+"\n";
            }
        }
        
        jLabel13.setText(subterfs);
    }
  
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jLabel1 = new javax.swing.JLabel();
        jPanel1 = new javax.swing.JPanel();
        jLabel11 = new javax.swing.JLabel();
        jSeparator1 = new javax.swing.JSeparator();
        jSeparator2 = new javax.swing.JSeparator();
        jLabel13 = new javax.swing.JLabel();
        jScrollPane1 = new javax.swing.JScrollPane();
        jTextArea1 = new javax.swing.JTextArea();

        setTitle("War Of Eternity - Map");
        setUndecorated(true);
        setResizable(false);
        getContentPane().setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());
        getContentPane().add(jLabel1, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 700, 550));

        jPanel1.setBorder(javax.swing.BorderFactory.createEtchedBorder());
        jPanel1.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jLabel11.setFont(new java.awt.Font("Serif", 3, 15)); // NOI18N
        jPanel1.add(jLabel11, new org.netbeans.lib.awtextra.AbsoluteConstraints(12, 13, 214, 20));
        jPanel1.add(jSeparator1, new org.netbeans.lib.awtextra.AbsoluteConstraints(2, 39, 124, 4));
        jPanel1.add(jSeparator2, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 510, 124, 10));

        jLabel13.setFont(new java.awt.Font("Tahoma", 3, 13)); // NOI18N
        jPanel1.add(jLabel13, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 520, 214, 20));

        jTextArea1.setEditable(false);
        jTextArea1.setBackground(new java.awt.Color(240, 240, 240));
        jTextArea1.setColumns(20);
        jTextArea1.setFont(new java.awt.Font("Serif", 2, 16)); // NOI18N
        jTextArea1.setLineWrap(true);
        jTextArea1.setRows(5);
        jTextArea1.setBorder(null);
        jScrollPane1.setViewportView(jTextArea1);

        jPanel1.add(jScrollPane1, new org.netbeans.lib.awtextra.AbsoluteConstraints(12, 49, 214, 430));

        getContentPane().add(jPanel1, new org.netbeans.lib.awtextra.AbsoluteConstraints(710, 0, 238, 550));

        pack();
    }// </editor-fold>//GEN-END:initComponents

    
    /**
     * @param args the command line arguments
     */
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
        } catch (ClassNotFoundException | InstantiationException | IllegalAccessException | javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(MapForm.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new MapForm(null).setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel13;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JSeparator jSeparator1;
    private javax.swing.JSeparator jSeparator2;
    private javax.swing.JTextArea jTextArea1;
    // End of variables declaration//GEN-END:variables

}
