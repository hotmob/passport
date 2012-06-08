package com.ammob.passport.model;

import org.apache.commons.lang.builder.ToStringBuilder;
import org.apache.commons.lang.builder.ToStringStyle;
import org.compass.annotations.Searchable;
import org.compass.annotations.SearchableComponent;
import org.compass.annotations.SearchableId;
import org.compass.annotations.SearchableProperty;
import org.springframework.ldap.core.DistinguishedName;
import org.springframework.ldap.odm.annotations.Attribute;
import org.springframework.ldap.odm.annotations.Entry;
import org.springframework.ldap.odm.annotations.Attribute.Type;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.util.StringUtils;

import com.ammob.passport.Constants;

import javax.naming.Name;
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
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;

/**
 * This class represents the basic "user" object in AppFuse that allows for authentication
 * and user management.  It implements Acegi Security's UserDetails interface.
 *
 * @author <a href="mailto:matt@raibledesigns.com">Matt Raible</a>
 *         Updated by Dan Kibler (dan@getrolling.com)
 *         Extended to implement Acegi UserDetails interface
 *         by David Carter david@carter.net
 */
@Entity
@Table(name = "app_user")
@Searchable
@XmlRootElement
@Entry(objectClasses={"userDetails", "inetOrgPerson", "top"})
public class User extends BaseObject implements Serializable, UserDetails {
	
    private static final long serialVersionUID = 3832626162173359411L;
    @org.springframework.ldap.odm.annotations.Transient
    private Long id;
    
    @Attribute(name="cn", syntax="1.3.6.1.4.1.1466.115.121.1.15")
    private String username;                    	// required
    @Attribute(name="userPassword", syntax="1.3.6.1.4.1.1466.115.121.1.40", type=Type.BINARY)
    private byte[] password;                  		// required
    @Attribute(name="sn", syntax="1.3.6.1.4.1.1466.115.121.1.15")
    private String firstName;                   		// required
    @Attribute(name="givenName", syntax="1.3.6.1.4.1.1466.115.121.1.15")
    private String lastName;                    		// required
    @Attribute(name="mail", syntax="1.3.6.1.4.1.1466.115.121.1.26")
    private String email;                       	    // required; unique
    @Attribute(name="telephoneNumber", syntax="1.3.6.1.4.1.1466.115.121.1.50")
    private String phoneNumber;
    @org.springframework.ldap.odm.annotations.Transient
    private String website;
    @org.springframework.ldap.odm.annotations.Transient
    private Address address = new Address();
    @org.springframework.ldap.odm.annotations.Transient
    private Integer version;
    @org.springframework.ldap.odm.annotations.Transient
    private Set<Role> roles = new HashSet<Role>();
    @org.springframework.ldap.odm.annotations.Transient
    private boolean enabled;
    @org.springframework.ldap.odm.annotations.Transient
    private boolean accountExpired;
    @org.springframework.ldap.odm.annotations.Transient
    private boolean accountLocked;
    @org.springframework.ldap.odm.annotations.Transient
    private boolean credentialsExpired;
    @org.springframework.ldap.odm.annotations.Transient
    private String confirmPassword;
    @org.springframework.ldap.odm.annotations.Transient
    private String passwordHint;
    
    @org.springframework.ldap.odm.annotations.Id
	private Name dn;                  					// DN
    @org.springframework.ldap.odm.annotations.Transient
	private String displayName;                   // 昵称, 显示名
	@Attribute(name="description", syntax="1.3.6.1.4.1.1466.115.121.1.15")
	private String description;                 		// 用户描述
	@org.springframework.ldap.odm.annotations.Transient
	private String regTime;                      	// 注册时间
	@org.springframework.ldap.odm.annotations.Transient
	private String uuid;                           		// UUID
	@org.springframework.ldap.odm.annotations.Transient
	private String avataUrl;
	@org.springframework.ldap.odm.annotations.Transient
	private Set<String> state = new HashSet<String>(); // 状态, 1: 邮箱已验证
	@Attribute(name="objectClass", syntax="1.3.6.1.4.1.1466.115.121.1.38")
	private List<String> objectClass=new ArrayList<String>();
	@Attribute(name="seeAlso", syntax="1.3.6.1.4.1.1466.115.121.1.12")
	private List<String> seeAlso=new ArrayList<String>();
	
    /**
     * Default constructor - creates a new instance with no values set.
     */
    public User() {
    }

    /**
     * Create a new instance and set the username.
     *
     * @param username login name for user.
     */
    public User(final String username) {
        this.username = username;
    }

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @SearchableId
    public Long getId() {
        return id;
    }

