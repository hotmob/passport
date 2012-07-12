package com.ammob.passport.model;

import org.apache.commons.lang.builder.ToStringBuilder;
import org.apache.commons.lang.builder.ToStringStyle;
import org.codehaus.jackson.annotate.JsonIgnoreProperties;
import org.compass.annotations.Searchable;
import org.compass.annotations.SearchableComponent;
import org.compass.annotations.SearchableId;
import org.compass.annotations.SearchableProperty;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.ldap.userdetails.LdapUserDetails;
import org.springframework.util.StringUtils;

import com.ammob.passport.Constants;

import javax.persistence.Column;
import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.Table;
import javax.persistence.Transient;
import javax.persistence.Version;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;
import java.io.Serializable;
import java.util.HashSet;
import java.util.LinkedHashSet;
import java.util.Set;

/**
 * This class represents the basic "user" object that allows for authentication and user management.  It implements Acegi Security's UserDetails interface.
 */
@Entity
@Table(name = "users")
@Searchable
@XmlRootElement
@JsonIgnoreProperties(value={ "password" }) 
public class User extends BaseObject implements Serializable, LdapUserDetails {
	
    private static final long serialVersionUID = 3832626162173359411L;
 
    @Id
    @SearchableId
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    @SearchableProperty
    @Column(nullable = false, length = 50, unique = true)
    private String username;                    	// required
    @Column(nullable = false)
    @XmlTransient
    private String password;                  		// required
    @SearchableProperty
    @Column(name = "first_name", nullable = false, length = 50)
    private String firstName;                   		// required
    @SearchableProperty
    @Column(name = "last_name", nullable = false, length = 50)
    private String lastName;                    		// required
    @SearchableProperty
    @Column(nullable = false, unique = true)
    private String email;                       	    // required; unique
    @SearchableProperty
    @Column(name = "phone_number")
    private String phoneNumber;
    @SearchableProperty
    private String website;
    @Embedded
    @SearchableComponent
    private Address address = new Address();
    @Version
    private Integer version;
    @JoinTable(
            name = "user_role",
            joinColumns = { @JoinColumn(name = "user_id") },
            inverseJoinColumns = @JoinColumn(name = "role_id"))
    @ManyToMany(fetch = FetchType.EAGER)
    private Set<Role> roles = new HashSet<Role>();
    @Column(name = "account_enabled")
    private boolean enabled;
    @Column(name = "account_expired", nullable = false)
    private boolean accountExpired;
    @Column(name = "account_locked", nullable = false)
    private boolean accountLocked;
    @Column(name = "credentials_expired", nullable = false)
    private boolean credentialsExpired;
    @Transient 
    @XmlTransient
    private String confirmPassword;
    @Column(name = "password_hint")
    @XmlTransient
    private String passwordHint;
    
	@Transient
	private String displayName;                   // 昵称, 显示名
	@Transient
	private String description;                 		// 用户描述
	@Transient
	private String regTime;                      	// 注册时间
	@Transient
	private String uuid;                           		// UUID
	@Transient
	private String avataUrl;
	@Transient
	private Set<String> state = new HashSet<String>(); // 状态, 1: 邮箱已验证
    // PPolicy data
	@Transient
    private int timeBeforeExpiration = Integer.MAX_VALUE;

	@Transient
    private int graceLoginsRemaining = Integer.MAX_VALUE;

	/**
     * Default constructor - creates a new instance with no values set.
     */
    public User() {}

    /**
     * Create a new instance and set the username.
     *
     * @param username login name for user.
     */
    public User(final String username) {
        this.username = username;
    }

    public Long getId() {
        return id;
    }

    public String getUsername() {
        return username;
    }

    public String getPassword() {
    	if(password == null)
    		return "";
		return new String(password);
    }

    public String getConfirmPassword() {
        return confirmPassword;
    }

