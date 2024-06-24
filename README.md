# DoctorAppointment

Data Distribution Inside This Project

1.pom.xml -> This file holds all the dependency of this project. 2.src/main/java -> This folder holds the main java programs.

--------Inside src/main/java there are 8 important packages.------

1. Base Package -> Contain Main SpringBoot run application class.

2. Controller package -> Contain 5 controllers to hadnle endpoints.

Auth Controller - for Login and Registration Authentication endpoints.

DoctorController - for Doctor entity endpoints.

AppointmentConrtoller - for Appointment endpoints.

MedicationController - for Medication nevigation endpoints;

PatientController - for index page.

3. Dto package -> Contain all Dto files

UserDto - for User entity.

MedicationDto - for Medication entity.

4. Entity Package - Contains all entity files.

Doctor , Appointment, Role, User, Patient, Medication

5. Exception Package -> Contains neccessary user defined Exceptions.

ResourceNotFoundExceptions

6. Respository package -> Contains all the Repository classes.

PatientRepository, AppointmentRepository, RoleRepository, UserRepository, MedicationRepository, DoctorRepository.

7. Service Package -> Contains all Bussiness logic for this application.

8. Configuration Package -> This package maintain the security Congiguration of this application.

----------------Steps To Run The Application-------------------

Step 1. First setup you database, I am using Local Database, so i have setup my configuration according to my local database.

I have also cofigured the H2-Console into Spring Security if user wants to use it.

To Setup Database -> Go to application.properties inside src/main/resources package and implement the dependecies of the database you want to use.

Step 2. Go to base pacakge and Run the main Application.

Step 3. User and Admin Registration ->

For Admin Registration - you have to register as a admin and to register as admin use this below email and password :-

Admin email -> admin@admin.com Admin password -> admin

you cannot make your own admin account to make your own admin account you have to change the logic setting of CustomUserServices inside services package.

Permition of pages for Admin ->login, register, users , buslist, addBus, updatebus

For User Login -> you can register as user with any email and password you want.

Step 4 After registering as an Admin you need to add Doctor Details for user. To add Doctor Details go to add Doctor page and fill the add Doctor form. You can update and delete the Doctor Details you added after adding.

Step 5 Now login as a User to enjoy bus application services like.

Book Appointment - Book appointment with doctor.
You can cancel the appointment as well from Appointment History.

Appointment History - It record the data of your appointments.

Add Medication - Add medication that is provided by the doctor and make a Medication list.
You can update and delete those medication from medication list.

Pages only User can Access : - index, addAppointment, addMedication, AppointmentHistory, MedicationList, UpdateMedication

Pages used by admin : - doctorList, addDoctor, updateDoctor.

