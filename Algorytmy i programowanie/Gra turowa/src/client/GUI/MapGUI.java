package client.GUI;

import client.game.components.unit.hero.*;
import client.game.components.utils.exceptions.InsufficientResourcesException;
import client.game.management.IGameManager;
import client.game.management.Manager;
import client.game.components.map.WorldMap;
import client.game.components.map.tile.Tile;
import client.game.components.utils.Directions;
import client.game.components.Castle;
import client.game.components.Resources;
import client.game.components.unit.Unit;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Map;

public class MapGUI {
    private JFrame frame;
    private Worker worker;
    private final IGameManager manager;
    private WorldMap worldMap;
    private BufferedImage mapImage;
    private JPanel mapPanel;  // JPanel to display the map
    private final int tileSize = 18;
    private JButton playTurnButton;
    private JPanel statsPanel; // Panel to display game statistics
    private JPanel actionPanel; // Panel to display player actions
    private JLabel castleHealthLabel;
    private JLabel castleLevelLabel;
    private JLabel castleDefenseLabel;
    private JLabel currentPlayerLabel;
    private JPanel unitPanel; // Panel to display unit information
    private JPanel resourcePanel; // Panel to display resource information
    private JLabel currentPlayerTopLabel; // Label to display the current player at the top
    private BufferedImage[] tileTextures = new BufferedImage[8];

    public MapGUI(IGameManager manager) {
        this.manager = manager;
        this.worldMap = manager.getWorldMap();
        initializeFrame();
        refreshMap();
        refreshStats();
        addTileClickListener(); // Add this line to initialize click listener
    }

