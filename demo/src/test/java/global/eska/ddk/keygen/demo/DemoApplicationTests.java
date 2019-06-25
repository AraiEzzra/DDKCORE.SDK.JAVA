package global.eska.ddk.keygen.demo;

import global.eska.ddk.keygen.account.AccountCreator;
import global.eska.ddk.keygen.passphrase.PassphraseGenerator;
import global.eska.ddk.keygen.sodium.KeyPair;
import global.eska.ddk.keygen.sodium.KeyPairCreator;
import lombok.extern.slf4j.Slf4j;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.math.BigDecimal;
import java.util.Arrays;

import static global.eska.ddk.keygen.utils.Etalon.*;
import static global.eska.ddk.keygen.utils.HashUtils.sha256;
import static org.junit.Assert.*;

@Ignore
@Slf4j
@RunWith(SpringRunner.class)
@SpringBootTest
public class DemoApplicationTests {

    @Autowired
    private PassphraseGenerator passphraseGenerator;
    @Autowired
    private KeyPairCreator keyPairCreator;
    @Autowired
    private AccountCreator accountCreator;

    @Test
    public void createPassphraseTest() {
        String passphrase = passphraseGenerator.createPassphrase();
        assertNotNull(passphrase);
        log.info("Random generated passphrase: {}", passphrase);
    }

    @Test
    public void createKeyPairTest() {
        // todo: make different tests
        // todo: log test results
        byte[] passphraseHash = sha256(PASSPHRASE);
        assertNotNull(passphraseHash);
        assertArrayEquals(PASSPHRASE_HASH, passphraseHash);

        KeyPair keyPair = keyPairCreator.createKeyPair(PASSPHRASE);
        assertNotNull(keyPair);
        assertArrayEquals(PUBLIC_KEY_BYTES, keyPair.getPublicKey());
        assertArrayEquals(PRIVATE_KEY_BYTES, keyPair.getSecretKey());
        assertEquals("PUBLIC_KEY_HEX", PUBLIC_KEY_HEX, keyPair.getPublicKeyHex());

        BigDecimal address = accountCreator.getAddressByPublicKey(keyPair.getPublicKey());
        assertEquals("ADDRESS", ADDRESS, address);

        log.info("{}", PASSPHRASE);
        log.info("{}", Arrays.toString(passphraseHash));
        log.info("{}", keyPair.getPublicKeyHex());
        log.info("{}", keyPair.getSecretKeyHex());
        log.info("{}", address);
    }

}
