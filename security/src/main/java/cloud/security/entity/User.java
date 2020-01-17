package cloud.security.entity;

import lombok.Data;

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
