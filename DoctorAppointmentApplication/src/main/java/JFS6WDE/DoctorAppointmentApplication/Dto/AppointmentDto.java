package JFS6WDE.DoctorAppointmentApplication.Dto;

import java.time.LocalDate;
import java.time.LocalTime;

import jakarta.persistence.Column;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class AppointmentDto {

    private Long appointmentId;

    @Max(5)
    @Min(1)
    @NotNull
    private int appointmentSlot;

    private LocalDate appointmentDate;

    @Column(nullable = false)
    private LocalTime appointmentTime;

    private int appointmentCharges;
}
