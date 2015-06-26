package com.deluan.shiro.gae.realm;

import com.google.appengine.api.datastore.*;
import com.google.appengine.api.users.User;

import org.apache.shiro.authc.*;
import org.apache.shiro.authz.AuthorizationException;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.Permission;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.authz.permission.WildcardPermission;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;

import groovy.util.ResourceException;

import java.util.Collection;
import java.util.HashSet;
import java.util.Set;
import java.util.logging.Logger;

/**
 * User: deluan Date: Sep 21, 2010 Time: 9:32:45 PM
 */
public class DatastoreRealm extends AuthorizingRealm {

	static final String DEFAULT_USER_STORE_KIND = "ShiroUsers";

	static final Logger log = Logger
			.getLogger("com.deluan.shiro.gae.realm.DatastoreRealm");
	private DatastoreService datastoreService;
	private String userStoreKind = DEFAULT_USER_STORE_KIND;

	private boolean permissionsLookupEnabled;

	public DatastoreRealm() {
		log.info("Creating a new instance of DatastoreRealm");
		this.datastoreService = DatastoreServiceFactory.getDatastoreService();
	}

	@Override
	protected AuthenticationInfo doGetAuthenticationInfo(
			AuthenticationToken token) throws AuthenticationException {
		String username = ((UsernamePasswordToken) token).getUsername();
		log.info("Attempting to authenticate " + username + " in DB realm...");

		// Null username is invalid
		if (username == null) {
			throw new AccountException(
					"Null usernames are not allowed by this realm.");
		}

		// Get the user with the given username. If the user is not
		// found, then they don't have an account and we throw an
		// exception.
		Entity user = findByUsername(username);
		if (user == null) {
			throw new UnknownAccountException("No account found for user '"
					+ username + "'");
		}

		log.info("Found user " + username + " in DB");

		SimpleAccount account = new SimpleAccount(username,
				user.getProperty("passwordHash"), "DatastoreRealm");

		return account;
	}

	private Entity findByUsername(String username) {
		Query query = new Query(userStoreKind);
		query.addFilter("username", Query.FilterOperator.EQUAL, username);
		PreparedQuery preparedQuery = datastoreService.prepare(query);
		return preparedQuery.asSingleEntity();
	}

	protected String getUsername(PrincipalCollection principals) {
		return getAvailablePrincipal(principals).toString();
    }	
	
	@Override
	protected AuthorizationInfo doGetAuthorizationInfo(
			PrincipalCollection principals) {
		Set<String> roles = new HashSet<String>();
		Set<Permission> permissions = new HashSet<Permission>();

		if (principals.isEmpty()) {
			throw new AuthorizationException("DatastoreRealm: Authorization error: Empty principals list!");
 		}
		// LOADING STUFF FOR PRINCIPAL
//		for (User userPrincipal : principals) {
			try {
				String userId = getUsername(principals);
				// User user = this.userManager.loadById(userPrincipal.getId());
				Entity user = findByUsername(userId);

				// Set<Role> userRoles = user.getRoles();
				// for (Role r : userRoles) {
				// roles.add(r.getName());
				if ("admin".equals(userId)) {
					permissions.add(new WildcardPermission("*"));
					roles.add("ADMIN_ROLE");
				} else {
					permissions.add(new WildcardPermission("user:*"));
					roles.add("USER_ROLE");
				}
				// Set<WildcardPermission> userPermissions = r.getPermissions();
				// for (WildcardPermission permission : userPermissions) {
				// if (!permissions.contains(permission)) {
				// permissions.add(permission);
				// }
				// }
				// }
				// } catch (InvalidDataException idEx) { //userManger exceptions
				// throw new AuthorizationException(idEx);
			} catch (Exception rEx) {
				throw new AuthorizationException(rEx);
			}
//		}
		// THIS IS THE MAIN CODE YOU NEED TO DO !!!!
		SimpleAuthorizationInfo info = new SimpleAuthorizationInfo(roles);
		info.setRoles(roles); // fill in roles
		info.setObjectPermissions(permissions); // add permisions (MUST
												// IMPLEMENT SHIRO PERMISSION
												// INTERFACE)

		return info;
	}

	public void setUserStoreKind(String userStoreKind) {
		this.userStoreKind = userStoreKind;
	}

	/**
	 * Enables lookup of permissions during authorization. The default is
	 * "false" - meaning that only roles are associated with a user. Set this to
	 * true in order to lookup roles and permissions.
	 * 
	 * @param permissionsLookupEnabled
	 *            true if permissions should be looked up during authorization,
	 *            or false if only roles should be looked up.
	 */
	public void setPermissionsLookupEnabled(boolean permissionsLookupEnabled) {
		this.permissionsLookupEnabled = permissionsLookupEnabled;
	}
}
