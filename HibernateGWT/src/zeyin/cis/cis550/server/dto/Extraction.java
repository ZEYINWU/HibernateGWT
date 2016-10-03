package zeyin.cis.cis550.server.dto;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

/**
 * An Extraction is a region of content that was extracted
 * (in part) for an Attrib-value pair
 * 
 * @author zives
 *
 */
@Entity
public class Extraction {
	int id;
	long start;
	int bytes;
	
	public Extraction() {
		id = 0;
		start = 0;
		bytes = 0;
	}
	
	public Extraction(long start, int bytes) {
		this();
		this.start = start;
		this.bytes = bytes;
	}
	
	@Id
	@GeneratedValue
	public int getId() {
		return id;
	}
	
	public void setId(int id) {
		this.id = id;
	}

	public long getStart() {
		return start;
	}

	public void setStart(long start) {
		this.start = start;
	}

	public int getBytes() {
		return bytes;
	}

	public void setBytes(int bytes) {
		this.bytes = bytes;
	}
	
	
}
