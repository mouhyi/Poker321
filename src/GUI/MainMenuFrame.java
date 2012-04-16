package GUI;

import java.awt.Dimension;
import java.rmi.RemoteException;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.ImageIcon;
import javax.swing.JOptionPane;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.DefaultTreeCellRenderer;
import javax.swing.tree.DefaultTreeModel;

/**
 * This class implement all the main lobby of the application where the game
 * lobby, friends panel, statistics panel, and account panel are displayed.
 * 
 * @author mattvertescher
 */
public class MainMenuFrame extends javax.swing.JFrame {

    public static GUIClient clientRequest;
    public static ServerListener serverListener;
   
    
    public static boolean inTable = false; 
    // public static boolean chatToggleButtonState = false;
   
    /**
     * Creates new form MainMenuScreen.
     * @param guiclient 
     * @param serverlistener
     */
    public MainMenuFrame(GUIClient guic, ServerListener sl) throws RemoteException, SQLException {
        initComponents();
        Dimension screenSize = java.awt.Toolkit.getDefaultToolkit().getScreenSize();
        this.setLocation((screenSize.width/2)-(this.getWidth()/2), (screenSize.height/2)-(this.getHeight()/2));  
        this.setIconImage(new ImageIcon(GUIClient.class.getResource("images/icon_playing_card.png")).getImage());
        
        clientRequest = guic; 
        serverListener = sl;
        serverListener.setMainMenuFrame(this);
        
        updateGameTree();
        updateGamePanel(clientRequest.getListOfGameTables()[0].toString());
        //updateFriendsPanel(GUIClient.getUsername());
        
        //updateGamePanel(clientRequest.getListOfGameTables()[0].toString());
        updateFriendsPanel(clientRequest.getUsername());
        
        
    }
    
    /**
     * Pop-Up method to be called by server to alert user
     * @param message 
     * @return completed 
     */
    public boolean displayPopUp(String message) {
        JOptionPane.showMessageDialog(this, message, "Message!", JOptionPane.INFORMATION_MESSAGE);
        return true;
    }
    
    /**
     * Method to be called by the server to add a new message to the game 
     * lobby console.
     * @param message
     * @return completed
     */
    public boolean addGameLobbyConsoleMessage(String message) {
        gameLobbyConsoleTextArea.append("  " + message + "\n");
        return true;
    }
    
    /**
     * Method to be called by server instructing main menu to enter game frame.
     * @return completed
     */
    public boolean enterGameFrame() throws RemoteException, SQLException {
        // Should check for errors here
    	System.out.println("mmf: current table" + clientRequest.getCurrentTable());
        openGameFrame(clientRequest.getCurrentTable());
        return true;
    }
    
    /**
     * Update the game panel with information from a particular table.
     * @param gameTable
     */
    private void updateGamePanel(String gameTable) {
        tableNameLabel.setText(gameTable);
        tablePlayersList.setListData(clientRequest.getPlayersAtGameTable(gameTable));
        tableAnteLabel.setText("Ante:   $" + Integer.toString(clientRequest.getTableAnte(gameTable)));
        tableBringInLabel.setText("Bring In:    $" + Double.toString(clientRequest.getTableBringIn(gameTable)));
        tableHostLabel.setText("Host:   " + clientRequest.getTableHost(gameTable));
        
        String currentTable = clientRequest.getCurrentTable();
        if (currentTable != null && currentTable.equals(gameTable))
            joinGameToggleButton.setSelected(true);
        else
            joinGameToggleButton.setSelected(false);
    }
    
    /**
     * Update the game tree with the tables from the server.
     */
    private void updateGameTree() {
        DefaultMutableTreeNode rootServer = new DefaultMutableTreeNode(GUIClient.getIp()); 
   
        String[] gameTables = clientRequest.getListOfGameTables();
        for (int i = 0; i < gameTables.length; i++) {
            DefaultMutableTreeNode table = new DefaultMutableTreeNode(gameTables[i]);
            String[] players = clientRequest.getPlayersAtGameTable(gameTables[i]); 
            for (int j = 0; j < players.length; j++) {
                table.add(new DefaultMutableTreeNode(players[j]));
            }
            rootServer.add(table);
        }    
        
        DefaultTreeCellRenderer cellRenderer = new DefaultTreeCellRenderer();
        cellRenderer.setClosedIcon(new ImageIcon(GUIClient.class.getResource("images/poker_chip_icon.png")));
        cellRenderer.setOpenIcon(new ImageIcon(GUIClient.class.getResource("images/poker_chip_icon.png")));
        cellRenderer.setLeafIcon(new ImageIcon(GUIClient.class.getResource("images/user_icon.png")));
       
        DefaultTreeModel tm = new DefaultTreeModel(rootServer);
        gameTree.setCellRenderer(cellRenderer);
        gameTree.setModel(tm);
    }
    
    /**
     * Update the friends panel with information from a particular user.
     * @param username
     */
    private void updateFriendsPanel(String username) throws RemoteException, SQLException {
        usernameFriendsPanelLabel.setText(username);
        avatarFriendsPanelLabel.setIcon(clientRequest.getAvatar(username));
        
        if (clientRequest.getUsersWorth(username) != null)
        	worthFriendsPanelLabel.setText(clientRequest.getUsersWorth(username));
        else if (clientRequest.getUsersWorth(username) == null)
        	worthFriendsPanelLabel.setText("getUsersWorth server error");
        
        String[] friends = clientRequest.getFriends(clientRequest.getUsername());
        String[] usernames = new String[friends.length + 1];
        usernames[0] = clientRequest.getUsername() + "   (Me)";
        for (int i = 0; i < friends.length; i++) {
            String onlineOrOffline = clientRequest.userOnline(friends[i]) ? "Online":"Offline"; 
            usernames[i + 1] = friends[i] + "   (" + onlineOrOffline + ")";
        }    
        friendsList.setListData(usernames);
    }

    /**
     * Updates the profile panel with the users information.
     */
    private void updateProfilePanel() {
        System.out.println("Profile Panel Shown");
        firstNameProfileLabel.setText("First Name: Haibo");
        lastNameProfileLabel.setText("Last Name: Zeng");
        emailProfileLabel.setText("Email: " + clientRequest.getEmail());
        usernameProfileLabel.setText("Username: " + clientRequest.getUsername());
        accountNumberProfileLabel.setText(clientRequest.getAccountNumber());
        fundsProfileLabel.setText("$324345");
        try {
            numberOfChipsProfileLabel.setText(clientRequest.getUsersWorth(clientRequest.getUsername()));
        } catch (RemoteException ex) {} catch (SQLException ex) {}
        ipProfileLabel.setText(GUIClient.getIp());

        avatarProfileLabel.setIcon(clientRequest.getAvatar(clientRequest.getUsername()));
    }

