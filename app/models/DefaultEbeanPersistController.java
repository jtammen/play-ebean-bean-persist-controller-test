package models;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;

import javax.annotation.PreDestroy;
import javax.persistence.Entity;
import javax.persistence.PostLoad;
import javax.persistence.PostPersist;
import javax.persistence.PostRemove;
import javax.persistence.PostUpdate;
import javax.persistence.PrePersist;
import javax.persistence.PreUpdate;

import com.avaje.ebean.EbeanServer;
import com.avaje.ebean.Transaction;
import com.avaje.ebean.event.BeanPersistAdapter;
import com.avaje.ebean.event.BeanPersistRequest;

/**
 * Source: https://gist.github.com/benoit-ponsero/3761155
 * 
 * This is a <code>BeanPersist</code> that looks for methods annotated with the
 * JPA Annotations <code>@PrePersist</code> <code>@PostPersist</code>
 * <code>@PreUpdate</code> <code>@PostUpdate</code> <code>@PreDestroy</code>
 * <code>@PostLoad</code>
 * 
 * registers those methods with this Listener and calls them when necessary.
 */
public class DefaultEbeanPersistController extends BeanPersistAdapter {
	private final Map<String, Method> postLoadMap = new TreeMap<String, Method>();

	private final Map<String, Method> prePersistMap = new TreeMap<String, Method>();
	private final Map<String, Method> postPersistMap = new TreeMap<String, Method>();

	private final Map<String, Method> preUpdateMap = new TreeMap<String, Method>();
	private final Map<String, Method> postUpdateMap = new TreeMap<String, Method>();

	private final Map<String, Method> preDestroyMap = new TreeMap<String, Method>();
	private final Map<String, Method> postRemoveMap = new TreeMap<String, Method>();

	private void getAndInvokeMethod(final Map<String, Method> map,
			final BeanPersistRequest<?> request) {
		getAndInvokeMethod(map, request.getBean(), request.getEbeanServer(),
				request.getTransaction());
	}

	private void getAndInvokeMethod(final Map<String, Method> map,
			final Object bean) {
		getAndInvokeMethod(map, bean, null, null);
	}

	private void getAndInvokeMethod(final Map<String, Method> map,
			final Object bean, final EbeanServer ebeanServer,
			final Transaction transaction) {
		final Method m = map.get(bean.getClass().getName());
		if (m != null) {
			try {
				if (ebeanServer != null && transaction != null) {
					m.invoke(bean, ebeanServer, transaction);
				} else {
					m.invoke(bean);
				}
			} catch (final IllegalAccessException e) {
				e.printStackTrace();
			} catch (final InvocationTargetException e) {
				e.printStackTrace();
			}
		}
	}

	@Override
	public boolean isRegisterFor(final Class<?> aClass) {
		if (aClass.getAnnotation(Entity.class) != null) {

			final Method[] methods = aClass.getMethods();
			boolean hasListener = false;
			for (final Method m : methods) {

				if (m.isAnnotationPresent(PrePersist.class)) {
					prePersistMap.put(aClass.getName(), m);
					hasListener = true;
				}

				if (m.isAnnotationPresent(PostPersist.class)) {
					postPersistMap.put(aClass.getName(), m);
					hasListener = true;
				}

				if (m.isAnnotationPresent(PreDestroy.class)) {
					preDestroyMap.put(aClass.getName(), m);
					hasListener = true;
				}

				if (m.isAnnotationPresent(PreUpdate.class)) {
					preUpdateMap.put(aClass.getName(), m);
					hasListener = true;
				}

				if (m.isAnnotationPresent(PostUpdate.class)) {
					postUpdateMap.put(aClass.getName(), m);
					hasListener = true;
				}

				if (m.isAnnotationPresent(PostLoad.class)) {
					postLoadMap.put(aClass.getName(), m);
					hasListener = true;
				}

				if (m.isAnnotationPresent(PostRemove.class)) {
					postRemoveMap.put(aClass.getName(), m);
					hasListener = true;
				}
			}

			return hasListener;
		}
		return false;
	}

	@Override
	public void postDelete(final BeanPersistRequest<?> request) {
		// there is no @PostDestroy annotation in JPA 2, so we use @PostRemove?!
		getAndInvokeMethod(postRemoveMap, request);
		super.postDelete(request);
	}

	@Override
	public void postInsert(final BeanPersistRequest<?> request) {
		getAndInvokeMethod(postPersistMap, request);
		super.postInsert(request);
	}

	@Override
	public void postLoad(final Object bean,
			final Set<String> includedProperties) {
		getAndInvokeMethod(postLoadMap, bean);
		super.postLoad(bean, includedProperties);
	}

	@Override
	public void postUpdate(final BeanPersistRequest<?> request) {
		getAndInvokeMethod(postUpdateMap, request);
		super.postUpdate(request);
	}

	@Override
	public boolean preDelete(final BeanPersistRequest<?> request) {
		getAndInvokeMethod(preDestroyMap, request);
		return super.preDelete(request);
	}

	@Override
	public boolean preInsert(final BeanPersistRequest<?> request) {
		getAndInvokeMethod(prePersistMap, request);
		return super.preInsert(request);
	}

	@Override
	public boolean preUpdate(final BeanPersistRequest<?> request) {
		getAndInvokeMethod(preUpdateMap, request);
		return super.preUpdate(request);
	}
}
