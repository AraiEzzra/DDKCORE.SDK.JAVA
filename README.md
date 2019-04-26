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
