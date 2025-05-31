package com.libros.sthephenking;

import com.libros.sthephenking.principal.Principal;
import com.libros.sthephenking.repository.LibroRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.AutoConfigureOrder;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class SthephenkingApplication{

	public static void main(String[] args) {
		SpringApplication.run(SthephenkingApplication.class, args);
	}

}
