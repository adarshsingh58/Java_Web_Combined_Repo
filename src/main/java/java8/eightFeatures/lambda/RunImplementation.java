package java8.eightFeatures.lambda;

public class RunImplementation {

    public Integer a;
    public static void main(String[] args) {
        Runnable runnable = runnableUsingAnonymousClass();
        Runnable runnableLambda = runnableUsingAnonymousClass();

        Thread thread = new Thread(runnableLambda);
        thread.start();
    }

    private static Runnable runnableUsingAnonymousClass() {
        return new Runnable() {
            @Override
            public void run() {
                System.out.println("Thread running");
            }
        };
    }

    private static Runnable runnableUsingLambda() {
        Runnable runnable = () -> System.out.println("Thread running");
        return runnable;
    }
}
