package application.abstractions;

import domain.models.Admin;
import org.jetbrains.annotations.Nullable;

import java.util.HashSet;

public interface IAdminRepository {
    HashSet<Admin> getAllAdmins();
    @Nullable Admin findAdmin(String adminPassword);
    void AddNewAdmin(Admin admin);
}