    /**
     * Opens the game frame for a particular table.
     * @param table
     */
    private void openGameFrame(String table) throws RemoteException, SQLException {
    	
    	System.out.println("mmf: open game frame 1");
    	
        GameFrame newGameFrame = new GameFrame(clientRequest, serverListener, table);
        System.out.println("mmf: open game frame 2");
        
        newGameFrame.setVisible(true);
        serverListener.releaseMainMenuFrame();       
        this.setVisible(false);
        this.dispose();
    }


    
    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        mainPanel = new javax.swing.JPanel();
        mainTabbedPane = new javax.swing.JTabbedPane();
        gameLobbyPanel = new javax.swing.JPanel();
        gameLobbySplitPane = new javax.swing.JSplitPane();
        gameTreeScrollPane = new javax.swing.JScrollPane();
        gameTree = new javax.swing.JTree();
        gameDetailsPanel = new javax.swing.JPanel();
        tableNameLabel = new javax.swing.JLabel();
        tablePlayersScrollPane = new javax.swing.JScrollPane();
        tablePlayersList = new javax.swing.JList();
        gameLobbySeparator = new javax.swing.JSeparator();
        openChatToggleButton = new javax.swing.JToggleButton();
        joinGameToggleButton = new javax.swing.JToggleButton();
        tableAnteLabel = new javax.swing.JLabel();
        tableBringInLabel = new javax.swing.JLabel();
        tableHostLabel = new javax.swing.JLabel();
        gameLobbyConsoleScrollPane = new javax.swing.JScrollPane();
        gameLobbyConsoleTextArea = new javax.swing.JTextArea();
        gameFramePictureLabel = new javax.swing.JLabel();
        friendsPanel = new javax.swing.JPanel();
        friendsSplitPane = new javax.swing.JSplitPane();
        friendsScrollPane = new javax.swing.JScrollPane();
        friendsList = new javax.swing.JList();
        friendDetailsPanel = new javax.swing.JPanel();
        usernameFriendsPanelLabel = new javax.swing.JLabel();
        avatarFriendsPanelLabel = new javax.swing.JLabel();
        worthFriendsPanelLabel = new javax.swing.JLabel();
        statisticsPanel = new javax.swing.JPanel();
        statisticsScrollPane = new javax.swing.JScrollPane();
        statisticsTable = new javax.swing.JTable();
        profilePanel = new javax.swing.JPanel();
        staticProfilePanel = new javax.swing.JPanel();
        firstNameProfileLabel = new javax.swing.JLabel();
        lastNameProfileLabel = new javax.swing.JLabel();
        emailProfileLabel = new javax.swing.JLabel();
        usernameProfileLabel = new javax.swing.JLabel();
        accountNumberProfileLabel = new javax.swing.JLabel();
        serverIPProfileLabel = new javax.swing.JLabel();
        ipProfileLabel = new javax.swing.JLabel();
        bankAccountProfileLabel = new javax.swing.JLabel();
        avalableFundsProfileLabel = new javax.swing.JLabel();
        fundsProfileLabel = new javax.swing.JLabel();
        chipsProfileLabel = new javax.swing.JLabel();
        numberOfChipsProfileLabel = new javax.swing.JLabel();
        creditsPanel = new javax.swing.JPanel();
        changeAvatarButton = new javax.swing.JButton();
        donateButton = new javax.swing.JButton();
        avatarProfilePanel = new javax.swing.JPanel();
        avatarProfileLabel = new javax.swing.JLabel();
        menuBar = new javax.swing.JMenuBar();
        pokerMenu = new javax.swing.JMenu();
        aboutMenuItem = new javax.swing.JMenuItem();
        toLoginMenuItem = new javax.swing.JMenuItem();
        quitMenuItem = new javax.swing.JMenuItem();
        gameMenu = new javax.swing.JMenu();
        createNewTableMenuItem = new javax.swing.JMenuItem();
        inviteFriendMenuItem = new javax.swing.JMenuItem();
        startGameMenuItem = new javax.swing.JMenuItem();
        friendsMenu = new javax.swing.JMenu();
        addFriendMenuItem = new javax.swing.JMenuItem();
        deleteFriendMenuItem = new javax.swing.JMenuItem();
        optionsMenu = new javax.swing.JMenu();
        purchaseChipsMenuItem = new javax.swing.JMenuItem();
        changeUsernameMenuItem = new javax.swing.JMenuItem();
        changePasswordMenuItem = new javax.swing.JMenuItem();
        uploadNewAvatarMenuItem = new javax.swing.JMenuItem();
        helpMenu = new javax.swing.JMenu();
        gameRulesMenuItem = new javax.swing.JMenuItem();
        documentationMenuItem = new javax.swing.JMenuItem();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setTitle("Main Menu ");
        setResizable(false);
        addWindowListener(new java.awt.event.WindowAdapter() {
            public void windowClosing(java.awt.event.WindowEvent evt) {
                formWindowClosing(evt);
            }
        });

        gameLobbyPanel.addComponentListener(new java.awt.event.ComponentAdapter() {
            public void componentShown(java.awt.event.ComponentEvent evt) {
                gameLobbyPanelComponentShown(evt);
            }
        });

