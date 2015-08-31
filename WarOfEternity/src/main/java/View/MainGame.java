package View;

import Characters.DockYard;
import Characters.DockYardController;
import Characters.Enemies;
import Characters.EnemiesController;
import Characters.Merchant;
import Characters.MerchantController;
import Characters.Player;
import Characters.PlayerController;
import Items.Item;
import Items.ItemController;
import Map.Area;
import Map.MapController;
import Parsers.ParserController;
import Serialization.LoadGameData;
import edu.cmu.sphinx.api.Configuration;
import edu.cmu.sphinx.api.LiveSpeechRecognizer;
import edu.cmu.sphinx.api.SpeechResult;
import java.awt.Color;
import java.awt.Window;
import java.awt.event.ActionEvent;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.io.IOException;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.AbstractAction;
import javax.swing.ImageIcon;
import javax.swing.JComponent;
import javax.swing.JOptionPane;
import javax.swing.KeyStroke;
import javax.swing.UnsupportedLookAndFeelException;

/**
 * GUI form for the main game. This form is the main game form (the form which
 * the player plays the game) and it consists player data such as weight and gold,
 * a message area which all the messages of the game are shown, a command area
 * which the user types the commands, an image area which changes for every area
 * that the user is at and a inventory which displays the items the player is 
 * carrying.
 * 
 * @author Vasilis Triantaris
 */
public class MainGame extends javax.swing.JFrame {
    
    //Data members
    public Player player;
    public String commandTyped; 
    public MapController mc;    
    public ItemController ic;
    public MerchantController merc;
    public EnemiesController ec;
    public Area startingArea;
    public DockYardController dyc;
    
    private final String projectFolder;
    private Configuration configuration;
    private LiveSpeechRecognizer recognizer;
    
