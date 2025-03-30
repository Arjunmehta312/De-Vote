/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Main.java to edit this template
 */
package oop_project;

import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author arjun
 */
public class OOP_Project {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        // Create voters
        Voter voter1 = new Voter(1, "John Doe", "0x123abc");
        Voter voter2 = new Voter(2, "Jane Smith", "0x456def");
        
        // Create an election with candidates
        List<String> candidates = new ArrayList<>();
        candidates.add("Candidate A");
        candidates.add("Candidate B");
        candidates.add("Candidate C");
        
        Election election = new Election(101, "Presidential Election", candidates);
        
        // Cast votes
        Vote vote1 = new Vote(voter1, election, "Candidate A");
        Vote vote2 = new Vote(voter2, election, "Candidate B");
        
        // Cast votes through election
        election.castVote(voter1, "Candidate A");
        election.castVote(voter2, "Candidate B");
        
        // Display information
        voter1.displayDetails();
        voter2.displayDetails();
        
        vote1.displayVote();
        vote2.displayVote();
        
        election.displayResults();
    }
    
} 