package com.ncu.springboot.api.cache.keys;

public interface RolePermissionCacheIdKeys extends CacheIdKeys {
	
	String NAMESPACE = CacheIdKeys.NAMESPACE + "role.";

    static String getRole2PermissionKey(String role) {
    	return NAMESPACE + "role_id." + role;
    }
    
    static String getPermission2MenuKey(String permission) {
    	return NAMESPACE + "permission_id." + permission;
    }
    
    static String getRolesByTokenKey(String token) {
    	return NAMESPACE + "token." + token + ".roles";
    }
    
    static String getRoleByRoleIdKey(String role_id) {
    	return NAMESPACE + "role_id." + role_id;
    }
    
    static String getPermissionByPermissionIdKey(String permission_id) {
    	return NAMESPACE + "permission_id." + permission_id;
    }
    
    static String getRoleIdsByTokenKey(String token) {
    	return NAMESPACE + "token." + token + ".role_ids";
    }
    
    static String getRoleIdsByUsercodeKey(String usercode) {
    	return NAMESPACE + "usercode." + usercode + ".role_ids";
    }
    
    static String getPermissionIdsByRoleIdKey(String role_id) {
    	return NAMESPACE + "role_id." + role_id + ".permission_ids";
    }

    static String getPermissionIds() {
        return NAMESPACE + "permission_ids";
    }
    static String getRoleByRoleIdsKey() {
        return NAMESPACE + "role_ids";
    }
    static String getPermissionByMenuIds() {
        return NAMESPACE + "menu_ids";
    }
    static String getPermissionByMenuButtonIds() {
        return NAMESPACE + "menuButton_ids";
    }
    static String getPermissionByPortIds() {
        return NAMESPACE + "port_ids";
    }


}
