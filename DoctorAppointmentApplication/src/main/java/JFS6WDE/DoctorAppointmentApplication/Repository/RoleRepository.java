package JFS6WDE.DoctorAppointmentApplication.Repository;

import org.springframework.data.jpa.repository.JpaRepository;

import JFS6WDE.DoctorAppointmentApplication.Entity.Role;

public interface RoleRepository extends JpaRepository<Role, Long> {
    Role findByName(String name);
}

