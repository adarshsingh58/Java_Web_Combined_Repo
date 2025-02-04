package java8.eightFeatures.functionalInterface;

public interface inter2 extends FuncInt {

    public void hi(String c);

    default String getName(){
        return "inter2";
    }

    default int getNumberOfAuthors(){
        return 1;
    }

}
