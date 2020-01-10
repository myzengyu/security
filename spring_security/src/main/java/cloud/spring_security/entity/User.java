package cloud.spring_security.entity;

import lombok.Data;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.List;

@Data
public class User {

    private Long id;

    private String username;

    private String password;

    private String photo;

    private String cellphone;

    private List<String> authorities;

}
