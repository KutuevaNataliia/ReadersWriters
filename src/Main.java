import java.io.IOException;

public class Main {

    public static void main(String[] args) throws IOException {
        CountingSemaphore countingSemaphore = new CountingSemaphore();
        Thread reader1 = new Thread(new Reader(countingSemaphore));
        reader1.start();
        Thread reader2 = new Thread(new Reader(countingSemaphore));
        reader2.start();
        Thread writer = new Thread(new Writer(countingSemaphore));
        writer.start();
        Thread reader3 = new Thread(new Reader(countingSemaphore));
        reader3.start();
    }
}
