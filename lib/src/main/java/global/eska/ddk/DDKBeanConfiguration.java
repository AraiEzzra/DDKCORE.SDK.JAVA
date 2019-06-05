package global.eska.ddk;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.goterl.lazycode.lazysodium.LazySodiumJava;
import com.goterl.lazycode.lazysodium.SodiumJava;
import global.eska.ddk.api.client.listeners.MessageListener;
import global.eska.ddk.api.client.middleware.Middleware;
import global.eska.ddk.api.client.model.MessageType;
import global.eska.ddk.api.client.model.TransactionType;
import global.eska.ddk.api.client.service.Blocker;
import global.eska.ddk.api.client.service.DDKClient;
import global.eska.ddk.api.client.socket.SocketClient;
import global.eska.ddk.api.client.utils.MessageTypeSerializer;
import global.eska.ddk.api.client.utils.TransactionTypeSerializer;
import global.eska.ddk.api.client.utils.Utils;
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
public class DDKBeanConfiguration {
    @Bean
    public SodiumJava lazySodium() {
        return new SodiumJava();
    }

    @Bean
    public LazySodiumJava lazySodiumJava(SodiumJava sodiumJava) {
        return new LazySodiumJava(sodiumJava);
    }

    @Bean
    public PassphraseGenerator passphraseGenerator() {
        return new DDKPathPhraseGenerator();
    }

    @Bean
    public KeyPairCreator keyPairCreator(LazySodiumJava lazySodium) {
        return new DDKKeyPairCreator(lazySodium);
    }

    @Bean
    public AccountCreator accountCreator() {
        return new DDKAccountCreator();
    }

    @Bean
    public CountDownLatch countDownLatch() {
        return new CountDownLatch(1);
    }

    @Bean
    public Middleware middleware(Blocker blocker, Utils utils) {
        return new Middleware(blocker, utils);
    }

    @Bean
    public MessageListener messageListener(Middleware middleware, Gson gson) {
        return new MessageListener(middleware, gson);
    }

    @Bean
    public DDKConfiguration ddkConfiguration() {
        return new DDKConfiguration();
    }

    @Bean
    public SocketClient socketClient(Middleware middleware, MessageListener messageListener,
                                     Blocker blocker, DDKConfiguration ddkConfiguration) {
        return new SocketClient(middleware, messageListener, blocker, ddkConfiguration);
    }

    @Bean
    public Gson gson() {
        GsonBuilder gsonBuilder = new GsonBuilder();
        gsonBuilder.registerTypeAdapter(MessageType.class, new MessageTypeSerializer());
        gsonBuilder.registerTypeAdapter(TransactionType.class, new TransactionTypeSerializer());
        Gson gson = gsonBuilder.create();
        return gson;
    }

    @Bean
    public Blocker blocker() {
        return new Blocker();
    }

    @Bean
    public Utils getUtils(Gson gson) {
        return new Utils(gson);
    }

    @Bean
    public DDKClient ddkClient(SocketClient socketClient, Utils utils){
        return new DDKClient(socketClient, utils);
    }

}
