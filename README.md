# RestTestClient does not trust self-signed certificates when the server uses SSL

Minimal reproducer for Spring Boot `4.2.0-M2`.

## Setup

- A simple `HelloController` exposing `GET /hello`, returning `Hello, World!`.
- The embedded server runs over HTTPS using a self-signed certificate
  (`src/main/resources/keystore.p12`), configured in `application.yaml`:

  ```yaml
  server:
    ssl:
      enabled: true
      key-store: classpath:keystore.p12
      key-store-password: changeit
      key-store-type: PKCS12
      key-alias: selfsigned
  ```

- Two otherwise identical `@SpringBootTest(webEnvironment = RANDOM_PORT)` tests call the controller:
  - `HelloControllerTestRestTemplateTests` uses `@AutoConfigureTestRestTemplate` and `TestRestTemplate`.
  - `HelloControllerRestTestClientTests` uses `@AutoConfigureRestTestClient` and `RestTestClient`.

## Expected behavior

Both tests pass. The auto-configured `TestRestTemplate` detects that the local server uses
HTTPS and trusts its self-signed certificate. The auto-configured `RestTestClient` should
behave the same way.

## Actual behavior

The `TestRestTemplate` test passes, but the `RestTestClient` test fails during the TLS handshake:

```
org.springframework.web.client.ResourceAccessException: I/O error on GET request for "https://localhost:56193/hello":
(certificate_unknown) PKIX path building failed: sun.security.provider.certpath.SunCertPathBuilderException:
unable to find valid certification path to requested target
```

## Reproduce

```shell
./mvnw test
```

Result:

```
HelloControllerTestRestTemplateTests.helloOverSsl   PASS
HelloControllerRestTestClientTests.helloOverSsl     ERROR (PKIX path building failed)
```
