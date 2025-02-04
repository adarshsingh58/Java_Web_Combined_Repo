package java8.eightFeatures.lambda;

import java.io.File;
import java.io.FileFilter;

public class Main {

    FileFilter fileFilter = new FileFilter() {
        @Override
        public boolean accept(File pathname) {
            return pathname.getName().endsWith("java");
        }
    };

    //Re-writing as lambda
    FileFilter fileFilter_lambda = (File pathname) -> pathname.getName().endsWith("java");

}
