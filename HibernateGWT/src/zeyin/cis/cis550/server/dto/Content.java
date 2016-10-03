package zeyin.cis.cis550.server.dto;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Lob;
import javax.persistence.OneToMany;

import org.hibernate.annotations.IndexColumn;

/**
 * A piece of content has an ID, a binary blob, and a set of
 * extracted attributes
 * 
 * @author zives
 *
 */
@Entity
public class Content {
	int id;
	
	byte[] blob;
	
	List<Attrib> attribs;
	
	public Content() {
		id = 0;
		blob = new byte[0];
		attribs = new ArrayList<Attrib>();
	}
	
	public Content(byte[] blob, List<Attrib> atts) {
		this();
		this.blob = blob;
		attribs = atts;
	}

	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	@Column(name="object", columnDefinition="blob", length=100000)
	@Lob
	public byte[] getBlob() {
		return blob;
	}

	public void setBlob(byte[] blob) {
		this.blob = blob;
	}

	@OneToMany(targetEntity=Attrib.class, cascade={CascadeType.ALL}, fetch=FetchType.EAGER)
	@IndexColumn(name = "attrib_inx")
	public List<Attrib> getAttribs() {
		return attribs;
	}

	public void setAttribs(List<Attrib> attribs) {
		this.attribs = attribs;
	}
	
	
}
