package jp.co.abemasa.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

import lombok.Data;

@Entity
@Table(name = "m_class")
@Data
public class MClassEntity {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	//	会場名
	@Column(name = "venue_name")
	private String venueName;
	//	学年
	@Column(name = "grade")
	private String grade;
	//	クラス名
	@Column(name = "class_name")
	private String className;

}
