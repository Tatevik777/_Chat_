

    public class Homework1 {
        static volatile char letter='A';
        static Object l=new Object();

        static class WaitNotify implements Runnable {
            private final char currentLetter;
            private final char nextLetter;

            public WaitNotify(char currentLetter, char nextLetter) {
                this.currentLetter = currentLetter;
                this.nextLetter = nextLetter;
            }

            @Override
            public void run() {
                for (int i = 0; i < 5; i++) {
                    synchronized (l) {
                        try {
                            while(letter!=currentLetter)
                                l.wait();
                            System.out.println(currentLetter);
                            letter=nextLetter;
                            l.notifyAll();
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }

                    }}}

            public static void main(String[] args) {
                System.out.println("Буквы, не подведите! ");

                new Thread(new WaitNotify('A','B')).start();
                new Thread (new WaitNotify('B','C')).start();
                new Thread(new WaitNotify('C', 'A')).start();
            }
        }}