    private void initializeFrame() {
        frame = new JFrame("<Game> " + manager.getPlayer().getPlayerName());
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(1000, 800);
        frame.setResizable(false);
        frame.setLayout(new BorderLayout()); // Set layout to BorderLayout to add button and stats panel

        // Create a panel for the current player label at the top
        JPanel topPanel = new JPanel();
        currentPlayerTopLabel = new JLabel();
        topPanel.add(currentPlayerTopLabel);
        frame.add(topPanel, BorderLayout.NORTH);

        mapPanel = new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                g.drawImage(mapImage, 0, 0, this);
            }
        };
        frame.add(mapPanel, BorderLayout.CENTER);

        statsPanel = new JPanel();
        statsPanel.setLayout(new BoxLayout(statsPanel, BoxLayout.Y_AXIS));
        statsPanel.setPreferredSize(new Dimension(200, frame.getHeight()));
        frame.add(statsPanel, BorderLayout.EAST);

        actionPanel = new JPanel();
        actionPanel.setLayout(new FlowLayout());
        frame.add(actionPanel, BorderLayout.SOUTH);

        // Dodaj przycisk Upgrade Castle
        JButton upgradeCastleButton = new JButton("Upgrade Castle");
        upgradeCastleButton.addActionListener(e -> upgradeCastle(upgradeCastleButton));
        actionPanel.add(upgradeCastleButton);

        // Dodaj przycisk Add Troop
        JButton addTroopButton = new JButton("Add Troop");
        addTroopButton.addActionListener(e -> addTroop());
        actionPanel.add(addTroopButton);

        playTurnButton = new JButton("Play Turn"); // Create the Play Turn button
        playTurnButton.addActionListener(e -> {
            refreshMap(); // Optionally refresh map to reflect any changes
            refreshStats(); // Refresh stats to reflect any changes
            manager.playTurn(); // Call playTurn method on button click
        });
        actionPanel.add(playTurnButton);

        castleHealthLabel = new JLabel();
        castleLevelLabel = new JLabel();
        castleDefenseLabel = new JLabel();
        currentPlayerLabel = new JLabel();

        statsPanel.add(castleHealthLabel);
        statsPanel.add(castleLevelLabel);
        statsPanel.add(castleDefenseLabel);

        unitPanel = new JPanel();
        unitPanel.setLayout(new BoxLayout(unitPanel, BoxLayout.Y_AXIS));
        statsPanel.add(unitPanel);

        resourcePanel = new JPanel();
        resourcePanel.setLayout(new BoxLayout(resourcePanel, BoxLayout.Y_AXIS));
        statsPanel.add(resourcePanel);

        frame.setVisible(true);
    }

    public void updateMap() {
        this.worldMap = manager.getWorldMap();
    }

    public void refreshMap() {
        mapImage = new BufferedImage(600, 600, BufferedImage.TYPE_INT_ARGB);
        Graphics2D g2 = mapImage.createGraphics();

        try {
            tileTextures[0] = ImageIO.read(new File("src/textures/desert.png"));
            tileTextures[1] = ImageIO.read(new File("src/textures/forest.png"));
            tileTextures[2] = ImageIO.read(new File("src/textures/mountains.png"));
            tileTextures[3] = ImageIO.read(new File("src/textures/plain.png"));
            tileTextures[4] = ImageIO.read(new File("src/textures/village.png"));
            tileTextures[5] = ImageIO.read(new File("src/textures/water.png"));
            tileTextures[6] = ImageIO.read(new File("src/textures/bridge.png"));
            tileTextures[7] = ImageIO.read(new File("src/textures/castle.png"));
        } catch(IOException e) {
            e.printStackTrace();
        }

        // Draw each tile
        for (int x = 0; x < worldMap.getSize(); x++) {
            for (int y = 0; y < worldMap.getSize(); y++) {
                Tile tile = worldMap.getTile(x, y);
                if (tile.getUnitOnTile() != null) {
                    g2.setColor(getUnitColor(tile));
                    g2.fillRect(y * tileSize, x * tileSize, tileSize, tileSize);
                } else g2.drawImage(scaleImage(getTileTexture(tile), tileSize, tileSize), y * tileSize, x * tileSize, null);
            }
        }
        g2.dispose();
        mapPanel.repaint();  // Repaint the panel to update the image display
    }

    private BufferedImage getTileTexture(Tile tile) {
        return switch (tile.getClass().getSimpleName()) {
            case "Desert" -> tileTextures[0];
            case "Forest" -> tileTextures[1];
            case "Mountains" -> tileTextures[2];
            case "Plain" -> tileTextures[3];
            case "Village" -> tileTextures[4];
            case "Water" -> tileTextures[5];
            case "Bridge" -> tileTextures[6];
            default -> tileTextures[7];
        };
    }

    private static BufferedImage scaleImage(BufferedImage originalImage, int width, int height) {
        Image scaledImage = originalImage.getScaledInstance(width, height, Image.SCALE_SMOOTH);
        BufferedImage bufferedScaledImage = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
        Graphics2D g2d = bufferedScaledImage.createGraphics();
        g2d.drawImage(scaledImage, 0, 0, null);
        g2d.dispose();
        return bufferedScaledImage;
    }

    private Color getUnitColor(Tile tile) {
        // This method should implement the logic to determine the tile color
        // based on the tile type and any units that might be present.
        // This replaces the switch statement in updatePanelColor.
        return switch (tile.getUnitOnTile().getClass().getSimpleName()) {
            case "Archer" -> new Color(250, 150, 50);
            case "Druid" -> new Color(24, 155, 155);
            case "Healer" -> new Color(200, 50, 200);
            case "Knight" -> new Color(114, 104, 104);
            case "Warlock" -> new Color(100, 50, 100);
            case "Wizard" -> new Color(241, 113, 113);
            case "Worker" -> Color.BLACK;
            case "Barbarian" -> new Color(38, 141, 96);
            case "Dragon" -> new Color(171, 78, 18);
            case "Thief" -> new Color(172, 196, 255);
            case "Trader" -> new Color(47, 47, 145);
            default -> Color.WHITE;
        };
    }



    private void addTileClickListener() {
        mapPanel.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                int x = e.getY() / tileSize;
                int y = e.getX() / tileSize;
                Tile clickedTile = worldMap.getTile(x, y);
                showTileInfo(clickedTile);
            }
        });
    }

    private void showTileInfo(Tile tile) {
        StringBuilder info = new StringBuilder("Tile Information:\n");
        info.append("Type: ").append(tile.getClass().getSimpleName()).append("\n");

        // Check if there is a unit on the tile
        Unit unit = tile.getUnitOnTile();
        if (unit != null) {
            info.append("Unit: ").append(unit.getClass().getSimpleName()).append("\n");
            // Assuming Unit has methods for life and damage, adjust accordingly if needed
            info.append("Health: ").append(unit.getHealth()).append("\n");
            info.append("Damage: ").append(unit.getDamage()).append("\n");
        } else {
            info.append("No unit on this tile.\n");
        }
        Resources resourceType = tile.getResource();
        if (resourceType != null) {
            info.append("Resource:\n");
            info.append(resourceType.name());  // Only one resource of this type
        } else {
            info.append("No resource on this tile.\n");
        }

        JOptionPane.showMessageDialog(frame, info.toString(), "Tile Information", JOptionPane.INFORMATION_MESSAGE);
    }

    private void handleTileClick(int x, int y) {
        Tile tile = manager.getWorldMap().getTile(x, y);
        if (tile.equals(worker.getTile())) {
            ArrayList<Directions> directions = worker.getRiverDirections();
            if (!directions.isEmpty()) {
                showDirectionButtons(directions);
            } else {
                JOptionPane.showMessageDialog(frame, "No river nearby to build a bridge.", "Action Not Possible", JOptionPane.INFORMATION_MESSAGE);
            }
        }
    }

    private void showDirectionButtons(ArrayList<Directions> directions) {
        JPanel buttonPanel = new JPanel(new FlowLayout());
        for (Directions direction : directions) {
            JButton button = new JButton(direction.name());
            button.addActionListener(e -> {
                try {
                    worker.buildBridge(manager.getWorldMap(), direction);
                    refreshMap();
                } catch (Exception ex) {
                    JOptionPane.showMessageDialog(frame, ex.getMessage(), "Action not possible", JOptionPane.INFORMATION_MESSAGE);
                }
            });
            buttonPanel.add(button);
        }
        JOptionPane.showMessageDialog(frame, buttonPanel, "Select Direction to Build Bridge", JOptionPane.PLAIN_MESSAGE);
    }

    private void refreshStats() {
        Castle castle = manager.getPlayer().getCastle();
        System.out.println("Refreshing stats...");
        System.out.println("Castle Level: " + castle.getLevel());  // Debugging line

        // Set layout and constraints for statsPanel
        statsPanel.setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.anchor = GridBagConstraints.WEST;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.weightx = 1.0;

        // Castle Health, Level, and Defense Labels
        castleHealthLabel.setText("Castle Health: " + castle.getHealthPoints());
        castleLevelLabel.setText("Castle Level: " + castle.getLevel());
        castleDefenseLabel.setText("Castle Defense: " + castle.getDefense());

        statsPanel.removeAll();
        statsPanel.add(castleHealthLabel, gbc);
        gbc.gridy++;
        statsPanel.add(castleLevelLabel, gbc);
        gbc.gridy++;
        statsPanel.add(castleDefenseLabel, gbc);

        // Resource Header
        gbc.gridy++;
        statsPanel.add(new JLabel("Your resources:"), gbc);

        // Update resources
        gbc.gridy++;
        resourcePanel.removeAll();
        resourcePanel.setLayout(new GridBagLayout());
        GridBagConstraints resGbc = new GridBagConstraints();
        resGbc.anchor = GridBagConstraints.WEST;
        resGbc.gridx = 0;
        resGbc.gridy = 0;
        resGbc.weightx = 1.0;

        for (Map.Entry<Resources, Integer> entry : castle.getResources().entrySet()) {
            JLabel resourceLabel = new JLabel(entry.getKey().name() + ": " + entry.getValue());
            resourcePanel.add(resourceLabel, resGbc);
            resGbc.gridy++;
        }
        statsPanel.add(resourcePanel, gbc);

        // Unit Header
        gbc.gridy++;
        statsPanel.add(new JLabel("Your troops:"), gbc);

        // Update units
        gbc.gridy++;
        unitPanel.removeAll();
        unitPanel.setLayout(new GridBagLayout());
        GridBagConstraints unitGbc = new GridBagConstraints();
        unitGbc.anchor = GridBagConstraints.WEST;
        unitGbc.gridx = 0;
        unitGbc.gridy = 0;
        unitGbc.weightx = 1.0;

        for (Map.Entry<String, ArrayList<Hero>> entry : castle.getTroops().entrySet()) {
            JPanel unitInfoPanel = new JPanel();
            unitInfoPanel.setLayout(new FlowLayout(FlowLayout.LEFT));

            JLabel unitLabel = new JLabel(entry.getKey() + ": " + entry.getValue().size());
            ColorSquare unitColorSquare = new ColorSquare(getHeroColor(entry.getKey()));

            unitInfoPanel.add(unitColorSquare);
            unitInfoPanel.add(unitLabel);
            unitPanel.add(unitInfoPanel, unitGbc);
            unitGbc.gridy++;
        }
        statsPanel.add(unitPanel, gbc);

        // Update current player
        currentPlayerTopLabel.setText("Current Player: " + manager.getPlayer().getPlayerName());

        statsPanel.revalidate();
        statsPanel.repaint();
    }

    private Color getHeroColor(String heroType) {
        return switch (heroType) {
            case "Archer" -> new Color(250, 150, 50);
            case "Druid" -> new Color(24, 155, 155);
            case "Healer" -> new Color(200, 50, 200);
            case "Knight" -> new Color(114, 104, 104);
            case "Warlock" -> new Color(100, 50, 100);
            case "Wizard" -> new Color(241, 113, 113);
            case "Worker" -> Color.BLACK;
            // Add more cases if needed
            default -> Color.WHITE;
        };
    }
    private void addTroop() {
        Castle castle = manager.getPlayer().getCastle();
        String[] troops = {"Archer", "Druid", "Healer", "Knight", "Warlock", "Wizard", "Worker"};
        String selectedTroop = (String) JOptionPane.showInputDialog(frame, "Select a troop to add:",
                "Add Troop", JOptionPane.PLAIN_MESSAGE, null, troops, troops[0]);
        Hero hero;
        if (selectedTroop != null && !selectedTroop.isEmpty() && castle.hasEnoughResourcesForAddingTroop(selectedTroop)) {
            switch (selectedTroop) {
                case "Archer":
                    hero = new Archer(castle);
                    break;
                case "Druid":
                    hero = new Druid(castle);
                    break;
                case "Healer":
                    hero = new Healer(castle);
                    break;
                case "Knight":
                    hero = new Knight(castle);
                    break;
                case "Warlock":
                    hero = new Warlock(castle);
                    break;
                case "Wizard":
                    hero = new Wizard(castle);
                    break;
                case "Worker":
                default:
                    hero = new Worker(castle);
                    break;
            }

            //castle.addTroop(hero);
            Tile location = castle.findEmptyAdjacentTile(worldMap);
            worldMap.placeUnitOnMap(hero, location.getX(), location.getY());
            JOptionPane.showMessageDialog(frame, selectedTroop + " added to your army.");
            refreshMap();
            refreshStats();
        } else {
            JOptionPane.showMessageDialog(frame, "You don't have enough resources to add this troop.");
        }
    }

    private void upgradeCastle(JButton upgradeCastleButton) {
        Castle castle = manager.getPlayer().getCastle();
        try {
            castle.upgradeCastle();
            JOptionPane.showMessageDialog(frame, "Castle upgraded successfully.");
        } catch (InsufficientResourcesException e) {
            JOptionPane.showMessageDialog(frame, "Upgrade failed - insufficient resources.");
        }
        refreshMap();
        refreshStats();
        upgradeCastleButton.setEnabled(false);
    }
}

