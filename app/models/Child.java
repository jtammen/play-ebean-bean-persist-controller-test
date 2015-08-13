package models;

import javax.annotation.PreDestroy;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.ManyToOne;

import com.avaje.ebean.EbeanServer;
import com.avaje.ebean.Query;
import com.avaje.ebean.Transaction;

import play.Logger;
import play.db.ebean.Model;

@Entity
public class Child extends Model {
	public Child(final String name) {
		this.name = name;
	}

	private static final long serialVersionUID = 1L;

	@Id
	public Long id;

	public final String name;

	@ManyToOne(optional = false)
	public Parent parent;

	@PreDestroy
	public void preDestroy(final EbeanServer server, final Transaction tx) {
		Logger.debug("Child.preDestroy(server: {}, tx: {})", server, tx);

		final Query<AnotherEntity> query = server.find(AnotherEntity.class);
		server.delete(server.findList(query, tx).iterator(), tx);
	}
}
