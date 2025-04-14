/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Main.java to edit this template
 */
package oop_project;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Scanner;

/**
 *
 * @author arjun
 */
public class OOP_Project {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        System.out.println("Initializing Blockchain Voting System...");
        
        // Create election with candidates
        List<String> candidates = new ArrayList<>();
        candidates.add("Candidate A");
        candidates.add("Candidate B");
        candidates.add("Candidate C");
        
        Election election = new Election(101, "Presidential Election", candidates);
        
        // Create and show Swing UI
        SwingVotingUI ui = new SwingVotingUI(election);
        ui.setVisible(true);
        
        // Previous console-based code is now replaced by the GUI
    }
    
    // Method to register a new voter
    private static void registerVoter(Scanner scanner, Map<String, Voter> voterRegistry) {
        System.out.println("\n--- Voter Registration on Blockchain ---");
        
        System.out.print("Enter voter ID (number): ");
        int voterId;
        try {
            voterId = Integer.parseInt(scanner.nextLine());
        } catch (NumberFormatException e) {
            System.out.println("Invalid ID format. Registration failed.");
            return;
        }
        
        // Check if voter ID already exists
        for (Voter existingVoter : voterRegistry.values()) {
            if (existingVoter.getVoterId() == voterId) {
                System.out.println("Error: Voter ID already exists on the blockchain!");
                return;
            }
        }
        
        System.out.print("Enter voter name: ");
        String name = scanner.nextLine();
        
        // Generate a random blockchain address (simplification)
        String blockchainAddress = "0x" + generateRandomHexString(40);
        
        Voter newVoter = new Voter(voterId, name, blockchainAddress);
        voterRegistry.put(blockchainAddress, newVoter);
        
        System.out.println("Voter successfully registered on the blockchain!");
        System.out.println("Your blockchain address: " + blockchainAddress);
        waitForBlockConfirmation();
        
        newVoter.displayDetails();
    }
    
    // Method to cast a vote
    private static void castVote(Scanner scanner, Map<String, Voter> voterRegistry, Election election) {
        System.out.println("\n--- Blockchain Voting Interface ---");
        
        if (voterRegistry.isEmpty()) {
            System.out.println("No registered voters found. Please register first.");
            return;
        }
        
        System.out.print("Enter your blockchain address: ");
        String blockchainAddress = scanner.nextLine();
        
        Voter voter = voterRegistry.get(blockchainAddress);
        if (voter == null) {
            System.out.println("Error: Invalid blockchain address. Vote rejected.");
            return;
        }
        
        // Display candidates
        List<String> candidates = election.getCandidates();
        System.out.println("\nCandidates:");
        for (int i = 0; i < candidates.size(); i++) {
            System.out.println((i + 1) + ". " + candidates.get(i));
        }
        
        // Get user vote
        System.out.print("Enter candidate number to vote: ");
        int candidateNumber;
        try {
            candidateNumber = Integer.parseInt(scanner.nextLine());
            if (candidateNumber < 1 || candidateNumber > candidates.size()) {
                System.out.println("Invalid candidate number. Vote rejected.");
                return;
            }
        } catch (NumberFormatException e) {
            System.out.println("Invalid input. Vote rejected.");
            return;
        }
        
        String selectedCandidate = candidates.get(candidateNumber - 1);
        
        // Cast vote through the election contract
        election.castVote(voter, selectedCandidate);
        waitForBlockConfirmation();
    }
    
    // Method to display all registered voters
    private static void displayAllVoters(Map<String, Voter> voterRegistry) {
        System.out.println("\n--- Registered Voters on Blockchain ---");
        
        if (voterRegistry.isEmpty()) {
            System.out.println("No voters registered yet.");
            return;
        }
        
        for (Voter voter : voterRegistry.values()) {
            voter.displayDetails();
        }
    }
    
    // Generate a random hex string for blockchain addresses
    private static String generateRandomHexString(int length) {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < length; i++) {
            sb.append("0123456789ABCDEF".charAt((int) (Math.random() * 16)));
        }
        return sb.toString();
    }
    
    // Simulate blockchain confirmation time
    private static void waitForBlockConfirmation() {
        System.out.println("Waiting for block confirmation...");
        try {
            Thread.sleep(1500);
            System.out.println("Block confirmed on the network!");
        } catch (InterruptedException e) {
            System.out.println("Block confirmation interrupted.");
        }
    }
} 