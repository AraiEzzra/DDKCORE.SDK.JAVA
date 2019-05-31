package global.eska.ddk.keygen.passphrase;

import io.github.novacrypto.bip39.MnemonicGenerator;
import io.github.novacrypto.bip39.Words;
import io.github.novacrypto.bip39.wordlists.English;
import org.springframework.stereotype.Component;

import java.security.SecureRandom;

@Component
public class DDKPathPhraseGenerator implements PassphraseGenerator {

    @Override
    public String createPassphrase() {
        StringBuilder sb = new StringBuilder();
        byte[] entropy = new byte[Words.TWELVE.byteLength()];
        new SecureRandom().nextBytes(entropy);
        new MnemonicGenerator(English.INSTANCE).createMnemonic(entropy, sb::append);
        return sb.toString();
    }
}
