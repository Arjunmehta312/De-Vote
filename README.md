# Decentralized Blockchain Voting System

A Java-based implementation of a decentralized voting system using blockchain technology. This project demonstrates secure, transparent, and tamper-proof electronic voting with an interactive user interface.

## Project Overview

This blockchain-based system provides:
- Cryptographically secure voter identification
- Immutable voting records using blockchain technology
- Prevention of double-voting through blockchain verification
- Transparent and auditable election results
- Digital signature verification for vote authenticity
- Interactive user interface for voter registration and voting

## Features

- **Blockchain Security**: Each vote is stored as a transaction in a blockchain with cryptographic hashing
- **Digital Signatures**: RSA-2048 cryptographic signatures verify voter identity and vote authenticity
- **Double-Vote Prevention**: Blockchain address tracking prevents voters from voting multiple times
- **Transparent Vote Counting**: All votes are publicly verifiable while maintaining voter privacy
- **Chain Validation**: Full blockchain verification before result tabulation
- **Block Confirmations**: Simulated network consensus for transaction validation
- **Interactive Interface**: Console-based menu system for voter registration and vote casting
- **Voter Registry**: Secure storage of voter identities linked to blockchain addresses

## Classes

### Voter
- Manages voter identity with unique blockchain addresses
- Generates RSA-2048 public/private key pairs for digital signatures
- Signs voting transactions cryptographically

```java
public class Voter {
    private int voterId;
    private String name;
    private String blockchainAddress;
    private PublicKey publicKey;
    private PrivateKey privateKey;
    
    // Constructor and key generation
    public Voter(int voterId, String name, String blockchainAddress) {
        this.voterId = voterId;
        this.name = name;
        this.blockchainAddress = blockchainAddress;
        generateKeyPair();
    }
    
    // Sign data with private key (for vote verification)
    public String signData(String data) {
        // Implementation uses SHA256withRSA signature algorithm
    }
}
```

### Vote
- Represents a blockchain transaction for a single vote
- Includes transaction hashing, digital signatures, and timestamps
- Maintains chain integrity with previous block references
- Provides signature verification functionality

```java
public class Vote {
    private Voter voter;
    private Election election;
    private String candidate;
    private String transactionHash;
    private String digitalSignature;
    private long timestamp;
    private String previousBlockHash;
    
    // Constructor creates and signs the blockchain transaction
    public Vote(Voter voter, Election election, String candidate) {
        // Implementation creates transaction hash and digital signature
    }
    
    // Verify the digital signature of the vote
    public boolean verifySignature() {
        // Implementation verifies using voter's public key
    }
}
```

### Election
- Represents an election contract on the blockchain
- Manages candidates and vote collection
- Prevents double-voting through blockchain address tracking
- Verifies blockchain integrity before counting votes
- Provides transparent result tabulation

```java
public class Election {
    private int electionId;
    private String name;
    private List<String> candidates;
    private List<Vote> blockchain;
    private Map<String, Boolean> voterParticipation;
    
    // Cast vote method adds a vote to the blockchain
    public void castVote(Voter voter, String candidate) {
        // Implementation prevents double voting and records vote
    }
    
    // Verify the entire blockchain before counting
    public boolean verifyBlockchain() {
        // Implementation verifies all digital signatures
    }
}
```

### OOP_Project
- Implements interactive console interface with a menu system
- Allows users to register as voters with unique blockchain identities
- Provides interface for casting votes on the blockchain
- Displays election results and registered voters
- Manages the voter registry and block confirmations

```java
public class OOP_Project {
    public static void main(String[] args) {
        // Initialize election and voter registry
        // Implement menu system for user interaction
    }
    
    private static void registerVoter(Scanner scanner, Map<String, Voter> voterRegistry) {
        // Implementation generates blockchain address and RSA keys
    }
    
    private static void castVote(Scanner scanner, Map<String, Voter> voterRegistry, Election election) {
        // Implementation authenticates voter and records vote
    }
}
```

## Running the Application

### Prerequisites
- Java Development Kit (JDK) 8 or higher
- NetBeans IDE (recommended as this is a NetBeans project)

### Installation
1. Clone the repository:
   ```
   git clone https://github.com/yourusername/blockchain-voting-system.git
   ```
2. Open the project in NetBeans IDE
3. Build the project to resolve dependencies

### Steps to Run
1. Open the project in NetBeans IDE
2. Right-click on the project in the Projects window
3. Select "Run" from the context menu

Alternatively, run from command line:
```
cd /path/to/OOP_Project
ant run
```

### User Interface Guide
When running the application, you'll see a menu with these options:
1. **Register as a voter** - Create a new voter identity with a blockchain address
2. **Cast a vote** - Submit a vote for your preferred candidate
3. **Display election results** - View current election results with blockchain verification
4. **Display all voters** - List all registered voters and their blockchain details
5. **Exit** - Close the application

## Technical Details

### Blockchain Implementation
The project implements a simplified blockchain with these key components:
- **Block Structure**: Each vote transaction is a block containing:
  - Voter information (ID, blockchain address)
  - Candidate selection
  - Timestamp
  - Previous block hash (to create the chain)
  - Digital signature
  - Transaction hash (SHA-256)
- **Chain Validation**: The system verifies the entire blockchain before tallying results by:
  - Checking digital signatures against voter public keys
  - Verifying transaction integrity through hashing
- **Double-Vote Prevention**: Uses blockchain address tracking to ensure each voter votes only once

### Cryptography
- **Key Generation**: RSA-2048 key pairs for digital signatures
- **Signature Algorithm**: SHA256withRSA for signing and verifying transactions
- **Hashing Algorithm**: SHA-256 for transaction and election hashing
- **Address Generation**: Simulated blockchain addresses with random hex strings

### Data Flow
1. Voter registers and receives a blockchain address and key pair
2. Voter authenticates with their blockchain address
3. Vote is cryptographically signed and added to the blockchain
4. Block confirmation simulates network consensus
5. Election results are tallied through blockchain verification

### Security Features
- **Immutable Records**: Once recorded, votes cannot be altered
- **Digital Signature Verification**: Ensures vote authenticity
- **Blockchain Address Authentication**: Secures voter identity
- **Transparent Vote Counting**: All votes are publicly verifiable

## Future Enhancements

- Integration with real blockchain networks (Ethereum, Solana)
- Smart contract implementation in Solidity
- Decentralized storage using IPFS
- Zero-knowledge proofs for enhanced voter privacy
- Mobile wallet support for voter authentication
- Multi-signature support for election administration
- Web-based user interface
- QR code generation for blockchain addresses
- Proof-of-stake consensus mechanism for vote validation
- Distributed ledger technology to eliminate central authority
- Integration with biometric authentication systems
- Real-time result monitoring dashboard
- Audit trail and transparent logging system
- Cross-chain interoperability for larger elections