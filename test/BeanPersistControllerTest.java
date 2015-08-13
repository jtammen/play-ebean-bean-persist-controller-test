
import static play.test.Helpers.fakeApplication;
import static play.test.Helpers.inMemoryDatabase;
import static play.test.Helpers.running;

import org.junit.Test;

import models.AnotherEntity;
import models.Child;
import models.DependentEntitiy;
import models.Parent;

public class BeanPersistControllerTest {
	@Test
	public void testPreDeleteTransaction() {
		running(fakeApplication(inMemoryDatabase()), new Runnable() {
			@Override
			public void run() {
				final Parent p = new Parent("Parent");
				p.children.add(new Child("C1"));
				p.save();

				final AnotherEntity ae = new AnotherEntity("AE1");
				ae.save();
				new DependentEntitiy("DE1", ae).save();

				p.delete();
			}
		});
	}
}
