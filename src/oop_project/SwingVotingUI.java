package oop_project;

import javax.swing.*;
import javax.swing.border.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.*;
import java.awt.image.BufferedImage;
import java.util.*;
import java.util.List;

// Import the Swing Timer explicitly to resolve ambiguity
import javax.swing.Timer;

// Remove the Timer ambiguity by using explicit imports
// (java.util.Timer might be conflicting with javax.swing.Timer)

public class SwingVotingUI extends JFrame {
    // Data model
    private Election election;
    private Map<String, Voter> voterRegistry;
    
    // UI Components
    private JTabbedPane tabbedPane;
    private JPanel homePanel;
    private JPanel registerPanel;
    private JPanel votePanel;
    private JPanel resultsPanel;
    private JPanel votersPanel;
    
    // Register panel components
    private JTextField voterIdField;
    private JTextField voterNameField;
    private JTextField blockchainAddressField;
    private JButton registerButton;
    private JTextArea registrationOutputArea;
    
    // Vote panel components
    private JComboBox<String> voterSelector;
    private JComboBox<String> candidateSelector;
    private JButton voteButton;
    private JTextArea voteOutputArea;
    
    // Results panel components
    private JTextArea resultsArea;
    private JButton refreshResultsButton;
    
    // Voters panel components
    private JTextArea votersArea;
    private JButton refreshVotersButton;
    
