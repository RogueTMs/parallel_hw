class Scenario1 {
    public static void main(String[] args) {
        System.out.println("=== Scenario1: A starts B, B throws an exception, A joins. ===");

        // Thread A is the main thread here.
        Thread B = new Thread(() -> {
            System.out.println("[B] Starting, about to throw RuntimeException.");
            throw new RuntimeException("Uncaught exception in B");
        });

        B.start();

        try {
            B.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        System.out.println("[A] B has terminated with exception. join returned. A continues...");
    }
}


class Scenario2 {
    public static void main(String[] args) {
        System.out.println("=== Scenario2: C joins B AFTER B's exception. ===");

        Thread B = new Thread(() -> {
            System.out.println("[B] Starting, will sleep briefly then throw...");
            try {
                Thread.sleep(2000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            throw new RuntimeException("Uncaught exception in B");
        });

        Thread C = new Thread(() -> {
            System.out.println("[C] Attempting to join B...");
            try {
                long startTime = System.currentTimeMillis();
                B.join(); 
                long endTime = System.currentTimeMillis();
                System.out.println("[C] B was already terminated, so join returned immediately.");
                System.out.println("[C] Time taken to join: " + (endTime - startTime) + "ms.");
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        });

        B.start();

        try {
            B.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println("[A] B is done.");

        C.start();

        try {
            C.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        System.out.println("[A] C also finished.");
    }
}


class Scenario3 {
    public static void main(String[] args) {
        System.out.println("=== Scenario3: D joins A. ===");

        Thread A = Thread.currentThread();

        Thread B = new Thread(() -> {
            System.out.println("[B] Starting and will throw a RuntimeException.");
            throw new RuntimeException("Uncaught exception in B");
        });

        Thread D = new Thread(() -> {
            System.out.println("[D] Attempting to join A...");
            try {
                A.join();
                System.out.println("[D] A has finished, so D is unblocked.");
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        });

        B.start();

        try {
            B.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println("[A] Done waiting for B.");

        D.start();

        try {
            Thread.sleep(500);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        System.out.println("[A] Main thread about to finish. Once this ends, D's join(A) will unblock.");
    }
}

