package annotations;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

@Retention(RetentionPolicy.RUNTIME)
public @interface TextCall {
	
	String value();
	
	boolean createNewOnEachCall() default false;

}
