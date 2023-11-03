package com.kbouali.mongo;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;

import java.util.List;

import static com.kbouali.mongo.Gender.MALE;
import static java.math.BigDecimal.TEN;
import static java.time.LocalDateTime.now;

@SpringBootApplication
public class MongoApplication {

    public static void main(String[] args) {
        SpringApplication.run(MongoApplication.class, args);
    }

    @Bean
    CommandLineRunner runner(StudentRepository repository, MongoTemplate template) {
        return args -> {
            Address address = new Address(
                    "Morocco",
                    "Salé",
                    "11030"
            );
            String email = "khalil@mail.com";
            Student student = new Student(
                    "Khalil",
                    "Bouali",
                    email,
                    MALE,
                    address,
                    List.of("Computer Science", "Maths"),
                    TEN,
                    now()
            );

            // usingMongoTemplateAndQuery(repository, template, email, student);

            repository.findStudentByEmail(email)
                    .ifPresentOrElse(s -> {
                        System.out.println("Student with email: " + email + " already exists.");
                    }, () -> {
                        System.out.println("Inserting student with email: " + email);
                        repository.insert(student);
                    });
        };
    }

    private void usingMongoTemplateAndQuery(StudentRepository repository, MongoTemplate template, String email, Student student) {
        Query query = new Query();
        query.addCriteria(Criteria.where("email").is(email));

        List<Student> students = template.find(query, Student.class);

        if (students.size() > 1) {
            throw new IllegalStateException("Found many students with email " + email);
        }

        if (students.isEmpty()) {
            System.out.println("Inserting student with email: " + email);
            repository.insert(student);
        } else {
            System.out.println("Student with email: " + email + " already exists.");
        }
    }
}
