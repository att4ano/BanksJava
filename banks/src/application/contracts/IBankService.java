package application.contracts;

import application.result.LoginResult;
import application.result.ServiceResult;

public interface IBankService {
    LoginResult login(String bankName);
    ServiceResult logout();
    ServiceResult updateInterest(double interest);
    ServiceResult updateCommission(double commission);
}
