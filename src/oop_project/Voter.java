/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package oop_project;

import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.SecureRandom;
import java.security.Signature;
import java.util.Base64;

/**
 *
 * @author arjun
 */
public class Voter {
    // Attributes
    private int voterId;
    private String name;
    private String blockchainAddress;
    private PublicKey publicKey;
    private PrivateKey privateKey;
    
    // Constructor
    public Voter(int voterId, String name, String blockchainAddress) {
        this.voterId = voterId;
        this.name = name;
        this.blockchainAddress = blockchainAddress;
        generateKeyPair();
    }
    
    // Generate cryptographic key pair for digital signatures
    private void generateKeyPair() {
        try {
            KeyPairGenerator keyGen = KeyPairGenerator.getInstance("RSA");
            SecureRandom random = SecureRandom.getInstanceStrong();
            keyGen.initialize(2048, random);
            KeyPair pair = keyGen.generateKeyPair();
            this.privateKey = pair.getPrivate();
            this.publicKey = pair.getPublic();
            System.out.println("Cryptographic keys generated for voter: " + name);
        } catch (Exception e) {
            System.out.println("Error generating key pair: " + e.getMessage());
        }
    }
    
    // Sign data with private key (used for vote verification)
    public String signData(String data) {
        try {
            Signature signature = Signature.getInstance("SHA256withRSA");
            signature.initSign(privateKey);
            signature.update(data.getBytes());
            byte[] signedData = signature.sign();
            return Base64.getEncoder().encodeToString(signedData);
        } catch (Exception e) {
            System.out.println("Error signing data: " + e.getMessage());
            return null;
        }
    }
    
    // Getters
    public int getVoterId() {
        return voterId;
    }
    
    public String getName() {
        return name;
    }
    
    public String getBlockchainAddress() {
        return blockchainAddress;
    }
    
    public PublicKey getPublicKey() {
        return publicKey;
    }
    
    // Method to display voter details
    public void displayDetails() {
        System.out.println("\nVoter Details:");
        System.out.println("Voter ID: " + voterId);
        System.out.println("Name: " + name);
        System.out.println("Blockchain Address: " + blockchainAddress);
        System.out.println("Public Key: " + Base64.getEncoder().encodeToString(publicKey.getEncoded()).substring(0, 20) + "...");
    }
} 