/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package oop_project;

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
    
    // Constructor
    public Election(int electionId, String name, List<String> candidates) {
        this.electionId = electionId;
        this.name = name;
        this.candidates = candidates;
        this.votes = new HashMap<>();
    }
    
    // Method to cast a vote
    public void castVote(Voter voter, String candidate) {
        if (candidates.contains(candidate)) {
            votes.put(voter.getVoterId(), candidate);
            System.out.println(voter.getName() + " has voted for " + candidate + " in " + this.name);
        } else {
            System.out.println("Invalid candidate: " + candidate);
        }
    }
    
    // Method to display results
    public void displayResults() {
        System.out.println("\nElection Results for " + name + " (ID: " + electionId + ")");
        System.out.println("Candidates: " + candidates);
        
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
    }
} 