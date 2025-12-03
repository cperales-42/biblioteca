package com.cperales.biblioteca;

import com.cperales.biblioteca.modelo.Usuario;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.Collections;

/**
 * Clase que adapta nuestro Usuario para Spring Security.
 * Implementa UserDetails.
 */
public class UsuarioPrincipal implements UserDetails {

    private final Usuario usuario;

    public UsuarioPrincipal(Usuario usuario) {
        this.usuario = usuario;
    }

    /**
     * Permite acceder al usuario original
     */
    public Usuario getUsuario() {
        return usuario;
    }

    /**
     * Devuelve los roles del usuario como GrantedAuthority
     */
    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        String rolConPrefijo = "ROLE_" + usuario.getRol();
        SimpleGrantedAuthority autoridad = new SimpleGrantedAuthority(rolConPrefijo);

        return Collections.singletonList(autoridad);
    }

    /**
     * Devuelve la contraseña del usuario
     */
    @Override
    public String getPassword() {
        return usuario.getPassword();
    }

    /**
     * Devuelve el nombre de usuario para autenticación (email)
     */
    @Override
    public String getUsername() {
        return usuario.getEmail();
    }

    /**
     * Estos métodos indican que la cuenta siempre está activa, desbloqueada y válida.
     * Puedes personalizar si algún día quieres implementar bloqueo o expiración.
     */
    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }
}