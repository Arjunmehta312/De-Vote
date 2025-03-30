/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package oop_project;

/**
 *
 * @author arjun
 */
public class Voter {
    // Attributes
    private int voterId;
    private String name;
    private String blockchainAddress;
    
    // Constructor
    public Voter(int voterId, String name, String blockchainAddress) {
        this.voterId = voterId;
        this.name = name;
        this.blockchainAddress = blockchainAddress;
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
    
    // Method to display voter details
    public void displayDetails() {
        System.out.println("\nVoter Details:");
        System.out.println("Voter ID: " + voterId);
        System.out.println("Name: " + name);
        System.out.println("Blockchain Address: " + blockchainAddress);
    }
} 