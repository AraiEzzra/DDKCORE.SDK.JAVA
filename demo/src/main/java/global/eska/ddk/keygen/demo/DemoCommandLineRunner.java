package global.eska.ddk.keygen.demo;

import global.eska.ddk.keygen.account.AccountCreator;
import global.eska.ddk.keygen.passphrase.PassphraseGenerator;
import global.eska.ddk.keygen.sodium.KeyPair;
import global.eska.ddk.keygen.sodium.KeyPairCreator;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class DemoCommandLineRunner implements CommandLineRunner {

    @Value("${greetings-phrase}")
    private String greetingsPhrase;

    private final PassphraseGenerator passphraseGenerator;

    private final KeyPairCreator keyPairCreator;

    private final AccountCreator accountCreator;

    public DemoCommandLineRunner(PassphraseGenerator passphraseGenerator, KeyPairCreator keyPairCreator, AccountCreator accountCreator) {
        this.passphraseGenerator = passphraseGenerator;
        this.keyPairCreator = keyPairCreator;
        this.accountCreator = accountCreator;
    }

    @Override
    public void run(String... args) {
        log.info("{}", greetingsPhrase);

        String passphrase = passphraseGenerator.createPassphrase();
        log.info("Random passphrase generated successful: {}", passphrase);

        KeyPair keyPair = keyPairCreator.createKeyPair(passphrase);

        log.info("KeyPair generated successful:");
        log.info("public key: {}", keyPair.getPublicKey());
        log.info("public key hex: {}", keyPair.getPublicKeyHex());
        log.info("secret key: {}", keyPair.getSecretKey());
        log.info("secret key hex: {}", keyPair.getSecretKeyHex());

        long address = accountCreator.getAddressByPublicKey(keyPair.getPublicKey());

        log.info("DDK address generated successful: {}", address);
    }
}
