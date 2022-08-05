package biz.gelicon.core.annotations;


import biz.gelicon.core.security.ACL;
import org.springframework.security.access.prepost.PreAuthorize;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
@PreAuthorize("hasAuthority('"+ ACL.SYSTEM_ROLE_CODE +"')")
public @interface CheckAdminPermission {
}
