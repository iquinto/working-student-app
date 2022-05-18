package com.iquinto.workingstudent.utils

import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule
import com.iquinto.workingstudent.model.Address
import com.iquinto.workingstudent.model.Area
import com.iquinto.workingstudent.model.JobPosition
import com.iquinto.workingstudent.model.enums.Province
import com.iquinto.workingstudent.model.enums.Role
import com.iquinto.workingstudent.security.WebSecurityConfig
import com.iquinto.workingstudent.service.JobPositionService
import com.iquinto.workingstudent.service.DataService
import com.iquinto.workingstudent.service.EmployerService
import com.iquinto.workingstudent.service.NotificationService
import com.iquinto.workingstudent.service.ReservationService
import com.iquinto.workingstudent.service.ScheduleService
import com.iquinto.workingstudent.service.SlotService
import com.iquinto.workingstudent.service.StudentService
import com.iquinto.workingstudent.service.UniversityService
import com.iquinto.workingstudent.service.UserService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.core.io.ClassPathResource
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.setup.MockMvcBuilders
import org.springframework.web.context.WebApplicationContext
import spock.lang.Specification
import java.time.LocalDate

class SharedConfigSpecification extends Specification {

    public final static String TEST_USER_STUDENT = "test_student1"
    public final static String TEST_USER_EMPLOYER = "test_employer1"
    public final static String TEST_PASSWORD = "secret_pass"

    protected MockMvc mockMvc
    @Autowired
    protected WebApplicationContext webApplicationContext
    @Autowired
    protected WebSecurityConfig webSecurityConfig
    @Autowired
    protected JobPositionService categoryService
    @Autowired
    protected NotificationService notificationService
    @Autowired
    protected UserService userService
    @Autowired
    protected StudentService studentService
    @Autowired
    protected EmployerService employerService
    @Autowired
    protected ReservationService reservationService
    @Autowired
    protected ScheduleService scheduleService
    @Autowired
    protected UniversityService universityService
    @Autowired
    PasswordEncoder encoder;
    @Autowired
    protected DataService dataService

    File cv = new ClassPathResource("files/test_cv.pdf").getFile()

    def setup() {
        mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext).build()
    }

    def cleanup() {
    }

    protected static String AS_JSON_STRING(final Object obj) {
        try {
            final ObjectMapper mapper = new ObjectMapper();
            mapper.registerModule(new JavaTimeModule());
            return mapper.writeValueAsString(obj);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    protected static Map requestStudent = [
            name        : "Broganz",
            surname     : "Quinto",
            username    : "isaquinto",
            password    : "1234567",
            phone       : "30000000",
            email       : "fake_user@uoc.edu",
            birthday    : LocalDate.parse("2000-12-07"),
            sex         : "Male",
            role        : Role.ROLE_STUDENT,
            description : "some ",
            university  : 1.toLong(),
            studentId   : "7654321",
            address     : [
                    street  : "Calle Pepito 24",
                    city    : "Cambrils",
                    province: Province.TARRAGONA,
                    zipcode : "43850",
                    country : "España"
            ],
            jobPositions: [1, 2]
    ]


    protected static Map requestEmployer = [
            name       : "Chiringuito Marino",
            username   : "smarino",
            password   : "1234567",
            phone      : "600075589",
            email      : "smarino@gmail.com",
            role       : Role.ROLE_EMPLOYER,
            description: "some ",
            address    : [
                    street  : "Calle Xenia 24",
                    city    : "Salou",
                    province: Province.TARRAGONA,
                    zipcode : "43850",
                    country : "España"
            ],
            area       : 1.toLong(),
            hasCompany : true,
            website    : "www.prova.com"
    ]


    static Set<Area> INIT_AREA_LIST() {
        Set<Area> areas = []
        Arrays.asList("Restauración", "Animación", "Música", "Danza", "Eseñanza", "Idioma",
                "Cuidado de niños/mayores", "Fontanería", "Estética y cosmética", "Electricidad", "Consumo y alimentación", "Confección", "Carpintería", "Automoción",
                "Artesanía", "Artes plásticas", "Artes interpretativas", "Agricultura y Jardinería").stream().forEach((a) -> {
            areas << new Area(a)
        });
        return areas;
    }

    static Set<JobPosition> INIT_JOBPOSITION_LIST() {
        Set<JobPosition> jobPositions = []

        Arrays.asList("Camarero", "Cocinero", "Profesor de baile", "Profesor de mates", "Profesor de idioma", "Conductor",
                "Programador", "Profesor de deportes", "Electricista").stream().forEach((c) -> {
            jobPositions << new JobPosition(c);
        });
        return jobPositions
    }

    static Address INIT_ADDRESS() {
        Address add = new Address();
        add.setStreet("Calle Per Moreto 1");
        add.setCity("Cambrils");
        add.setZipcode("43850");
        add.setProvince(Province.TARRAGONA.getText());
        add.setCountry("España");
    }
}