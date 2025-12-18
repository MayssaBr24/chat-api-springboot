package tn.essat.messaging.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import tn.essat.messaging.model.Utilisateur;


@Repository
public interface IUtilisateur  extends JpaRepository<Utilisateur,Integer>{

	public Utilisateur findByUsername(String username);
	
}
