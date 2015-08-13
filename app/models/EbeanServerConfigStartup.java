package models;

import java.util.Set;

import com.avaje.ebean.config.ServerConfig;
import com.avaje.ebean.event.BeanPersistAdapter;
import com.avaje.ebean.event.BeanPersistListener;
import com.avaje.ebean.event.BeanPersistRequest;
import com.avaje.ebean.event.ServerConfigStartup;

import play.Logger;

public class EbeanServerConfigStartup implements ServerConfigStartup {
	public static final class ChildBeanPersistController extends BeanPersistAdapter {
		@Override
		public boolean isRegisterFor(final Class<?> cls) {
			return cls.isAssignableFrom(Child.class);
		}

		@Override
		public void postDelete(final BeanPersistRequest<?> request) {
			Logger.debug("ChildBeanPersistController.postDelete(request: {})", request);
			super.postDelete(request);
		}

		@Override
		public boolean preDelete(final BeanPersistRequest<?> request) {
			Logger.debug("ChildBeanPersistController.preDelete(request: {})", request);
			return super.preDelete(request);
		}
	}

	public final static class ChildBeanPersistListener implements BeanPersistListener<Child> {
		@Override
		public boolean inserted(final Child bean) {
			Logger.debug("ChildBeanPersistListener.inserted(bean: {})", bean);
			return false;
		}

		@Override
		public boolean updated(final Child bean, final Set<String> updatedProperties) {
			Logger.debug("ChildBeanPersistListener.updated(bean: {})", bean);
			return false;
		}

		@Override
		public boolean deleted(final Child bean) {
			Logger.debug("ChildBeanPersistListener.deleted(bean: {})", bean);
			return false;
		}

		@Override
		public void remoteInsert(final Object id) {
		}

		@Override
		public void remoteUpdate(final Object id) {
		}

		@Override
		public void remoteDelete(final Object id) {
		}
	}

	@Override
	public void onStart(final ServerConfig serverConfig) {
		serverConfig.add(new ChildBeanPersistListener());

		serverConfig.add(new ChildBeanPersistController());
	}
}
