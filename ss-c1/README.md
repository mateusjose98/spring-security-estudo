# Estudo sobre o módulo Spring Security

## Fluxo básico de funcionamento 
![img.png](img.png)

Ao adicionar a dependencia do Spring Security, já estamos, de certa forma, protegidos.
Por padrão, o Spring configura uma autenticação HttpBasic baseada em username e password. O username é "user" e a password é um UUID gerado no console da aplicação em tempo de execução.
Se no exemplo, temos um projeto apenas com as dependências WEB e SECURITY do spring, ao chamarmos qualquer endpoint, obteremos um status HTTP 401.

![img_1.png](img_1.png)

Isso acontece pois a aplicação está com segurança baseada em HttpBasi. Mas se passarmos nos Headers o username e senha corretos, conseguimos acesso:

![img_2.png](img_2.png)

Navegando no POSTMAN, na aba Headers, vemos que é enviada uma string da forma "Basic base64('username:password')" que garantirá o acesso.

O filtro neste caso é o BasicAuthenticationFilter que, segundo a documentação:

<blockquote>

Processes a HTTP request's BASIC authorization headers, putting the result into the SecurityContextHolder.

In summary, this filter is responsible for processing any request that has a HTTP request header of Authorization with an authentication scheme of Basic and a Base64-encoded username:password token. For example, to authenticate user "Aladdin" with password "open sesame" the following header would be presented:

 Authorization: Basic QWxhZGRpbjpvcGVuIHNlc2FtZQ==
 
This filter can be used to provide BASIC authentication services to both remoting protocol clients (such as Hessian and SOAP) as well as standard user agents (such as Internet Explorer and Netscape).

If authentication is successful, the resulting Authentication object will be placed into the SecurityContextHolder.

If authentication fails and ignoreFailure is false (the default), an AuthenticationEntryPoint implementation is called (unless the ignoreFailure property is set to true). Usually this should be BasicAuthenticationEntryPoint, which will prompt the user to authenticate again via BASIC authentication.

Basic authentication is an attractive protocol because it is simple and widely deployed. However, it still transmits a password in clear text and as such is undesirable in many situations. Digest authentication is also provided by Spring Security and should be used instead of Basic authentication wherever possible. See DigestAuthenticationFilter.

</blockquote>

Mas, e se quisermos configurar a nossa maneira? Precisamos sobrescrever a configuração padrão através de uma classe de configuração.
Criamos a classe ProjectConfig da seguinte maneira:

```
@Configuration
public class ProjectConfig {

    @Bean
    public UserDetailsService userDetailsService() {
        var uds = new InMemoryUserDetailsManager();
        var u1 = User.withUsername("mateus")
                                    .password("{noop}123")
                                    .authorities("read")
                            .build();
        uds.createUser(u1);
        return uds;

    }

```

Aqui, sobrescrevemos o UserDetailsService padrão e usando uma implementação em memória. 
Como não estamos ainda não há um PasswordEncoder provido por nós, precisamos adicionar "{noop}" na senha do usuário, isso diz ao Spring que não estamos usando um PasswordEncoder.
Outra alternativa é colocar um PasswordEncoder da seguinte forma (depreciada, não use!):

```

  @Bean
    public PasswordEncoder passwordEncoder() {
        return NoOpPasswordEncoder.getInstance();
    }

```
