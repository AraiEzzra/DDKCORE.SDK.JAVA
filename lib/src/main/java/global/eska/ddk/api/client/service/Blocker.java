package global.eska.ddk.api.client.service;

import org.springframework.stereotype.Component;

import java.util.concurrent.CountDownLatch;

@Component
public class Blocker {

    private CountDownLatch doneSignal;

    public void lock() {
        if (doneSignal == null || doneSignal.getCount() == 0){
            generate();
        }
        System.out.println("LOCK!!!!! "+ + doneSignal.getCount());
        try {
            doneSignal.await();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public void unlock() {
        doneSignal.countDown();
        System.out.println("UNLOCK!!!!! "+ doneSignal.getCount());
    }

    public CountDownLatch getDoneSignal() {
        return doneSignal;
    }

    public void generate() {
        doneSignal = new CountDownLatch(1);
    }
}
