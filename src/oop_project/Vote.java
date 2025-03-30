/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package oop_project;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.time.Instant;
import java.util.Base64;

/**
 *
 * @author arjun
 */
public class Vote {
    // Association attributes (This demonstrates simple association)
    private Voter voter;  // A vote is associated with a voter
    private Election election;  // A vote is associated with an election
    private String candidate;
    
    // Blockchain specific attributes
    private String transactionHash;
    private String digitalSignature;
    private long timestamp;
    private String previousBlockHash;
    private static String lastBlockHash = "0000000000000000000000000000000000000000000000000000000000000000"; // Genesis block
    
    // Constructor (establishes the association)
    public Vote(Voter voter, Election election, String candidate) {
        this.voter = voter;
        this.election = election;
        this.candidate = candidate;
        this.timestamp = Instant.now().getEpochSecond();
        this.previousBlockHash = lastBlockHash;
        
        // Create transaction data
        String transactionData = createTransactionData();
        
        // Hash the transaction
        this.transactionHash = calculateHash(transactionData);
        
        // Sign the transaction with voter's private key
        this.digitalSignature = voter.signData(this.transactionHash);
        
        // Update the chain
        lastBlockHash = this.transactionHash;
        
        System.out.println("Vote recorded on blockchain: " + voter.getName() + " voted for " + candidate);
        System.out.println("Transaction hash: " + transactionHash.substring(0, 10) + "...");
    }
    
    private String createTransactionData() {
        return voter.getVoterId() + ":" + 
               voter.getBlockchainAddress() + ":" + 
               election.getElectionId() + ":" + 
               candidate + ":" + 
               timestamp + ":" + 
               previousBlockHash;
    }
    
    private String calculateHash(String data) {
        try {
            MessageDigest digest = MessageDigest.getInstance("SHA-256");
            byte[] hash = digest.digest(data.getBytes(StandardCharsets.UTF_8));
            StringBuilder hexString = new StringBuilder();
            for (byte b : hash) {
                String hex = Integer.toHexString(0xff & b);
                if (hex.length() == 1) hexString.append('0');
                hexString.append(hex);
            }
            return hexString.toString();
        } catch (Exception e) {
            throw new RuntimeException("Error calculating hash: " + e.getMessage());
        }
    }
    
    public boolean verifySignature() {
        try {
            java.security.Signature signature = java.security.Signature.getInstance("SHA256withRSA");
            signature.initVerify(voter.getPublicKey());
            signature.update(transactionHash.getBytes());
            return signature.verify(Base64.getDecoder().decode(digitalSignature));
        } catch (Exception e) {
            System.out.println("Error verifying signature: " + e.getMessage());
            return false;
        }
    }
    
    // Method to display vote information
    public void displayVote() {
        System.out.println("\nVote Blockchain Transaction Information:");
        System.out.println("Voter: " + voter.getName() + " (ID: " + voter.getVoterId() + ")");
        System.out.println("Blockchain Address: " + voter.getBlockchainAddress());
        System.out.println("Candidate: " + candidate);
        System.out.println("Transaction Hash: " + transactionHash);
        System.out.println("Previous Block Hash: " + previousBlockHash);
        System.out.println("Timestamp: " + timestamp);
        System.out.println("Signature Valid: " + verifySignature());
    }
    
    // Getters
    public String getTransactionHash() {
        return transactionHash;
    }
    
    public String getDigitalSignature() {
        return digitalSignature;
    }
    
    public Voter getVoter() {
        return voter;
    }
    
    public String getCandidate() {
        return candidate;
    }
} 