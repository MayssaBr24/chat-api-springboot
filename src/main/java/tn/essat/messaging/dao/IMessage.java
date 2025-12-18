package tn.essat.messaging.dao;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import tn.essat.messaging.model.Message;

@Repository
public interface IMessage extends JpaRepository<Message, Integer> {

    @Query("select m from Message m where m.usersend.id = ?1 and m.etat != 3 and m.etat != 4")
    List<Message> getAllMessagsSend(int id);

    @Query("select m from Message m where m.userreceive.id = ?1 and m.etat != 1 and m.etat != 2 and m.etat != 4")
    List<Message> getAllMessagsReceive(int id);
}