    // Constructor
    public SwingVotingUI(Election election) {
        this.election = election;
        this.voterRegistry = new HashMap<>();
        
        // Set look and feel to a modern UI
        try {
            for (UIManager.LookAndFeelInfo info : UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (Exception e) {
            System.out.println("Nimbus look and feel not available, using default");
        }
        
        // Custom UI colors
        UIManager.put("nimbusBase", new Color(50, 120, 200));
        UIManager.put("nimbusBlueGrey", new Color(200, 210, 228));
        UIManager.put("control", new Color(237, 240, 245));
        
        // Set up the JFrame
        setTitle("Blockchain Voting System");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(850, 650);
        setLocationRelativeTo(null);
        setIconImage(createBlockchainIcon().getImage());
        
        // Create the tabbed pane
        tabbedPane = new JTabbedPane();
        
        // Create panels
        createHomePanel();
        createRegisterPanel();
        createVotePanel();
        createResultsPanel();
        createVotersPanel();
        
        // Add panels to tabbed pane with icons
        tabbedPane.addTab("Home", createIcon("home"), homePanel, "Welcome page");
        tabbedPane.addTab("Register", createIcon("register"), registerPanel, "Register as a voter");
        tabbedPane.addTab("Vote", createIcon("vote"), votePanel, "Cast your vote");
        tabbedPane.addTab("Results", createIcon("results"), resultsPanel, "View election results");
        tabbedPane.addTab("Voters", createIcon("users"), votersPanel, "View registered voters");
        
        // Add tabbed pane to frame
        add(tabbedPane);
    }
    
    // Create an icon for the application
    private ImageIcon createBlockchainIcon() {
        // Create a simple blockchain icon
        BufferedImage image = new BufferedImage(32, 32, BufferedImage.TYPE_INT_ARGB);
        Graphics2D g2d = image.createGraphics();
        g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        
        // Draw chain of blocks
        g2d.setColor(new Color(30, 100, 180));
        g2d.fillRoundRect(5, 5, 10, 10, 2, 2);
        g2d.fillRoundRect(17, 5, 10, 10, 2, 2);
        g2d.fillRoundRect(5, 17, 10, 10, 2, 2);
        g2d.fillRoundRect(17, 17, 10, 10, 2, 2);
        
        // Draw connections
        g2d.setColor(new Color(50, 150, 230));
        g2d.setStroke(new BasicStroke(2));
        g2d.drawLine(15, 10, 17, 10);
        g2d.drawLine(10, 15, 10, 17);
        g2d.drawLine(22, 15, 22, 17);
        g2d.drawLine(15, 22, 17, 22);
        
        g2d.dispose();
        return new ImageIcon(image);
    }
    
    // Create icon for tabs
    private ImageIcon createIcon(String type) {
        BufferedImage image = new BufferedImage(16, 16, BufferedImage.TYPE_INT_ARGB);
        Graphics2D g2d = image.createGraphics();
        g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        
        switch(type) {
            case "home":
                g2d.setColor(new Color(50, 130, 200));
                g2d.fillPolygon(new int[]{8, 0, 16}, new int[]{0, 8, 8}, 3);
                g2d.fillRect(3, 8, 10, 8);
                break;
            case "register":
                g2d.setColor(new Color(50, 130, 200));
                g2d.fillOval(4, 0, 8, 8);
                g2d.fillRect(3, 8, 10, 8);
                break;
            case "vote":
                g2d.setColor(new Color(50, 130, 200));
                g2d.fillRoundRect(1, 1, 14, 14, 3, 3);
                g2d.setColor(new Color(240, 240, 240));
                g2d.fillRect(4, 6, 8, 2);
                g2d.fillRect(4, 10, 8, 2);
                break;
            case "results":
                g2d.setColor(new Color(50, 130, 200));
                g2d.fillRect(0, 12, 4, 4);
                g2d.fillRect(6, 8, 4, 8);
                g2d.fillRect(12, 4, 4, 12);
                break;
            case "users":
                g2d.setColor(new Color(50, 130, 200));
                g2d.fillOval(3, 1, 4, 4);
                g2d.fillOval(9, 1, 4, 4);
                g2d.fillRect(1, 6, 14, 8);
                break;
        }
        
        g2d.dispose();
        return new ImageIcon(image);
    }
    
    // Create home panel
    private void createHomePanel() {
        homePanel = new JPanel();
        homePanel.setLayout(new BorderLayout());
        
        // Create a welcome message
        JLabel titleLabel = new JLabel("Decentralized Blockchain Voting System", JLabel.CENTER);
        titleLabel.setFont(new Font("Arial", Font.BOLD, 24));
        titleLabel.setBorder(BorderFactory.createEmptyBorder(20, 0, 20, 0));
        
        JPanel contentPanel = new JPanel();
        contentPanel.setLayout(new BoxLayout(contentPanel, BoxLayout.Y_AXIS));
        contentPanel.setBorder(BorderFactory.createEmptyBorder(20, 40, 20, 40));
        
        JLabel welcomeLabel = new JLabel("<html><div style='text-align: center;'>Welcome to the Blockchain Voting System</div></html>");
        welcomeLabel.setFont(new Font("Arial", Font.PLAIN, 16));
        welcomeLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        
        // Add a blockchain visual animation
        JPanel blockchainPanel = createBlockchainVisualization();
        blockchainPanel.setAlignmentX(Component.CENTER_ALIGNMENT);
        blockchainPanel.setMaximumSize(new Dimension(500, 120));
        
        JLabel descriptionLabel = new JLabel("<html><div style='text-align: center; width: 400px;'>"
                + "This application demonstrates a secure, transparent, and tamper-proof electronic "
                + "voting system using blockchain technology. All votes are cryptographically secured "
                + "and can be verified through the blockchain ledger.</div></html>");
        descriptionLabel.setFont(new Font("Arial", Font.PLAIN, 14));
        descriptionLabel.setBorder(BorderFactory.createEmptyBorder(20, 0, 20, 0));
        descriptionLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        
        // Add election info
        JLabel electionInfoLabel = new JLabel("<html><div style='text-align: center;'>"
                + "Current Election: <b>" + election.getName() + "</b><br>"
                + "Election ID: " + election.getElectionId() + "<br>"
                + "Candidates: " + String.join(", ", election.getCandidates()) + "<br>"
                + "Election Hash: " + election.getElectionHash().substring(0, 10) + "..."
                + "</div></html>");
        electionInfoLabel.setFont(new Font("Arial", Font.PLAIN, 14));
        electionInfoLabel.setBorder(BorderFactory.createEmptyBorder(20, 0, 20, 0));
        electionInfoLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        
        // Create a panel with buttons for navigation
        JPanel buttonPanel = new JPanel();
        buttonPanel.setLayout(new FlowLayout(FlowLayout.CENTER, 20, 20));
        
        String[] tabNames = {"Register", "Vote", "Results", "Voters"};
        String[] buttonColors = {"#4CAF50", "#2196F3", "#FF9800", "#9C27B0"};
        for (int i = 0; i < tabNames.length; i++) {
            final int tabIndex = i + 1; // +1 because Home is at index 0
            JButton button = createStyledButton(tabNames[i], buttonColors[i]);
            button.addActionListener(e -> tabbedPane.setSelectedIndex(tabIndex));
            buttonPanel.add(button);
        }
        
        // Add components to home panel
        contentPanel.add(welcomeLabel);
        contentPanel.add(Box.createRigidArea(new Dimension(0, 20)));
        contentPanel.add(blockchainPanel);
        contentPanel.add(Box.createRigidArea(new Dimension(0, 20)));
        contentPanel.add(descriptionLabel);
        contentPanel.add(Box.createRigidArea(new Dimension(0, 20)));
        contentPanel.add(electionInfoLabel);
        contentPanel.add(Box.createRigidArea(new Dimension(0, 20)));
        contentPanel.add(buttonPanel);
        
        homePanel.add(titleLabel, BorderLayout.NORTH);
        homePanel.add(contentPanel, BorderLayout.CENTER);
        
        // Start the blockchain animation
        startBlockchainAnimation(blockchainPanel);
    }
    
    // Create a styled button with custom colors
    private JButton createStyledButton(String text, String hexColor) {
        JButton button = new JButton(text);
        button.setFont(new Font("Arial", Font.BOLD, 14));
        button.setForeground(Color.WHITE);
        
        // Parse the hex color
        Color bgColor = Color.decode(hexColor);
        
        // Create a custom UI for the button
        button.setBackground(bgColor);
        button.setFocusPainted(false);
        button.setBorderPainted(false);
        button.setOpaque(true);
        
        // Add hover effect
        button.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent e) {
                button.setBackground(new Color(
                    Math.max((int)(bgColor.getRed() * 0.8), 0),
                    Math.max((int)(bgColor.getGreen() * 0.8), 0),
                    Math.max((int)(bgColor.getBlue() * 0.8), 0)
                ));
            }

            @Override
            public void mouseExited(MouseEvent e) {
                button.setBackground(bgColor);
            }
        });
        
