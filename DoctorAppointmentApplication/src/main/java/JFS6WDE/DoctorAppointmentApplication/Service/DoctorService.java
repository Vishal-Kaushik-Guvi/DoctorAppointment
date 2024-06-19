package JFS6WDE.DoctorAppointmentApplication.Service;

import java.util.List;

import org.springframework.data.domain.Page;

import JFS6WDE.DoctorAppointmentApplication.Entity.Doctor;

public interface DoctorService {
List<Doctor> getAllDoctors();
Doctor createDoctor(Doctor doctor);
void deleteDoctorById(long id);
Doctor viewDoctorById(long id);
void updateDoctor(Doctor doctor);

Page<Doctor> findPaginated(int pageNo,int pageSize,String sortField,String sortDirection);

}
