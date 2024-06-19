package JFS6WDE.DoctorAppointmentApplication.Service;

import java.util.List;

import JFS6WDE.DoctorAppointmentApplication.Dto.UserDto;
import JFS6WDE.DoctorAppointmentApplication.Entity.User;

public interface UserService {
    void saveUser(UserDto userDto);

    User findUserByEmail(String email);

    List<UserDto> findAllUsers();
}