package com.projeto.helpapet.model.services.validation;


	import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import javax.validation.Constraint;
import javax.validation.Payload; 
	 
	@Constraint(validatedBy = AdotanteUpdateValidator.class) 
	@Target({ ElementType.TYPE })
	@Retention(RetentionPolicy.RUNTIME) 
	
	public @interface AdotanteUpdate { 
	 String message() default "Erro de validação"; 
	 
	    Class<?>[] groups() default {}; 
	    Class<? extends Payload>[] payload() default {}; 
}
