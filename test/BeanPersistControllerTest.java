
import static play.test.Helpers.fakeApplication;
import static play.test.Helpers.inMemoryDatabase;
import static play.test.Helpers.running;

import org.junit.Test;

import models.A;
import models.B;
import models.C;
import models.D;

public class BeanPersistControllerTest {
	@Test
	public void testPreDeleteTransaction() {
		running(fakeApplication(inMemoryDatabase()), new Runnable() {
			@Override
			public void run() {
				final A a = new A("Parent");
				a.children.add(new B("B"));
				a.save();

				final C c = new C("C");
				c.save();
				new D("D", c).save();

				a.delete();
			}
		});
	}
}
