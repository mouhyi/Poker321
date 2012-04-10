package GUI;

import java.awt.Dimension;
import java.rmi.RemoteException;
import java.sql.SQLException;
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
    
    public static boolean inTable = false; 
    // public static boolean chatToggleButtonState = false;
   
    /**
     * Creates new form MainMenuScreen.
     */
    public MainMenuFrame(GUIClient guic) throws RemoteException, SQLException {
        initComponents();
        Dimension screenSize = java.awt.Toolkit.getDefaultToolkit().getScreenSize();
        this.setLocation((screenSize.width/2)-(this.getWidth()/2), (screenSize.height/2)-(this.getHeight()/2));  
        this.setIconImage(new ImageIcon(GUIClient.class.getResource("images/icon_playing_card.png")).getImage());
        
        clientRequest = guic; 
        
        updateGameTree();
        updateGamePanel(GUIClient.getListOfGameTables()[0].toString());
        //updateFriendsPanel(GUIClient.getUsername());
        
        //updateGamePanel(clientRequest.getListOfGameTables()[0].toString());
        updateFriendsPanel(clientRequest.getUsername());
        
    }
    
    /**
     * Pop-Up method to be called by server to alert user
     * @param message 
     */
    public void displayPopUp(String message) {
        JOptionPane.showMessageDialog(this, message, "Message!", JOptionPane.INFORMATION_MESSAGE);
    }
    
    /**
     * Update the game panel with information from a particular table.
     */
    private void updateGamePanel(String gameTable) {
        tableNameLabel.setText(gameTable);
        tablePlayersList.setListData(GUIClient.getPlayersAtGameTable(gameTable));
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
        // Optional to remove root node 
        //gameTree.setRootVisible(false); 
        
        DefaultMutableTreeNode rootServer = new DefaultMutableTreeNode(GUIClient.getIp()); 
   
        String[] gameTables = GUIClient.getListOfGameTables();
        for (int i = 0; i < gameTables.length; i++) {
            DefaultMutableTreeNode table = new DefaultMutableTreeNode(gameTables[i]);
            String[] players = GUIClient.getPlayersAtGameTable(gameTables[i]); 
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
     */
    private void updateFriendsPanel(String username) throws RemoteException, SQLException {
        usernameFriendsPanelLabel.setText(username);
        avatarFriendsPanelLabel.setIcon(GUIClient.getAvatar(username));
        worthFriendsPanelLabel.setText(clientRequest.getUsersWorth(username));
       
        String[] friends = clientRequest.getFriends(clientRequest.getUsername());
        String[] usernames = new String[friends.length + 1];
        usernames[0] = clientRequest.getUsername();
        for (int i = 0; i < friends.length; i++)
            usernames[i + 1] = friends[i];
        friendsList.setListData(usernames);
    }
   
    private void openGameFrame(String table) {
        GameFrame newGame = new GameFrame(clientRequest, table);
        newGame.setVisible(true);
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

        jSeparator1 = new javax.swing.JSeparator();
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
        jLabel3 = new javax.swing.JLabel();
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
        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
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
        changeEmailMenuItem = new javax.swing.JMenuItem();
        changeUsernameMenuItem = new javax.swing.JMenuItem();
        changePasswordMenuItem = new javax.swing.JMenuItem();
        uploadNewAvatarMenuItem = new javax.swing.JMenuItem();
        helpMenu = new javax.swing.JMenu();
        gameRulesMenuItem = new javax.swing.JMenuItem();
        documentationMenuItem = new javax.swing.JMenuItem();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setTitle("Main Menu ");
        setResizable(false);

        gameLobbyPanel.addComponentListener(new java.awt.event.ComponentAdapter() {
            public void componentShown(java.awt.event.ComponentEvent evt) {
                gameLobbyPanelComponentShown(evt);
            }
        });

        gameLobbySplitPane.setDividerLocation(150);

        gameTree.addTreeSelectionListener(new javax.swing.event.TreeSelectionListener() {
            public void valueChanged(javax.swing.event.TreeSelectionEvent evt) {
                gameTreeValueChanged(evt);
            }
        });
        gameTreeScrollPane.setViewportView(gameTree);

        gameLobbySplitPane.setLeftComponent(gameTreeScrollPane);

        tableNameLabel.setFont(new java.awt.Font("Lucida Grande", 0, 24)); // NOI18N
        tableNameLabel.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        tableNameLabel.setText("Table 1");
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
        gameLobbyConsoleTextArea.setRows(5);
        gameLobbyConsoleScrollPane.setViewportView(gameLobbyConsoleTextArea);

        jLabel3.setFont(new java.awt.Font("Rockwell Extra Bold", 0, 36)); // NOI18N
        jLabel3.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel3.setText("321 Poker");

        org.jdesktop.layout.GroupLayout gameDetailsPanelLayout = new org.jdesktop.layout.GroupLayout(gameDetailsPanel);
        gameDetailsPanel.setLayout(gameDetailsPanelLayout);
        gameDetailsPanelLayout.setHorizontalGroup(
            gameDetailsPanelLayout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
            .add(org.jdesktop.layout.GroupLayout.TRAILING, tableNameLabel, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, 475, Short.MAX_VALUE)
            .add(gameDetailsPanelLayout.createSequentialGroup()
                .add(gameDetailsPanelLayout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
                    .add(gameDetailsPanelLayout.createSequentialGroup()
                        .addContainerGap()
                        .add(gameDetailsPanelLayout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
                            .add(org.jdesktop.layout.GroupLayout.TRAILING, gameLobbyConsoleScrollPane)
                            .add(gameDetailsPanelLayout.createSequentialGroup()
                                .add(tablePlayersScrollPane, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, 191, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                                .add(18, 18, 18)
                                .add(gameDetailsPanelLayout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
                                    .add(tableHostLabel, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                    .add(tableAnteLabel, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                    .add(tableBringInLabel, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
                            .add(gameLobbySeparator)
                            .add(gameDetailsPanelLayout.createSequentialGroup()
                                .add(joinGameToggleButton, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .add(18, 18, 18)
                                .add(openChatToggleButton, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, 222, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE))))
                    .add(gameDetailsPanelLayout.createSequentialGroup()
                        .add(12, 12, 12)
                        .add(jLabel3, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
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
                .add(gameDetailsPanelLayout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
                    .add(openChatToggleButton, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, 46, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                    .add(joinGameToggleButton, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, 46, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(org.jdesktop.layout.LayoutStyle.UNRELATED)
                .add(jLabel3, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, 103, Short.MAX_VALUE)
                .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                .add(gameLobbySeparator, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                .add(gameLobbyConsoleScrollPane, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, 90, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );

        gameLobbySplitPane.setRightComponent(gameDetailsPanel);

        org.jdesktop.layout.GroupLayout gameLobbyPanelLayout = new org.jdesktop.layout.GroupLayout(gameLobbyPanel);
        gameLobbyPanel.setLayout(gameLobbyPanelLayout);
        gameLobbyPanelLayout.setHorizontalGroup(
            gameLobbyPanelLayout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
            .add(org.jdesktop.layout.GroupLayout.TRAILING, gameLobbyPanelLayout.createSequentialGroup()
                .addContainerGap()
                .add(gameLobbySplitPane, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, 636, Short.MAX_VALUE)
                .addContainerGap())
        );
        gameLobbyPanelLayout.setVerticalGroup(
            gameLobbyPanelLayout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
            .add(org.jdesktop.layout.GroupLayout.TRAILING, gameLobbyPanelLayout.createSequentialGroup()
                .add(gameLobbySplitPane)
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
        friendsList.addListSelectionListener(new javax.swing.event.ListSelectionListener() {
            public void valueChanged(javax.swing.event.ListSelectionEvent evt) {
                friendsListValueChanged(evt);
            }
        });
        friendsScrollPane.setViewportView(friendsList);

        friendsSplitPane.setLeftComponent(friendsScrollPane);

        friendDetailsPanel.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));

        usernameFriendsPanelLabel.setFont(new java.awt.Font("Lucida Grande", 0, 24)); // NOI18N
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
            .add(friendDetailsPanelLayout.createSequentialGroup()
                .add(friendDetailsPanelLayout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
                    .add(org.jdesktop.layout.GroupLayout.TRAILING, usernameFriendsPanelLabel, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .add(org.jdesktop.layout.GroupLayout.TRAILING, worthFriendsPanelLabel, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .add(org.jdesktop.layout.GroupLayout.TRAILING, avatarFriendsPanelLabel, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, 467, Short.MAX_VALUE))
                .addContainerGap())
        );
        friendDetailsPanelLayout.setVerticalGroup(
            friendDetailsPanelLayout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
            .add(friendDetailsPanelLayout.createSequentialGroup()
                .addContainerGap()
                .add(usernameFriendsPanelLabel, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, 32, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(org.jdesktop.layout.LayoutStyle.UNRELATED)
                .add(avatarFriendsPanelLabel, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, 300, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                .add(18, 18, 18)
                .add(worthFriendsPanelLabel, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, 38, Short.MAX_VALUE)
                .addContainerGap())
        );

        friendsSplitPane.setRightComponent(friendDetailsPanel);

        org.jdesktop.layout.GroupLayout friendsPanelLayout = new org.jdesktop.layout.GroupLayout(friendsPanel);
        friendsPanel.setLayout(friendsPanelLayout);
        friendsPanelLayout.setHorizontalGroup(
            friendsPanelLayout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
            .add(friendsPanelLayout.createSequentialGroup()
                .addContainerGap()
                .add(friendsSplitPane, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, 636, Short.MAX_VALUE)
                .addContainerGap())
        );
        friendsPanelLayout.setVerticalGroup(
            friendsPanelLayout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
            .add(friendsPanelLayout.createSequentialGroup()
                .add(friendsSplitPane)
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
                .add(statisticsScrollPane, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, 636, Short.MAX_VALUE)
                .addContainerGap())
        );
        statisticsPanelLayout.setVerticalGroup(
            statisticsPanelLayout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
            .add(statisticsPanelLayout.createSequentialGroup()
                .add(statisticsScrollPane, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, 418, Short.MAX_VALUE)
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
                .add(accountNumberProfileLabel, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
            .add(staticProfilePanelLayout.createSequentialGroup()
                .addContainerGap()
                .add(staticProfilePanelLayout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
                    .add(bankAccountProfileLabel, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, 238, Short.MAX_VALUE)
                    .add(emailProfileLabel, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .add(org.jdesktop.layout.GroupLayout.TRAILING, usernameProfileLabel, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .add(firstNameProfileLabel, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .add(org.jdesktop.layout.GroupLayout.TRAILING, lastNameProfileLabel, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .add(org.jdesktop.layout.GroupLayout.TRAILING, avalableFundsProfileLabel, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .add(org.jdesktop.layout.GroupLayout.TRAILING, staticProfilePanelLayout.createSequentialGroup()
                        .add(staticProfilePanelLayout.createParallelGroup(org.jdesktop.layout.GroupLayout.TRAILING)
                            .add(org.jdesktop.layout.GroupLayout.LEADING, serverIPProfileLabel, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .add(org.jdesktop.layout.GroupLayout.LEADING, numberOfChipsProfileLabel, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .add(org.jdesktop.layout.GroupLayout.LEADING, chipsProfileLabel, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .add(fundsProfileLabel, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                        .addContainerGap())))
            .add(staticProfilePanelLayout.createSequentialGroup()
                .addContainerGap()
                .add(ipProfileLabel, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
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
                .add(18, 18, 18)
                .add(ipProfileLabel)
                .addContainerGap())
        );

        creditsPanel.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));

        jLabel1.setText("Graphical User Interface made by");

        jLabel2.setFont(new java.awt.Font("Zapfino", 0, 13)); // NOI18N
        jLabel2.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel2.setText("Matthew Vertescher");

        org.jdesktop.layout.GroupLayout creditsPanelLayout = new org.jdesktop.layout.GroupLayout(creditsPanel);
        creditsPanel.setLayout(creditsPanelLayout);
        creditsPanelLayout.setHorizontalGroup(
            creditsPanelLayout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
            .add(creditsPanelLayout.createSequentialGroup()
                .addContainerGap()
                .add(creditsPanelLayout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
                    .add(jLabel1, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .add(jLabel2, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap())
        );
        creditsPanelLayout.setVerticalGroup(
            creditsPanelLayout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
            .add(creditsPanelLayout.createSequentialGroup()
                .addContainerGap()
                .add(jLabel1)
                .addPreferredGap(org.jdesktop.layout.LayoutStyle.UNRELATED)
                .add(jLabel2, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, 44, Short.MAX_VALUE)
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
                .add(avatarProfileLabel, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, 360, Short.MAX_VALUE)
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
                        .add(avatarProfilePanel, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, 320, Short.MAX_VALUE)
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
            .add(mainTabbedPane)
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

        changeEmailMenuItem.setText("Change Email");
        changeEmailMenuItem.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                changeEmailMenuItemActionPerformed(evt);
            }
        });
        optionsMenu.add(changeEmailMenuItem);

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
     */
    private void joinGameToggleButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_joinGameToggleButtonActionPerformed
        String currentTable = clientRequest.getCurrentTable();
        
        if (joinGameToggleButton.isSelected() && !inTable && clientRequest.getCurrentTable() != null) {
            clientRequest.joinGameTable(currentTable);
            openGameFrame(currentTable); // to be removed
            inTable = true;
            
            //openGameFrame(tableNameLabel.getText()); 
        }
        
        else if (joinGameToggleButton.isSelected() && inTable && clientRequest.getCurrentTable().equals(tableNameLabel.getText())) {
            clientRequest.leaveGameTable(currentTable);
            inTable = false;
        }
    }//GEN-LAST:event_joinGameToggleButtonActionPerformed

    /**
     * When the chat toggle button pressed, a new chat window opens. 
     */
    private void openChatToggleButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_openChatToggleButtonActionPerformed

        ChatFrame lobbyChat = null;
        try {
            lobbyChat = new ChatFrame(clientRequest, GUIClient.getOpponentsAtGameTable(clientRequest.getUsername(), tableNameLabel.getText()));
        } catch (RemoteException ex) {} catch (SQLException ex) {}
        lobbyChat.setDefaultCloseOperation(ChatFrame.DISPOSE_ON_CLOSE);
        lobbyChat.setVisible(true); 
        openChatToggleButton.setSelected(false);
    }//GEN-LAST:event_openChatToggleButtonActionPerformed

    /**
     * When the game lobby panel is shown, it updates.
     */
    private void gameLobbyPanelComponentShown(java.awt.event.ComponentEvent evt) {//GEN-FIRST:event_gameLobbyPanelComponentShown
        System.out.println("Game Lobby Panel Shown");
        updateGameTree();
        updateGamePanel(GUIClient.getListOfGameTables()[0].toString());
    }//GEN-LAST:event_gameLobbyPanelComponentShown

    /**
     * When the friends panel is shown, it updates.
     */
    private void friendsPanelComponentShown(java.awt.event.ComponentEvent evt) {//GEN-FIRST:event_friendsPanelComponentShown
        System.out.println("Friends Panel Shown");
        // Update friends panel 
        //System.out.println(friendsList.getSelectedValue().toString());
        
    }//GEN-LAST:event_friendsPanelComponentShown
    
    /**
     * When the statistics panel is shown, the statistics table updates. 
     */
    private void statisticsPanelComponentShown(java.awt.event.ComponentEvent evt) {//GEN-FIRST:event_statisticsPanelComponentShown
        System.out.println("Statistics Panel Shown");
        // Statistics updated 
        
        String[][] statistics = null;
        try {
            statistics = clientRequest.retrieveStatistics();
        } catch (SQLException ex) {}
        int r = 0; 
        while (statistics.length > r) {
            for (int c = 0; c < 5; c++)
                statisticsTable.getModel().setValueAt(statistics[r][c], r, c);
            r += 1; 
        } 
        
    }//GEN-LAST:event_statisticsPanelComponentShown

    /**
     * When the profile panel is shown, it updates.
     */
    private void profilePanelComponentShown(java.awt.event.ComponentEvent evt) {//GEN-FIRST:event_profilePanelComponentShown
        System.out.println("Options Panel Shown");
        firstNameProfileLabel.setText("First Name Here");
        lastNameProfileLabel.setText("Last Name Here");
        emailProfileLabel.setText(clientRequest.getEmail());
        usernameProfileLabel.setText(clientRequest.getUsername());
        accountNumberProfileLabel.setText("Account Number Here");
        fundsProfileLabel.setText("$(Funds)");
        numberOfChipsProfileLabel.setText(clientRequest.getChips(clientRequest.getUsername()));
        ipProfileLabel.setText(GUIClient.getIp()); 
        
    }//GEN-LAST:event_profilePanelComponentShown

    /**
     * When the user clicks on a friends name in the friends list, the friends
     * panel updates.
     * @param evt 
     */
    private void friendsListValueChanged(javax.swing.event.ListSelectionEvent evt) {//GEN-FIRST:event_friendsListValueChanged
        if (friendsList.getSelectedValue() != null)
            try {
            updateFriendsPanel(friendsList.getSelectedValue().toString());
        } catch (RemoteException ex) {} catch (SQLException ex) {}
    }//GEN-LAST:event_friendsListValueChanged

    
     /**
     * If a table name in the tree is selected, the game panel is told to 
     * display its information.
     */
    private void gameTreeValueChanged(javax.swing.event.TreeSelectionEvent evt) {//GEN-FIRST:event_gameTreeValueChanged
        if (gameTree.getLastSelectedPathComponent() != null) {
            String selection = gameTree.getLastSelectedPathComponent().toString();
            String[] listOfTables = GUIClient.getListOfGameTables();
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
        JOptionPane.showMessageDialog(this, "321 Poker developed by:\n\nPeter Henderson\nMatthew Lee\nArthur Kam\nMatthew Vertescher\nMouhyi ", "About", JOptionPane.PLAIN_MESSAGE);
    }//GEN-LAST:event_aboutMenuItemActionPerformed
    
    /**
     * Returns the user to the login screen. 
     * @param evt 
     */
    private void toLoginMenuItemActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_toLoginMenuItemActionPerformed
        LoginFrame lf = new LoginFrame();
        lf.setVisible(true);
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
    }//GEN-LAST:event_inviteFriendMenuItemActionPerformed

    /**
     * Attempts to start a game of poker at a table. 
     * @param evt 
     */
    private void startGameMenuItemActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_startGameMenuItemActionPerformed
        String currentTable = clientRequest.getCurrentTable();
        if (inTable = true && clientRequest.getTableHost(currentTable).equals(clientRequest.getUsername())) {
            boolean request = clientRequest.startGameRequest(currentTable); 
            if (request = false)
                JOptionPane.showMessageDialog(this, "The server hates you.", "Start Game Request Denied", JOptionPane.ERROR_MESSAGE);
            else 
                openGameFrame(currentTable); 
        }
        else if (inTable = false)
            JOptionPane.showMessageDialog(this, "You are not part of a table", "Start Game Request Denied", JOptionPane.ERROR_MESSAGE);
        else if (!clientRequest.getTableHost(currentTable).equals(clientRequest.getUsername()))
            JOptionPane.showMessageDialog(this, "You are not the host", "Start Game Request Denied", JOptionPane.ERROR_MESSAGE);
    }//GEN-LAST:event_startGameMenuItemActionPerformed

    /**
     * Tries to add a friend.
     * @param evt 
     */
    private void addFriendMenuItemActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_addFriendMenuItemActionPerformed
        String friend = JOptionPane.showInputDialog(this, "Friend: ", "Add Friend", JOptionPane.PLAIN_MESSAGE);
        boolean completed = false;
        try {
            completed = clientRequest.addFriend(friend);
        } catch (RemoteException ex) {} catch (SQLException ex) {}
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
        String friend = JOptionPane.showInputDialog(this, "Friend: ", "Delete Friend", JOptionPane.PLAIN_MESSAGE);
        boolean completed = false;
		try {
			completed = clientRequest.deleteFriend(friend);
		} catch (RemoteException e) {} catch (SQLException e) {}
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
        if (newUsername.equals(""))
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
     * Attempts to change a uses email.
     * @param evt 
     */
    private void changeEmailMenuItemActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_changeEmailMenuItemActionPerformed
        String newEmail = JOptionPane.showInputDialog(this, "New Email: ", "Change Email", JOptionPane.PLAIN_MESSAGE);
        if (newEmail.equals(""))
            JOptionPane.showMessageDialog(this, "Email field blank", "Error", JOptionPane.ERROR_MESSAGE);
        else if (newEmail.equals(clientRequest.getEmail()))    
            JOptionPane.showMessageDialog(this, "This is already your email", "Error", JOptionPane.ERROR_MESSAGE);
        else {
            boolean changed = false;
            try {
                changed = clientRequest.setEmail(newEmail);
            } catch (RemoteException ex) {} catch (SQLException ex) {}
            if (changed)
                    JOptionPane.showMessageDialog(this, "Email Changed", "Success!", JOptionPane.INFORMATION_MESSAGE);
                else
                    JOptionPane.showMessageDialog(this, "The server hates you.", "Error", JOptionPane.ERROR_MESSAGE);
        }   
    }//GEN-LAST:event_changeEmailMenuItemActionPerformed

    /**
     * Attempts to change a uses password.
     * @param evt 
     */
    private void changePasswordMenuItemActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_changePasswordMenuItemActionPerformed
        String oldPassword = JOptionPane.showInputDialog(this, "Old Password: ", "Enter Old Password", JOptionPane.PLAIN_MESSAGE);
        if (oldPassword.equals(clientRequest.getPassword())) {
            String newPassword1 = JOptionPane.showInputDialog(this, "New Password: ", "Enter New Password", JOptionPane.PLAIN_MESSAGE);
            String newPassword2 = JOptionPane.showInputDialog(this, "New Password: ", "Enter New Password Again", JOptionPane.PLAIN_MESSAGE);
            if (newPassword1.equals(newPassword2) && !newPassword1.equals("")) {
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
 
    
    
    
    
   
    
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JMenuItem aboutMenuItem;
    private javax.swing.JLabel accountNumberProfileLabel;
    private javax.swing.JMenuItem addFriendMenuItem;
    private javax.swing.JLabel avalableFundsProfileLabel;
    private javax.swing.JLabel avatarFriendsPanelLabel;
    private javax.swing.JLabel avatarProfileLabel;
    private javax.swing.JPanel avatarProfilePanel;
    private javax.swing.JLabel bankAccountProfileLabel;
    private javax.swing.JMenuItem changeEmailMenuItem;
    private javax.swing.JMenuItem changePasswordMenuItem;
    private javax.swing.JMenuItem changeUsernameMenuItem;
    private javax.swing.JLabel chipsProfileLabel;
    private javax.swing.JMenuItem createNewTableMenuItem;
    private javax.swing.JPanel creditsPanel;
    private javax.swing.JMenuItem deleteFriendMenuItem;
    private javax.swing.JMenuItem documentationMenuItem;
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
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JSeparator jSeparator1;
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