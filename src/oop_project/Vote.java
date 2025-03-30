/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package oop_project;

/**
 *
 * @author arjun
 */
public class Vote {
    // Association attributes (This demonstrates simple association)
    private Voter voter;  // A vote is associated with a voter
    private Election election;  // A vote is associated with an election
    private String candidate;
    
    // Constructor (establishes the association)
    public Vote(Voter voter, Election election, String candidate) {
        this.voter = voter;
        this.election = election;
        this.candidate = candidate;
        System.out.println("Vote recorded: " + voter.getName() + " voted for " + candidate);
    }
    
    // Method to display vote information
    public void displayVote() {
        System.out.println("\nVote Information:");
        System.out.println("Voter: " + voter.getName() + " (ID: " + voter.getVoterId() + ")");
        System.out.println("Candidate: " + candidate);
    }
} 