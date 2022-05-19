package com.iquinto.workingstudent.service;

import com.iquinto.workingstudent.model.*;
import com.iquinto.workingstudent.model.enums.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import javax.transaction.Transactional;
import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.*;

@Slf4j
@Service
public class DataService {

    @Value("${spring.profiles.active}")
    private String activeProfile;

    @Autowired
    JobPositionService jobPositionService;

    @Autowired
    StudentService studentService;

    @Autowired
    UserService userService;

    @Autowired
    EmployerService employerService;

    @Autowired
    SlotService slotService;

    @Autowired
    ReservationService reservationService;

    @Autowired
    AddressService addressService;

    @Autowired
    NotificationService notificationService;

    @Autowired
    HistoryService historyService;

    @Autowired
    RatingService ratingService;

    @Autowired
    PasswordEncoder encoder;

    @Autowired
    JobPostService jobPostService;

    @Autowired
    UniversityService universityService;

    @Autowired
    ScheduleService scheduleService;

    @Autowired
    AreaService areaService;

    @Autowired
    PhotoStorageService photoStorageService;

    @Autowired
    ResumeStorageService resumeStorageService;

    @Transactional
    public void populateData() throws ParseException, IOException {
        log.info("[m:populateData] Loading data for database...");

        /******************************************************************
         * LOAD JOB POSTION
         ******************************************************************/

        String desc = "<p>Lorem i<strong>psum dolor sit amet</strong>, consectetur <strong>adipiscing elit</strong>, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua. Ut enim ad minim veniam, <strong>quis nostrud</strong> exercitation ullamco laboris nisi ut aliquip ex ea commodo consequat. Duis aute irure dolor in reprehenderit <strong>in voluptate velit</strong> esse cillum dolore eu fugiat nulla pariatur. Excepteur sint occaecat cupidatat non proident sunt in culpa qui officia deserunt <strong>mollit anim</strong> id est laborum.</p>";
        List<DaysOfTheWeek> daysOfTheWeeks = Arrays.asList(DaysOfTheWeek.values());
        for (DaysOfTheWeek day : daysOfTheWeeks){
            slotService.save(new Slot(day,09.00, 09.59));
            slotService.save(new Slot(day,10.00, 10.59));
            slotService.save(new Slot(day,11.00, 11.59));
            slotService.save(new Slot(day,12.00, 12.59));
            slotService.save(new Slot(day,13.00, 13.59));
            slotService.save(new Slot(day,14.00, 14.59));
            slotService.save(new Slot(day,15.00, 15.59));
            slotService.save(new Slot(day,16.00, 16.59));
            slotService.save(new Slot(day,17.00, 17.59));
            slotService.save(new Slot(day,18.00, 18.59));
            slotService.save(new Slot(day,19.00, 19.59));
            slotService.save(new Slot(day,20.00, 20.59));
            slotService.save(new Slot(day,21.00, 21.59));
            slotService.save(new Slot(day,22.00, 22.59));
            slotService.save(new Slot(day,23.00, 24.00));
        }

        Arrays.asList("Universidad de Sevilla", "Universidad de Granada", "Universidad de Córdoba", "Universidad de Málaga", "Universidad de Cádiz", "Universidad de Almería", "Universidad de Huelva", "Universidad de Jaén", "Universidad Internacional de Andalucía", "Universidad Pablo de Olavide", "Universidad Loyola Andalucía", "Universidad de Zaragoza", "Universidad San Jorge", "Universidad de Oviedo", "Universidad de las Islas Baleares", "Universidad de La Laguna", "Universidad de Las Palmas de Gran Canaria", "Universidad Europea de Canarias", "Universidad Fernando Pessoa-Canarias", "Universidad de Cantabria", "Universidad Europea del Atlántico", "Universidad de Castilla-La Mancha", "Universidad de Salamanca", "Universidad de Valladolid", "Universidad Pontificia de Salamanca", "Universidad de León", "Universidad de Burgos", "Universidad Católica Santa Teresa de Jesús de Ávila", "Universidad Europea Miguel de Cervantes", "Universidad IE", "Universidad Internacional Isabel I de Castilla", "Universidad de Barcelona", "Universidad Autónoma de Barcelona", "Universidad Politécnica de Cataluña", "Universidad Pompeu Fabra", "Universidad Ramon Llull", "Universidad de Gerona", "Universidad de Lérida", "Universidad Rovira i Virgili", "Universidad Oberta de Cataluña", "Universidad Internacional de Cataluña", "Universidad de Vich", "Universidad Abad Oliva CEU", "Universidad de Valencia", "Universidad Politécnica de Valencia", "Universidad de Alicante", "Universidad Jaime I", "Universidad Miguel Hernández de Elche", "Universidad CEU Cardenal Herrera", "Universidad Católica de Valencia San Vicente Mártir", "Universidad Internacional de Valencia", "Universidad Europea de Valencia", "Universidad de Extremadura", "Universidad de Santiago de Compostela", "Universidad de La Coruña", "Universidad de Vigo", "Universidad de La Rioja", "Universidad Internacional de La Rioja", "Universidad Complutense de Madrid", "Universidad Pontificia Comillas", "Universidad Internacional Menéndez Pelayo", "Universidad Autónoma de Madrid", "Universidad Politécnica de Madrid", "Universidad Nacional de Educación a Distancia", "Universidad de Alcalá", "Universidad Carlos III de Madrid", "Universidad Alfonso X el Sabio", "Universidad CEU San Pablo", "Universidad Francisco de Vitoria", "Universidad Antonio de Nebrija", "Universidad Europea de Madrid", "Universidad Rey Juan Carlos", "Universidad Camilo José Cela", "Universidad a Distancia de Madrid", "Universidad Eclesiástica San Dámaso",
                "Saint Louis University Madrid Campus","ESIC Universidad", "Universidad Internacional Villanueva","CUNEF Universidad", "Universidad de Murcia", "Universidad Católica San Antonio", "Universidad Politécnica de Cartagena", "Universidad de Navarra", "Universidad Pública de Navarra", "Universidad de Deusto", "Universidad del País Vasco", "Universidad de Mondragón"
        ).stream().forEach((it)-> {
            universityService.save(new University(it));
        });

        Arrays.asList("Restauración", "Animación", "Música", "Danza", "Eseñanza", "Idioma",
                "Cuidado de niños/mayores", "Fontanería", "Estética y cosmética", "Electricidad", "Consumo y alimentación", "Confección", "Carpintería", "Automoción",
                "Artesanía", "Artes plásticas", "Artes interpretativas", "Agricultura y Jardinería").stream().forEach((a)-> {
            areaService.save(new Area(a));
        });

        Arrays.asList("Camarero/a", "Cocinero/a", "Profesor/a de baile", "Profesor/a de mates", "Profesor/a de idioma", "Conductor/a",
                "Programador/a", "Profesor de deportes/a", "Monitor/a de comedor", "Monitor/a de bus", "Repartidor/a, Dependiente/a").stream().forEach((c)-> {
            jobPositionService.save(new JobPosition(c));
        });


        addressService.save(new Address("Calle Pere Moreto 3",
                "Cambrils", "43850", Province.TARRAGONA.getText()));
        addressService.save(new Address("Avenida Barcelona 21",
                "Salou", "43840", Province.TARRAGONA.getText()));
        addressService.save(new Address("Calle Alosa 22",
                "Tarragona", "43007", Province.TARRAGONA.getText()));
        addressService.save(new Address("Calle Altafulla 22",
                "Tarragona", "43006", Province.TARRAGONA.getText()));
        addressService.save(new Address("Calle Alfred Opisso 22",
                "Tarragona", "43002", Province.TARRAGONA.getText()));
        addressService.save(new Address("Calle Adriatic 22",
                "Reus", "43205", Province.TARRAGONA.getText()));
        addressService.save(new Address("Calle Aguila 1",
                "Reus", "43205", Province.TARRAGONA.getText()));


        /******************************************************************
         * LOAD EMPLOYER
         ******************************************************************/

        Employer e1 = new Employer("test_employer1",  "broganzquinto@gmail.com",
                addressService.save(new Address("Calle Corona 1",
                        "Reus", "43206", Province.TARRAGONA.getText())), Role.ROLE_EMPLOYER);
        e1.setName("Chiringuito de Verano");
        e1.setPhone("6000745578");
        e1.setDescription(desc);
        e1.setWebsite("www.prova.com");
        e1.setPassword(encoder.encode("secret_pass"));
        e1.setArea(areaService.findByName("Restauración"));
        e1.setHasCompany(true);
        e1 = employerService.save(e1);


        Employer e2 = new Employer("test_employer2",  "broganzquinto@gmail.com",
                addressService.save(new Address("Calle Virus 1",
                        "Cambrils", "43206", Province.TARRAGONA.getText())), Role.ROLE_EMPLOYER);
        e2.setName("Jun");
        e2.setSurname("Alvarez");
        e2.setPhone("6000745578");
        e2.setDescription(desc);
        e1.setWebsite("www.prova.com");
        e2.setPassword(encoder.encode("secret_pass"));
        e1.setArea(areaService.findByName("Música"));
        e2.setHasCompany(true);
        e2 = employerService.save(e2);

        /******************************************************************
         * LOAD STUDENT
         ******************************************************************/
        Set<JobPosition> jobPosition1 = new HashSet<>();
        Arrays.asList(1L, 2L, 3L).stream().forEach((c)-> {
            jobPosition1.add(jobPositionService.findById(c));
        });

        Set<JobPosition> jobPosition2 = new HashSet<>();
        Arrays.asList(4L, 5L, 6L).stream().forEach((c)-> {
            jobPosition2.add(jobPositionService.findById(c));
        });

        Set<JobPosition> jobPosition3 = new HashSet<>();
        Arrays.asList(7L, 8L, 9L).stream().forEach((c)-> {
            jobPosition3.add(jobPositionService.findById(c));
        });

        Student s1 = new Student("test_student1",  "broganzquinto@gmail.com",
                addressService.get(1L), Role.ROLE_STUDENT);
        s1.setName("Isagani");
        s1.setSurname("Quinto");
        s1.setPhone("6555343321");
        s1.setBirthday(LocalDate.parse("2000-01-08"));
        s1.setSex("Male");
        s1.setRole(Role.ROLE_STUDENT);
        s1.setDescription(desc);
        s1.setUniversity(universityService.findById(1L));
        s1.setStudentId("245678954");
        s1.setPassword(encoder.encode("secret_pass"));
        s1.setJobPositions(jobPosition1);
        s1 = studentService.save(s1);


        Student s2 = new Student("test_student2",  "test_student2@gmail.com",
                addressService.get(2L), Role.ROLE_STUDENT);
        s2.setName("Maria");
        s2.setSurname("Diana");
        s2.setPhone("6555343331");
        s2.setBirthday(LocalDate.parse("2000-12-07"));
        s2.setSex("Female");
        s2.setRole(Role.ROLE_STUDENT);
        s2.setDescription(desc);
        s2.setUniversity(universityService.findById(2L));
        s2.setStudentId("2456781576");
        s2.setPassword(encoder.encode("secret_pass"));
        s2.setJobPositions(jobPosition2);
        s2 = studentService.save(s2);

        Student s3 = new Student("test_student3",  "test_student3@gmail.com",
                addressService.get(3L), Role.ROLE_STUDENT);
        s3.setName("Angelica");
        s3.setSurname("Laza");
        s3.setPhone("6022345562");
        s3.setBirthday(LocalDate.parse("2000-12-12"));
        s3.setSex("Female");
        s3.setRole(Role.ROLE_STUDENT);
        s3.setDescription(desc);
        s3.setUniversity(universityService.findById(3L));
        s3.setStudentId("2456781576");
        s3.setPassword(encoder.encode("secret_pass"));
        s3.setJobPositions(jobPosition3);
        s3 = studentService.save(s3);

        Student s4 = new Student("test_student4",  "test_student4@gmail.com",
                addressService.get(4L), Role.ROLE_STUDENT);
        s4.setName("Angelica");
        s4.setSurname("Laza");
        s4.setPhone("6022345564");
        s4.setBirthday(LocalDate.parse("2000-03-02"));
        s4.setSex("Female");
        s4.setRole(Role.ROLE_STUDENT);
        s4.setDescription(desc);
        s4.setUniversity(universityService.findById(4L));
        s4.setStudentId("2456781576");
        s4.setPassword(encoder.encode("secret_pass"));
        s4.setJobPositions(jobPosition1);
        s4 = studentService.save(s4);

        Student s5 = new Student("test_student5",  "test_student5@gmail.com",
                addressService.get(5L), Role.ROLE_STUDENT);
        s5.setName("Kelly");
        s5.setSurname("Ramirez");
        s5.setPhone("6022345564");
        s5.setBirthday(LocalDate.parse("2000-05-04"));
        s5.setSex("Female");
        s5.setRole(Role.ROLE_STUDENT);
        s5.setDescription(desc);
        s5.setUniversity(universityService.findById(5L));
        s5.setStudentId("2456782376");
        s5.setPassword(encoder.encode("secret_pass"));
        s5.setJobPositions(jobPosition2);
        s5 = studentService.save(s5);

        Student s6 = new Student("test_student6",  "test_student6@gmail.com",
                addressService.get(6L), Role.ROLE_STUDENT);
        s6.setName("Manuel");
        s6.setSurname("Victoria");
        s6.setPhone("6022345214");
        s6.setBirthday(LocalDate.parse("2000-03-04"));
        s6.setSex("Female");
        s6.setRole(Role.ROLE_STUDENT);
        s6.setDescription(desc);
        s6.setUniversity(universityService.findById(6L));
        s6.setStudentId("2456782236");
        s6.setPassword(encoder.encode("secret_pass"));
        s6.setJobPositions(jobPosition3);
        s6 = studentService.save(s6);

        Student s7 = new Student("test_student7",  "test_student7@gmail.com",
                addressService.get(6L), Role.ROLE_STUDENT);
        s7.setName("Susi");
        s7.setSurname("Mandela");
        s7.setPhone("6022321214");
        s7.setBirthday(LocalDate.parse("2000-02-11"));
        s7.setSex("Female");
        s7.setRole(Role.ROLE_STUDENT);
        s7.setDescription(desc);
        s7.setUniversity(universityService.findById(7L));
        s7.setStudentId("2456723236");
        s7.setPassword(encoder.encode("secret_pass"));
        s7.setJobPositions(jobPosition2);
        s7 = studentService.save(s7);


        /******************************************************************
         * SET PHOTO AND CV to STUDENT 1
         ******************************************************************/

        if(!activeProfile.equalsIgnoreCase("docker")){
            Resource resource = new ClassPathResource("files/male.png");
            Path path = Paths.get(resource.getURI());
            byte[] bytes = Files.readAllBytes(path);
            Photo f = photoStorageService.storeWithFile(resource.getFilename(), "image/png", bytes);
            s1.setPhoto(f);
            s1 = studentService.save(s1);

            resource = new ClassPathResource("files/test_cv.pdf");
            path = Paths.get(resource.getURI());
            bytes = Files.readAllBytes(path);
            Resume resume = new Resume(resource.getFilename(), "application/pdf", bytes);
            resume.setAlias("CV_Baile");
            resume.setDescription("Para las personas que necesiten profesores de baile online");
            resume.setStudent(s1);
            Set<Resume> resumes = new HashSet<>();
            resumes.add(resumeStorageService.save(resume));
        }


        /******************************************************************
         * LOAD SCHEDULE
         ******************************************************************/

        scheduleService.save(new Schedule(s1, slotService.findByDayAndStAndStartTimeAndEndTime(DaysOfTheWeek.LUNES, 10.00, 10.59)));
        scheduleService.save(new Schedule(s2, slotService.findByDayAndStAndStartTimeAndEndTime(DaysOfTheWeek.MARTES, 10.00, 10.59)));
        scheduleService.save(new Schedule(s3, slotService.findByDayAndStAndStartTimeAndEndTime(DaysOfTheWeek.MIERCOLES, 11.00, 11.59)));
        scheduleService.save(new Schedule(s4, slotService.findByDayAndStAndStartTimeAndEndTime(DaysOfTheWeek.JUEVES, 12.00, 12.59)));
        scheduleService.save(new Schedule(s5, slotService.findByDayAndStAndStartTimeAndEndTime(DaysOfTheWeek.VIERNES, 13.00, 13.59)));
        scheduleService.save(new Schedule(s6, slotService.findByDayAndStAndStartTimeAndEndTime(DaysOfTheWeek.SABADO, 14.00, 14.59)));
        scheduleService.save(new Schedule(s7, slotService.findByDayAndStAndStartTimeAndEndTime(DaysOfTheWeek.DOMINGO, 15.00, 15.59)));

        List<Slot> weekends = slotService.findAllByDay(DaysOfTheWeek.SABADO);
        weekends.addAll(slotService.findAllByDay(DaysOfTheWeek.DOMINGO));
        weekends.addAll(slotService.findAllByStartTimeGreaterThan(15.00));
        weekends.stream().forEach((slot)-> {
            studentService.findAll().stream().forEach((student) ->{
                scheduleService.save(new Schedule(student, slot));
            });
        });

        /******************************************************************
         * LOAD NOTIFICATION
         ******************************************************************/


        for (String n : Arrays.asList(Notification.SAVE, Notification.ACCEPTED,
                Notification.DELETE,Notification.EDIT, Notification.REJECTED)){
            Notification notification = notificationService.save(new  Notification(e1, s1, n, "some"));
            Set <Slot> slots = new HashSet<>();
            slots.add(slotService.findById(1L));
            slots.add(slotService.findById(25L));
            notification.setSlots(slots);
            notificationService.save(notification);
        }


        /******************************************************************
         * LOAD RATING
         ******************************************************************/


        ratingService.save(new Rating(1L, 5, s1));
        ratingService.save(new Rating(1L, 3, s2));
        ratingService.save(new Rating(1L, 4, s3));
        ratingService.save(new Rating(1L, 5, s4));
        ratingService.save(new Rating(1L, 4, s5));
        ratingService.save(new Rating(1L, 3, s6));
        //ratingService.save(new Rating(1L, 3, s7));

        /******************************************************************
         * LOAD JOB POST
         ******************************************************************/

        Set<Student> students = new HashSet<>();
        students.add(s1);
        students.add(s2);


        String requirements = "<p>Lorem ipsum dolor sit amet, consectetur adipiscing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua. Ut enim ad minim veniam:</p>\n" +
                "<ul>\n" +
                "<li>Sed ut perspiciatis unde omnis</li>\n" +
                "<li>Iste natus error sit</li>\n" +
                "<li>Nemo enim ipsam voluptatem</li>\n" +
                "<li>Neque porro quisquam est</li>\n" +
                "<li>Ut enim ad minima veniam</li>\n" +
                "<li>Neque porro quisquam est</li>\n" +
                "<li>Ut enim ad minima</li>\n" +
                "</ul>\n" +
                "<p>Magni dolores eos qui ratione voluptatem sequi nesciunt. Neque porro quisquam est, qui dolorem ipsum quia dolor sit amet, consectetur.</p>";
        String desciption = "Ut enim ad minima veniam, quis nostrum exercitationem ullam corporis suscipit laboriosam consequatur? Quis autem vel eum iure  qui dolorem eum fugiat quo voluptas nulla pariatur?\n" +
                "\n" +
                "At vero eos et accusamus et iusto odio dignissimos ducimus qui blanditiis praesentium voluptatum deleniti atque corrupti quos dolores et quas molestias excepturi sint occaecati cupiditate non provident, similique sunt in culpa qui officia deserunt mollitia animi, id est laborum et dolorum fuga. Et harum quidem rerum facilis est et expedita distinctio.\n" +
                "\n" +
                "Nam libero tempore cum\n" +
                "Soluta nobis est eligendi optio\n" +
                "Cumque nihil impedit quo minus id quod maxime placeat facere possimus\n" +
                "Omnis voluptas assumenda est, omnis dolor repellendus. Temporibus autem quibusdam\n" +
                "Ets aut officiis debitis aut rerum necessitatibus saepe eveniet.\n" +
                "\n" +
                "\n" +
                "Et voluptates repudiandae sint et molestiae non recusandae. Itaque earum rerum hic tenetur a sapiente delectus, ut aut reiciendis voluptatibus maiores alias consequatur aut perferendis doloribus asperiores repellat.\n" +
                "\n" +
                "Ic tenetur a sapiente!";

        JobPost j1 = new JobPost();
        j1.setType("temporary");
        j1.setTitle("Ayudante de sala");
        j1.setRequirements(requirements);
        j1.setDescription(desciption);
        j1.setStartDate(LocalDate.now());
        j1.setCategory(areaService.findByName("Restauración"));
        j1.setEmployer(employerService.findByUsername("test_employer1"));
        j1.setYearSalary(20000.00);
        j1.setExpiration(LocalDate.now().plusDays(10));
        j1.setCandidates(students);
        jobPostService.save(j1);

        JobPost j2 = new JobPost();
        j2.setType("temporary");
        j2.setTitle("Profesor de danza");
        j2.setRequirements(requirements);
        j2.setDescription(desciption);
        j2.setStartDate(LocalDate.now());
        j2.setCategory(areaService.findByName("Danza"));
        j2.setEmployer(employerService.findByUsername("test_employer1"));
        j2.setYearSalary(17000.00);
        j2.setExpiration(LocalDate.now().minusDays(2));
        jobPostService.save(j2);

        JobPost j3 = new JobPost();
        j3.setType("temporary");
        j3.setTitle("Animador/a");
        j3.setRequirements(requirements);
        j3.setDescription(desciption);
        j3.setStartDate(LocalDate.now());
        j3.setCategory(areaService.findByName("Animación"));
        j3.setEmployer(employerService.findByUsername("test_employer1"));
        j3.setYearSalary(19000.00);
        j3.setExpiration(LocalDate.now().plusDays(10));
        jobPostService.save(j3);

        JobPost j4 = new JobPost();
        j4.setType("temporary");
        j4.setTitle("Profesor de Guitara");
        j4.setRequirements(requirements);
        j4.setDescription(desciption);
        j4.setStartDate(LocalDate.now());
        j4.setCategory(areaService.findByName("Música"));
        j4.setEmployer(employerService.findByUsername("test_employer1"));
        j4.setYearSalary(19000.00);
        j4.setExpiration(LocalDate.now().plusDays(10));
        jobPostService.save(j4);

        JobPost j5 = new JobPost();
        j5.setType("temporary");
        j5.setTitle("Profesor de Ingles");
        j5.setRequirements(requirements);
        j5.setDescription(desciption);
        j5.setStartDate(LocalDate.now());
        j5.setCategory(areaService.findByName("Idioma"));
        j5.setEmployer(employerService.findByUsername("test_employer1"));
        j5.setYearSalary(19000.00);
        j5.setExpiration(LocalDate.now().plusDays(10));
        jobPostService.save(j5);

        JobPost jI1 = new JobPost();
        jI1.setType("internship");
        jI1.setTitle("Practica Ayudante de sala");
        jI1.setRequirements(requirements);
        jI1.setDescription(desciption);
        jI1.setStartDate(LocalDate.now());
        jI1.setCategory(areaService.findByName("Restauración"));
        jI1.setEmployer(employerService.findByUsername("test_employer1"));
        jI1.setYearSalary(20000.00);
        jI1.setExpiration(LocalDate.now().plusDays(10));
        jobPostService.save(jI1);

        JobPost jI2 = new JobPost();
        jI2.setType("internship");
        jI2.setTitle("Practica Automoción");
        jI2.setRequirements(requirements);
        jI2.setDescription(desciption);
        jI2.setStartDate(LocalDate.now());
        jI2.setCategory(areaService.findByName("Automoción"));
        jI2.setEmployer(employerService.findByUsername("test_employer1"));
        jI2.setYearSalary(17000.00);
        jI2.setExpiration(LocalDate.now().minusDays(2));
        jobPostService.save(jI2);

        JobPost jI3 = new JobPost();
        jI3.setType("internship");
        jI3.setTitle("Practica Animador/a");
        jI3.setRequirements(requirements);
        jI3.setDescription(desciption);
        jI3.setStartDate(LocalDate.now());
        jI3.setCategory(areaService.findByName("Animación"));
        jI3.setEmployer(employerService.findByUsername("test_employer1"));
        jI3.setYearSalary(19000.00);
        jI3.setExpiration(LocalDate.now().plusDays(10));
        jobPostService.save(jI3);

        JobPost jI4 = new JobPost();
        jI4.setType("internship");
        jI4.setTitle("Practica Profesor de Guitara");
        jI4.setRequirements(requirements);
        jI4.setDescription(desciption);
        jI4.setStartDate(LocalDate.now());
        jI4.setCategory(areaService.findByName("Música"));
        jI4.setEmployer(employerService.findByUsername("test_employer1"));
        jI4.setYearSalary(19000.00);
        jI4.setExpiration(LocalDate.now().plusDays(10));
        jobPostService.save(jI4);

        JobPost jI5 = new JobPost();
        jI5.setType("internship");
        jI5.setTitle("Practica Profesor de Danza");
        jI5.setRequirements(requirements);
        jI5.setDescription(desciption);
        jI5.setStartDate(LocalDate.now());
        jI5.setCategory(areaService.findByName("Danza"));
        jI5.setEmployer(employerService.findByUsername("test_employer1"));
        jI5.setYearSalary(19000.00);
        jI5.setExpiration(LocalDate.now().plusDays(10));
        jobPostService.save(jI5);

        /******************************************************************
         * LOAD JOB HISTORY
         ******************************************************************/

        List<Integer> meses1 = Arrays.asList(3, 3 , 4, 5, 6, 8, 9, 9, 10, 11, 12,12,12,12,12);
        for (int m : meses1){
            String str = "13/" + m +"/2019";
            Date date = new SimpleDateFormat("dd/MM/yyyy").parse(str);
            LocalDate localDate = date.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
            historyService.save(new History("test_student1", (long) (Math.floor(Math.random() * 50) + 1), localDate.getMonthValue(), localDate.getYear(), localDate));
        }

        List<Integer> meses2 = Arrays.asList(1, 1, 1, 1, 2, 2, 3 , 4, 5, 6, 7, 8, 9, 9, 10, 11,12);
        for (int m : meses2){
            String str = "13/" + m +"/2020";
            Date date = new SimpleDateFormat("dd/MM/yyyy").parse(str);
            LocalDate localDate = date.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
            historyService.save(new History("test_student1", (long) (Math.floor(Math.random() * 50) + 1), localDate.getMonthValue(), localDate.getYear(), localDate));
        }

        List<Integer> meses3 = Arrays.asList(1, 1, 1, 2,2, 3, 3 , 4, 5, 6, 7, 8, 9, 9, 10, 11,12,12,12);
        for (int m : meses3){
            String str = "13/" + m +"/2021";
            Date date = new SimpleDateFormat("dd/MM/yyyy").parse(str);
            LocalDate localDate = date.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
            historyService.save(new History("test_student1", (long) (Math.floor(Math.random() * 50) + 1), localDate.getMonthValue(), localDate.getYear(), localDate));
        }

        List<Integer> meses4 = Arrays.asList(1, 1 , 2, 3, 3 , 4, 5,5, 6 );
        for (int m : meses4){
            String str = "13/" + m +"/2022";
            Date date = new SimpleDateFormat("dd/MM/yyyy").parse(str);
            LocalDate localDate = date.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
            historyService.save(new History("test_student1", (long) (Math.floor(Math.random() * 50) + 1), localDate.getMonthValue(), localDate.getYear(), localDate));
        }

        printObjects();
    }

    public void printObjects(){
        log.info("Employer: " + employerService.findAll().size());
        log.info("Student: " + studentService.findAll().size());
        log.info("Address: " + addressService.findAll().size());
        log.info("Area: " + areaService.findAll().size());
        log.info("JoPosition: " + jobPositionService.findAll().size());
        log.info("Slot: " + slotService.findAll().size());
        log.info("Notification: " + notificationService.findAll().size());
        log.info("History: " + historyService.findAll().size());
        log.info("University: " + universityService.findAll().size());
        log.info("Reservation: " + reservationService.findAll().size());
        log.info("Rating: " + ratingService.findAll().size());
        log.info("JobPost: " + jobPostService.findAll().size());
        log.info("Schedule: " + scheduleService.findAll().size());
    }

    @Transactional
    public void destroyData() {
        notificationService.deleteAll();
        historyService.deleteAll();
        universityService.deleteAll();
        addressService.deleteAll();
        jobPositionService.deleteAll();
        areaService.deleteAll();
        slotService.deleteAll();
        reservationService.deleteAll();
        ratingService.deleteAll();
        jobPostService.deleteAll();
        scheduleService.deleteAll();
        employerService.deleteAll();
        studentService.deleteAll();
        userService.deleteAll();
    }


}
