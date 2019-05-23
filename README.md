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



###How it works:
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

```

###For create transaction:
Please view the 
[documentation](https://github.com/AraiEzzra/DDKCORE/blob/master/docs/api/transaction.md#create-transaction) 
for a more comprehensive guide.