        gameLobbySplitPane.setDividerLocation(150);
        gameLobbySplitPane.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                gameLobbySplitPaneMouseClicked(evt);
            }
        });

        gameTree.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                gameTreeMouseClicked(evt);
            }
        });
        gameTree.addTreeSelectionListener(new javax.swing.event.TreeSelectionListener() {
            public void valueChanged(javax.swing.event.TreeSelectionEvent evt) {
                gameTreeValueChanged(evt);
            }
        });
        gameTreeScrollPane.setViewportView(gameTree);

        gameLobbySplitPane.setLeftComponent(gameTreeScrollPane);

        tableNameLabel.setFont(new java.awt.Font("Lucida Grande", 0, 24)); // NOI18N
        tableNameLabel.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        tableNameLabel.setText("Welcome to 321 Poker!");
        tableNameLabel.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);

        tablePlayersList.setModel(new javax.swing.AbstractListModel() {
            String[] strings = { "Item 1", "Item 2", "Item 3", "Item 4", "Item 5" };
            public int getSize() { return strings.length; }
            public Object getElementAt(int i) { return strings[i]; }
        });
        tablePlayersScrollPane.setViewportView(tablePlayersList);

        openChatToggleButton.setText("Open Lobby Chat");
        openChatToggleButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                openChatToggleButtonActionPerformed(evt);
            }
        });

        joinGameToggleButton.setText("Join Game ");
        joinGameToggleButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                joinGameToggleButtonActionPerformed(evt);
            }
        });

        tableAnteLabel.setText("Ante: $10");

        tableBringInLabel.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        tableBringInLabel.setText("Bring In: $100");

        tableHostLabel.setText("Host: You");

        gameLobbyConsoleTextArea.setColumns(20);
        gameLobbyConsoleTextArea.setEditable(false);
        gameLobbyConsoleTextArea.setRows(5);
        gameLobbyConsoleScrollPane.setViewportView(gameLobbyConsoleTextArea);

        gameFramePictureLabel.setFont(new java.awt.Font("Rockwell Extra Bold", 0, 36)); // NOI18N
        gameFramePictureLabel.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        gameFramePictureLabel.setIcon(new javax.swing.ImageIcon(getClass().getResource("/GUI/images/filler_Image.jpg"))); // NOI18N

        org.jdesktop.layout.GroupLayout gameDetailsPanelLayout = new org.jdesktop.layout.GroupLayout(gameDetailsPanel);
        gameDetailsPanel.setLayout(gameDetailsPanelLayout);
        gameDetailsPanelLayout.setHorizontalGroup(
            gameDetailsPanelLayout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
            .add(org.jdesktop.layout.GroupLayout.TRAILING, tableNameLabel, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, 486, Short.MAX_VALUE)
            .add(gameDetailsPanelLayout.createSequentialGroup()
                .add(gameDetailsPanelLayout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
                    .add(gameDetailsPanelLayout.createSequentialGroup()
                        .addContainerGap()
                        .add(gameDetailsPanelLayout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
                            .add(org.jdesktop.layout.GroupLayout.TRAILING, gameLobbyConsoleScrollPane, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, 462, Short.MAX_VALUE)
                            .add(gameDetailsPanelLayout.createSequentialGroup()
                                .add(tablePlayersScrollPane, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, 191, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                                .add(18, 18, 18)
                                .add(gameDetailsPanelLayout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
                                    .add(tableHostLabel, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, 253, Short.MAX_VALUE)
                                    .add(tableAnteLabel, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, 253, Short.MAX_VALUE)
                                    .add(tableBringInLabel, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, 253, Short.MAX_VALUE)))
                            .add(gameLobbySeparator, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, 462, Short.MAX_VALUE)
                            .add(gameDetailsPanelLayout.createSequentialGroup()
                                .add(joinGameToggleButton, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, 222, Short.MAX_VALUE)
                                .add(18, 18, 18)
                                .add(openChatToggleButton, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, 222, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE))))
                    .add(gameDetailsPanelLayout.createSequentialGroup()
                        .add(12, 12, 12)
                        .add(gameFramePictureLabel, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, 462, Short.MAX_VALUE)))
                .addContainerGap())
        );
        gameDetailsPanelLayout.setVerticalGroup(
            gameDetailsPanelLayout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
            .add(gameDetailsPanelLayout.createSequentialGroup()
                .add(tableNameLabel, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, 33, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                .add(gameDetailsPanelLayout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
                    .add(gameDetailsPanelLayout.createSequentialGroup()
                        .add(tableAnteLabel)
                        .add(18, 18, 18)
                        .add(tableBringInLabel)
                        .add(18, 18, 18)
                        .add(tableHostLabel))
                    .add(tablePlayersScrollPane, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, 88, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                .add(gameDetailsPanelLayout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING, false)
                    .add(joinGameToggleButton, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .add(openChatToggleButton, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                .add(gameFramePictureLabel, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, 166, Short.MAX_VALUE)
                .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                .add(gameLobbySeparator, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                .add(gameLobbyConsoleScrollPane, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, 78, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );

        gameLobbySplitPane.setRightComponent(gameDetailsPanel);

        org.jdesktop.layout.GroupLayout gameLobbyPanelLayout = new org.jdesktop.layout.GroupLayout(gameLobbyPanel);
        gameLobbyPanel.setLayout(gameLobbyPanelLayout);
        gameLobbyPanelLayout.setHorizontalGroup(
            gameLobbyPanelLayout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
            .add(org.jdesktop.layout.GroupLayout.TRAILING, gameLobbyPanelLayout.createSequentialGroup()
                .addContainerGap()
                .add(gameLobbySplitPane, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, 642, Short.MAX_VALUE)
                .addContainerGap())
        );
        gameLobbyPanelLayout.setVerticalGroup(
            gameLobbyPanelLayout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
            .add(org.jdesktop.layout.GroupLayout.TRAILING, gameLobbyPanelLayout.createSequentialGroup()
                .add(gameLobbySplitPane, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, 442, Short.MAX_VALUE)
                .addContainerGap())
        );

        mainTabbedPane.addTab("Game Lobby", gameLobbyPanel);

        friendsPanel.addComponentListener(new java.awt.event.ComponentAdapter() {
            public void componentShown(java.awt.event.ComponentEvent evt) {
                friendsPanelComponentShown(evt);
            }
        });

        friendsSplitPane.setDividerLocation(150);

        friendsList.setModel(new javax.swing.AbstractListModel() {
            String[] strings = { "Item 1", "Item 2", "Item 3", "Item 4", "Item 5" };
            public int getSize() { return strings.length; }
            public Object getElementAt(int i) { return strings[i]; }
        });
        friendsList.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                friendsListMouseClicked(evt);
            }
        });
        friendsList.addListSelectionListener(new javax.swing.event.ListSelectionListener() {
            public void valueChanged(javax.swing.event.ListSelectionEvent evt) {
                friendsListValueChanged(evt);
            }
        });
        friendsScrollPane.setViewportView(friendsList);

        friendsSplitPane.setLeftComponent(friendsScrollPane);

        friendDetailsPanel.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));

        usernameFriendsPanelLabel.setFont(new java.awt.Font("Lucida Grande", 0, 24));
        usernameFriendsPanelLabel.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        usernameFriendsPanelLabel.setText("Username");

        avatarFriendsPanelLabel.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        avatarFriendsPanelLabel.setIcon(new javax.swing.ImageIcon(getClass().getResource("/GUI/images/question_mark.jpg"))); // NOI18N

        worthFriendsPanelLabel.setFont(new java.awt.Font("Lucida Grande", 0, 18)); // NOI18N
        worthFriendsPanelLabel.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        worthFriendsPanelLabel.setText("Worth: $ Nothing");

        org.jdesktop.layout.GroupLayout friendDetailsPanelLayout = new org.jdesktop.layout.GroupLayout(friendDetailsPanel);
        friendDetailsPanel.setLayout(friendDetailsPanelLayout);
        friendDetailsPanelLayout.setHorizontalGroup(
            friendDetailsPanelLayout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
            .add(org.jdesktop.layout.GroupLayout.TRAILING, friendDetailsPanelLayout.createSequentialGroup()
                .add(friendDetailsPanelLayout.createParallelGroup(org.jdesktop.layout.GroupLayout.TRAILING)
                    .add(friendDetailsPanelLayout.createSequentialGroup()
                        .addContainerGap()
                        .add(avatarFriendsPanelLabel, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, 460, Short.MAX_VALUE))
                    .add(worthFriendsPanelLabel, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, 472, Short.MAX_VALUE)
                    .add(usernameFriendsPanelLabel, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, 472, Short.MAX_VALUE))
                .addContainerGap())
        );
        friendDetailsPanelLayout.setVerticalGroup(
            friendDetailsPanelLayout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
            .add(friendDetailsPanelLayout.createSequentialGroup()
                .addContainerGap()
                .add(usernameFriendsPanelLabel, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, 32, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                .add(18, 18, 18)
                .add(avatarFriendsPanelLabel, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, 300, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                .add(18, 18, 18)
                .add(worthFriendsPanelLabel, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, 48, Short.MAX_VALUE)
                .addContainerGap())
        );

        friendsSplitPane.setRightComponent(friendDetailsPanel);

        org.jdesktop.layout.GroupLayout friendsPanelLayout = new org.jdesktop.layout.GroupLayout(friendsPanel);
        friendsPanel.setLayout(friendsPanelLayout);
        friendsPanelLayout.setHorizontalGroup(
            friendsPanelLayout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
            .add(friendsPanelLayout.createSequentialGroup()
                .addContainerGap()
                .add(friendsSplitPane, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, 642, Short.MAX_VALUE)
                .addContainerGap())
        );
        friendsPanelLayout.setVerticalGroup(
            friendsPanelLayout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
            .add(friendsPanelLayout.createSequentialGroup()
                .add(friendsSplitPane, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, 442, Short.MAX_VALUE)
                .addContainerGap())
        );

        mainTabbedPane.addTab("Friends", friendsPanel);

        statisticsPanel.addComponentListener(new java.awt.event.ComponentAdapter() {
            public void componentShown(java.awt.event.ComponentEvent evt) {
                statisticsPanelComponentShown(evt);
            }
        });

        statisticsTable.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null, null},
                {null, null, null, null, null},
                {null, null, null, null, null},
                {null, null, null, null, null},
                {null, null, null, null, null},
                {null, null, null, null, null},
                {null, null, null, null, null},
                {null, null, null, null, null},
                {null, null, null, null, null},
                {null, null, null, null, null},
                {null, null, null, null, null},
                {null, null, null, null, null},
                {null, null, null, null, null},
                {null, null, null, null, null},
                {null, null, null, null, null},
                {null, null, null, null, null},
                {null, null, null, null, null},
                {null, null, null, null, null},
                {null, null, null, null, null},
                {null, null, null, null, null},
                {null, null, null, null, null},
                {null, null, null, null, null},
                {null, null, null, null, null},
                {null, null, null, null, null},
                {null, null, null, null, null}
            },
            new String [] {
                "Player", "Wins ", "Losses", "Amount Won", "Statistical Ranking"
            }
        ) {
            Class[] types = new Class [] {
                java.lang.String.class, java.lang.String.class, java.lang.String.class, java.lang.String.class, java.lang.String.class
            };
            boolean[] canEdit = new boolean [] {
                false, false, false, false, false
            };

            public Class getColumnClass(int columnIndex) {
                return types [columnIndex];
            }

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        statisticsTable.getTableHeader().setReorderingAllowed(false);
        statisticsScrollPane.setViewportView(statisticsTable);
        statisticsTable.getColumnModel().getColumn(1).setResizable(false);
        statisticsTable.getColumnModel().getColumn(1).setPreferredWidth(15);
        statisticsTable.getColumnModel().getColumn(2).setResizable(false);
        statisticsTable.getColumnModel().getColumn(2).setPreferredWidth(15);
        statisticsTable.getColumnModel().getColumn(3).setResizable(false);
        statisticsTable.getColumnModel().getColumn(3).setPreferredWidth(20);

        org.jdesktop.layout.GroupLayout statisticsPanelLayout = new org.jdesktop.layout.GroupLayout(statisticsPanel);
        statisticsPanel.setLayout(statisticsPanelLayout);
        statisticsPanelLayout.setHorizontalGroup(
            statisticsPanelLayout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
            .add(statisticsPanelLayout.createSequentialGroup()
                .addContainerGap()
                .add(statisticsScrollPane, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, 642, Short.MAX_VALUE)
                .addContainerGap())
        );
        statisticsPanelLayout.setVerticalGroup(
            statisticsPanelLayout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
            .add(statisticsPanelLayout.createSequentialGroup()
                .add(statisticsScrollPane, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, 442, Short.MAX_VALUE)
                .addContainerGap())
        );

        mainTabbedPane.addTab("Statistics", statisticsPanel);

        profilePanel.addComponentListener(new java.awt.event.ComponentAdapter() {
            public void componentShown(java.awt.event.ComponentEvent evt) {
                profilePanelComponentShown(evt);
            }
        });

        staticProfilePanel.setBorder(new javax.swing.border.SoftBevelBorder(javax.swing.border.BevelBorder.RAISED));

        firstNameProfileLabel.setText("(First Name)");

        lastNameProfileLabel.setText("(Last Name)");

        emailProfileLabel.setText("(Email)");

        usernameProfileLabel.setText("(Username)");

        accountNumberProfileLabel.setText("(Account Number)");

        serverIPProfileLabel.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        serverIPProfileLabel.setText("Server IP: ");

        ipProfileLabel.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        ipProfileLabel.setText("(ip from server)");

        bankAccountProfileLabel.setText("Bank Account Number: ");

        avalableFundsProfileLabel.setText("Avalable Funds:");

        fundsProfileLabel.setText("$ (21312.32)");

        chipsProfileLabel.setText("Chips:");

        numberOfChipsProfileLabel.setText("(Number of Chips)");

        org.jdesktop.layout.GroupLayout staticProfilePanelLayout = new org.jdesktop.layout.GroupLayout(staticProfilePanel);
        staticProfilePanel.setLayout(staticProfilePanelLayout);
        staticProfilePanelLayout.setHorizontalGroup(
            staticProfilePanelLayout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
            .add(staticProfilePanelLayout.createSequentialGroup()
                .add(12, 12, 12)
                .add(accountNumberProfileLabel, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, 238, Short.MAX_VALUE))
            .add(staticProfilePanelLayout.createSequentialGroup()
                .addContainerGap()
                .add(staticProfilePanelLayout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
                    .add(bankAccountProfileLabel, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, 238, Short.MAX_VALUE)
                    .add(emailProfileLabel, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, 238, Short.MAX_VALUE)
                    .add(org.jdesktop.layout.GroupLayout.TRAILING, usernameProfileLabel, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, 238, Short.MAX_VALUE)
                    .add(firstNameProfileLabel, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, 238, Short.MAX_VALUE)
                    .add(org.jdesktop.layout.GroupLayout.TRAILING, lastNameProfileLabel, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, 238, Short.MAX_VALUE)
                    .add(org.jdesktop.layout.GroupLayout.TRAILING, avalableFundsProfileLabel, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, 238, Short.MAX_VALUE)
                    .add(org.jdesktop.layout.GroupLayout.TRAILING, staticProfilePanelLayout.createSequentialGroup()
                        .add(staticProfilePanelLayout.createParallelGroup(org.jdesktop.layout.GroupLayout.TRAILING)
                            .add(org.jdesktop.layout.GroupLayout.LEADING, serverIPProfileLabel, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, 226, Short.MAX_VALUE)
                            .add(org.jdesktop.layout.GroupLayout.LEADING, numberOfChipsProfileLabel, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, 226, Short.MAX_VALUE)
                            .add(org.jdesktop.layout.GroupLayout.LEADING, chipsProfileLabel, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, 226, Short.MAX_VALUE)
                            .add(fundsProfileLabel, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, 226, Short.MAX_VALUE))
                        .addContainerGap())))
            .add(staticProfilePanelLayout.createSequentialGroup()
                .addContainerGap()
                .add(ipProfileLabel, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, 226, Short.MAX_VALUE)
                .addContainerGap())
        );
        staticProfilePanelLayout.setVerticalGroup(
            staticProfilePanelLayout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
            .add(staticProfilePanelLayout.createSequentialGroup()
                .addContainerGap()
                .add(firstNameProfileLabel)
                .add(18, 18, 18)
                .add(lastNameProfileLabel)
                .add(18, 18, 18)
                .add(emailProfileLabel)
                .add(18, 18, 18)
                .add(usernameProfileLabel)
                .add(18, 18, 18)
                .add(bankAccountProfileLabel)
                .addPreferredGap(org.jdesktop.layout.LayoutStyle.UNRELATED)
                .add(accountNumberProfileLabel)
                .add(18, 18, 18)
                .add(avalableFundsProfileLabel)
                .addPreferredGap(org.jdesktop.layout.LayoutStyle.UNRELATED)
                .add(fundsProfileLabel)
                .add(18, 18, 18)
                .add(chipsProfileLabel)
                .addPreferredGap(org.jdesktop.layout.LayoutStyle.UNRELATED)
                .add(numberOfChipsProfileLabel)
                .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED, 40, Short.MAX_VALUE)
                .add(serverIPProfileLabel)
                .addPreferredGap(org.jdesktop.layout.LayoutStyle.UNRELATED)
                .add(ipProfileLabel)
                .add(12, 12, 12))
        );

        creditsPanel.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));

        changeAvatarButton.setText("Change Avatar");
        changeAvatarButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                changeAvatarButtonActionPerformed(evt);
            }
        });

        donateButton.setText("Donate!");
        donateButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                donateButtonActionPerformed(evt);
            }
        });

        org.jdesktop.layout.GroupLayout creditsPanelLayout = new org.jdesktop.layout.GroupLayout(creditsPanel);
        creditsPanel.setLayout(creditsPanelLayout);
        creditsPanelLayout.setHorizontalGroup(
            creditsPanelLayout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
            .add(org.jdesktop.layout.GroupLayout.TRAILING, creditsPanelLayout.createSequentialGroup()
                .addContainerGap()
                .add(creditsPanelLayout.createParallelGroup(org.jdesktop.layout.GroupLayout.TRAILING)
                    .add(org.jdesktop.layout.GroupLayout.LEADING, donateButton, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, 348, Short.MAX_VALUE)
                    .add(org.jdesktop.layout.GroupLayout.LEADING, changeAvatarButton, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, 348, Short.MAX_VALUE))
                .addContainerGap())
        );
        creditsPanelLayout.setVerticalGroup(
            creditsPanelLayout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
            .add(creditsPanelLayout.createSequentialGroup()
                .addContainerGap()
                .add(changeAvatarButton, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, 35, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                .add(donateButton, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, 32, Short.MAX_VALUE)
                .addContainerGap())
        );

        avatarProfilePanel.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        avatarProfilePanel.setPreferredSize(new java.awt.Dimension(374, 350));

        avatarProfileLabel.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        avatarProfileLabel.setIcon(new javax.swing.ImageIcon(getClass().getResource("/GUI/images/question_mark.jpg"))); // NOI18N

        org.jdesktop.layout.GroupLayout avatarProfilePanelLayout = new org.jdesktop.layout.GroupLayout(avatarProfilePanel);
        avatarProfilePanel.setLayout(avatarProfilePanelLayout);
        avatarProfilePanelLayout.setHorizontalGroup(
            avatarProfilePanelLayout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
            .add(org.jdesktop.layout.GroupLayout.TRAILING, avatarProfilePanelLayout.createSequentialGroup()
                .addContainerGap()
                .add(avatarProfileLabel, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, 348, Short.MAX_VALUE)
                .addContainerGap())
        );
        avatarProfilePanelLayout.setVerticalGroup(
            avatarProfilePanelLayout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
            .add(org.jdesktop.layout.GroupLayout.TRAILING, avatarProfilePanelLayout.createSequentialGroup()
                .addContainerGap()
                .add(avatarProfileLabel, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
        );

        org.jdesktop.layout.GroupLayout profilePanelLayout = new org.jdesktop.layout.GroupLayout(profilePanel);
        profilePanel.setLayout(profilePanelLayout);
        profilePanelLayout.setHorizontalGroup(
            profilePanelLayout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
            .add(profilePanelLayout.createSequentialGroup()
                .addContainerGap()
                .add(staticProfilePanel, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(org.jdesktop.layout.LayoutStyle.UNRELATED)
                .add(profilePanelLayout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
                    .add(avatarProfilePanel, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .add(creditsPanel, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap())
        );
        profilePanelLayout.setVerticalGroup(
            profilePanelLayout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
            .add(profilePanelLayout.createSequentialGroup()
                .addContainerGap()
                .add(profilePanelLayout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
                    .add(profilePanelLayout.createSequentialGroup()
                        .add(avatarProfilePanel, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, 325, Short.MAX_VALUE)
                        .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                        .add(creditsPanel, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE))
                    .add(staticProfilePanel, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap())
        );

        mainTabbedPane.addTab("Profile", profilePanel);

        org.jdesktop.layout.GroupLayout mainPanelLayout = new org.jdesktop.layout.GroupLayout(mainPanel);
        mainPanel.setLayout(mainPanelLayout);
        mainPanelLayout.setHorizontalGroup(
            mainPanelLayout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
            .add(mainTabbedPane)
        );
        mainPanelLayout.setVerticalGroup(
            mainPanelLayout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
            .add(mainTabbedPane, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, 497, Short.MAX_VALUE)
        );

        pokerMenu.setText("321 Poker");

        aboutMenuItem.setText("About 321 Poker");
        aboutMenuItem.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                aboutMenuItemActionPerformed(evt);
            }
        });
        pokerMenu.add(aboutMenuItem);

        toLoginMenuItem.setText("Return To Login");
        toLoginMenuItem.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                toLoginMenuItemActionPerformed(evt);
            }
        });
        pokerMenu.add(toLoginMenuItem);

        quitMenuItem.setText("Quit 321 Poker");
        quitMenuItem.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                quitMenuItemActionPerformed(evt);
            }
        });
        pokerMenu.add(quitMenuItem);

        menuBar.add(pokerMenu);

        gameMenu.setText("Game");

        createNewTableMenuItem.setText("Create New Game");
        createNewTableMenuItem.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                createNewTableMenuItemActionPerformed(evt);
            }
        });
        gameMenu.add(createNewTableMenuItem);

        inviteFriendMenuItem.setText("Invite Friend ");
        inviteFriendMenuItem.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                inviteFriendMenuItemActionPerformed(evt);
            }
        });
        gameMenu.add(inviteFriendMenuItem);

        startGameMenuItem.setText("Start Game");
        startGameMenuItem.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                startGameMenuItemActionPerformed(evt);
            }
        });
        gameMenu.add(startGameMenuItem);

        menuBar.add(gameMenu);

        friendsMenu.setText("Friends");

        addFriendMenuItem.setText("Add Friend");
        addFriendMenuItem.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                addFriendMenuItemActionPerformed(evt);
            }
        });
        friendsMenu.add(addFriendMenuItem);

        deleteFriendMenuItem.setText("Delete Friend");
        deleteFriendMenuItem.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                deleteFriendMenuItemActionPerformed(evt);
            }
        });
        friendsMenu.add(deleteFriendMenuItem);

        menuBar.add(friendsMenu);

        optionsMenu.setText("Options");

        purchaseChipsMenuItem.setText("Purchase Chips");
        purchaseChipsMenuItem.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                purchaseChipsMenuItemActionPerformed(evt);
            }
        });
        optionsMenu.add(purchaseChipsMenuItem);

        changeUsernameMenuItem.setText("Change Username");
        changeUsernameMenuItem.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                changeUsernameMenuItemActionPerformed(evt);
            }
        });
        optionsMenu.add(changeUsernameMenuItem);

        changePasswordMenuItem.setText("Change Password");
        changePasswordMenuItem.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                changePasswordMenuItemActionPerformed(evt);
            }
        });
        optionsMenu.add(changePasswordMenuItem);

        uploadNewAvatarMenuItem.setText("Upload New Avatar");
        uploadNewAvatarMenuItem.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                uploadNewAvatarMenuItemActionPerformed(evt);
            }
        });
        optionsMenu.add(uploadNewAvatarMenuItem);

        menuBar.add(optionsMenu);

        helpMenu.setText("Help");

        gameRulesMenuItem.setText("Game Rules");
        gameRulesMenuItem.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                gameRulesMenuItemActionPerformed(evt);
            }
        });
        helpMenu.add(gameRulesMenuItem);

        documentationMenuItem.setText("Documentation");
        documentationMenuItem.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                documentationMenuItemActionPerformed(evt);
            }
        });
        helpMenu.add(documentationMenuItem);

        menuBar.add(helpMenu);

        setJMenuBar(menuBar);

        org.jdesktop.layout.GroupLayout layout = new org.jdesktop.layout.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
            .add(mainPanel, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
            .add(mainPanel, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    
    
    /**
     * Join game toggle button pressed.
     * @param evt
     */
    private void joinGameToggleButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_joinGameToggleButtonActionPerformed
        String selectedTable = tableNameLabel.getText();
        //System.out.println(selectedTable);
        if (joinGameToggleButton.isSelected() && clientRequest.getCurrentTable()==null) { // && !inTable
            System.out.println("JOIN TABLE");
            clientRequest.joinGameTable(selectedTable);
           
            inTable = true;
            updateGamePanel(selectedTable);
            joinGameToggleButton.setText("Leave Game");
        }
        
        else if (!joinGameToggleButton.isSelected() && clientRequest.getCurrentTable().equals(tableNameLabel.getText())) { //inTable
            System.out.println("LEAVE TABLE");
            clientRequest.leaveGameTable(selectedTable);
          
            inTable = false;  
            updateGamePanel(selectedTable);
            joinGameToggleButton.setText("Join Game");
        }

        else {
             JOptionPane.showMessageDialog(this, "The server hates you.", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }//GEN-LAST:event_joinGameToggleButtonActionPerformed

    /**
     * When the chat toggle button pressed, a new chat window opens.
     * @param evt
     */
    private void openChatToggleButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_openChatToggleButtonActionPerformed

        ChatFrame lobbyChat = null;
        try {
            lobbyChat = new ChatFrame(clientRequest, serverListener, clientRequest.getOpponentsAtGameTable(clientRequest.getUsername(), tableNameLabel.getText()));
        } catch (RemoteException ex) {} catch (SQLException ex) {}
        lobbyChat.setDefaultCloseOperation(ChatFrame.DISPOSE_ON_CLOSE);
        lobbyChat.setVisible(true); 
        openChatToggleButton.setSelected(false);
    }//GEN-LAST:event_openChatToggleButtonActionPerformed

    /**
     * When the game lobby panel is shown, it updates.
     * @param evt
     */
    private void gameLobbyPanelComponentShown(java.awt.event.ComponentEvent evt) {//GEN-FIRST:event_gameLobbyPanelComponentShown
        System.out.println("Game Lobby Panel Shown");
        updateGameTree();
        updateGamePanel(clientRequest.getListOfGameTables()[0]);
    }//GEN-LAST:event_gameLobbyPanelComponentShown

    /**
     * When the friends panel is shown, it updates.
     * @param evt
     */
    private void friendsPanelComponentShown(java.awt.event.ComponentEvent evt) {//GEN-FIRST:event_friendsPanelComponentShown
        System.out.println("Friends Panel Shown");
        try {
            updateFriendsPanel(clientRequest.getUsername());
        } catch (RemoteException ex) {} catch (SQLException ex) {}
    }//GEN-LAST:event_friendsPanelComponentShown
    
    /**
     * When the statistics panel is shown, the statistics table updates. 
     * @param evt
     */
    private void statisticsPanelComponentShown(java.awt.event.ComponentEvent evt) {//GEN-FIRST:event_statisticsPanelComponentShown
        System.out.println("Statistics Panel Shown");
        // Statistics updated 
        
        String[][] statistics = null;
        try {
            statistics = clientRequest.retrieveStatistics();
        } catch (SQLException ex) { JOptionPane.showMessageDialog(this, "The server hates you.", "Statistics Request Failed", JOptionPane.ERROR_MESSAGE);}
        int r = 0; 
        while (statistics.length > r) {
            for (int c = 0; c < 5; c++)
                statisticsTable.getModel().setValueAt(statistics[r][c], r, c);
            r += 1; 
        } 
        
    }//GEN-LAST:event_statisticsPanelComponentShown

    /**
     * When the profile panel is shown, it updates.
     * @param evt
     */
    private void profilePanelComponentShown(java.awt.event.ComponentEvent evt) {//GEN-FIRST:event_profilePanelComponentShown
        updateProfilePanel();
    }//GEN-LAST:event_profilePanelComponentShown

    /**
     * When the user clicks on a friends name in the friends list, the friends
     * panel updates.
     * @param evt 
     */
    private void friendsListValueChanged(javax.swing.event.ListSelectionEvent evt) {//GEN-FIRST:event_friendsListValueChanged

        if (friendsList.getSelectedValue() != null) {
             String str = friendsList.getSelectedValue().toString();
             String  friendSelected = str.substring(0,  str.lastIndexOf("   "));
              try {
                  updateFriendsPanel(friendSelected);
                  System.out.println("MainMenuFrame: friendsListValueChanged() friendSelected: " + friendSelected +";");
                  if (friendSelected.equals(clientRequest.getUsername()))
                       updateFriendsPanel(clientRequest.getUsername());

               } catch (RemoteException ex) {} catch (SQLException ex) {}

        }
    }//GEN-LAST:event_friendsListValueChanged

    
     /**
     * If a table name in the tree is selected, the game panel is told to 
     * display its information.
     * @param evt
     */
    private void gameTreeValueChanged(javax.swing.event.TreeSelectionEvent evt) {//GEN-FIRST:event_gameTreeValueChanged
        if (gameTree.getLastSelectedPathComponent() != null) {
            String selection = gameTree.getLastSelectedPathComponent().toString();
            String[] listOfTables = clientRequest.getListOfGameTables();
            for (int i = 0; i < listOfTables.length; i++) {
                if (selection.equals(listOfTables[i])) {
                    updateGamePanel(selection);    
                    break;
                }    
            }               
        }
        
        
    }//GEN-LAST:event_gameTreeValueChanged

    /*
     * Menu Item Methods
     */
    
    /**
     * Display information about the program when selected. 
     * @param evt 
     */
    private void aboutMenuItemActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_aboutMenuItemActionPerformed
        JOptionPane.showMessageDialog(this, "321 Poker developed by:\n\nPeter Henderson\nMatthew Lee\nArthur Kam\nMatthew Vertescher\nMouhyi Eddine El Bouhali", "About", JOptionPane.PLAIN_MESSAGE);
    }//GEN-LAST:event_aboutMenuItemActionPerformed
    
    /**
     * Returns the user to the login screen. 
     * @param evt 
     */
    private void toLoginMenuItemActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_toLoginMenuItemActionPerformed
        LoginFrame lf = new LoginFrame();
        lf.setVisible(true);
        serverListener.releaseMainMenuFrame();
        this.setVisible(false); 
        this.dispose();
        try {
            clientRequest.logout();
        } catch (RemoteException ex) {} catch (SQLException ex) {}
    }//GEN-LAST:event_toLoginMenuItemActionPerformed

    /**
     * Quits the application.
     * @param evt 
     */
    private void quitMenuItemActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_quitMenuItemActionPerformed
        serverListener.releaseMainMenuFrame();
        this.setVisible(false); 
        this.dispose();
        try {
            clientRequest.logout();
        } catch (RemoteException ex) {} catch (SQLException ex) {}
        System.exit(0); 
    }//GEN-LAST:event_quitMenuItemActionPerformed
    
    /**
     * Opens a new CreateTableFrame to create a new table on the server.
     * @param evt 
     */
    private void createNewTableMenuItemActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_createNewTableMenuItemActionPerformed
        CreateTableFrame ctf = new CreateTableFrame(clientRequest);
        ctf.setVisible(true);
        ctf.setDefaultCloseOperation(CreateTableFrame.DISPOSE_ON_CLOSE);
    }//GEN-LAST:event_createNewTableMenuItemActionPerformed
    
    /**
     * Attempts to invite a friend to a table. 
     * @param evt 
     */
    private void inviteFriendMenuItemActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_inviteFriendMenuItemActionPerformed
        if (inTable = true) {
            String friend = JOptionPane.showInputDialog(this, "Friend: ", "Invite Friend", JOptionPane.PLAIN_MESSAGE);
            boolean valid = false;
            if (friend != null) {
            	try {
            		valid = clientRequest.hasFriend(friend);
            	} catch (RemoteException ex) {} catch (SQLException ex) {}
            	if (valid) {
            		boolean completed = clientRequest.inviteFriendToTable(clientRequest.getCurrentTable(), friend);
            		if (completed)
            			JOptionPane.showMessageDialog(this, "You have invited " + friend + " to join your table.", "Invite Sent!", JOptionPane.INFORMATION_MESSAGE);
            		else
            			JOptionPane.showMessageDialog(this, "Friend is offline", "Error", JOptionPane.ERROR_MESSAGE);
            		}
            	else 
            		JOptionPane.showMessageDialog(this, "Friend does not exist...", "Error", JOptionPane.ERROR_MESSAGE);
            }
        }    
    }//GEN-LAST:event_inviteFriendMenuItemActionPerformed

    /**
     * Attempts to start a game of poker at a table. 
     * @param evt 
     */
    private void startGameMenuItemActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_startGameMenuItemActionPerformed
        String currentTable = clientRequest.getCurrentTable();
        if (inTable = true && clientRequest.getTableHost(currentTable).equals(clientRequest.getUsername())) {
            boolean request = clientRequest.startGameRequest(currentTable); 
            if (request == false){
                JOptionPane.showMessageDialog(this, "The server hates you.", "Start Game Request Denied", JOptionPane.ERROR_MESSAGE);
            }//else 
              //  openGameFrame(currentTable); 
        }
        else if (inTable == false)
            JOptionPane.showMessageDialog(this, "You are not part of a table", "Start Game Request Denied", JOptionPane.ERROR_MESSAGE);
        else if (!clientRequest.getTableHost(currentTable).equals(clientRequest.getUsername()))
            JOptionPane.showMessageDialog(this, "You are not the host", "Start Game Request Denied", JOptionPane.ERROR_MESSAGE);
    }//GEN-LAST:event_startGameMenuItemActionPerformed

    /**
     * Tries to add a friend.
     * @param evt 
     */
    private void addFriendMenuItemActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_addFriendMenuItemActionPerformed
        String friend = "";
        try {
            friend = JOptionPane.showInputDialog(this, "Friend: ", "Add Friend", JOptionPane.PLAIN_MESSAGE);
        } catch (NullPointerException e) {
            JOptionPane.showMessageDialog(this, "No input.", "Error", JOptionPane.ERROR_MESSAGE);
        }    
        boolean completed = false;
        try {
            completed = clientRequest.addFriend(friend);
        } catch (RemoteException ex) {} catch (SQLException ex) {} catch (NullPointerException e) {}
        if (completed) 
            JOptionPane.showMessageDialog(this, friend + " has been added to your friends", "Done!", JOptionPane.INFORMATION_MESSAGE);
        else 
            JOptionPane.showMessageDialog(this, "Could not add friend", "Sorry", JOptionPane.ERROR_MESSAGE);
    }//GEN-LAST:event_addFriendMenuItemActionPerformed

    /**
     * Tries to delete a friend.
     * @param evt 
     */
    private void deleteFriendMenuItemActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_deleteFriendMenuItemActionPerformed
        String friend = "";
        try {
            friend = JOptionPane.showInputDialog(this, "Friend: ", "Delete Friend", JOptionPane.PLAIN_MESSAGE);
        } catch (NullPointerException e) {
            JOptionPane.showMessageDialog(this, "No input.", "Error", JOptionPane.ERROR_MESSAGE);
        }      
        boolean completed = false;
        try {
            completed = clientRequest.deleteFriend(friend);
        } catch (RemoteException ex) {} catch (SQLException ex) {} catch (NullPointerException e) {}
        if (completed) 
            JOptionPane.showMessageDialog(this, friend + " has been removed from your friends", "Done!", JOptionPane.INFORMATION_MESSAGE);
        else 
            JOptionPane.showMessageDialog(this, "Could not remove friend", "Sorry", JOptionPane.ERROR_MESSAGE);
    }//GEN-LAST:event_deleteFriendMenuItemActionPerformed

    /**
     * Attempts to change a users username.
     * @param evt 
     */
    private void changeUsernameMenuItemActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_changeUsernameMenuItemActionPerformed
        String newUsername = JOptionPane.showInputDialog(this, "New Username: ", "Change Username", JOptionPane.PLAIN_MESSAGE);

        if (newUsername == null) {
             //Do Nothing
        }
        else if (newUsername.equals(""))
            JOptionPane.showMessageDialog(this, "Username field blank", "Error", JOptionPane.ERROR_MESSAGE);
        else if (newUsername.equals(clientRequest.getUsername()))    
            JOptionPane.showMessageDialog(this, "This is already your username", "Error", JOptionPane.ERROR_MESSAGE);
        else {
            boolean changed = false;
            try {
                changed = clientRequest.setUsername(newUsername);
            } catch (RemoteException ex) {} catch (SQLException ex) {}
            if (changed)
                    JOptionPane.showMessageDialog(this, "Username Changed", "Success!", JOptionPane.INFORMATION_MESSAGE);
                else
                    JOptionPane.showMessageDialog(this, "The server hates you.", "Error", JOptionPane.ERROR_MESSAGE);
        }   
    }//GEN-LAST:event_changeUsernameMenuItemActionPerformed

    /**
     * Attempts to purchase chips.
     * @param evt 
     */
    private void purchaseChipsMenuItemActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_purchaseChipsMenuItemActionPerformed
        String chipsString = JOptionPane.showInputDialog(this, "Number of Chips: ", "Purchase Chips", JOptionPane.PLAIN_MESSAGE);

        if (chipsString == null) {
             //Do Nothing
        }
        else if (chipsString.equals(""))
            JOptionPane.showMessageDialog(this, "Please enter an amount.", "Error", JOptionPane.ERROR_MESSAGE);
        else {
            boolean isNumber = false;
            try {
                double d = Double.parseDouble(chipsString);
                isNumber = true;
            } catch (NumberFormatException e) {
                JOptionPane.showMessageDialog(this, "Not a number.", "Error", JOptionPane.ERROR_MESSAGE);

            }
            if (isNumber) {
                double chips = Double.parseDouble(chipsString);

                boolean purchased = clientRequest.purchaseChips(chips);
                if (purchased) {
                    JOptionPane.showMessageDialog(this, "Chips puchased.", "Success!", JOptionPane.INFORMATION_MESSAGE);
                } else {
                    JOptionPane.showMessageDialog(this, "The server hates you.", "Error", JOptionPane.ERROR_MESSAGE);
                }
            }
        }
    }//GEN-LAST:event_purchaseChipsMenuItemActionPerformed

    /**
     * Attempts to change a users password.
     * @param evt 
     */
    private void changePasswordMenuItemActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_changePasswordMenuItemActionPerformed
        String oldPassword = JOptionPane.showInputDialog(this, "Old Password: ", "Enter Old Password", JOptionPane.PLAIN_MESSAGE);
         if (oldPassword == null) {
             //Do Nothing
        }
         else if (oldPassword.equals(clientRequest.getPassword())) {
            String newPassword1 = JOptionPane.showInputDialog(this, "New Password: ", "Enter New Password", JOptionPane.PLAIN_MESSAGE);
            String newPassword2 = JOptionPane.showInputDialog(this, "New Password: ", "Enter New Password Again", JOptionPane.PLAIN_MESSAGE);
             if (newPassword1 == null || newPassword2 == null) {
                 //Do Nothing
            }
            else if (newPassword1.equals(newPassword2) && !newPassword1.equals("")) {
                boolean changed = false;
                try {
                    changed = clientRequest.setPassword(newPassword1);
                } catch (RemoteException ex) {} catch (SQLException ex) {}
                if (changed)
                    JOptionPane.showMessageDialog(this, "Password Changed", "Success!", JOptionPane.INFORMATION_MESSAGE);
                else
                    JOptionPane.showMessageDialog(this, "The server hates you.", "Error", JOptionPane.ERROR_MESSAGE);
            }  
            else     
                JOptionPane.showMessageDialog(this, "Passwords invalid.", "Error", JOptionPane.ERROR_MESSAGE);
        }
        else 
            JOptionPane.showMessageDialog(this, "Incorrect Password", "Error", JOptionPane.ERROR_MESSAGE);
    }//GEN-LAST:event_changePasswordMenuItemActionPerformed
    
    /**
     * Attempts to upload a new avatar to the server. 
     * @param evt 
     */
    private void uploadNewAvatarMenuItemActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_uploadNewAvatarMenuItemActionPerformed
        JOptionPane.showMessageDialog(this, "Avatar Upload Offline", "Error", JOptionPane.ERROR_MESSAGE);
    }//GEN-LAST:event_uploadNewAvatarMenuItemActionPerformed

    /**
     * Opens the game rules.
     * @param evt 
     */
    private void gameRulesMenuItemActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_gameRulesMenuItemActionPerformed
        JOptionPane.showMessageDialog(this, "Game Rules Missing", "Error", JOptionPane.ERROR_MESSAGE);
    }//GEN-LAST:event_gameRulesMenuItemActionPerformed

    /** 
     * Opens the documentation.
     * @param evt 
     */
    private void documentationMenuItemActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_documentationMenuItemActionPerformed
        JOptionPane.showMessageDialog(this, "Documentation Missing", "Error", JOptionPane.ERROR_MESSAGE);
    }//GEN-LAST:event_documentationMenuItemActionPerformed
    
    /**
     * When the frame closes, server listener releases its main menu frame.
     * @param evt 
     */
    private void formWindowClosing(java.awt.event.WindowEvent evt) {//GEN-FIRST:event_formWindowClosing
        serverListener.releaseMainMenuFrame();
    }//GEN-LAST:event_formWindowClosing

    /**
     * When the game lobby split pane is clicked, it updates.
     * @param evt 
     */
    private void gameLobbySplitPaneMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_gameLobbySplitPaneMouseClicked
        updateGameTree();
        if (clientRequest.getCurrentTable() != null) 
            updateGamePanel(clientRequest.getCurrentTable());  
        updateGamePanel(tableNameLabel.getText());
    }//GEN-LAST:event_gameLobbySplitPaneMouseClicked

    /**
     * When the game tree is clicked, it updates.
     * @param evt 
     */
    private void gameTreeMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_gameTreeMouseClicked
        updateGameTree();
    }//GEN-LAST:event_gameTreeMouseClicked

    /**
     * When the friends list is clicked, it updates.
     * @param evt 
     */
    private void friendsListMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_friendsListMouseClicked
        //try {
        //   updateFriendsPanel(clientRequest.getUsername());
        //} catch (RemoteException ex) {} catch (SQLException ex) {}
    }//GEN-LAST:event_friendsListMouseClicked

    /**
     * When the change avatar button is pressed, a pop-up appears.
     * @param evt 
     */
    private void changeAvatarButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_changeAvatarButtonActionPerformed
        String[] listOfAvatars = {"4chan.gif","Bubbles.jpg","DSotM.png","Gosling.png","blossoms.jpeg","buttercup.jpg","butterfly.jpg","chiyo.jpg","controller.jpg","creeper.jpg","default.jpg","dog.png","domo.jpg","female.jpg","guyfawkes.png","haibo.JPG","hawk.jpg","illusion1.jpg","illusion2.jpg","notBluffing.jpg","reimu.png","shark.jpg","stairs.gif"};
        String  avatarName = (String) JOptionPane.showInputDialog(this, "Please select an avatar","Change Avatar", JOptionPane.PLAIN_MESSAGE, null, listOfAvatars,"Tennis");

        if (avatarName != null) {
            ImageIcon newAvatar = new ImageIcon(GUIClient.class.getResource("avatars/"+avatarName));

           boolean avatarChanged =  clientRequest.setAvatar(newAvatar);
           if (avatarChanged) 
                JOptionPane.showMessageDialog(this, "Your avatar has been changed to " + avatarName, "Avatar Changed", JOptionPane.INFORMATION_MESSAGE);
           else
                JOptionPane.showMessageDialog(this, "Peter is lazy.", "Error", JOptionPane.ERROR_MESSAGE);
           updateProfilePanel();
        }

    }//GEN-LAST:event_changeAvatarButtonActionPerformed

    /**
     * When the donate button is pressed, a pop-up appears.
     * @param evt 
     */
    private void donateButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_donateButtonActionPerformed

         JOptionPane.showMessageDialog(this, "https://www.paypal.com/cgi-bin/webscr?cmd=_donations&business=LFF5K54VJ32B2&lc=CA&item_name=321Cards&currency_code=CAD&bn=PP%2dDonationsBF%3abtn_donate_LG%2egif%3aNonHosted", "Donate", JOptionPane.PLAIN_MESSAGE);

    }//GEN-LAST:event_donateButtonActionPerformed
 

    
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JMenuItem aboutMenuItem;
    private javax.swing.JLabel accountNumberProfileLabel;
    private javax.swing.JMenuItem addFriendMenuItem;
    private javax.swing.JLabel avalableFundsProfileLabel;
    private javax.swing.JLabel avatarFriendsPanelLabel;
    private javax.swing.JLabel avatarProfileLabel;
    private javax.swing.JPanel avatarProfilePanel;
    private javax.swing.JLabel bankAccountProfileLabel;
    private javax.swing.JButton changeAvatarButton;
    private javax.swing.JMenuItem changePasswordMenuItem;
    private javax.swing.JMenuItem changeUsernameMenuItem;
    private javax.swing.JLabel chipsProfileLabel;
    private javax.swing.JMenuItem createNewTableMenuItem;
    private javax.swing.JPanel creditsPanel;
    private javax.swing.JMenuItem deleteFriendMenuItem;
    private javax.swing.JMenuItem documentationMenuItem;
    private javax.swing.JButton donateButton;
    private javax.swing.JLabel emailProfileLabel;
    private javax.swing.JLabel firstNameProfileLabel;
    private javax.swing.JPanel friendDetailsPanel;
    private javax.swing.JList friendsList;
    private javax.swing.JMenu friendsMenu;
    private javax.swing.JPanel friendsPanel;
    private javax.swing.JScrollPane friendsScrollPane;
    private javax.swing.JSplitPane friendsSplitPane;
    private javax.swing.JLabel fundsProfileLabel;
    private javax.swing.JPanel gameDetailsPanel;
    private javax.swing.JLabel gameFramePictureLabel;
    private javax.swing.JScrollPane gameLobbyConsoleScrollPane;
    private javax.swing.JTextArea gameLobbyConsoleTextArea;
    private javax.swing.JPanel gameLobbyPanel;
    private javax.swing.JSeparator gameLobbySeparator;
    private javax.swing.JSplitPane gameLobbySplitPane;
    private javax.swing.JMenu gameMenu;
    private javax.swing.JMenuItem gameRulesMenuItem;
    private javax.swing.JTree gameTree;
    private javax.swing.JScrollPane gameTreeScrollPane;
    private javax.swing.JMenu helpMenu;
    private javax.swing.JMenuItem inviteFriendMenuItem;
    private javax.swing.JLabel ipProfileLabel;
    private javax.swing.JToggleButton joinGameToggleButton;
    private javax.swing.JLabel lastNameProfileLabel;
    private javax.swing.JPanel mainPanel;
    private javax.swing.JTabbedPane mainTabbedPane;
    private javax.swing.JMenuBar menuBar;
    private javax.swing.JLabel numberOfChipsProfileLabel;
    private javax.swing.JToggleButton openChatToggleButton;
    private javax.swing.JMenu optionsMenu;
    private javax.swing.JMenu pokerMenu;
    private javax.swing.JPanel profilePanel;
    private javax.swing.JMenuItem purchaseChipsMenuItem;
    private javax.swing.JMenuItem quitMenuItem;
    private javax.swing.JLabel serverIPProfileLabel;
    private javax.swing.JMenuItem startGameMenuItem;
    private javax.swing.JPanel staticProfilePanel;
    private javax.swing.JPanel statisticsPanel;
    private javax.swing.JScrollPane statisticsScrollPane;
    private javax.swing.JTable statisticsTable;
    private javax.swing.JLabel tableAnteLabel;
    private javax.swing.JLabel tableBringInLabel;
    private javax.swing.JLabel tableHostLabel;
    private javax.swing.JLabel tableNameLabel;
    private javax.swing.JList tablePlayersList;
    private javax.swing.JScrollPane tablePlayersScrollPane;
    private javax.swing.JMenuItem toLoginMenuItem;
    private javax.swing.JMenuItem uploadNewAvatarMenuItem;
    private javax.swing.JLabel usernameFriendsPanelLabel;
    private javax.swing.JLabel usernameProfileLabel;
    private javax.swing.JLabel worthFriendsPanelLabel;
    // End of variables declaration//GEN-END:variables
}


// MAYBE?
class ConnectionLostExceptionError extends Exception {
    public ConnectionLostExceptionError() { 
        System.out.println("Connection Lost"); 
    } 
    
}