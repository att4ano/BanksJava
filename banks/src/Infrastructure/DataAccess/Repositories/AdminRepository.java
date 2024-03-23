package Infrastructure.DataAccess.Repositories;

import application.abstractions.IAdminRepository;
import domain.models.Admin;
import org.jetbrains.annotations.Nullable;

import java.util.HashSet;
import java.util.Set;

/**
 * Репозиторий админов
 */
public class AdminRepository implements IAdminRepository {
    private final Set<Admin> admins;

    public AdminRepository(HashSet<Admin> admins) {
        this.admins = admins;
    }

    /**
     * @return все админы
     */
    @Override
    public Set<Admin> getAllAdmins() {
        return admins;
    }

    /**
     * @param adminPassword найти конкретного админа
     * @return найденный админ
     */
    @Override
    public @Nullable Admin findAdmin(String adminPassword) {
        return admins.stream()
                .filter(admin -> admin.getPassword().equals(adminPassword))
                .findFirst()
                .orElse(null);
    }

    /**
     * @param admin админ, которого надо добавить
     */
    @Override
    public void AddNewAdmin(Admin admin) {
        admins.add(admin);
    }
}
