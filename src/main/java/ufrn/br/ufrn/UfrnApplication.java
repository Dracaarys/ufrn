package ufrn.br.ufrn;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.ServletComponentScan;


@ServletComponentScan
@SpringBootApplication
public class UfrnApplication {

	public static void main(String[] args) {
		SpringApplication.run(UfrnApplication.class, args);
	}

}
