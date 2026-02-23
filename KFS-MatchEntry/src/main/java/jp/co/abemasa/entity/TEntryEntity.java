package jp.co.abemasa.entity;

import java.time.LocalDateTime;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

import lombok.Data;

@Entity
@Table(name = "t_entry")
@Data
public class TEntryEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "full_name", nullable = false)
    private String fullName;

    @Column(name = "kana_name", nullable = false)
    private String kanaName;

    @Column(name = "grade", nullable = false)
    private String grade;

    @Column(name = "venue", nullable = false)
    private String venue;

    @Column(name = "participate", nullable = false)
    private String participate;

    @Column(name = "participating_class")
    private String participatingClass;
    
    @Column(name = "insert_datetime", nullable = false)
    private LocalDateTime insertDatetime;
}
