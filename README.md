### Java SDK library for DDK

Purposes:
- Generate random passphrase.
- Create KeyPair from random passphrase.
- Create DDK account number from public key.

Contains two modules:
- library (jar).
- console demo application (jar).

### Getting started

Requires:
- jdk-8
- maven 3.5.+

Installation:
```bash
git clone https://github.com/AraiEzzra/DDKCORE.SDK.JAVA.git
cd DDKCORE.SDK.JAVA
mvn clean install
```

After installation you can use library in maven projects by adding dependency:
```xml
        <dependency>
            <groupId>global.eska.ddk</groupId>
            <artifactId>lib</artifactId>
            <version>1.0.0</version>
        </dependency>
```

```yaml
# add your configuration to application.yml
# protocol should be http
    ddk:
      protocol: http
      host: localhost
      port: 7008
```

### How it works:
```java
    // Initialise PassphraseGenerator for create random passphrase
    PassphraseGenerator passphraseGenerator = new DDKPathPhraseGenerator();
    // Create new random passphrase
    String passphrase = passphraseGenerator.createPassphrase();
    
    // Initialise LazySodium for generate KeyPair
    LazySodiumJava lazySodium = new LazySodiumJava(new SodiumJava());

    // Initialise KeyPairCreator for create keyPair by passphrase
    KeyPairCreator keyPairCreator = new DDKKeyPairCreator(lazySodium);
    
    // Initialise AccountCreator for generate detached account
    // If you create send transaction account will be create in the network
    AccountCreator accountCreator = new DDKAccountCreator();
    
    // Create KeyPair by passphrase
    // Key pair contains hash public key and hash secret key
    KeyPair keyPair = keyPairCreator.createKeyPair(passphrase);
    
    // Get address by public key
    BigInteger address = accountCreator.getAddressByPublicKey(keyPair.getPublicKey());
    
    // All next methods can throw DDKApplicationException you have to process it
    // Get account
    Account account = ddkClient.getAccount(ADDRESS);
    
    // Get account balance
    Long balance = ddkClient.getAccountBalance(ADDRESS);
    
    // Get transaction
    Transaction transaction = transaction = ddkClient.getTransaction(TRANSACTION_ID)
    
    // For get transactions you have to create Filter and Sort
    // Filter
    Filter filter = new Filter(null, BLOCK_ID, null); // you can filter by block id, Transaction type and senderPublicKey
    // Sort
    Sort sort = new Sort("createdAt", SortDirection.ASC);
    // transfer filter and sort and limit with offset for pagination to getTransactions method
    List<Transaction> transactions = transactions = ddkClient.getTransactions(filter, 10, 0, sort);
    
    // Get transactions by height(transfer height, limit and offset)
    List<Transaction> transactions = transactions = ddkClient.getTransactionsByHeight(1, 10, 0);
    
    // Send transaction(for that moment you can create only TransactionType.SEND and AssetSend)
    // first of all you should create Asset :
    AssetSend assetSend = new AssetSend();
    assetSend.setAmount(50000L);
    assetSend.setRecipientAddress(RECIPIENT_ADDRESS);
    
    // next you should create transaction and set Asset as you can see below:
    Transaction<AssetSend> transaction = new Transaction<>();
    transaction.setType(TransactionType.SEND);
    transaction.setSenderAddress(SENDER_ADDRESS);
    transaction.setSenderPublicKey(SENDER_PUBLIC_KEY);
    transaction.setAsset(assetSend);
    
    // now send transaction to creation
    // for signnature transaction you have to transfer sender's secret
    Transaction transaction = ddkClient.createTransaction(transaction, SECRET);
    // if transaction valid and node success take transaction you receive transaction with id
    // you can use this id for get transaction and check if this transaction successfully apply to network

```

#### For fill funds to customer's account create send transaction to customer's account address from crypto exchange account
#### For receive funds from customer's account create send transaction from customer's address to crypto exchange account


### For create transaction:
Please view the 
[documentation](https://github.com/AraiEzzra/DDKCORE/blob/master/docs/api/transaction.md#create-transaction) 
for a more comprehensive guide.
