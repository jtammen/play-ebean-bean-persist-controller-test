package models;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.OneToOne;

import play.db.ebean.Model;

@Entity
public class DependentEntitiy extends Model {
	public DependentEntitiy(final String name, final AnotherEntity parent) {
		this.name = name;
		this.parent = parent;
	}

	private static final long serialVersionUID = 1L;

	@Id
	public Long id;

	public final String name;

	@OneToOne(optional = false)
	public AnotherEntity parent;
}
