package com.test.usermanagement.model;

import java.util.Date;

import org.springframework.format.annotation.DateTimeFormat;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonFormat.Shape;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "USER_INFO")
public class User {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Integer id;

	@Column(name = "FIRST_NAME")
	private String firstName;

	@Column(name = "LAST_NAME")
	private String lastName;

	@Column(name = "EMAIL")
	private String email;

	@Column(name = "STATUS")
	private Boolean status;

	@Column(name = "MOBILE_NUMBER")
	private String mobileNumber;

	@Column(name = "DOB")
	@DateTimeFormat(pattern = "yyyy-MM-dd")
	@JsonFormat(shape = Shape.STRING, pattern = "yyyy-MM-dd", timezone = "Asia/Kolkata")
	private Date dob;

	@Column(name = "ADDRESS")
	private String address;

}
