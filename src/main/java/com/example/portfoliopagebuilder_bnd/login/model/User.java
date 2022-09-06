package com.example.portfoliopagebuilder_bnd.login.model;

import java.sql.Timestamp;

import javax.persistence.Entity;
import javax.persistence.Id;

import org.hibernate.annotations.CreationTimestamp;

import com.sun.istack.NotNull;

import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * oauth2 google user
 * id = provider_providerId
 * username = {sub}
 * email = {email}
 * role = "ROLE_USER"
 */
@Entity
@Data
@NoArgsConstructor
public class User {
	@Id
	private String id;
	@NotNull
	private String username;
	@NotNull
	private String email;
	@NotNull
	private String role;
	private String profile;


	@CreationTimestamp
	private Timestamp createDate;

	@Builder
	public User(String id, String username, String email, String role, Timestamp createDate) {
		this.id = id;
		this.username = username;
		this.email = email;
		this.role = role;
		this.createDate = createDate;
	}

	public User update(String username, String email){
		this.username = username;
		this.email = email;

		return this;
	}
}

