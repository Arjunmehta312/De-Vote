/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package oop_project;

import java.security.MessageDigest;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 *
 * @author arjun
 */
public class Election {
    // Attributes
    private int electionId;
    private String name;
    private List<String> candidates;
    private Map<Integer, String> votes;
    
    // Blockchain specific attributes
    private List<Vote> blockchain;
    private String electionHash;
    private Map<String, Boolean> voterParticipation;  // Track voters by blockchain address
    
    // Constructor
    public Election(int electionId, String name, List<String> candidates) {
        this.electionId = electionId;
        this.name = name;
        this.candidates = candidates;
        this.votes = new HashMap<>();
        this.blockchain = new ArrayList<>();
        this.voterParticipation = new HashMap<>();
        
        // Generate a unique hash for this election
        this.electionHash = calculateElectionHash();
        
        System.out.println("New election created on blockchain: " + name);
        System.out.println("Election Hash: " + electionHash.substring(0, 10) + "...");
    }
    
    // Method to cast a vote
    public void castVote(Voter voter, String candidate) {
        // Check if voter already voted in this election
        if (voterParticipation.containsKey(voter.getBlockchainAddress())) {
            System.out.println("ERROR: Voter " + voter.getName() + " already voted in this election! Vote rejected.");
            return;
        }
        
        if (candidates.contains(candidate)) {
            // Create a blockchain transaction (vote)
            Vote voteTransaction = new Vote(voter, this, candidate);
            
            // Record the vote in the blockchain
            blockchain.add(voteTransaction);
            
            // Update mapping of voter ID to candidate choice for counting
            votes.put(voter.getVoterId(), candidate);
            
            // Mark this voter as having participated
            voterParticipation.put(voter.getBlockchainAddress(), true);
            
            System.out.println(voter.getName() + " has voted for " + candidate + " in " + this.name);
            System.out.println("Transaction added to election blockchain. Current block height: " + blockchain.size());
        } else {
            System.out.println("Invalid candidate: " + candidate);
        }
    }
    
    private String calculateElectionHash() {
        try {
            String electionData = electionId + name + String.join(",", candidates);
            MessageDigest digest = MessageDigest.getInstance("SHA-256");
            byte[] hash = digest.digest(electionData.getBytes(StandardCharsets.UTF_8));
            StringBuilder hexString = new StringBuilder();
            for (byte b : hash) {
                String hex = Integer.toHexString(0xff & b);
                if (hex.length() == 1) hexString.append('0');
                hexString.append(hex);
            }
            return hexString.toString();
        } catch (Exception e) {
            throw new RuntimeException("Error calculating election hash: " + e.getMessage());
        }
    }
    
    // Verify the entire blockchain
    public boolean verifyBlockchain() {
        boolean isValid = true;
        
        System.out.println("\nVerifying election blockchain integrity...");
        for (Vote vote : blockchain) {
            boolean validSignature = vote.verifySignature();
            if (!validSignature) {
                System.out.println("Invalid signature found for vote from " + vote.getVoter().getName());
                isValid = false;
            }
        }
        
        System.out.println("Blockchain verification " + (isValid ? "SUCCESSFUL" : "FAILED"));
        return isValid;
    }
    
    // Method to display results
    public void displayResults() {
        System.out.println("\nElection Results for " + name + " (ID: " + electionId + ")");
        System.out.println("Election Hash: " + electionHash);
        System.out.println("Total Blocks in Chain: " + blockchain.size());
        System.out.println("Candidates: " + candidates);
        
        // Verify blockchain integrity before showing results
        boolean chainIsValid = verifyBlockchain();
        if (!chainIsValid) {
            System.out.println("WARNING: Blockchain integrity check failed! Results may be compromised.");
        }
        
        // Count votes for each candidate
        Map<String, Integer> results = new HashMap<>();
        for (String candidate : candidates) {
            results.put(candidate, 0);
        }
        
        for (String votedCandidate : votes.values()) {
            results.put(votedCandidate, results.get(votedCandidate) + 1);
        }
        
        // Display vote counts
        System.out.println("Results:");
        for (String candidate : candidates) {
            System.out.println(candidate + ": " + results.get(candidate) + " votes");
        }
        
        System.out.println("Total votes cast: " + votes.size());
        System.out.println("Blockchain Status: " + (chainIsValid ? "Valid" : "Compromised"));
    }
    
    // Getters
    public int getElectionId() {
        return electionId;
    }
    
    public String getName() {
        return name;
    }
    
    public List<String> getCandidates() {
        return candidates;
    }
    
    public String getElectionHash() {
        return electionHash;
    }
} 