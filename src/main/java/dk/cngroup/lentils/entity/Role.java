package dk.cngroup.lentils.entity;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Column;

@Entity
@Table(name = "role")
public class Role {

    @Id
    @GeneratedValue
    @Column(name = "role_id")
    private Long roleId;

    @Column(name = "role")
    private String role;

    public Role() {
    }

    public Long getRoleId() {
        return roleId;
    }

    public void setRoleId(final Long roleId) {
        this.roleId = roleId;
    }

    public String getRole() {
        return role;
    }

    public void setRole(final String role) {
        this.role = role;
    }
}
