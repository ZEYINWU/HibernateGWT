package zeyin.cis.cis550.server.dto;

import java.util.HashSet;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToMany;

/**
 * An Attrib is an extracted attribute-value pair from Content
 * 
 * @author zives
 *
 */
@Entity
public class Attrib {
	int id;
	String key;
	String value;
	Set<Extraction> extractions;
	
	public Attrib() {
		id = 0;
		key = "";
		value = "";
		extractions = new HashSet<Extraction>();
	}
	
	public Attrib(String key, String value) {
		this();
		this.key = key;
		this.value = value;
	}

	@Id
	@GeneratedValue
	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	@Column(name="label")
	public String getKey() {
		return key;
	}

	public void setKey(String key) {
		this.key = key;
	}

	public String getValue() {
		return value;
	}

	public void setValue(String value) {
		this.value = value;
	}

	@OneToMany(cascade={CascadeType.ALL}, targetEntity=Extraction.class)
	public Set<Extraction> getExtractions() {
		return extractions;
	}

	public void setExtractions(Set<Extraction> extractions) {
		this.extractions = extractions;
	}
	
	
}
