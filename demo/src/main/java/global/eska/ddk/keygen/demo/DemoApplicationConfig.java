package global.eska.ddk.keygen.demo;

import com.goterl.lazycode.lazysodium.LazySodiumJava;
import com.goterl.lazycode.lazysodium.SodiumJava;
import global.eska.ddk.keygen.account.AccountCreator;
import global.eska.ddk.keygen.account.DDKAccountCreator;
import global.eska.ddk.keygen.passphrase.DDKPathPhraseGenerator;
import global.eska.ddk.keygen.passphrase.PassphraseGenerator;
import global.eska.ddk.keygen.sodium.DDKKeyPairCreator;
import global.eska.ddk.keygen.sodium.KeyPairCreator;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class DemoApplicationConfig {
    @Bean
    public SodiumJava getLazySodium() {
        return new SodiumJava();
    }

    @Bean
    public LazySodiumJava getLazySodiumJava(SodiumJava sodiumJava) {
        return new LazySodiumJava(sodiumJava);
    }

    @Bean
    public PassphraseGenerator getPassphraseGenerator() {
        return new DDKPathPhraseGenerator();
    }

    @Bean
    public KeyPairCreator getKeyPairCreator(LazySodiumJava lazySodium) {
        return new DDKKeyPairCreator(lazySodium);
    }

    @Bean
    public AccountCreator getAccountCreator() {
        return new DDKAccountCreator();
    }
}
