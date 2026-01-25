package javaconcepts.eightFeatures.StringandIOchanges;

import java.util.StringJoiner;

/*
* before jdk 7, to concat string we used stringbuilder or stringbuffer as using string directly with
* "+" concat operator was inefficient because multiple intermediate objects were formed.
* On Jdk 7, "+" concat operator by default internally used stringbuilder so that was a great change.
*
* On Jdk8,things are even easier as there is a new API StringJoiner which takes in the seperator/delimiter
* of choice and joins string.
*
* */
public class StringJoinerDemo {

    public static void main(String[] args) {
        StringJoiner stringJoiner=new StringJoiner(",");
        stringJoiner.add("Adarsh").add("Prakhar").add("Piyush");
        System.out.println(stringJoiner);
//Output: Adarsh,Prakhar,Piyush

        /*We can also provide prefix and postfix in coinstructor*/
        StringJoiner stringJoinerPrePost=new StringJoiner(",","{","}");
        stringJoinerPrePost.add("Adarsh").add("Prakhar").add("Piyush");
        System.out.println(stringJoinerPrePost);
        //output: {Adarsh,Prakhar,Piyush}
    }
}
