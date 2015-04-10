package at.ac.tuwien.infosys.rosebery.test.spark1;

import org.apache.spark.storage.StorageLevel;
import org.apache.spark.streaming.receiver.Receiver;

/**
 * @author Bernhard Nickel, e0925384, e0925384@student.tuwien.ac.at
 */
public class TestReceiver extends Receiver<String> {

    public TestReceiver() {
        super(StorageLevel.MEMORY_ONLY_SER());
    }

    @Override
    public void onStart() {
        new Thread()  {
            @Override
            public void run() {
                receive();
            }
        }.start();
    }

    @Override
    public void onStop() {

    }

    private void receive() {
        for (int i = 0; i < 100; i++ ) {
            String s = "test-" + System.currentTimeMillis();

            System.out.println("SparkTest1-TestReceiver:" + s);
            store(s);

            try {
                Thread.sleep(2000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}
