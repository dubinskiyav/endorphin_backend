package biz.gelicon.core.security;

import biz.gelicon.core.components.core.accessrole.AccessRole;
import biz.gelicon.core.components.core.capcode.CapCode;
import biz.gelicon.core.components.core.proguser.Proguser;
import org.aopalliance.intercept.MethodInvocation;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

public class UserDetailsImpl implements UserDetails {

    private final Proguser pu;
    private List<GrantedRole> grantedRoles;

    // используется в Method Security для передачи в PermissionEvaluator
    // имеет значении при вычислении прав
    // Внимание! не потокобезопасный, поэтому на каждый запрос должен
    // создаваться собственный UserDetails
    private MethodInvocation methodInvocation;

    public UserDetailsImpl(Proguser pu, Collection<AccessRole> roles) {
        this.pu = pu;
        this.grantedRoles = roles.stream().map(GrantedRole::new).collect(Collectors.toList());
    }

    public UserDetailsImpl(Proguser pu) {
        this.pu = pu;
        // неавторизированный пользователь
        this.grantedRoles = null;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return grantedRoles;
    }

    @Override
    public String getPassword() {
        // используется CredentialProvider
        return null;
    }

    public Proguser getProgUser() {
        return pu;
    }

    @Override
    public String getUsername() {
        return pu.getProguserName();
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return pu.getStatusId()== CapCode.USER_IS_ACTIVE;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }

    public boolean isSysDba() {
        return Proguser.isSysDba(pu.getProguserId());
    }

    public boolean containsSystemRole() {
        return grantedRoles.stream().anyMatch(ar->ACL.SYSTEM_ROLE_CODE.equals(ar.getAuthority()));
    }

    static class GrantedRole implements GrantedAuthority, GrantedAccessRole {
        private int id;

        public GrantedRole(AccessRole role) {
            this.id = role.getAccessRoleId();
        }

        @Override
        public String getAuthority() {
            return ACL.genRoleName(id);
        }

        @Override
        public int accessRoleId() {
            return this.id;
        }
    }

    public MethodInvocation getMethodInvocation() {
        return methodInvocation;
    }

    public void setMethodInvocation(MethodInvocation mi) {
        this.methodInvocation = mi;
    }
}
