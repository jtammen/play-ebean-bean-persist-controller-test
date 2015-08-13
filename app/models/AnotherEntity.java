package models;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.OneToOne;

import play.db.ebean.Model;

@Entity
public class AnotherEntity extends Model {
	public AnotherEntity(final String name) {
		this.name = name;
	}

	private static final long serialVersionUID = 1L;

	@Id
	public Long id;

	public final String name;

	@OneToOne(optional = false, mappedBy = "parent")
	public DependentEntitiy dep;
}
