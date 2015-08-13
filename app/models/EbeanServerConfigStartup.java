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
			return cls.isAssignableFrom(B.class);
		}

		@Override
		public void postDelete(final BeanPersistRequest<?> request) {
			Logger.debug("B.postDelete(request: {})", request);
			super.postDelete(request);
		}

		@Override
		public boolean preDelete(final BeanPersistRequest<?> request) {
			Logger.debug("B.preDelete(request: {})", request);
			return super.preDelete(request);
		}
	}

	public final static class ChildBeanPersistListener implements BeanPersistListener<B> {
		@Override
		public boolean inserted(final B bean) {
			Logger.debug("B.inserted(bean: {})", bean);
			return false;
		}

		@Override
		public boolean updated(final B bean, final Set<String> updatedProperties) {
			Logger.debug("B.updated(bean: {})", bean);
			return false;
		}

		@Override
		public boolean deleted(final B bean) {
			Logger.debug("B.deleted(bean: {})", bean);
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
