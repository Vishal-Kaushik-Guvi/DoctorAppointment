package JFS6WDE.DoctorAppointmentApplication.Service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import JFS6WDE.DoctorAppointmentApplication.Entity.Doctor;
import JFS6WDE.DoctorAppointmentApplication.Repository.DoctorRepository;
import jakarta.persistence.EntityNotFoundException;

@Service
public class DoctorServiceImpl implements DoctorService {

    @Autowired
    private DoctorRepository doctorRepository;

    @Override
    public List<Doctor> getAllDoctors() {
        return doctorRepository.findAll();
    }

    @Override
    public Doctor createDoctor(Doctor doctor) {
        return doctorRepository.save(doctor);
    }

    @Override
    public void deleteDoctorById(long id) {
        doctorRepository.deleteById(id);
    }

    @Override
    public Doctor viewDoctorById(long id) {
        return doctorRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Invalid doctor ID: " + id));
    }

    @Override
    public void updateDoctor(Doctor doctor) {
        if (doctor == null) {
            throw new IllegalArgumentException("Doctor cannot be null");
        }

        Optional<Doctor> existingDoctorOptional = doctorRepository.findById(doctor.getId());
        if (!existingDoctorOptional.isPresent()) {
            throw new EntityNotFoundException("Doctor with ID " + doctor.getId() + " not found");
        }

        Doctor existingDoctor = existingDoctorOptional.get();
        existingDoctor.setName(doctor.getName());
        existingDoctor.setSpecialization(doctor.getSpecialization());
        existingDoctor.setEmail(doctor.getEmail());
        existingDoctor.setPhoneNumber(doctor.getPhoneNumber());
        existingDoctor.setAppointments(doctor.getAppointments());
        existingDoctor.setMedications(doctor.getMedications());

        doctorRepository.save(existingDoctor);
    }

    @Override
	public Page<Doctor> findPaginated(int pageNo, int pageSize, String sortField, String sortDirection) {
		// check if the sorting is ascending or descending
		Sort sort = sortDirection.equalsIgnoreCase(Sort.Direction.ASC.name()) ? Sort.by(sortField).ascending()
				: Sort.by(sortField).descending();
		Pageable pageable = PageRequest.of(pageNo - 1, pageSize, sort);
		return doctorRepository.findAll(pageable);
	}

}
