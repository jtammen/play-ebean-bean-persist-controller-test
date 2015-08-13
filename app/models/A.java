package models;

import java.util.List;

import javax.annotation.PreDestroy;
import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.OneToMany;

import com.avaje.ebean.EbeanServer;
import com.avaje.ebean.Transaction;

import play.Logger;
import play.db.ebean.Model;

@Entity
public class A extends Model {
	public A(final String name) {
		this.name = name;
	}

	private static final long serialVersionUID = 1L;

	@Id
	public Long id;

	public final String name;

	@OneToMany(mappedBy = "parent", cascade = CascadeType.PERSIST)
	public List<B> children;

	@PreDestroy
	public void preDestroy(final EbeanServer server, final Transaction tx) {
		Logger.debug("A.preDestroy(server: {}, tx: {})", server, tx);

		server.delete(children.iterator(), tx);
	}
}
