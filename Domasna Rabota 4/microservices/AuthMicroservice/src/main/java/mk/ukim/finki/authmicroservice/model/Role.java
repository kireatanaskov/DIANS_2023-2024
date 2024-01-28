package mk.ukim.finki.authmicroservice.model;


import org.springframework.security.core.GrantedAuthority;

public enum Role implements GrantedAuthority {
    ROLE_ADMIN, ROLE_USER, ROLE_NON;

    @Override
    public String getAuthority() {
        return name();
    }
}
