package Threads;

import java.util.concurrent.*;

public class ExecService {
    public static void main(String[] args) throws ExecutionException, InterruptedException, TimeoutException {
        ExecutorService ex= Executors.newFixedThreadPool(3);

        Runnable runnableTask1= () -> {
            for (int i = 0; i < 100; i++) {
                System.out.println("Hello from runnableTask1 -> " + i);
            }

        };

        Callable<String> callableTask = () -> {
            for (int i = 0; i < 100; i++) {
                System.out.println("Hello from callable task -> " + i);
            }
            return "Task's execution from callable successful";
        };

        ex.execute(runnableTask1);
        Future<String> fut=ex.submit(callableTask);

        System.out.println(fut.get(200, TimeUnit.MILLISECONDS));

        ex.shutdown();
    }
}
