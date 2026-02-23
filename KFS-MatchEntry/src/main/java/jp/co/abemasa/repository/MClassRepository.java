package jp.co.abemasa.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import jp.co.abemasa.entity.MClassEntity;

public interface MClassRepository extends JpaRepository<MClassEntity, Long> {
	
	List<MClassEntity> findByVenueNameInAndGrade(List<String> venueNames, String grade);

}
