package com.one.mongoreactivehumanresources.data_services;

import com.one.mongoreactivehumanresources.documents.*;
import com.one.mongoreactivehumanresources.documents.enums.Level;
import com.one.mongoreactivehumanresources.documents.enums.Risk;
import com.one.mongoreactivehumanresources.exceptions.NotFoundException;
import com.one.mongoreactivehumanresources.repositories.*;
import org.apache.logging.log4j.LogManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.Date;

@Service
public class DatabaseSeederService {

    public static final String VARIOUS_CODE = "1";
    public static final String VARIOUS_NAME = "Various";
    public static final String CUSTOMER_POINTS_CODE = "0";
    public static final String CUSTOMER_POINTS_NAME = "Customer points";

    @Value("${one.admin.mobile}")
    private String mobile;
    @Value("${one.admin.username}")
    private String username;
    @Value("${one.admin.password}")
    private String password;

    private Environment environment;
    private UserRepository userRepository;
    private LanguageRepository languageRepository;
    private JobRepository jobRepository;
    private CompetencyRepository competencyRepository;
    private CandidateRepository candidateRepository;
    private EmployeeRepository employeeRepository;

    @Autowired
    public DatabaseSeederService(Environment environment, UserRepository userRepository,
                                 LanguageRepository languageRepository, JobRepository jobRepository,
                                 CompetencyRepository competencyRepository, CandidateRepository candidateRepository,
                                 EmployeeRepository employeeRepository) {
        this.userRepository = userRepository;
        this.environment = environment;
        this.languageRepository = languageRepository;
        this.jobRepository = jobRepository;
        this.competencyRepository = competencyRepository;
        this.candidateRepository = candidateRepository;
        this.employeeRepository = employeeRepository;
    }

    @PostConstruct
    public void constructor() {
        String[] profiles = this.environment.getActiveProfiles();
        if(Arrays.asList(profiles).contains("dev")) {
            this.deleteAllAndInitializeAndSeedDataBase();
        }
    }

    public void deleteAllAndInitializeAndSeedDataBase() {
        this.deleteAllAndInitialize();
        this.seedDataBaseJava();
    }

    public void seedDataBaseJava() {
        LogManager.getLogger(this.getClass()).warn("------- Initial Load from JAVA -----------");

        User[] users = {
                User.builder().mobile("8496388432").username("tomas25").password("1212").roles(Role.CUSTOMER).build(),
                User.builder().mobile("8095452578").username("sebastian25").password("1212").roles(Role.CUSTOMER).build(),
        };
        this.userRepository.saveAll(Arrays.asList(users));

        Language[] languages = {
                new Language("Español"),
                new Language("Ingles"),
                new Language("Frances")
        };
        this.languageRepository.saveAll(Arrays.asList(languages));

        Job[] jobs = {
            new Job("Programador Java", Risk.ALTO, 20000.0, 25000.0),
            new Job("Programador Android", Risk.MEDIO, 15000.0, 20000.0),
            new Job("Programadodor Angular", Risk.BAJO, 25000.0, 30000.0)
        };
        this.jobRepository.saveAll(Arrays.asList(jobs));

        Competency[] competencies = {
                new Competency("Git"),
                new Competency("Sql"),
                new Competency("Css")
        };
        this.competencyRepository.saveAll(Arrays.asList(competencies));

        Training[] trainings = {
            new Training("Certificacion", Level.TECNICO, LocalDateTime.of(2020, 3, 13,0,0,0),
                    LocalDateTime.of(2021, 3, 13, 0,0,0), "UNAPEC"),
            new Training("Doctorado", Level.DOCTORADO, LocalDateTime.of(2020, 3, 13, 0,0,0),
                    LocalDateTime.of(2021, 3, 13, 0,0,0), "INTEC")
        };

        WorkExperience[] workExperiences = {
            new WorkExperience("La sirena", "Director general", LocalDateTime.of(2020, 3, 13, 0, 0, 0),
                    LocalDateTime.of(2021, 3, 13, 0, 0, 0), 20000),
            new WorkExperience("La sirena", "Director general", LocalDateTime.of(2020, 3, 13, 0, 0, 0),
                    LocalDateTime.of(2021, 3, 13, 0, 0, 0), 20000)
        };

        Contact[] contacts = {
                new Contact("Sebastian Garcia Otaño", "8492388432"),
                new Contact("Flavia Otaño", "8492056080")
        };

        User user = this.userRepository.findByMobile("8496388432")
                .orElseThrow(() -> new NotFoundException("mobile not found" + mobile));

        Language[] languageCompetencies = {
                this.languageRepository.findByName("Español"),
                this.languageRepository.findByName("Ingles"),
        };

        Competency[] candidateCompetencies = {
                this.competencyRepository.findByDescription("Git"),
                this.competencyRepository.findByDescription("Sql"),
        };

        Job candidateJob = this.jobRepository.findByName("Programador Java");

        Candidate[] candidates = {
          new Candidate("Tomas Garcia Otaño", "40241349477", 20000, Arrays.asList(trainings), Arrays.asList(workExperiences),
                  Arrays.asList(contacts), user, Arrays.asList(languageCompetencies), Arrays.asList(candidateCompetencies), candidateJob)
        };
        this.candidateRepository.saveAll(Arrays.asList(candidates));

        Job employeeJob = this.jobRepository.findByName("Programador Java");

        Employee[] employees = {
                new Employee("Tomas Garcia Otaño",  "40241349477", 2000, user, employeeJob)
        };
        this.employeeRepository.saveAll(Arrays.asList(employees));
    }

    public void deleteAllAndInitialize() {
        LogManager.getLogger(this.getClass()).warn("------- Delete All -----------");
        this.userRepository.deleteAll();
        this.languageRepository.deleteAll();
        this.jobRepository.deleteAll();
        this.competencyRepository.deleteAll();
        this.candidateRepository.deleteAll();
        this.employeeRepository.deleteAll();
        
        this.initialize();
    }

    private void initialize() {
        if(!this.userRepository.findByMobile(this.mobile).isPresent()) {
            LogManager.getLogger(this.getClass()).warn("------- Create Admin -----------");
            User user = User.builder().mobile(this.mobile).username(this.username).password(password).roles(Role.ADMIN).build();
            this.userRepository.save(user);
        }
    }
}
