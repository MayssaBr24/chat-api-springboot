package tn.essat.messaging.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import tn.essat.messaging.dao.IMessage;
import tn.essat.messaging.dao.IUtilisateur;
import tn.essat.messaging.model.Message;
import tn.essat.messaging.model.Utilisateur;


@CrossOrigin("*")
@RequestMapping("/messagerie")
@RestController
public class AppREST {

@Autowired
IUtilisateur dao_u;
@Autowired
IMessage dao_m;


@GetMapping("/utilisateurs")
public List<Utilisateur> f1() {
return dao_u.findAll(); }

@GetMapping("/utilisateur/{id}")
public Utilisateur f2(@PathVariable int id) {
return dao_u.findById(id).get();
}

@GetMapping("/messagesrecus/{id}")
public List<Message> f3(@PathVariable("id")Integer id ){
return dao_m.getAllMessagsReceive(id);

}
@GetMapping("/messagesenvoyes/{id}")
public List<Message> f4(@PathVariable("id")Integer id ){
return dao_m.getAllMessagsSend(id);

}

    @GetMapping("/message/{id}")
    public Message f5(@PathVariable int id) {
return dao_m.findById(id).get();
}
   
    @PostMapping("/addMessage")
    public void f6(@RequestBody Message m) {
    dao_m.save(m);
    }
   
    @DeleteMapping("/delMessage/{id}")
    public void f7(@PathVariable int id) {
    dao_m.deleteById(id);
    }

    @GetMapping("/delMessageRecu")
    public void f8(@PathVariable int id) {
    Message m = dao_m.findById(id).get();
    if (m.getEtat() == 2) {
m.setEtat(4);
    }else {
        m.setEtat(3);
    }
    dao_m.save(m);

    }
}

