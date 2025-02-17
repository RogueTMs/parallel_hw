class Example_1 {
    public static void main(String[] args) {
        System.out.println("Trivial single-threaded deadlock:");

        try {
            Thread.currentThread().join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}

class Example_2{
    static volatile Runnable lambda = null;

    public static void main(String... args) throws Exception {
        System.out.println("Classic two-thread deadlock:");

        Thread A = new Thread(() -> { lambda.run(); });
        Thread B = new Thread(() -> {
            try {
                A.join();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        });

        lambda = () -> {
            try {
                B.join();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        };
        
        A.start(); B.start();
        A.join(); B.join();
        }
}
