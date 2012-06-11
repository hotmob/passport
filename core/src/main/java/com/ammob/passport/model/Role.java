package com.ammob.passport.model;

import org.apache.commons.lang.builder.ToStringBuilder;
import org.apache.commons.lang.builder.ToStringStyle;
import org.springframework.security.core.GrantedAuthority;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import java.io.Serializable;

/**
 * This class is used to represent available roles in the database.
 *
 * @author <a href="mailto:matt@raibledesigns.com">Matt Raible</a>
 *         Version by Dan Kibler dan@getrolling.com
 *         Extended to implement Acegi GrantedAuthority interface
 *         by David Carter david@carter.net
 */
@Entity
@Table(name = "role")
@NamedQueries({
        @NamedQuery(
                name = "findRoleByName",
                query = "select r from Role r where r.name = :name "
        )
})
public class Role extends BaseObject implements Serializable, GrantedAuthority {
	
    private static final long serialVersionUID = 3690197650654049848L;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    @Column(length = 20)
    private String name;
    @Column(length = 64)
    private String description;

    /**
     * Default constructor - creates a new instance with no values set.
     */
    public Role() {}

    /**
     * Create a new instance and set the name.
     *
     * @param name name of the role.
     */
    public Role(final String name) {
        this.name = name;
    }

    public Long getId() {
        return id;
    }

    /**
     * @return the name property (getAuthority required by Acegi's GrantedAuthority interface)
     * @see org.springframework.security.core.GrantedAuthority#getAuthority()
     */
    public String getAuthority() {
        return getName();
    }

    public String getName() {
        return this.name;
    }

    public String getDescription() {
        return this.description;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    /**
     * {@inheritDoc}
     */
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Role)) {
            return false;
        }
        final Role role = (Role) o;
        return !(name != null ? !name.equals(role.name) : role.name != null);
    }

    /**
     * {@inheritDoc}
     */
    public int hashCode() {
        return (name != null ? name.hashCode() : 0);
    }

    /**
     * {@inheritDoc}
     */
    public String toString() {
        return new ToStringBuilder(this, ToStringStyle.SIMPLE_STYLE).append(this.name).toString();
    }

    /**
     * {@inheritDoc}
     */
    public int compareTo(Object o) {
        return (equals(o) ? 0 : -1);
    }
}
