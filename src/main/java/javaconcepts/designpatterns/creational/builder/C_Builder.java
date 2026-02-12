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
public class C_Builder {

    public static void main(String[] args) {

        /*
         * here req fields are final in builder, optional can be set
         * via builder.
         * Now,Object is always valid, Required vs optional is explicit,
         * Readable, self-documenting, Immutable after creation,
         * Easy to extend without breaking callers
         *
         * Builder is NOT just about “many args”
         * Use Builder when:
         * ✔ Many optional fields
         * ✔ Object must be immutable
         * ✔ Construction is multi-step
         * ✔ You want readable creation
         * ✔ Invariants must hold
         * Do NOT use Builder when:
         * ❌ Object has 2–3 fields
         * ❌ Construction is trivial
         * ❌ Performance is ultra-critical
         * ❌ DTOs / simple data holders
         * */
        HttpRequestBuilder reqBuilder =
                new C_Builder().new HttpRequestBuilder("www.jnjk.com", "POST");
        reqBuilder.timeout(30);
        reqBuilder.followRedirects(true);
        reqBuilder.headers(Map.of("Auth", "token"));
        HttpRequest request = reqBuilder.build();

    }

    class HttpRequestBuilder {
        private A_WithoutBuilder.HttpRequest httpRequest;
        private final String url;// required
        private final String method;// required
        private int timeout;
        private boolean followRedirects;
        private Map<String, String> headers;

        public HttpRequestBuilder(String url,
                                  String method) {
            this.url = url;
            this.method = method;
        }

        public HttpRequestBuilder timeout(int timeout) {
            this.timeout = timeout;
            return this;
        }

        public HttpRequestBuilder followRedirects(boolean followRedirects) {
            this.followRedirects = followRedirects;
            return this;
        }

        public HttpRequestBuilder headers(Map<String, String> headers) {
            this.headers = headers;
            return this;
        }

        public HttpRequest build() {
            return new HttpRequest(url, method, timeout, followRedirects, headers);
        }
    }

    /*
     * No setters, all vars are final. so object is immutable once created
     * */
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
