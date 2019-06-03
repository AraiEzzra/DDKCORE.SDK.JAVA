package global.eska.ddk.keygen.demo;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.goterl.lazycode.lazysodium.LazySodiumJava;
import com.goterl.lazycode.lazysodium.SodiumJava;
import global.eska.ddk.api.client.listeners.MessageListener;
import global.eska.ddk.api.client.middleware.Middleware;
import global.eska.ddk.api.client.service.Blocker;
import global.eska.ddk.api.client.service.DDKService;
import global.eska.ddk.api.client.socket.SocketClient;
import global.eska.ddk.keygen.account.AccountCreator;
import global.eska.ddk.keygen.account.DDKAccountCreator;
import global.eska.ddk.keygen.passphrase.DDKPathPhraseGenerator;
import global.eska.ddk.keygen.passphrase.PassphraseGenerator;
import global.eska.ddk.keygen.sodium.DDKKeyPairCreator;
import global.eska.ddk.keygen.sodium.KeyPairCreator;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.concurrent.CountDownLatch;

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

    @Bean
    public CountDownLatch getCountDownLatch() {
        return new CountDownLatch(1);
    }

    @Bean
    public Middleware middleware() {
        return new Middleware();
    }

    @Bean
    public MessageListener messageListener(Middleware middleware) {
        return new MessageListener(middleware);
    }

    @Bean
    public SocketClient socketClient(Middleware middleware, MessageListener messageListener) {
        return new SocketClient(middleware, messageListener);
    }

    @Bean
    public ObjectMapper objectMapper() {
        return new ObjectMapper();
    }

    @Bean
    public Blocker blocker() {
        return new Blocker();
    }

    @Bean
    public DDKService ddkService(
            SocketClient socketClient,
            ObjectMapper objectMapper) {
        return new DDKService(socketClient, objectMapper);
    }

}