    public String getPasswordHint() {
        return passwordHint;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public String getEmail() {
        return email;
    }
    
    public String getPhoneNumber() {
        return phoneNumber;
    }

    public String getWebsite() {
        return website;
    }

    public Address getAddress() {
        return address;
    }

    public Set<Role> getRoles() {
        return roles;
    }

    /**
     * Adds a role for the user
     *
     * @param role the fully instantiated role
     */
    public void addRole(Role role) {
        getRoles().add(role);
    }

    /**
     * @return GrantedAuthority[] an array of roles.
     * @see org.springframework.security.core.userdetails.UserDetails#getAuthorities()
     */
    public Set<GrantedAuthority> getAuthorities() {
        Set<GrantedAuthority> authorities = new LinkedHashSet<GrantedAuthority>();
        authorities.addAll(roles);
        return authorities;
    }

    public Integer getVersion() {
        return version;
    }

    public boolean isEnabled() {
        return enabled;
    }

    public boolean isAccountExpired() {
        return accountExpired;
    }

    /**
     * @see org.springframework.security.core.userdetails.UserDetails#isAccountNonExpired()
     * @return true if account is still active
     */
    public boolean isAccountNonExpired() {
        return !isAccountExpired();
    }

    public boolean isAccountLocked() {
        return accountLocked;
    }

    /**
     * @see org.springframework.security.core.userdetails.UserDetails#isAccountNonLocked()
     * @return false if account is locked
     */
    public boolean isAccountNonLocked() {
        return !isAccountLocked();
    }

    public boolean isCredentialsExpired() {
        return credentialsExpired;
    }

    /**
     * @see org.springframework.security.core.userdetails.UserDetails#isCredentialsNonExpired()
     * @return true if credentials haven't expired
     */
    public boolean isCredentialsNonExpired() {
        return !credentialsExpired;
    }

    public void setId(Long id) {
        this.id = id;
    }
	
    public void setUsername(String username) {
        this.username = username;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void setConfirmPassword(String confirmPassword) {
        this.confirmPassword = confirmPassword;
    }

    public void setPasswordHint(String passwordHint) {
        this.passwordHint = passwordHint;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public void setWebsite(String website) {
        this.website = website;
    }

    public void setAddress(Address address) {
        this.address = address;
    }

    public void setRoles(Set<Role> roles) {
        this.roles = roles;
    }
    
    public void setVersion(Integer version) {
        this.version = version;
    }

    public void setEnabled(boolean enabled) {
        this.enabled = enabled;
    }

    public void setAccountExpired(boolean accountExpired) {
        this.accountExpired = accountExpired;
    }

    public void setAccountLocked(boolean accountLocked) {
        this.accountLocked = accountLocked;
    }

    public void setCredentialsExpired(boolean credentialsExpired) {
        this.credentialsExpired = credentialsExpired;
    }

    /**
     * {@inheritDoc}
     */
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof User)) {
            return false;
        }

        final User user = (User) o;

        return !(username != null ? !username.equals(user.getUsername()) : user.getUsername() != null);

    }

    /**
     * {@inheritDoc}
     */
    public int hashCode() {
        return (username != null ? username.hashCode() : 0);
    }

    /**
     * {@inheritDoc}
     */
    public String toString() {
        ToStringBuilder sb = new ToStringBuilder(this, ToStringStyle.DEFAULT_STYLE)
                .append("username", this.username)
                .append("enabled", this.enabled)
                .append("accountExpired", this.accountExpired)
                .append("credentialsExpired", this.credentialsExpired)
                .append("accountLocked", this.accountLocked);

        if (roles != null) {
            sb.append("Granted Authorities: ");

            int i = 0;
            for (Role role : roles) {
                if (i > 0) {
                    sb.append(", ");
                }
                sb.append(role.toString());
                i++;
            }
        } else {
            sb.append("No Granted Authorities");
        }
        return sb.toString();
    }
	
	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}
	
	public String getDisplayName() {
		if(!StringUtils.hasText(displayName))
			return firstName + " " + lastName;
		return displayName;
	}
	
	public void setDisplayName(String displayName) {
		this.displayName = displayName;
	}
	
	public String getState() {
		return state.toString().substring(1, state.toString().length() - 1);
	}
	
	public void setState(String state) {
		if(StringUtils.hasText(state)) {
			if(state.contains(",")){
				String [] states = state.split(",");
				for(String strTmp : states){
					if(StringUtils.hasText(strTmp)) {
						this.state.add(strTmp.trim());
					}
				}
			} else {
				this.state.add(state.trim());
			}
		}
	}
	
	public String getRegTime() {
		return regTime;
	}

	public void setRegTime(String regTime) {
		this.regTime = regTime;
	}
	
	public String getUuid() {
		return uuid;
	}
	
	public void setUuid(String uuid) {
		this.uuid = uuid;
	}
	
	public String getAvataUrl() {
		if(!StringUtils.hasText(this.avataUrl)) {
			if(StringUtils.hasText(this.uuid)){
				this.avataUrl =  Constants.USER_DEFAULT_AVATA_URL_PREFIX + this.uuid.replaceAll("-", "/") + Constants.USER_DEFAULT_AVATA_URL_SUFFIX;
			}
		}
		return this.avataUrl;
	}

	public void setAvataUrl(String avataUrl) {
		this.avataUrl = avataUrl;
	}

	public void setTimeBeforeExpiration(int timeBeforeExpiration) {
		this.timeBeforeExpiration = timeBeforeExpiration;
	}
	
	public int getTimeBeforeExpiration() {
		return timeBeforeExpiration;
	}

	public void setGraceLoginsRemaining(int graceLoginsRemaining) {
		this.graceLoginsRemaining = graceLoginsRemaining;
	}
	
    public int getGraceLoginsRemaining() {
		return graceLoginsRemaining;
	}

	@Override
	public String getDn() {
		return null;
	}
}
