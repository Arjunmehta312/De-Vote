/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Main.java to edit this template
 */
package oop_project;

import java.util.ArrayList;
import java.util.List;
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
        System.out.println("========= Decentralized Blockchain Voting System =========");
        System.out.println("Initializing blockchain network...");
        
        // Create voters with blockchain addresses
        System.out.println("\n--- Creating voters with blockchain wallets ---");
        Voter voter1 = new Voter(1, "John Doe", "0x3F4e93DB67c4B9d673e4887A982ED7c36116e649");
        Voter voter2 = new Voter(2, "Jane Smith", "0x7Ab4d1D5Ef2681563d8D80C7D5D0728B7872BDe1");
        Voter voter3 = new Voter(3, "Bob Johnson", "0x9E5F382B2996F7cc0D552f20E7E7e2A8a2E62C42");
        
        // Create an election with candidates
        System.out.println("\n--- Creating election smart contract on blockchain ---");
        List<String> candidates = new ArrayList<>();
        candidates.add("Candidate A");
        candidates.add("Candidate B");
        candidates.add("Candidate C");
        
        Election election = new Election(101, "Presidential Election", candidates);
        
        System.out.println("\n--- Casting votes as blockchain transactions ---");
        // Cast votes as blockchain transactions
        election.castVote(voter1, "Candidate A");
        
        // Wait a moment to simulate blockchain confirmation time
        waitForBlockConfirmation();
        
        election.castVote(voter2, "Candidate B");
        waitForBlockConfirmation();
        
        election.castVote(voter3, "Candidate A");
        waitForBlockConfirmation();
        
        // Try double-voting (should be rejected)
        System.out.println("\n--- Attempting double-vote (should be rejected) ---");
        election.castVote(voter1, "Candidate C");
        
        // Display voter information including blockchain details
        System.out.println("\n--- Voter Wallet Information ---");
        voter1.displayDetails();
        voter2.displayDetails();
        voter3.displayDetails();
        
        // Display individual vote blockchain transactions
        System.out.println("\n--- Vote Blockchain Transactions ---");
        Vote vote1 = new Vote(voter1, election, "Candidate A");
        vote1.displayVote();
        
        // Verify and display election results from blockchain
        System.out.println("\n--- Verifying and displaying election results from blockchain ---");
        election.displayResults();
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