package javaconcepts.arraytype;

public class ArrayType {

    // [] Array is an object type in JAVA, its object class i '[' followed by primitive type like I for integer, D for double etc
    public static void main(String[] args) {
        int[] arr1d=new int[3];
        int[][] arr2d=new int[3][3];
        double[][] arr2_String=new double[3][3];

        System.out.println(arr1d.getClass().getName());
        System.out.println(arr2d.getClass().getName());
        System.out.println(arr2_String.getClass().getName());

    }
}
