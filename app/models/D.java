package models;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.OneToOne;

import play.db.ebean.Model;

@Entity
public class D extends Model {
	public D(final String name, final C c) {
		this.name = name;
		this.c = c;
	}

	private static final long serialVersionUID = 1L;

	@Id
	public Long id;

	public final String name;

	@OneToOne(optional = false)
	public C c;
}
