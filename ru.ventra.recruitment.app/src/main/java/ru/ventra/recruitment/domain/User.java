package ru.ventra.recruitment.domain;

import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.Table;
import javax.persistence.Version;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;


@Entity
@Table(name="User")
public class User implements UserDetails {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name="id")
	private Long id;

	@NotNull
	@Size(max = 50)
	@Column(name = "login", unique = true, nullable = false, length = 50)
	private String login;

	@Column(name="password", nullable = false)
	private String password;

	@Version
	@Column(name="version")
	private Integer version;
	
	@ManyToMany
    @JoinTable(
    	name = "UserRole", 
    	joinColumns = @JoinColumn(name="userId"), 
    	inverseJoinColumns = @JoinColumn(name="roleId")
    )
    private Set<Role> roles = new HashSet<Role>();
	
	

	public Long getId() {
		return this.id;
	}

	public String getLogin() {
		return this.login;
	}

	public void setLogin(String login) {
		this.login = login;
	}

	public String getPassword() {
		return this.password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public Integer getVersion() {
		return this.version;
	}
	
	public Set<Role> getRoles() {
		return roles;
	}

	@Override
	public Collection<GrantedAuthority> getAuthorities() {
		
		Collection<GrantedAuthority> authorities = new HashSet<GrantedAuthority>();
		
		for (Role role : roles) {
			authorities.add((GrantedAuthority)role);
		}
		
		return authorities;
	}

	@Override
	public String getUsername() {
		return this.login;
	}

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
