# Decentralized Blockchain Voting System

A Java-based implementation of a decentralized voting system using blockchain technology. This project demonstrates secure, transparent, and tamper-proof electronic voting.

## Project Overview

This blockchain-based system provides:
- Cryptographically secure voter identification
- Immutable voting records using blockchain technology
- Prevention of double-voting through blockchain verification
- Transparent and auditable election results
- Digital signature verification for vote authenticity

## Features

- **Blockchain Security**: Each vote is stored as a transaction in a blockchain with cryptographic hashing
- **Digital Signatures**: RSA-2048 cryptographic signatures verify voter identity and vote authenticity
- **Double-Vote Prevention**: Blockchain address tracking prevents voters from voting multiple times
- **Transparent Vote Counting**: All votes are publicly verifiable while maintaining voter privacy
- **Chain Validation**: Full blockchain verification before result tabulation
- **Block Confirmations**: Simulated network consensus for transaction validation

## Classes

### Voter
- Manages voter identity with unique blockchain addresses
- Generates RSA-2048 public/private key pairs for digital signatures
- Signs voting transactions cryptographically

### Vote
- Represents a blockchain transaction for a single vote
- Includes transaction hashing, digital signatures, and timestamps
- Maintains chain integrity with previous block references
- Provides signature verification functionality

### Election
- Represents an election contract on the blockchain
- Manages candidates and vote collection
- Prevents double-voting through blockchain address tracking
- Verifies blockchain integrity before counting votes
- Provides transparent result tabulation

### OOP_Project
- Demonstrates the system's functionality
- Creates voter identities with blockchain wallets
- Deploys an election smart contract
- Records votes as blockchain transactions
- Shows double-voting prevention
- Displays blockchain transaction details and verification

## Running the Application

### Prerequisites
- Java Development Kit (JDK) 8 or higher
- NetBeans IDE (recommended as this is a NetBeans project)

### Steps
1. Open the project in NetBeans IDE
2. Right-click on the project in the Projects window
3. Select "Run" from the context menu

Alternatively, run from command line:
```
cd /path/to/OOP_Project
ant run
```

## Technical Details

- **Cryptography**: RSA-2048 key pairs for digital signatures
- **Hashing Algorithm**: SHA-256 for transaction and election hashing
- **Block Structure**: Includes transaction data, signatures, timestamps, and previous block references
- **Consensus**: Simulated block confirmations
- **Authentication**: Public key infrastructure for voter verification

## Future Enhancements

- Integration with real blockchain networks (Ethereum, Solana)
- Smart contract implementation in Solidity
- Decentralized storage using IPFS
- Zero-knowledge proofs for enhanced voter privacy
- Mobile wallet support for voter authentication
- Multi-signature support for election administration

## License

This project is open-source and available for educational purposes. 