    public MainGame(String playerName, boolean newGameProcess, LoadGameData loadObj, String playerClass) {
        initComponents();
        this.projectFolder = System.getProperty("user.dir");
        
        //Configure the data for the voice recognition
       // this.ConfigureVoiceRecognitionData();

        if(newGameProcess)
            this.SettingDataForNewGame(playerName, playerClass);
        else
            this.SettingDataForExistingGame(loadObj);
      
        //---------- Setting Parser Data ------------------
        final ParserController  pc = new ParserController();
 
        //-------------- Setting Form Data ----------------
        this.SetBasicComponentData();

        //Creating playerController object that with the parsing decissiion excecutes the action that the user wants.
        final PlayerController playerContr = new PlayerController("", "", "");
        
        //Event listener for the text field.
        jTextField1.addKeyListener(new KeyAdapter() {
                
               //Key event.
               public void keyPressed(KeyEvent e){

                   //If the button 'Enter' is pressed then ...
                   if(e.getKeyCode() == KeyEvent.VK_ENTER){
                       player.SetCharacterHealth(jProgressBar1.getValue());
                       String textAreaContent = jTextArea1.getText();
                       textAreaContent += "\n\n > "+jTextField1.getText();
                       jTextArea1.setText(textAreaContent);
                       commandTyped = jTextField1.getText().toString();
                       
                       //Decide the parsing action from the verb of action command that the user typed
                       String parsingDecision = pc.ParserControllingMethodForActionDecision(jTextField1.getText());
                       
                       //Setting the action data for the player controller   
                       playerContr.SetParsingDecision(parsingDecision);
                       playerContr.SetNounPartOfCommand(pc.GetNounOnPlayerCommand());
                       playerContr.SetVerbPartOfCommand(pc.GetVerbOnPlayerCommand());
        
                       //Sets the data of the enemies every time the user is making an action
                       //that is because whenever the player is attacking an enemy the enemies
                       //data are changing.
                       ec.SetGameEnemiesData();
                       List<Enemies> enemyList = ec.GetGameEnemyList();
                       
                       String actionResult = playerContr.PlayerMainControllingMethodForActionDecision(player, ic.GetListOfItems(), ec, mc.GetAreasList(), enemyList, dyc.GetDockYardList());
        
                       //if the command that the user gave is invalid then ...
                       if(actionResult.equals("")){
                           textAreaContent += "\n\n"+parsingDecision;
                           jTextArea1.setText(textAreaContent);
                       }
                       else{
                           textAreaContent += "\n\n"+actionResult;
                           jTextArea1.setText(textAreaContent);
                       }
  
                       //Setting image to jLabel.
                       SetImageAfterPlayerActionCommand(playerContr);
                     
                       //Clearing the text field after command submission
                       jTextField1.setText("");
                      
                       //calling the method tha will set the data of inventory
                       SetPlayerDataAfterActionCommandHadBeenExcecuted();
                   }
                   
                   //When the UP arrok key is pressed then it repeats the previous command
                   if(e.getKeyCode() == KeyEvent.VK_UP){
                       if(!commandTyped.equals(""))
                           jTextField1.setText(commandTyped);
                   }
               }  
            }
        );
        
        //Event Listener that occurres whenever the ESC button is pressed 
        getRootPane().getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW).put( KeyStroke.getKeyStroke(KeyEvent.VK_ESCAPE, 0), "Cancel"); 
        getRootPane().getActionMap().put("Cancel", new AbstractAction(){
            public void actionPerformed(ActionEvent e){
                    
                StartGUI sgui = new StartGUI(true, player, ec, merc, mc, ic);
                sgui.setVisible(true); 
                
            }
        });
        
    }
    
    private void SetImageAfterPlayerActionCommand(PlayerController playerContr){
        //Tries to load the image file. If its does not succeed then it load another image
        try{
            ImageIcon icon = null; 
            if((!this.player.GetAreaLocation().GetAreaImage().equals("")) && (!this.ec.GetBattleProgressState()))
                icon = new ImageIcon(this.projectFolder+"\\src\\main\\AreaImages\\"+ this.player.GetAreaLocation().GetAreaImage()); 
            else if((this.player.GetAreaLocation().GetAreaImage().equals("")) && (!this.ec.GetBattleProgressState()))
                icon = new ImageIcon(this.projectFolder+"\\src\\main\\AreaImages\\noImageAvailable.jpg"); 
            else if (this.ec.GetBattleProgressState())
                icon = new ImageIcon(this.projectFolder+"\\src\\main\\EnemyImages\\"+playerContr.GetEnemyToBattle().GetEnemyImage()); 
            
            jLabel1.setIcon(icon);
        }
        catch(Exception ex){
            if(!this.ec.GetBattleProgressState()){
                ImageIcon icon = new ImageIcon(this.projectFolder+"\\src\\main\\AreaImages\\noImageAvailable.jpg"); 
                jLabel1.setIcon(icon);
            }
        }
    }

    //Method that sets the basic data for the component of the form.
    private void SetBasicComponentData(){       
        String itemsSelected = "";
        double weightSum;
        
        jProgressBar1.setStringPainted(true);
        jProgressBar1.setForeground(Color.BLACK);
       
        NumberFormat formater = new DecimalFormat("#0.00");
        
        weightSum = player.CalculatingPlayerInventoryItemWeight();
        jLabel2.setText(String.valueOf(formater.format(weightSum))+" / 100");
        
        for(Item eachItemInInventory : player.GetItemsSelectedByTheUser())    
            itemsSelected += eachItemInInventory.GetItemName()+"\n";
        jTextArea2.setText(itemsSelected);
        
        ImageIcon icon = new ImageIcon(this.projectFolder+"\\src\\main\\java\\ApplicationImages\\weight.png"); 
        jLabel3.setIcon(icon);
        
        jLabel5.setText(formater.format(this.player.GetCharacterGold())+"");
        icon = new ImageIcon(this.projectFolder+"\\src\\main\\java\\ApplicationImages\\goldCoin.png"); 
        jLabel4.setIcon(icon);
        
        jLabel9.setText(this.player.GetCharacterDamage()+"");
        icon = new ImageIcon(this.projectFolder+"\\src\\main\\java\\ApplicationImages\\attackIcon.png");
        jLabel7.setIcon(icon);
        
        jLabel10.setText(this.player.GetCharacterArmor()+"");
        icon = new ImageIcon(this.projectFolder+"\\src\\main\\java\\ApplicationImages\\shieldIcon.png");
        jLabel8.setIcon(icon);
        
        jLabel12.setText(this.player.GetAreaLocation().GetAreasName());
        icon = new ImageIcon(this.projectFolder+"\\src\\main\\java\\ApplicationImages\\mapIcon.png");
        jLabel11.setIcon(icon);
        
        jLabel14.setText("Level : "+ this.player.GetPlayerLevel());
        icon = new ImageIcon(this.projectFolder+"\\src\\main\\java\\ApplicationImages\\levelIcon.png");
        jLabel13.setIcon(icon);
        
        icon = new ImageIcon(this.projectFolder+"\\src\\main\\java\\ApplicationImages\\strengthIcon.png");
        jLabel16.setText(this.player.GetCharacterStrength()+"");
        jLabel15.setIcon(icon);
        
        icon = new ImageIcon(this.projectFolder+"\\src\\main\\java\\ApplicationImages\\agilityIcon.png");
        jLabel18.setText(this.player.GetCharacterAgility()+"");
        jLabel17.setIcon(icon);
        
        icon = new ImageIcon(this.projectFolder+"\\src\\main\\java\\ApplicationImages\\intelligenceIcon.png");
        jLabel20.setText(this.player.GetCharacterInteligence()+"");
        jLabel19.setIcon(icon);
        
        icon = new ImageIcon(this.projectFolder+"\\src\\main\\java\\ApplicationImages\\recognitionIcon.png");
        jButton1.setIcon(icon);
    }
    
    //Method that sets the data of the inventory
    private void SetPlayerDataAfterActionCommandHadBeenExcecuted(){
        String itemsSelected = "";
        double weightSum;
        
        //Number format for double data type variables so that htey can be desplayed
        //with two digits after dot.
        NumberFormat formater = new DecimalFormat("#0.00");

        weightSum = player.CalculatingPlayerInventoryItemWeight();
        for(Item eachItemInInventory : player.GetItemsSelectedByTheUser())    
            itemsSelected += eachItemInInventory.GetItemName()+"\n";

        for(Item eachItem : this.ic.GetListOfItems()){
            if(eachItem.GetItemType() == 1 && eachItem.GetItemValue() == 0)
                eachItem.SetItemValue(8);
        }
        
        jTextArea2.setText(itemsSelected);
        jLabel2.setText(String.valueOf(formater.format(weightSum))+" / 100");
        jLabel5.setText(formater.format(this.player.GetCharacterGold())+"");
        jLabel9.setText(this.player.GetCharacterDamage()+"");
        jLabel10.setText(this.player.GetCharacterArmor()+"");
        jLabel12.setText(player.GetAreaLocation().GetAreasName());
        jLabel14.setText("Level : "+ this.player.GetPlayerLevel());
        jProgressBar1.setValue(player.GetCharacterHealth());
        jProgressBar2.setValue(player.GetPlayerExperience());
        
        if(player.GetCharacterHealth() <= 0){
            JOptionPane.showConfirmDialog(this, "You Died!, Game Over!", "You Died!", JOptionPane.OK_OPTION);
            StartGUI sgui = new StartGUI(false, null, null, null, null, null);
            sgui.setVisible(true);
            this.setVisible(false);
        }
    }
    
    //Method that sets the data after new game has been made
    private void SettingDataForNewGame(String playerName, String playerClass){
        this.mc = new MapController();
 
        //Sets as starting area the first on the area list.
        this.startingArea = this.mc.GetAreasList().get(0);
        this.ConfigurePlayerFromClass(playerClass, playerName);
        jTextArea1.setText(this.player.GetAreaLocation().GetAreaDescription());
        
        //Sets at the begining of the application the image of the first area.
        ImageIcon icon = new ImageIcon(this.projectFolder+"\\src\\main\\AreaImages\\"+ this.player.GetAreaLocation().GetAreaImage()); 
        jLabel1.setIcon(icon);

        this.ic = new ItemController(mc.GetAreasList());
        
        this.merc = new MerchantController(mc.GetAreasList());
        this.merc.SetMerchantSectionDataControllingMethod();
        
        this.ec = new EnemiesController(mc.GetAreasList());
        this.dyc = new DockYardController(mc.GetAreasList(), ic.GetListOfItems());
        this.dyc.DockYardMainControllingMethod();
        ec.SetGameEnemiesData();
    }
    
    //Method that loads the data for an existing game.
    private void SettingDataForExistingGame(LoadGameData loadObj){
        jTextArea1.setText("");
        
        this.mc = loadObj.GetMapController();
        this.player = loadObj.GetPlayerObject();
        jTextArea1.setText(this.player.GetAreaLocation().GetAreaDescription());
        
        //Sets at the image of the area that the user last saved to.
        ImageIcon icon = new ImageIcon(this.projectFolder+"\\src\\main\\AreaImages\\"+ this.player.GetAreaLocation().GetAreaImage()); 
        jLabel1.setIcon(icon);
        
        this.ic = loadObj.GetItemController();
        
        this.merc = new MerchantController(mc.GetAreasList());
        this.merc.SetMerchantSectionDataControllingMethod();
        
        this.ec = new EnemiesController(mc.GetAreasList());
        this.dyc = new DockYardController(mc.GetAreasList(), ic.GetListOfItems());
        this.dyc.DockYardMainControllingMethod();
        ec.SetGameEnemiesData();
        jProgressBar2.setValue(this.player.GetPlayerExperience());
        jProgressBar1.setValue(this.player.GetCharacterHealth());
        
    }
    
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jLabel1 = new javax.swing.JLabel();
        jScrollPane1 = new javax.swing.JScrollPane();
        jTextArea1 = new javax.swing.JTextArea();
        jTextField1 = new javax.swing.JTextField();
        jScrollPane3 = new javax.swing.JScrollPane();
        jTextArea2 = new javax.swing.JTextArea();
        jLabel2 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();
        jPanel1 = new javax.swing.JPanel();
        jLabel7 = new javax.swing.JLabel();
        jLabel8 = new javax.swing.JLabel();
        jLabel9 = new javax.swing.JLabel();
        jLabel10 = new javax.swing.JLabel();
        jLabel11 = new javax.swing.JLabel();
        jLabel12 = new javax.swing.JLabel();
        jLabel13 = new javax.swing.JLabel();
        jLabel14 = new javax.swing.JLabel();
        jLabel15 = new javax.swing.JLabel();
        jLabel16 = new javax.swing.JLabel();
        jLabel17 = new javax.swing.JLabel();
        jLabel18 = new javax.swing.JLabel();
        jLabel19 = new javax.swing.JLabel();
        jLabel20 = new javax.swing.JLabel();
        jToggleButton1 = new javax.swing.JToggleButton();
        jProgressBar2 = new javax.swing.JProgressBar();
        jButton1 = new javax.swing.JButton();
        jProgressBar1 = new javax.swing.JProgressBar();
        jLabel6 = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setTitle("War Of Eternity");
        setResizable(false);
        addWindowListener(new java.awt.event.WindowAdapter() {
            public void windowClosing(java.awt.event.WindowEvent evt) {
                formWindowClosing(evt);
            }
        });

        jLabel1.setBorder(javax.swing.BorderFactory.createEtchedBorder());

        jTextArea1.setEditable(false);
        jTextArea1.setColumns(20);
        jTextArea1.setLineWrap(true);
        jTextArea1.setRows(5);
        jScrollPane1.setViewportView(jTextArea1);

        jScrollPane3.setBorder(javax.swing.BorderFactory.createTitledBorder("Inventory"));

        jTextArea2.setEditable(false);
        jTextArea2.setColumns(20);
        jTextArea2.setLineWrap(true);
        jTextArea2.setRows(5);
        jScrollPane3.setViewportView(jTextArea2);

        jLabel3.setToolTipText("Player Weight");

        jLabel4.setToolTipText("Player Gold");

        jPanel1.setBorder(javax.swing.BorderFactory.createEtchedBorder());
        jPanel1.setPreferredSize(new java.awt.Dimension(205, 92));

        jLabel7.setToolTipText("Player Damage");

        jLabel8.setToolTipText("Player Armor");

        jLabel11.setToolTipText("Areas Name");

        jLabel15.setText("jLabel15");
        jLabel15.setToolTipText("Strength Points");

        jLabel17.setText("jLabel17");
        jLabel17.setToolTipText("Agility Points");

        jLabel19.setText("jLabel19");
        jLabel19.setToolTipText("Intelligence Points");

        jToggleButton1.setText("Map");
        jToggleButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jToggleButton1ActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jToggleButton1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(jLabel13, javax.swing.GroupLayout.PREFERRED_SIZE, 32, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(jLabel14, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(jLabel11, javax.swing.GroupLayout.PREFERRED_SIZE, 32, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(jLabel12, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(jLabel7, javax.swing.GroupLayout.PREFERRED_SIZE, 32, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(jLabel9, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(jLabel8, javax.swing.GroupLayout.PREFERRED_SIZE, 32, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(jLabel10, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addComponent(jLabel15, javax.swing.GroupLayout.PREFERRED_SIZE, 32, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(18, 18, 18)
                                .addComponent(jLabel16, javax.swing.GroupLayout.PREFERRED_SIZE, 131, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addComponent(jLabel17, javax.swing.GroupLayout.PREFERRED_SIZE, 32, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(18, 18, 18)
                                .addComponent(jLabel18, javax.swing.GroupLayout.PREFERRED_SIZE, 131, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addComponent(jLabel19, javax.swing.GroupLayout.PREFERRED_SIZE, 32, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(18, 18, 18)
                                .addComponent(jLabel20, javax.swing.GroupLayout.PREFERRED_SIZE, 131, javax.swing.GroupLayout.PREFERRED_SIZE)))
                        .addGap(0, 0, Short.MAX_VALUE)))
                .addContainerGap())
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(11, 11, 11)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel13, javax.swing.GroupLayout.PREFERRED_SIZE, 32, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel14, javax.swing.GroupLayout.PREFERRED_SIZE, 32, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(11, 11, 11)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel15, javax.swing.GroupLayout.PREFERRED_SIZE, 32, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel16, javax.swing.GroupLayout.PREFERRED_SIZE, 32, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(11, 11, 11)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel17, javax.swing.GroupLayout.PREFERRED_SIZE, 32, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel18, javax.swing.GroupLayout.PREFERRED_SIZE, 32, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(11, 11, 11)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel19, javax.swing.GroupLayout.PREFERRED_SIZE, 32, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel20, javax.swing.GroupLayout.PREFERRED_SIZE, 32, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(11, 11, 11)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jLabel9, javax.swing.GroupLayout.PREFERRED_SIZE, 32, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel7, javax.swing.GroupLayout.PREFERRED_SIZE, 32, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(11, 11, 11)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jLabel10, javax.swing.GroupLayout.PREFERRED_SIZE, 32, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel8, javax.swing.GroupLayout.PREFERRED_SIZE, 32, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(11, 11, 11)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel11, javax.swing.GroupLayout.PREFERRED_SIZE, 32, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel12, javax.swing.GroupLayout.PREFERRED_SIZE, 32, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(11, 11, 11)
                .addComponent(jToggleButton1)
                .addContainerGap(15, Short.MAX_VALUE))
        );

        jProgressBar2.setToolTipText("Experience Bar");
        jProgressBar2.setPreferredSize(new java.awt.Dimension(146, 12));
        jProgressBar2.setRequestFocusEnabled(false);
        jProgressBar2.setString("Experience");

        jButton1.setText("Recognize");
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });

        jProgressBar1.setToolTipText("Health Bar");
        jProgressBar1.setValue(100);

        jLabel6.setText("Health Bar :");

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(20, 20, 20)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(jTextField1, javax.swing.GroupLayout.DEFAULT_SIZE, 600, Short.MAX_VALUE)
                            .addComponent(jScrollPane1))
                        .addGap(18, 18, 18)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jButton1)))
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 400, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(jScrollPane3, javax.swing.GroupLayout.PREFERRED_SIZE, 196, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addComponent(jProgressBar2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(jLabel3, javax.swing.GroupLayout.PREFERRED_SIZE, 32, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(jLabel2, javax.swing.GroupLayout.PREFERRED_SIZE, 185, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(jLabel4, javax.swing.GroupLayout.PREFERRED_SIZE, 32, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(jLabel5, javax.swing.GroupLayout.PREFERRED_SIZE, 185, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addComponent(jLabel6)
                            .addComponent(jProgressBar1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))))
                .addContainerGap(24, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(13, 13, 13)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(jLabel1, javax.swing.GroupLayout.DEFAULT_SIZE, 250, Short.MAX_VALUE)
                            .addComponent(jScrollPane3))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jProgressBar2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jLabel6)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jProgressBar1, javax.swing.GroupLayout.PREFERRED_SIZE, 31, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(jLabel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(jLabel3, javax.swing.GroupLayout.PREFERRED_SIZE, 32, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(jLabel4, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(jLabel5, javax.swing.GroupLayout.PREFERRED_SIZE, 32, javax.swing.GroupLayout.PREFERRED_SIZE))))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, 354, Short.MAX_VALUE)
                    .addComponent(jScrollPane1))
                .addGap(8, 8, 8)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jTextField1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jButton1))
                .addGap(26, 26, 26))
        );

        pack();
        setLocationRelativeTo(null);
    }// </editor-fold>//GEN-END:initComponents

    private void formWindowClosing(java.awt.event.WindowEvent evt) {//GEN-FIRST:event_formWindowClosing
        int confirmed = JOptionPane.showConfirmDialog(null, 
            "Are you sure you want to exit the game?\nAll your progress will be"
                    + "automatacally lost!", "Exit Game Message Box",
            JOptionPane.YES_NO_OPTION);

        if (confirmed == JOptionPane.YES_OPTION) {
            System.exit(0);
        }
    }//GEN-LAST:event_formWindowClosing

    private void jToggleButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jToggleButton1ActionPerformed
        MapForm mf = new MapForm(this.mc);

        if(jToggleButton1.isSelected()){
            mf.setVisible(true);
        }
        else{
            //Closes the form thats is classes name is "class View.MapForm"
            System.gc();
            for (Window window : Window.getWindows()) {
                if(window.getClass().toString().equals("class View.MapForm"))
                    window.dispose();
            }
        }
    }//GEN-LAST:event_jToggleButton1ActionPerformed

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
        // Start recognition process pruning previously cached data.
        recognizer.startRecognition(true);
        SpeechResult result = recognizer.getResult();
        // Pause recognition process. It can be resumed then with startRecognition(false).
        recognizer.stopRecognition();

        jTextField1.setText(result.getHypothesis());
    }//GEN-LAST:event_jButton1ActionPerformed

    /**
     * Method that creates the player object for the specific player class
     * that the user selected.
     * 
     * @param playerClass The name of the class that the user selected.
     * @param playerName The name of the player that the user typed when creating new game.
     */
    private void ConfigurePlayerFromClass(String playerClass, String playerName){
        
        switch(playerClass){
            
            case "warrior" :
                this.player = new Player(startingArea, jProgressBar1.getValue(), playerName, jProgressBar2.getValue(), playerClass, 14, 6, 8);
            break;
                
            case "rogue" :
                this.player = new Player(startingArea, jProgressBar1.getValue(), playerName, jProgressBar2.getValue(), playerClass, 6, 8, 14);
            break;
                
            case "mage" :
                this.player = new Player(startingArea, jProgressBar1.getValue(), playerName, jProgressBar2.getValue(), playerClass, 5, 14, 9);
            break;
                
        }
        
    }
    
    /**
     * Method that configures the data for the recognition system. It uses a
     * configuration item which holds the path of the acoustic model, the path
     * to the dictionary and the path to language model. After that a 
     * LiveSpeechRecognizer object is created with the above configuration.
     */
    private void ConfigureVoiceRecognitionData(){
        
        this.configuration = new Configuration();
 
        // Set path to acoustic model.
        this.configuration.setAcousticModelPath("resource:/edu/cmu/sphinx/models/en-us/en-us");
        // Set path to dictionary.
        this.configuration.setDictionaryPath("resource:/edu/cmu/sphinx/models/en-us/cmudict-en-us.dict");
        // Set language model.
        this.configuration.setLanguageModelPath("resource:/edu/cmu/sphinx/models/en-us/en-us.lm.bin");
        
        try {
            this.recognizer = new LiveSpeechRecognizer(this.configuration);
        } catch (IOException ex) {
            //Logger.getLogger(MainGame.class.getName()).log(Level.SEVERE, null, ex);
            System.out.println("You dont have a microphone.");
        }
        
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
            java.util.logging.Logger.getLogger(MainGame.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>
        
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
               
                new MainGame("", true, null, "").setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton jButton1;
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
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel20;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JProgressBar jProgressBar1;
    private javax.swing.JProgressBar jProgressBar2;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane3;
    private javax.swing.JTextArea jTextArea1;
    private javax.swing.JTextArea jTextArea2;
    private javax.swing.JTextField jTextField1;
    private javax.swing.JToggleButton jToggleButton1;
    // End of variables declaration//GEN-END:variables
}
