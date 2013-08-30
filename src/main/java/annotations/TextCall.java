package annotations;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

/**<p>The TextCall Annotation is used to mark you test help methods with a plain text expression 
 * that can be used in a .atc file in order to call the method.</p>
 * 
 * <p>The calss that this method belongs to should only have one constructor but you can use the 
 * {@link AutomatedTestConstructer} Annotation if you have more than one in order to tell framework 
 * which one it should use</p>
 * 
 * <p>The TextCall Annotation has a mandatory value attribute and optional createNewOnEachCall</p>
 * 
 * 
 * @author roblovell
 *
 */
@Retention(RetentionPolicy.RUNTIME)
public @interface TextCall {
	
	/**<p>This is the expression that will be used in order to match the method by plain text.</p>
	 * 
	 * <p>The expression can be in any format over one line with the use of '?' as place holders for entering 
	 * in the parameter values in the plain text so if you had the expression </p>
	 * <pre>
	 * {@code @TextCall(value = "click button ?")
	 *     public void click(String buttonName)
	 *     {
	 *     ...
	 *     }</pre>
	 * <p>Then the text in you file could be.</p>
	 * 
	 * 
	 * click button "continue"
	 * 
	 * 
	 * @return The expression that will be used in order to match the method
	 */
	String value();
	
	/**<p>This is a flag to instruct the framework that you want a new instance of the class loaded each time
	 * the method is called</p>
	 * 
	 * <p>This enables you to then use other page object frameworks which rely on you being on the page before 
	 * you initiate the object</p>
	 * 
	 * 
	 * @return true if you need the obejct to be recreated for every method call
	 */
	boolean createNewOnEachCall() default false;

}
