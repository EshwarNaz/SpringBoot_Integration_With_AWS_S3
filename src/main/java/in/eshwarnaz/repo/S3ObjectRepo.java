package in.eshwarnaz.repo;

import org.springframework.data.jpa.repository.JpaRepository;

import in.eshwarnaz.entity.S3Entity;

public interface S3ObjectRepo extends JpaRepository<S3Entity, Integer>{

}
