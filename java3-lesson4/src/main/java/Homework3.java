public class Homework3 {
    static class MFU {
        Object print1 = new Object();
        Object scan1 = new Object();

        public void printer(String file, int n) {
            synchronized (print1) {
                System.out.println("Печатаю.... " + file);
                for (int i = 0; i < n; i++) {
                    System.out.println("Страница " + i + " документа " + " file" + " печатается..");
                    try {
                        Thread.sleep(50);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
            System.out.println("Печать документа " + file + " завершена.");
        }

        public void scan(String file, int n) {
            synchronized (scan1) {
                System.out.println("Сканирую " + file);
                for (int i = 0; i < n; i++) {
                    System.out.println("Страница " + i + " документа " + file + " сканируется...");
                    try {
                        Thread.sleep(50);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
            System.out.println("Сканирование документа " + file + " завершено.");

        }

        public static void main(String[] args) {
            MFU mfu = new MFU();
            new Thread(() -> mfu.scan("A", 4)).start();
            new Thread(() -> mfu.printer("A", 4)).start();
            new Thread(() -> mfu.printer("B", 7)).start();
        }
    }
}

