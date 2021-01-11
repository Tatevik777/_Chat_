import java.io.FileNotFoundException;
import java.io.PrintWriter;

class Homework2 {
    public static void main(String[] args) {

        class TextWrite implements Runnable {
            PrintWriter printWriter = null;
            private int p;
            private int w = 10;

            public TextWrite(PrintWriter printWriter, int p) {
                this.printWriter = printWriter;
                this.p = p;
            }

            @Override
            public void run() {
                for (int i = 0; i < w; i++) {
                    try {
                        Thread.sleep(20);
                        printWriter.print(p);
                        printWriter.flush();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
        }
        PrintWriter printWriter = null;
        try {
            printWriter = new PrintWriter("Test1.txt");
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        Thread t1 = new Thread(new TextWrite(printWriter, 5));
        Thread t2 = new Thread(new TextWrite(printWriter, 7));
        Thread t3 = new Thread(new TextWrite(printWriter, 9));

        try {
            t1.start();
            t1.join();
            t2.start();
            t2.join();
            t3.start();
            t3.join();

        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        printWriter.close();
    }
}

