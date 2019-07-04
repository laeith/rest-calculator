This is a small REST-based calculator service.

**Before running make sure that your system meets these requirements:**
- **Java 11 is present**
- **Maven is present**
- **Port 8080 is available (if not then you may change it in application.yml)**

**How to run locally:**
   - **Run Application.main()**

This will automatically run the application in an embedded mode (using embedded Tomcat). This will also start an in-memory HSQL database for development / test needs.

Production deployment can be approached in many ways, depending on the environment / system requirements e.g. we can:
- Build a JAR out of this project and run it directly in production environment.
- Build a WAR file and deploy to an external servlet container (it would be advisable to create a proper build script for that, e.g. one that would remove embedded tomcat dependencies from the WAR file)

In any case we always have to make sure that production system adheres to the requirements (available ports, open ports, connection availability etc.).

Other remarks:
  - There is no security implemented whatsoever.
  - For production type environments we probably would need to externalize configuration somehow, either to a file or some other way (port configuration, logs configuration etc.)
  - All APIs assume dot as a decimal separator
  - Basic calculator API uses BigDecimals for precision
  - e^x integral calculation is done with doubles at the moment, this severely limits precision, though the main goal here is to utilize multithreaded computation in order to speed up Riemann summing. I might rewrite it to BigDecimals in the future.
  - There are 2 test types, slow and quick ones, for very quick iterations one can run only tests tagged as 'quick'.
  - /history endpoint returns all history at the moment, might need some pagination in the future
  - At the moment, 'Calculator' uses an out of the box solution for infix expression evaluation, it uses 'EvalEx' library. In the future I might rewrite it to a custom solution using custom Tokenizer, Parser and e.g. modified Shunting Yard algorithm for evaluation.

There are 3 Spring profiles available (see SpringProfile class): 
- **development** (default): starts HSQL as an in-mem database
- **production**: reads database credentials from application.yml and assumes PostgreSQL as a production database
- **test**: uses in-mem HSQL for tests

This application bundles swagger UI, accessible at:
- /calculator/swagger-ui.html

This also includes a benchmark for IntegralCalculator, see IntegralCalculatorBenchmark for specifics. The benchmark uses JMH and a few helper classes, just for the sake of comparison.
On my machine I get the following results:

        Benchmark                                      Mode  Cnt    Score   Error  Units
        integralservicebenchmark.timeitparallel4units  avgt   10   32.508 ± 0.680  ms/op
        integralservicebenchmark.timeitparallel1unit   avgt   10  103.756 ± 1.387  ms/op
        IntegralServiceBenchmark.timeItSequential      avgt   10   89.483 ± 0.193  ms/op
