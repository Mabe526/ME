package jp.co.abemasa.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import jp.co.abemasa.entity.TEntryEntity;

public interface TEntryRepository extends JpaRepository<TEntryEntity, Long> {

}
