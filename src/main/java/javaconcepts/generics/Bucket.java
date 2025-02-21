package javaconcepts.generics;

/**
 * The most commonly used type parameter names are:
 * E - Element (used extensively by the Java Collections Framework)
 * K - Key
 * N - Number
 * T - Type
 * V - Value
 *
 * */
public class Bucket<T,Y> {// Any Generic Type defined here can be used in class
    private T typeOfDataInBucket;
    private Y someRandomData;

    public <U extends Number> U getSomeValue(U u){//here in methods generic Char dont need to be defined at classlevel

        return u;
    }

    public <U extends String> void printTheInput(U u){//here in methods generic Char dont need to be defined at classlevel
        System.out.println(u);
    }

    public T getTypeOfDataInBucket() {
        return typeOfDataInBucket;
    }

    public void setTypeOfDataInBucket(T typeOfDataInBucket) {
        this.typeOfDataInBucket = typeOfDataInBucket;
    }

    public Y getSomeRandomData() {
        return someRandomData;
    }

    public void setSomeRandomData(Y someRandomData) {
        this.someRandomData = someRandomData;
    }
}
