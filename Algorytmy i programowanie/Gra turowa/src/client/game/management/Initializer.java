package client.game.management;

import client.GUI.BackgroundLabel;
import client.GUI.BackgroundMapGenerator;
import client.GUI.MapGUI;
import client.game.components.map.WorldMap;
import client.network.ClientCommunicationLogic;
import server.Server;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ItemEvent;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class Initializer {
    private IGameManager gameManager;
    private MapGUI mapGUI;
    private boolean isHost = false;
    private static final int INITIAL_PLAYERS = 1;
    public static final int MAP_SIZE = 35;
    private ClientCommunicationLogic networkComponent;
    private int playerNumber;


    public Initializer() {
        this.gameManager = new Manager(INITIAL_PLAYERS);
    }

    public void setupGame(boolean isNetworkGame, boolean isHost, String name, String hostAddress) {
        setPlayerName(name);
        if (isNetworkGame) {
            initializeMap(MAP_SIZE);
            setupNetwork(isHost, hostAddress);
        } else {
            gameManager.setTotalPlayers(playerNumber);
            startLocalGame();
        }
        if (gameManager.getWorldMap() != null) {
            showMapGUI();
        }

        try {
            Thread.sleep(4000);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    private void setupNetwork(boolean isHost, String hostAddress) {
        this.isHost = isHost;
        this.networkComponent = new ClientCommunicationLogic(isHost, gameManager);
        if (isHost) {
            new Thread(Server::new).start();
        }
        try {
            if (isHost) {
                networkComponent.connect("localhost");
            } else {
                networkComponent.connect(hostAddress);
            }
        } catch (IOException e) {
            JOptionPane.showMessageDialog(null, "Failed to connect to server: " + e.getMessage(), "Network Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void startLocalGame() {
        initializeMap(MAP_SIZE);
        gameManager = new SPGameManager(gameManager);
        gameManager.startGame();
        SwingUtilities.invokeLater(this::showLocalMapGUI);
    }

    private void initializeMap(int size) {
        WorldMap initialMap = new WorldMap(size,playerNumber);
        gameManager.updateMap(initialMap);
    }

    private void showMapGUI() {
        Timer refreshTimer = new Timer(100, _ -> {
            if (gameManager.isRefreshMap()) {
                if (this.mapGUI == null) {
                    this.mapGUI = new MapGUI(gameManager);
                } else {
                    mapGUI.updateMap();
                    mapGUI.refreshMap();
                    gameManager.setRefreshMap(false);
                }
            }
        });
        refreshTimer.start();
    }

    private void showLocalMapGUI() {
        if (this.mapGUI == null) {
            this.mapGUI = new MapGUI(gameManager);
        } else {
            mapGUI.updateMap();
            mapGUI.refreshMap();
            gameManager.setRefreshMap(false);
        }
    }

    private void setPlayerName(String name) {
        gameManager.setPlayerName(name);
    }

    public static void main(String[] args) {
        Initializer initializer = new Initializer();
        initializer.showInitialWindow();
    }

    private void showInitialWindow() {
        JFrame frame = new JFrame("Game Setup");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLayout(new CardLayout());

        frame.setPreferredSize(new Dimension(800, 600));
        JPanel mainPanel = new JPanel(new CardLayout());
        JPanel startPanel = createStartPanel(mainPanel, frame);
        JPanel setupPanel = createSetupPanel(mainPanel, frame);

        mainPanel.add(startPanel, "StartPanel");
        mainPanel.add(setupPanel, "SetupPanel");

        frame.add(mainPanel);
        frame.pack();
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
    }

    private JPanel createStartPanel(JPanel mainPanel, JFrame frame) {
        WorldMap worldMap = new WorldMap(100,2); // Example size, adjust as needed
        BackgroundMapGenerator bgGenerator = new BackgroundMapGenerator(worldMap, 10); // Example tile size, adjust as needed
        BufferedImage bgImage = bgGenerator.generateBackgroundImage(800, 600);

        JPanel startPanel = new JPanel(new BorderLayout()) {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                g.drawImage(bgImage, 0, 0, getWidth(), getHeight(), this);
            }
        };

        BackgroundLabel welcomeLabel = new BackgroundLabel("APRO STRATEGIA TUROWA", SwingConstants.CENTER);
        try {
            Font pixelFont = Font.createFont(Font.TRUETYPE_FONT, new File("resources/8-bit Arcade In.ttf")).deriveFont(65f);
            welcomeLabel.setFont(pixelFont);
        } catch (Exception e) {
            welcomeLabel.setFont(new Font("Serif", Font.BOLD, 80));
        }
        welcomeLabel.setForeground(new Color(0, 0, 139)); // Set font color
        startPanel.add(welcomeLabel, BorderLayout.CENTER);

        JButton startButton = new JButton("Play");
        startButton.setFont(new Font("Serif", Font.BOLD, 18));
        startButton.setBackground(new Color(70, 70, 70)); // Set button background color
        startButton.setForeground(Color.WHITE); // Set button font color
        startButton.setFocusPainted(false);
        startButton.addActionListener(e -> {
            CardLayout cl = (CardLayout) mainPanel.getLayout();
            cl.show(mainPanel, "SetupPanel"); // Switch to the setup panel
            frame.setSize(new Dimension(300, 200));
            frame.setLocationRelativeTo(null); // Środkowanie okna na ekranie

        });
        startPanel.add(startButton, BorderLayout.SOUTH);
        return startPanel;
    }
    private JPanel createSetupPanel(JPanel mainPanel, JFrame frame) {
        JPanel setupPanel = new JPanel(new BorderLayout());
        setupPanel.setBackground(new Color(30, 30, 30));

        JPanel panel = new JPanel(new GridLayout(0, 2));
        JCheckBox networkGameCheckbox = new JCheckBox("Network Game");
        JCheckBox hostCheckbox = new JCheckBox("Host");
        JTextField nameField = new JTextField("Cristiano Ronaldo");
        JTextField playersField = new JTextField("2");
        JTextField hostAddressField = new JTextField("localhost");
        JButton startButton = new JButton("Start Game");

        panel.add(new JLabel("Is this a network game?"));
        panel.add(networkGameCheckbox);
        panel.add(new JLabel("Are you hosting the game?"));
        panel.add(hostCheckbox);
        panel.add(new JLabel("Your Nickname:"));
        panel.add(nameField);

        hostAddressField.setVisible(false);
        panel.add(new JLabel("Host Address:"));
        panel.add(hostAddressField);

        playersField.setVisible(false);
        panel.add(new JLabel("Number of Players:"));
        panel.add(playersField);

        hostCheckbox.addItemListener(e -> {
            if (e.getStateChange() == ItemEvent.DESELECTED) {
                hostAddressField.setVisible(true);
                playersField.setVisible(false);
                startButton.setVisible(false);
            } else {
                hostAddressField.setVisible(false);
                playersField.setVisible(true);
            }
            panel.revalidate();
            panel.repaint();
        });

        networkGameCheckbox.addItemListener(e -> {
            if (e.getStateChange() == ItemEvent.DESELECTED) {
                hostAddressField.setVisible(false);
            } else {
                if (hostCheckbox.isSelected()) {
                    hostAddressField.setVisible(false);
                } else {
                    hostAddressField.setVisible(true);
                }
            }
            panel.revalidate();
            panel.repaint();
        });

        JPanel buttonPanel = new JPanel();
        JButton okButton = new JButton("OK");
        JButton cancelButton = new JButton("Cancel");

        okButton.addActionListener(e -> {
            frame.setPreferredSize(new Dimension(300, 200));
            frame.setLocationRelativeTo(null);
            boolean isNetworkGame = networkGameCheckbox.isSelected();
            boolean isHost = hostCheckbox.isSelected();
            String name = nameField.getText();
            playerNumber = Integer.parseInt(playersField.getText());
            String hostAddress = hostAddressField.getText();

            setupGame(isNetworkGame, isHost, name, hostAddress);

            if (isHost) {
                startButton.setVisible(true);
                frame.pack();
            } else {
                frame.dispose();
            }
        });

        cancelButton.addActionListener(e -> System.exit(0));

        startButton.addActionListener(e -> {
            new Thread(() -> {
                networkComponent.startGame();
            }).start();
            frame.dispose();
        });

        startButton.setVisible(false);

        buttonPanel.add(okButton);
        buttonPanel.add(cancelButton);
        buttonPanel.add(startButton);

        setupPanel.add(panel, BorderLayout.CENTER);
        setupPanel.add(buttonPanel, BorderLayout.SOUTH);

        return setupPanel;
    }
}
