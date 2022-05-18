package com.iquinto.workingstudent;

import com.iquinto.workingstudent.service.DataService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;
import org.springframework.core.io.ClassPathResource;
import org.springframework.jdbc.datasource.init.ResourceDatabasePopulator;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.PreDestroy;
import javax.sql.DataSource;
import java.io.IOException;
import java.text.ParseException;

@SpringBootApplication
public class WorkingStudentApplication {

	@Autowired
	private DataSource dataSource;

	@Value("${spring.profiles.active}")
	private String activeProfile;

	@Value("${server.port}")
	private int port;

	@Autowired
	DataService dataService;

	public static void main(String[] args) {
		SpringApplication.run(WorkingStudentApplication.class, args);
	}


	@Transactional
	@EventListener(ApplicationReadyEvent.class)
	public void doSomethingAfterStartup() throws ParseException, IOException {
		ResourceDatabasePopulator resourceDatabasePopulator =  null;
		if(activeProfile.equals("test")){
			dataService.populateData();
		} else {
			resourceDatabasePopulator = new ResourceDatabasePopulator(false, false,
					"UTF-8", new ClassPathResource("scripts/schema.sql"));
			resourceDatabasePopulator.execute(dataSource);
			dataService.populateData();
		}

		System.out.println("working-student server is running at  http://localhost:" + port + "/api  [profile : " + activeProfile + "]");
	}

}
