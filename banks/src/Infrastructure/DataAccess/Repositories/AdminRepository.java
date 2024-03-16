package Infrastructure.DataAccess.Repositories;

import application.abstractions.IAdminRepository;
import domain.models.Admin;
import org.jetbrains.annotations.Nullable;

import java.util.HashSet;
import java.util.UUID;

/**
 * Репозиторий админов
 */
public class AdminRepository implements IAdminRepository {
    private final HashSet<Admin> _admin;

    public AdminRepository(HashSet<Admin> admin) {
        _admin = admin;
    }

    /**
     * @return все админы
     */
    @Override
    public HashSet<Admin> getAllAdmins() {
        return _admin;
    }

    /**
     * @param adminPassword найти конкретного админа
     * @return найденный админ
     */
    @Override
    public @Nullable Admin findAdmin(String adminPassword) {
        return _admin.stream()
                .filter(admin -> admin.get_password().equals(adminPassword))
                .findFirst()
                .orElse(null);
    }

    /**
     * @param admin админ, которого надо добавить
     */
    @Override
    public void AddNewAdmin(Admin admin) {
        _admin.add(admin);
    }
}
