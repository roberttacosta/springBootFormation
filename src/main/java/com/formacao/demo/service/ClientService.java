package com.formacao.demo.service;

import com.formacao.demo.domain.Client;
import com.formacao.demo.domain.Transaction;
import com.formacao.demo.repository.AccountRepository;
import com.formacao.demo.repository.ClientRepository;
import com.formacao.demo.service.excepetion.ObjectNotFoundExcepetion;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ClientService {


    private ClientRepository clientRepository;

    private AccountRepository accountRepository;

    public Client find(Integer id){
        Optional<Client> obj = clientRepository.findById(id);
        return obj.orElseThrow(() -> new ObjectNotFoundExcepetion("Objeto não encontrado:" + id + ". Tipo:" + Client.class.getName()));
    }

    public Client findByCPF(String CPF){
        Optional<Client> obj = Optional.ofNullable(clientRepository.findByCpf(CPF));
        return obj.orElseThrow(() -> new ObjectNotFoundExcepetion("Objeto não encontrado:" + CPF + ". Tipo:" + Client.class.getName()));
    }

    public Client insert (Client obj){
        obj.setIdClient(null);
        return clientRepository.save(obj);
    }

    public void updateData (Client newObj, Client obj) {
        newObj.setName(obj.getName());
    }

    public Client update(Client obj) {
        Client newObj = find(obj.getIdClient());
        updateData(newObj, obj);
        return clientRepository.save(newObj);
    }

    public void delete(Integer id) {
        find(id);
        clientRepository.deleteById(id);
    }

    public List<Client> findAll(){
        return clientRepository.findAll();
    }
}
