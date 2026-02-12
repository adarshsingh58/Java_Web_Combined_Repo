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
public class B_NaiveFixWithoutBuilder {

    public static void main(String[] args) {
        /*
         * Here mandatory options are part of constructor
         * Optional options are part of setter .
         * Why this is bad?
         * Object can be half-built
         * Thread-unsafe
         * Impossible to make immutable (since setter are exposed for optional fields)
         * */
        HttpRequest request =
                new B_NaiveFixWithoutBuilder().new HttpRequest(
                        "https://api.example.com",
                        "POST"
                );
        request.setTimeout(30);
        request.setHeaders(Map.of("Auth", "token"));
        request.setFollowRedirects(true);

    }

    class HttpRequest {
        private final String url;
        private final String method;
        private int timeout;
        private boolean followRedirects;
        private Map<String, String> headers;

        public HttpRequest(
                String url,
                String method
        ) {
            this.url = url;
            this.method = method;
        }

        public void setTimeout(int timeout) {
            this.timeout = timeout;
        }

        public void setFollowRedirects(boolean followRedirects) {
            this.followRedirects = followRedirects;
        }

        public void setHeaders(Map<String, String> headers) {
            this.headers = headers;
        }
    }

}
