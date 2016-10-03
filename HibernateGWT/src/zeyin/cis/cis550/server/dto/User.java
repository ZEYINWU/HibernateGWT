package zeyin.cis.cis550.server.dto;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import org.hibernate.annotations.IndexColumn;

/**
 * Simple representation of a person
 * 
 * @author zives
 *
 */
@Entity
@Table(name="USER")
public class User {
	int id;
	String name;
	List<Content> content;
	Set<User> follows = new HashSet<User>();
	
	@Id
	@GeneratedValue
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	
	@Column(name = "name")
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	
	@OneToMany(cascade={CascadeType.ALL},fetch=FetchType.EAGER)
	@IndexColumn(name="content_order")
	public List<Content> getContent() {
		return content;
	}
	public void setContent(List<Content> content) {
		this.content = content;
	}
	
	// TODO: add support for getFollows() with the following
	// constraints
	@ManyToMany(cascade={CascadeType.ALL},fetch=FetchType.LAZY)
	@Column(name = "follows")
	public Set<User> getFollows() {
		return follows;
	}
	public void follow(User user) {
		this.follows.add(user);
	}
	public void setFollows(Set<User> follows) {
		this.follows = follows;
	}

	public User(String name) {
		this();
		this.name = name;
	}
	public User() {
		id = 0;
		this.name = "";
		content = new ArrayList<Content>();
	}
}
