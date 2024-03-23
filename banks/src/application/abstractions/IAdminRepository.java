package application.abstractions;

import domain.models.Admin;
import org.jetbrains.annotations.Nullable;

import java.util.HashSet;
import java.util.Set;

public interface IAdminRepository {
    Set<Admin> getAllAdmins();
    @Nullable Admin findAdmin(String adminPassword);
    void AddNewAdmin(Admin admin);
}
