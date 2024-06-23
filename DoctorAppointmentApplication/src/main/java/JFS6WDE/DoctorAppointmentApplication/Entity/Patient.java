package JFS6WDE.DoctorAppointmentApplication.Entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Patient {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column(name = "Patient_Name", nullable = false)
    private String name;

    @Column(name = "Medical_History")
    private String medicalHistory;

}
