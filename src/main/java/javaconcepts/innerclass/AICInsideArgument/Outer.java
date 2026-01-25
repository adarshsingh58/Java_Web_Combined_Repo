package InnerClass.AICInsideArgument;

/**
 * AIC inside argument extends the class they are made from.
 *
 * @author adarsh.a.singh
 */
public class Outer {

    public static void main(String[] args) {
        /*
         * Here an anonymous Inenr class s created and this will extend Hello
         * class. Now isnide the AIC, we have overridden the hello() and made it
         * to print namaste instead of hello form the original implementatin.
         * even HelloInterface hello = new HelloInterface() is correct syntax as in case of Thread Interface.
         * In which case we say our AIC implements the HelloInterface instead of saying AIC extends the Hello class
         */

        HelloInterface originalHello = new Hello();
        /*HelloInterface hello = new Hello() {
			//AIC starts
			public void hello() {
				System.out.println("Namaste ");
			}
		};*/

        HelloInterface hello = () -> {
            System.out.println("Namaste");
        };

        originalHello.hello();
        hello.hello();
    }

}
