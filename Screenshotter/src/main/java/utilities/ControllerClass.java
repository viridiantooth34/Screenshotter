package utilities;

import org.testng.annotations.Test;

public class ControllerClass {

	@Test

	public void controller() {

		DummyStringReturn st = new DummyStringReturn();

		// Call the method to set the status value

		st.itIsaMethod();

		// Print the status value

		System.out.println(DummyStringReturn.status);

	}

}
