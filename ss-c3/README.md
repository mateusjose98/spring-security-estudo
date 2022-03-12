# Aula 03 de Spring Security - UserDetailsManager


Neste momento, usamos o JdbcUserDetailsManager que é um contrato que segue as restrições do JDBC
que tem a seguinte assinatura

```
public class JdbcUserDetailsManager extends JdbcDaoImpl implements UserDetailsManager, GroupManager { ... }
public interface UserDetailsManager extends UserDetailsService {
```
veja que o UserDetailsManager possui mais métodos que conseguem completamente gerenciar o usuário a ser usado.
Nesta aula, apredemos usar o PasswordEncoder BCrypt que trabalha com a lógica à prova de roubo de hash, ou seja, não conseguimos reverter um hash bcrypt e achar  a senha.