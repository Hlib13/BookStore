package book.shop.bookstore.service.role;

import book.shop.bookstore.model.Role;
import book.shop.bookstore.model.RoleName;
import book.shop.bookstore.repository.user.RoleRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class RoleServiceImpl implements RoleService {
    private final RoleRepository repository;

    @Override
    public Role getRoleByRoleName(RoleName roleName) {
        return repository.findRoleByRoleName(roleName).orElseThrow(
                () -> new RuntimeException("Can't find role by role name:" + roleName));
    }
}
