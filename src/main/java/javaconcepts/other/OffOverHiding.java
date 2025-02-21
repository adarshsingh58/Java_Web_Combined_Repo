package javaconcepts.other;

public class OffOverHiding {

    public static void main(String[] args) {

    }

    class Super{
        public void foo(){
            System.out.println("Super foo");
        }
    }

    //Not allowed
  /*  class Sub extends Super{
        public static void foo(){
            System.out.println("Sub foo");
        }
    }*/
}
