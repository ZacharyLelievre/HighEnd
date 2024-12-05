# Getting Started

### Reference Documentation
For further reference, please consider the following sections:

* [Official Gradle documentation](https://docs.gradle.org)
* [Spring Boot Gradle Plugin Reference Guide](https://docs.spring.io/spring-boot/3.3.5/gradle-plugin)
* [Create an OCI image](https://docs.spring.io/spring-boot/3.3.5/gradle-plugin/packaging-oci-image.html)
* [Spring HATEOAS](https://docs.spring.io/spring-boot/3.3.5/reference/web/spring-hateoas.html)
* [OAuth2 Authorization Server](https://docs.spring.io/spring-boot/3.3.5/reference/web/spring-security.html#web.security.oauth2.authorization-server)
* [OAuth2 Client](https://docs.spring.io/spring-boot/3.3.5/reference/web/spring-security.html#web.security.oauth2.client)
* [Okta Spring Boot documentation](https://github.com/okta/okta-spring-boot#readme)
* [Spring Security](https://docs.spring.io/spring-boot/3.3.5/reference/web/spring-security.html)
* [Thymeleaf](https://docs.spring.io/spring-boot/3.3.5/reference/web/servlet.html#web.servlet.spring-mvc.template-engines)
* [Spring Web](https://docs.spring.io/spring-boot/3.3.5/reference/web/servlet.html)
* [Spring Web Services](https://docs.spring.io/spring-boot/3.3.5/reference/io/webservices.html)
* [Spring Reactive Web](https://docs.spring.io/spring-boot/3.3.5/reference/web/reactive.html)

### Guides
The following guides illustrate how to use some features concretely:

* [Building a Hypermedia-Driven RESTful Web Service](https://spring.io/guides/gs/rest-hateoas/)
* [Accessing data with MySQL](https://spring.io/guides/gs/accessing-data-mysql/)
* [Okta-Hosted Login Page Example](https://github.com/okta/samples-java-spring/tree/master/okta-hosted-login)
* [Custom Login Page Example](https://github.com/okta/samples-java-spring/tree/master/custom-login)
* [Okta Spring Security Resource Server Example](https://github.com/okta/samples-java-spring/tree/master/resource-server)
* [Securing a Web Application](https://spring.io/guides/gs/securing-web/)
* [Spring Boot and OAuth2](https://spring.io/guides/tutorials/spring-boot-oauth2/)
* [Authenticating a User with LDAP](https://spring.io/guides/gs/authenticating-ldap/)
* [Handling Form Submission](https://spring.io/guides/gs/handling-form-submission/)
* [Building a RESTful Web Service](https://spring.io/guides/gs/rest-service/)
* [Serving Web Content with Spring MVC](https://spring.io/guides/gs/serving-web-content/)
* [Building REST services with Spring](https://spring.io/guides/tutorials/rest/)
* [Producing a SOAP web service](https://spring.io/guides/gs/producing-web-service/)
* [Building a Reactive RESTful Web Service](https://spring.io/guides/gs/reactive-rest-service/)

### Additional Links
These additional references should also help you:

* [Gradle Build Scans – insights for your project's build](https://scans.gradle.com#gradle)

## OAuth 2.0 and OIDC with Okta

If you don't have a free Okta developer account, you can create one with [the Okta CLI](https://cli.okta.com):

```bash
$ okta register
```

Then, register your Spring Boot app on Okta using:

```bash
$ okta apps create
```

Select **Web** > **Okta Spring Boot Starter** and accept the default redirect URIs.

