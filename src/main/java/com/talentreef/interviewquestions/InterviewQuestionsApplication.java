package com.talentreef.interviewquestions;

import com.talentreef.interviewquestions.takehome.respositories.WidgetRepository;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@SpringBootApplication
@EnableJpaRepositories(basePackageClasses = WidgetRepository.class)
@EntityScan(basePackages = "com.talentreef.interviewquestions.takehome.models")
public class InterviewQuestionsApplication {

  public static void main(String[] args) {
    SpringApplication.run(InterviewQuestionsApplication.class, args);
  }

}
