package book.shop.bookstore.service.role;

import book.shop.bookstore.model.Role;
import book.shop.bookstore.model.RoleName;

public interface RoleService {
    Role getRoleByRoleName(RoleName roleName);
}
