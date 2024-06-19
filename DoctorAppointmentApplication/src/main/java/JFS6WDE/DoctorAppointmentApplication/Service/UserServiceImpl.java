package JFS6WDE.DoctorAppointmentApplication.Service;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import JFS6WDE.DoctorAppointmentApplication.Dto.UserDto;
import JFS6WDE.DoctorAppointmentApplication.Entity.Patient;
import JFS6WDE.DoctorAppointmentApplication.Entity.Role;
import JFS6WDE.DoctorAppointmentApplication.Entity.User;
import JFS6WDE.DoctorAppointmentApplication.Repository.PatientRepository;
import JFS6WDE.DoctorAppointmentApplication.Repository.RoleRepository;
import JFS6WDE.DoctorAppointmentApplication.Repository.UserRepository;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserRepository userRepository;
    @Autowired
    private RoleRepository roleRepository;
    @Autowired
    private PasswordEncoder passwordEncoder;

    public UserServiceImpl(UserRepository userRepository,
                           RoleRepository roleRepository,
                           PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.roleRepository = roleRepository;
        this.passwordEncoder = passwordEncoder;
    }
    
    @Autowired
    private PatientRepository patientRepository;

    @Override
    public void saveUser(UserDto userDto) {
        User user = new User();
        user.setName(userDto.getFirstName() + " " + userDto.getLastName());
        user.setEmail(userDto.getEmail());
        user.setPassword(passwordEncoder.encode(userDto.getPassword()));

        Patient patient = new Patient();

        patient.setName(userDto.getFirstName() + " " + userDto.getLastName());

        Role userRole = roleRepository.findByName("ROLE_USER");
        if (userRole == null) {
            userRole = new Role();
            userRole.setName("ROLE_USER");
            roleRepository.save(userRole);
        }

        Role adminRole = roleRepository.findByName("ROLE_ADMIN");
        if (adminRole == null) {
            adminRole = new Role();
            adminRole.setName("ROLE_ADMIN");
            roleRepository.save(adminRole);
        }

        user.setRoles(Arrays.asList(userRole));

        if (userDto.getEmail().equalsIgnoreCase("admin@admin.com") && userDto.getPassword().equalsIgnoreCase("admin")) {
            user.setRoles(Arrays.asList(adminRole));
        }

        userRepository.save(user);
        patientRepository.save(patient);
    }

    @Override
    public User findUserByEmail(String email) {
        return userRepository.findByEmail(email);
    }

    @Override
    public List<UserDto> findAllUsers() {
        List<User> users = userRepository.findAll();
        return users.stream()
                .map(this::mapToUserDto)
                .collect(Collectors.toList());
    }

    private UserDto mapToUserDto(User user) {
        UserDto userDto = new UserDto();
        String[] str = user.getName().split(" ");
        userDto.setFirstName(str[0]);
        userDto.setLastName(str[1]);
        userDto.setEmail(user.getEmail());
        return userDto;
    }
}