package InnerClass.NestedClassesAndInterface.InterfaceInsideClass;

public class InterfaceInsideClass {

    public static void main(String[] args) {
        InterfaceInsideClass insideClass = new InterfaceInsideClass();
        InterfaceInsideClass.InnerClass class1 = insideClass.new InnerClass();
        class1.hello();
    }

    interface innerInterface {
        public void hello();
    }

    class InnerClass implements innerInterface {
        @Override
        public void hello() {
            System.out.println("Hello");
        }

    }
}