    @Column(nullable = false, length = 50, unique = true)
    @SearchableProperty
    public String getUsername() {
        return username;
    }

    @Column(nullable = false)
    @XmlTransient
    public String getPassword() {
    	if(password == null)
    		return "";
		return new String(password);
    }

    @Transient @XmlTransient
    public String getConfirmPassword() {
        return confirmPassword;
    }

    @Column(name = "password_hint")
    @XmlTransient
    public String getPasswordHint() {
        return passwordHint;
    }

    @Column(name = "first_name", nullable = false, length = 50)
    @SearchableProperty
    public String getFirstName() {
        return firstName;
    }

    @Column(name = "last_name", nullable = false, length = 50)
    @SearchableProperty
    public String getLastName() {
        return lastName;
    }

    @Column(nullable = false, unique = true)
    @SearchableProperty
    public String getEmail() {
        return email;
    }

    @Column(name = "phone_number")
    @SearchableProperty
    public String getPhoneNumber() {
        return phoneNumber;
    }

    @SearchableProperty
    public String getWebsite() {
        return website;
    }

    /**
     * Returns the full name.
     *
     * @return firstName + ' ' + lastName
     */
    @Transient
    public String getFullName() {
        return firstName + ' ' + lastName;
    }

    @Embedded
    @SearchableComponent
    public Address getAddress() {
        return address;
    }

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(
            name = "user_role",
            joinColumns = { @JoinColumn(name = "user_id") },
            inverseJoinColumns = @JoinColumn(name = "role_id")
    )
    public Set<Role> getRoles() {
        return roles;
    }

    /**
     * Convert user roles to LabelValue objects for convenience.
     *
     * @return a list of LabelValue objects with role information
     */
    @Transient
    public List<LabelValue> getRoleList() {
        List<LabelValue> userRoles = new ArrayList<LabelValue>();

        if (this.roles != null) {
            for (Role role : roles) {
                // convert the user's roles to LabelValue Objects
                userRoles.add(new LabelValue(role.getName(), role.getName()));
            }
        }

        return userRoles;
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
    @Transient
    public Set<GrantedAuthority> getAuthorities() {
        Set<GrantedAuthority> authorities = new LinkedHashSet<GrantedAuthority>();
        authorities.addAll(roles);
        return authorities;
    }

    @Version
    public Integer getVersion() {
        return version;
    }

    @Column(name = "account_enabled")
    public boolean isEnabled() {
        return enabled;
    }

    @Column(name = "account_expired", nullable = false)
    public boolean isAccountExpired() {
        return accountExpired;
    }

    /**
     * @see org.springframework.security.core.userdetails.UserDetails#isAccountNonExpired()
     * @return true if account is still active
     */
    @Transient
    public boolean isAccountNonExpired() {
        return !isAccountExpired();
    }

    @Column(name = "account_locked", nullable = false)
    public boolean isAccountLocked() {
        return accountLocked;
    }

    /**
     * @see org.springframework.security.core.userdetails.UserDetails#isAccountNonLocked()
     * @return false if account is locked
     */
    @Transient
    public boolean isAccountNonLocked() {
        return !isAccountLocked();
    }

    @Column(name = "credentials_expired", nullable = false)
    public boolean isCredentialsExpired() {
        return credentialsExpired;
    }

    /**
     * @see org.springframework.security.core.userdetails.UserDetails#isCredentialsNonExpired()
     * @return true if credentials haven't expired
     */
    @Transient
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
        this.password = password.getBytes();
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
	
	@Transient
	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}
	
	@Transient
	public String getDisplayName() {
		return displayName;
	}
	
	public void setDisplayName(String displayName) {
		this.displayName = displayName;
	}
	
	@Transient
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
	
	@Transient
	public String getRegTime() {
		return regTime;
	}

	public void setRegTime(String regTime) {
		this.regTime = regTime;
	}
	
	@Transient
	public String getUuid() {
		return uuid;
	}
	
	public void setUuid(String uuid) {
		this.uuid = uuid;
	}
	
	@Transient
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

	@Transient
	public Name getDn() {
		return dn;
	}

	public void setDn(String dn) {
		this.dn=new DistinguishedName(dn);
	}
	
	@Transient
	public Iterator<String> getObjectClassIterator() {
		return Collections.unmodifiableList(objectClass).iterator();
	}
	
	public void addSeeAlso(String seeAlso) {
		this.seeAlso.add(seeAlso);
	}

	public void removeSeeAlso(String seeAlso) {
		this.seeAlso.remove(seeAlso);
	}

	@Transient
	public Iterator<String> getSeeAlsoIterator() {
		return seeAlso.iterator();
	}
}
