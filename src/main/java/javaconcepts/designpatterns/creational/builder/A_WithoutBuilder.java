package javaconcepts.designpatterns.creational.builder;

import java.util.Map;

/*
 * If adding a new object type forces changes all over your code, think “creational pattern.”
 * Builder: Object construction is complex with many optional parts
 * eg: Building an HTTP request with many headers/options
 *
 * Real-life scenario: Creating an HTTP request
 * Why this is realistic: Many optional fields, Some mandatory, Order matters, Object should be immutable after creation
 *
 * */
public class A_WithoutBuilder {

    public static void main(String[] args) {
        /*
         * What goes wrong
         * What does 30 mean?
         * Easy to swap arguments
         * Adding one field breaks all callers
         * Impossible to make fields optional cleanly
         * 
         * This is called Telescoping Constructor problem.
         * */
        HttpRequest request =
                new A_WithoutBuilder().new HttpRequest(
                        "https://api.example.com",
                        "POST",
                        30,
                        true,
                        Map.of("Auth", "token")
                );

    }

    class HttpRequest {
        private final String url;
        private final String method;
        private final int timeout;
        private final boolean followRedirects;
        private final Map<String, String> headers;

        public HttpRequest(
                String url,
                String method,
                int timeout,
                boolean followRedirects,
                Map<String, String> headers
        ) {
            this.url = url;
            this.method = method;
            this.timeout = timeout;
            this.followRedirects = followRedirects;
            this.headers = headers;
        }
    }

}
