package in.ranjitkokare.springbootmongodb.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.mongodb.core.mapping.event.ValidatingMongoEventListener;
import org.springframework.validation.beanvalidation.LocalValidatorFactoryBean;

@Configuration	//run at starting of application
public class ValidationConfig {
	
	@Bean	//event listener >triggered before when we persisting any data to the database 
	public ValidatingMongoEventListener validationMongoEventListener() {
		return new ValidatingMongoEventListener(validator());//trigger constraint violation exception(null values)
	}
	
	@Bean	// implementation class for the validator
	public LocalValidatorFactoryBean validator() {
		return new LocalValidatorFactoryBean();
	}
}