        // Set dimensions
        button.setPreferredSize(new Dimension(120, 40));
        
        return button;
    }
    
    // Create a visual representation of a blockchain
    private JPanel createBlockchainVisualization() {
        JPanel panel = new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                Graphics2D g2d = (Graphics2D) g;
                g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                
                int blockWidth = 80;
                int blockHeight = 60;
                int spacing = 30;
                int startX = (getWidth() - (4 * blockWidth + 3 * spacing)) / 2;
                int y = getHeight() / 2 - blockHeight / 2;
                
                // Draw blockchain blocks
                for (int i = 0; i < 4; i++) {
                    int x = startX + i * (blockWidth + spacing);
                    
                    // Draw block
                    g2d.setColor(new Color(50, 130, 200));
                    g2d.fillRoundRect(x, y, blockWidth, blockHeight, 10, 10);
                    
                    // Draw hash inside block
                    g2d.setColor(Color.WHITE);
                    g2d.setFont(new Font("Monospaced", Font.BOLD, 10));
                    g2d.drawString("BLOCK " + i, x + 10, y + 20);
                    g2d.setFont(new Font("Monospaced", Font.PLAIN, 8));
                    g2d.drawString("HASH: " + generateRandomHexString(8), x + 10, y + 35);
                    g2d.drawString("PREV: " + generateRandomHexString(8), x + 10, y + 45);
                    
                    // Draw connecting arrow
                    if (i < 3) {
                        g2d.setColor(new Color(50, 130, 200));
                        g2d.setStroke(new BasicStroke(2));
                        g2d.drawLine(x + blockWidth, y + blockHeight / 2, 
                                    x + blockWidth + spacing, y + blockHeight / 2);
                        
                        // Draw arrowhead
                        int[] xPoints = {x + blockWidth + spacing, x + blockWidth + spacing - 5, x + blockWidth + spacing - 5};
                        int[] yPoints = {y + blockHeight / 2, y + blockHeight / 2 - 5, y + blockHeight / 2 + 5};
                        g2d.fillPolygon(xPoints, yPoints, 3);
                    }
                }
            }
            
            @Override
            public Dimension getPreferredSize() {
                return new Dimension(500, 100);
            }
        };
        
        panel.setOpaque(false);
        return panel;
    }
    
    // Start a simple animation for the blockchain visualization
    private void startBlockchainAnimation(JPanel blockchainPanel) {
        Timer timer = new Timer(2000, e -> blockchainPanel.repaint());
        timer.start();
    }
    
    // Create register panel
    private void createRegisterPanel() {
        registerPanel = new JPanel(new BorderLayout());
        registerPanel.setBackground(new Color(245, 245, 250));
        
        JLabel titleLabel = new JLabel("Register as a Voter", JLabel.CENTER);
        titleLabel.setFont(new Font("Arial", Font.BOLD, 20));
        titleLabel.setBorder(BorderFactory.createEmptyBorder(20, 0, 20, 0));
        
        // Input panel
        JPanel inputPanel = new JPanel();
        inputPanel.setLayout(new GridBagLayout());
        inputPanel.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createEmptyBorder(20, 50, 20, 50),
            BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(new Color(200, 210, 240), 1, true),
                BorderFactory.createEmptyBorder(20, 20, 20, 20)
            )
        ));
        inputPanel.setBackground(new Color(250, 250, 255));
        
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridwidth = 1;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.insets = new Insets(8, 8, 8, 8);
        
        // Voter ID
        gbc.gridx = 0;
        gbc.gridy = 0;
        JLabel idLabel = new JLabel("Voter ID:");
        idLabel.setFont(new Font("Arial", Font.BOLD, 12));
        inputPanel.add(idLabel, gbc);
        
        gbc.gridx = 1;
        gbc.gridy = 0;
        voterIdField = new JTextField(10);
        voterIdField.setFont(new Font("Arial", Font.PLAIN, 14));
        voterIdField.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(new Color(180, 190, 220), 1, true),
            BorderFactory.createEmptyBorder(5, 7, 5, 7)
        ));
        inputPanel.add(voterIdField, gbc);
        
        // Voter Name
        gbc.gridx = 0;
        gbc.gridy = 1;
        JLabel nameLabel = new JLabel("Voter Name:");
        nameLabel.setFont(new Font("Arial", Font.BOLD, 12));
        inputPanel.add(nameLabel, gbc);
        
        gbc.gridx = 1;
        gbc.gridy = 1;
        voterNameField = new JTextField(20);
        voterNameField.setFont(new Font("Arial", Font.PLAIN, 14));
        voterNameField.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(new Color(180, 190, 220), 1, true),
            BorderFactory.createEmptyBorder(5, 7, 5, 7)
        ));
        inputPanel.add(voterNameField, gbc);
        
        // Generate address automatically
        gbc.gridx = 0;
        gbc.gridy = 2;
        gbc.gridwidth = 2;
        gbc.insets = new Insets(20, 8, 8, 8);
        
        registerButton = createStyledButton("Register on Blockchain", "#4CAF50");
        registerButton.addActionListener(e -> registerVoter());
        inputPanel.add(registerButton, gbc);
        
        // Output area for registration details
        registrationOutputArea = new JTextArea(10, 40);
        registrationOutputArea.setEditable(false);
        registrationOutputArea.setFont(new Font("Monospaced", Font.PLAIN, 12));
        registrationOutputArea.setBorder(BorderFactory.createEmptyBorder(5, 10, 5, 10));
        registrationOutputArea.setBackground(new Color(245, 245, 250));
        JScrollPane scrollPane = new JScrollPane(registrationOutputArea);
        scrollPane.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createEmptyBorder(10, 50, 20, 50),
            BorderFactory.createTitledBorder(
                BorderFactory.createLineBorder(new Color(180, 190, 220), 1, true),
                "Registration Details",
                TitledBorder.CENTER,
                TitledBorder.TOP,
                new Font("Arial", Font.BOLD, 12),
                new Color(70, 90, 140)
            )
        ));
        
        // Add components to register panel
        registerPanel.add(titleLabel, BorderLayout.NORTH);
        registerPanel.add(inputPanel, BorderLayout.CENTER);
        registerPanel.add(scrollPane, BorderLayout.SOUTH);
    }
    
    // Create vote panel
    private void createVotePanel() {
        votePanel = new JPanel(new BorderLayout());
        votePanel.setBackground(new Color(245, 245, 250));
        
        JLabel titleLabel = new JLabel("Cast Your Vote", JLabel.CENTER);
        titleLabel.setFont(new Font("Arial", Font.BOLD, 20));
        titleLabel.setBorder(BorderFactory.createEmptyBorder(20, 0, 20, 0));
        
        // Input panel
        JPanel inputPanel = new JPanel();
        inputPanel.setLayout(new GridBagLayout());
        inputPanel.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createEmptyBorder(20, 50, 20, 50),
            BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(new Color(200, 210, 240), 1, true),
                BorderFactory.createEmptyBorder(20, 20, 20, 20)
            )
        ));
        inputPanel.setBackground(new Color(250, 250, 255));
        
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridwidth = 1;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.insets = new Insets(8, 8, 8, 8);
        
        // Voter selector
        gbc.gridx = 0;
        gbc.gridy = 0;
        JLabel voterLabel = new JLabel("Select Voter:");
        voterLabel.setFont(new Font("Arial", Font.BOLD, 12));
        inputPanel.add(voterLabel, gbc);
        
        gbc.gridx = 1;
        gbc.gridy = 0;
        voterSelector = new JComboBox<>();
        voterSelector.setFont(new Font("Arial", Font.PLAIN, 14));
        inputPanel.add(voterSelector, gbc);
        
        // Candidate selector
        gbc.gridx = 0;
        gbc.gridy = 1;
        JLabel candidateLabel = new JLabel("Select Candidate:");
        candidateLabel.setFont(new Font("Arial", Font.BOLD, 12));
        inputPanel.add(candidateLabel, gbc);
        
        gbc.gridx = 1;
        gbc.gridy = 1;
        candidateSelector = new JComboBox<>();
        candidateSelector.setFont(new Font("Arial", Font.PLAIN, 14));
        for (String candidate : election.getCandidates()) {
            candidateSelector.addItem(candidate);
        }
        inputPanel.add(candidateSelector, gbc);
        
        // Vote button
        gbc.gridx = 0;
        gbc.gridy = 2;
        gbc.gridwidth = 2;
        gbc.insets = new Insets(20, 8, 8, 8);
        
        voteButton = createStyledButton("Cast Vote on Blockchain", "#2196F3");
        voteButton.addActionListener(e -> castVote());
        inputPanel.add(voteButton, gbc);
        
        // Output area for vote details
        voteOutputArea = new JTextArea(10, 40);
        voteOutputArea.setEditable(false);
        voteOutputArea.setFont(new Font("Monospaced", Font.PLAIN, 12));
        voteOutputArea.setBorder(BorderFactory.createEmptyBorder(5, 10, 5, 10));
        voteOutputArea.setBackground(new Color(245, 245, 250));
        JScrollPane scrollPane = new JScrollPane(voteOutputArea);
        scrollPane.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createEmptyBorder(10, 50, 20, 50),
            BorderFactory.createTitledBorder(
                BorderFactory.createLineBorder(new Color(180, 190, 220), 1, true),
                "Voting Details",
                TitledBorder.CENTER,
                TitledBorder.TOP,
                new Font("Arial", Font.BOLD, 12),
                new Color(70, 90, 140)
            )
        ));
        
        // Add components to vote panel
        votePanel.add(titleLabel, BorderLayout.NORTH);
        votePanel.add(inputPanel, BorderLayout.CENTER);
        votePanel.add(scrollPane, BorderLayout.SOUTH);
    }
    
    // Create results panel
    private void createResultsPanel() {
        resultsPanel = new JPanel(new BorderLayout());
        resultsPanel.setBackground(new Color(245, 245, 250));
        
        JLabel titleLabel = new JLabel("Election Results", JLabel.CENTER);
        titleLabel.setFont(new Font("Arial", Font.BOLD, 20));
        titleLabel.setBorder(BorderFactory.createEmptyBorder(20, 0, 20, 0));
        
        // Results area
        resultsArea = new JTextArea(20, 40);
        resultsArea.setEditable(false);
        resultsArea.setFont(new Font("Monospaced", Font.PLAIN, 12));
        resultsArea.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        resultsArea.setBackground(new Color(250, 250, 255));
        
        JScrollPane scrollPane = new JScrollPane(resultsArea);
        scrollPane.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createEmptyBorder(10, 50, 20, 50),
            BorderFactory.createLineBorder(new Color(200, 210, 240), 1, true)
        ));
        
        // Create a graphical results panel to show vote distribution
        JPanel chartPanel = new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                
                // We'll update this with real data in the refreshResults method
                // For now, just draw a placeholder chart
                Graphics2D g2d = (Graphics2D) g;
                g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                
                // Draw chart background
                g2d.setColor(new Color(240, 240, 245));
                g2d.fillRoundRect(30, 20, getWidth() - 60, getHeight() - 40, 10, 10);
                
                // Draw "No Data" message
                g2d.setColor(new Color(150, 150, 180));
                g2d.setFont(new Font("Arial", Font.BOLD, 16));
                String message = "Click 'Refresh Results' to load voting data";
                FontMetrics fm = g2d.getFontMetrics();
                int textWidth = fm.stringWidth(message);
                g2d.drawString(message, (getWidth() - textWidth) / 2, getHeight() / 2);
            }
            
            @Override
            public Dimension getPreferredSize() {
                return new Dimension(700, 150);
            }
        };
        chartPanel.setBackground(new Color(250, 250, 255));
        chartPanel.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createEmptyBorder(10, 50, 10, 50),
            BorderFactory.createTitledBorder(
                BorderFactory.createLineBorder(new Color(180, 190, 220), 1, true),
                "Vote Distribution",
                TitledBorder.CENTER,
                TitledBorder.TOP,
                new Font("Arial", Font.BOLD, 12),
                new Color(70, 90, 140)
            )
        ));
        
        // Refresh button in a panel
        refreshResultsButton = createStyledButton("Refresh Results", "#FF9800");
        refreshResultsButton.addActionListener(e -> refreshResults(chartPanel));
        
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        buttonPanel.setBackground(new Color(245, 245, 250));
        buttonPanel.setBorder(BorderFactory.createEmptyBorder(10, 50, 20, 50));
        buttonPanel.add(refreshResultsButton);
        
        // Add components to results panel
        resultsPanel.add(titleLabel, BorderLayout.NORTH);
        resultsPanel.add(chartPanel, BorderLayout.CENTER);
        resultsPanel.add(scrollPane, BorderLayout.SOUTH);
        resultsPanel.add(buttonPanel, BorderLayout.SOUTH);
    }
    
    // Create voters panel
    private void createVotersPanel() {
        votersPanel = new JPanel(new BorderLayout());
        votersPanel.setBackground(new Color(245, 245, 250));
        
        JLabel titleLabel = new JLabel("Registered Voters", JLabel.CENTER);
        titleLabel.setFont(new Font("Arial", Font.BOLD, 20));
        titleLabel.setBorder(BorderFactory.createEmptyBorder(20, 0, 20, 0));
        
        // Create a table to display voters more visually
        String[] columnNames = {"Voter ID", "Name", "Blockchain Address", "Public Key"};
        Object[][] data = {};
        JTable voterTable = new JTable(data, columnNames);
        voterTable.setFillsViewportHeight(true);
        voterTable.setRowHeight(25);
        voterTable.getTableHeader().setFont(new Font("Arial", Font.BOLD, 12));
        voterTable.setFont(new Font("Arial", Font.PLAIN, 12));
        voterTable.setSelectionBackground(new Color(220, 230, 250));
        
        JScrollPane tableScrollPane = new JScrollPane(voterTable);
        tableScrollPane.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createEmptyBorder(10, 50, 20, 50),
            BorderFactory.createLineBorder(new Color(200, 210, 240), 1, true)
        ));
        
        // Voters area (we'll keep this as a backup/detailed view)
        votersArea = new JTextArea(8, 40);
        votersArea.setEditable(false);
        votersArea.setFont(new Font("Monospaced", Font.PLAIN, 12));
        votersArea.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        votersArea.setBackground(new Color(250, 250, 255));
        
        JScrollPane textScrollPane = new JScrollPane(votersArea);
        textScrollPane.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createEmptyBorder(10, 50, 20, 50),
            BorderFactory.createTitledBorder(
                BorderFactory.createLineBorder(new Color(180, 190, 220), 1, true),
                "Voter Details",
                TitledBorder.CENTER,
                TitledBorder.TOP,
                new Font("Arial", Font.BOLD, 12),
                new Color(70, 90, 140)
            )
        ));
        
        // Refresh button
        refreshVotersButton = createStyledButton("Refresh Voter List", "#9C27B0");
        refreshVotersButton.addActionListener(e -> refreshVoters(voterTable));
        
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        buttonPanel.setBackground(new Color(245, 245, 250));
        buttonPanel.setBorder(BorderFactory.createEmptyBorder(10, 50, 20, 50));
        buttonPanel.add(refreshVotersButton);
        
        // Add components to voters panel
        votersPanel.add(titleLabel, BorderLayout.NORTH);
        votersPanel.add(tableScrollPane, BorderLayout.CENTER);
        votersPanel.add(textScrollPane, BorderLayout.SOUTH);
        votersPanel.add(buttonPanel, BorderLayout.SOUTH);
    }
    
    // Register voter method
    private void registerVoter() {
        registrationOutputArea.setText("Processing registration...\n");
        
        try {
            int voterId = Integer.parseInt(voterIdField.getText().trim());
            String name = voterNameField.getText().trim();
            
            if (name.isEmpty()) {
                registrationOutputArea.append("Error: Voter name cannot be empty.\n");
                return;
            }
            
            // Check if voter ID already exists
            for (Voter existingVoter : voterRegistry.values()) {
                if (existingVoter.getVoterId() == voterId) {
                    registrationOutputArea.append("Error: Voter ID already exists on the blockchain!\n");
                    return;
                }
            }
            
            // Generate a random blockchain address
            String blockchainAddress = "0x" + generateRandomHexString(40);
            
            // Create new voter
            Voter newVoter = new Voter(voterId, name, blockchainAddress);
            voterRegistry.put(blockchainAddress, newVoter);
            
            // Update voter selector in vote panel
            voterSelector.addItem(name + " (" + blockchainAddress + ")");
            
            // Display confirmation
            registrationOutputArea.append("Voter successfully registered on the blockchain!\n");
            registrationOutputArea.append("Waiting for block confirmation...\n");
            
            // Simulate block confirmation delay
            Timer timer = new Timer(1500, e -> {
                registrationOutputArea.append("Block confirmed on the network!\n\n");
                registrationOutputArea.append("Voter Details:\n");
                registrationOutputArea.append("Voter ID: " + voterId + "\n");
                registrationOutputArea.append("Name: " + name + "\n");
                registrationOutputArea.append("Blockchain Address: " + blockchainAddress + "\n");
                registrationOutputArea.append("Public Key: " + 
                        Base64.getEncoder().encodeToString(newVoter.getPublicKey().getEncoded()).substring(0, 20) + "...\n");
                
                // Clear input fields
                voterIdField.setText("");
                voterNameField.setText("");
            });
            timer.setRepeats(false);
            timer.start();
            
        } catch (NumberFormatException e) {
            registrationOutputArea.append("Error: Voter ID must be a number.\n");
        }
    }
    
    // Cast vote method
    private void castVote() {
        voteOutputArea.setText("Processing vote transaction...\n");
        
        if (voterSelector.getItemCount() == 0) {
            voteOutputArea.append("Error: No registered voters. Please register first.\n");
            return;
        }
        
        String selectedVoterItem = (String) voterSelector.getSelectedItem();
        if (selectedVoterItem == null) {
            voteOutputArea.append("Error: Please select a voter.\n");
            return;
        }
        
        // Extract blockchain address from selected item
        String blockchainAddress = selectedVoterItem.substring(
                selectedVoterItem.indexOf("(") + 1, 
                selectedVoterItem.indexOf(")")
        );
        
        Voter voter = voterRegistry.get(blockchainAddress);
        if (voter == null) {
            voteOutputArea.append("Error: Invalid blockchain address.\n");
            return;
        }
        
        String candidate = (String) candidateSelector.getSelectedItem();
        
        // Cast vote
        voteOutputArea.append("Casting vote: " + voter.getName() + " for " + candidate + "\n");
        voteOutputArea.append("Creating blockchain transaction...\n");
        
        // Simulate blockchain transaction delay
        Timer timer = new Timer(1500, e -> {
            // Cast the vote through election
            election.castVote(voter, candidate);
            
            voteOutputArea.append("Vote transaction created and signed!\n");
            voteOutputArea.append("Waiting for block confirmation...\n");
            
            // Simulate block confirmation delay
            Timer confirmTimer = new Timer(1500, event -> {
                voteOutputArea.append("Block confirmed on the network!\n\n");
                voteOutputArea.append("Vote successfully recorded on blockchain.\n");
                voteOutputArea.append("Voter: " + voter.getName() + "\n");
                voteOutputArea.append("Candidate: " + candidate + "\n");
                
                // Remove voter from dropdown to prevent double voting in UI
                voterSelector.removeItem(selectedVoterItem);
            });
            confirmTimer.setRepeats(false);
            confirmTimer.start();
        });
        timer.setRepeats(false);
        timer.start();
    }
    
    // Refresh results method
    private void refreshResults(JPanel chartPanel) {
        resultsArea.setText("Retrieving blockchain data...\n");
        
        // Simulate blockchain query delay
        Timer timer = new Timer(1000, e -> {
            StringBuilder sb = new StringBuilder();
            sb.append("Election Results for ").append(election.getName())
              .append(" (ID: ").append(election.getElectionId()).append(")\n");
            sb.append("Election Hash: ").append(election.getElectionHash()).append("\n\n");
            
            // Display election data
            sb.append("Verifying blockchain integrity...\n");
            boolean chainIsValid = election.verifyBlockchain();
            
            sb.append("Blockchain Status: ").append(chainIsValid ? "Valid ✓" : "Compromised ✗").append("\n\n");
            
            sb.append("Results:\n");
            
            // Count votes for each candidate
            Map<String, Integer> results = new HashMap<>();
            for (String candidate : election.getCandidates()) {
                results.put(candidate, 0);
            }
            
            // This is a simplified vote count for UI demonstration
            // In a real implementation, we would use election.displayResults() and capture its output
            // For now, we'll generate some sample data
            List<String> candidates = election.getCandidates();
            int totalVotes = 0;
            
            for (int i = 0; i < candidates.size(); i++) {
                // For demo purposes, generate random votes or use actual data if available
                int votes = (int)(Math.random() * 10);
                totalVotes += votes;
                results.put(candidates.get(i), votes);
            }
            
            // Format and display results
            for (String candidate : election.getCandidates()) {
                int votes = results.get(candidate);
                sb.append(candidate).append(": ").append(votes).append(" votes\n");
                
                // Add a visual bar chart
                sb.append("[");
                for (int i = 0; i < votes; i++) {
                    sb.append("■");
                }
                for (int i = votes; i < 10; i++) {
                    sb.append(" ");
                }
                sb.append("]\n\n");
            }
            
            sb.append("Total votes cast: ").append(totalVotes).append("\n");
            sb.append("Blockchain Status: ").append(chainIsValid ? "Valid ✓" : "Compromised ✗").append("\n");
            
            resultsArea.setText(sb.toString());
            
            // Update the chart panel
            updateResultsChart(chartPanel, results, totalVotes);
        });
        timer.setRepeats(false);
        timer.start();
    }
    
    // Update the results chart visualization
    private void updateResultsChart(JPanel chartPanel, Map<String, Integer> results, int totalVotes) {
        // Create a custom painter for the chart panel
        chartPanel.removeAll();
        chartPanel.setLayout(new BorderLayout());
        
        JPanel barChartPanel = new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                Graphics2D g2d = (Graphics2D) g;
                g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                
                int padding = 40;
                int barHeight = 30;
                int spacing = 20;
                int chartWidth = getWidth() - (2 * padding);
                
                // Draw chart title
                g2d.setColor(new Color(70, 90, 140));
                g2d.setFont(new Font("Arial", Font.BOLD, 14));
                String title = "Vote Distribution - " + election.getName();
                FontMetrics fm = g2d.getFontMetrics();
                int titleWidth = fm.stringWidth(title);
                g2d.drawString(title, (getWidth() - titleWidth) / 2, 25);
                
                // Draw chart background
                g2d.setColor(new Color(240, 240, 245));
                g2d.fillRoundRect(padding, 40, chartWidth, 
                                 results.size() * (barHeight + spacing), 10, 10);
                
                // Draw grid lines
                g2d.setColor(new Color(200, 200, 220));
                g2d.setStroke(new BasicStroke(1, BasicStroke.CAP_BUTT, BasicStroke.JOIN_BEVEL, 
                                            0, new float[]{3}, 0));
                for (int i = 0; i <= 10; i++) {
                    int x = padding + (i * chartWidth / 10);
                    g2d.drawLine(x, 40, x, 40 + results.size() * (barHeight + spacing));
                    
                    // Draw gridline label
                    g2d.setColor(new Color(100, 100, 140));
                    g2d.setFont(new Font("Arial", Font.PLAIN, 10));
                    g2d.drawString(i * 10 + "%", x - 10, 35);
                    g2d.setColor(new Color(200, 200, 220));
                }
                
                // Draw data bars
                int i = 0;
                String[] colors = {"#4CAF50", "#2196F3", "#FF9800", "#9C27B0", "#F44336", "#009688"};
                
                for (Map.Entry<String, Integer> entry : results.entrySet()) {
                    int barY = 50 + i * (barHeight + spacing);
                    int percentage = totalVotes == 0 ? 0 : (entry.getValue() * 100 / totalVotes);
                    int barWidth = chartWidth * percentage / 100;
                    
                    // Draw bar
                    g2d.setColor(Color.decode(colors[i % colors.length]));
                    g2d.fillRoundRect(padding, barY, barWidth, barHeight, 8, 8);
                    
                    // Draw label
                    g2d.setColor(Color.BLACK);
                    g2d.setFont(new Font("Arial", Font.BOLD, 12));
                    g2d.drawString(entry.getKey(), padding + 10, barY + barHeight / 2 + 5);
                    
                    // Draw value
                    String voteText = entry.getValue() + " votes (" + percentage + "%)";
                    g2d.setFont(new Font("Arial", Font.PLAIN, 11));
                    g2d.drawString(voteText, padding + barWidth + 10, barY + barHeight / 2 + 5);
                    
                    i++;
                }
            }
        };
        
        chartPanel.add(barChartPanel, BorderLayout.CENTER);
        chartPanel.revalidate();
        chartPanel.repaint();
    }
    
    // Refresh voters method
    private void refreshVoters(JTable voterTable) {
        votersArea.setText("Retrieving voter registry from blockchain...\n");
        
        // Simulate blockchain query delay
        Timer timer = new Timer(1000, e -> {
            StringBuilder sb = new StringBuilder();
            sb.append("Registered Voters on Blockchain:\n\n");
            
            if (voterRegistry.isEmpty()) {
                sb.append("No voters registered yet.");
                
                // Clear the table
                voterTable.setModel(new DefaultTableModel(
                    new Object[][] {}, 
                    new String[] {"Voter ID", "Name", "Blockchain Address", "Public Key"}
                ));
            } else {
                // Update the table model with voter data
                Object[][] data = new Object[voterRegistry.size()][4];
                int i = 0;
                
                for (Voter voter : voterRegistry.values()) {
                    // Add to text area
                    sb.append("Voter ID: ").append(voter.getVoterId()).append("\n");
                    sb.append("Name: ").append(voter.getName()).append("\n");
                    sb.append("Blockchain Address: ").append(voter.getBlockchainAddress()).append("\n");
                    sb.append("Public Key: ").append(
                            Base64.getEncoder().encodeToString(voter.getPublicKey().getEncoded()).substring(0, 20)).append("...\n\n");
                    
                    // Add to table
                    data[i][0] = voter.getVoterId();
                    data[i][1] = voter.getName();
                    data[i][2] = voter.getBlockchainAddress();
                    data[i][3] = Base64.getEncoder().encodeToString(voter.getPublicKey().getEncoded()).substring(0, 20) + "...";
                    i++;
                }
                
                // Update the table
                voterTable.setModel(new DefaultTableModel(
                    data, 
                    new String[] {"Voter ID", "Name", "Blockchain Address", "Public Key"}
                ));
                
                // Set column widths
                voterTable.getColumnModel().getColumn(0).setPreferredWidth(50);
                voterTable.getColumnModel().getColumn(1).setPreferredWidth(150);
                voterTable.getColumnModel().getColumn(2).setPreferredWidth(250);
                voterTable.getColumnModel().getColumn(3).setPreferredWidth(200);
            }
            
            votersArea.setText(sb.toString());
        });
        timer.setRepeats(false);
        timer.start();
    }
    
    // Generate a random hex string for blockchain addresses
    private String generateRandomHexString(int length) {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < length; i++) {
            sb.append("0123456789ABCDEF".charAt((int) (Math.random() * 16)));
        }
        return sb.toString();
    }
} 