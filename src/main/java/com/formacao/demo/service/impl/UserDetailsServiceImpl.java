package com.formacao.demo.service.impl;

import com.formacao.demo.domain.Client;
import com.formacao.demo.repository.ClientRepository;
import com.formacao.demo.security.UserSpringSecurity;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class UserDetailsServiceImpl implements UserDetailsService {
    private ClientRepository clientRepository;

    public UserDetailsServiceImpl(ClientRepository clientRepository){
        this.clientRepository = clientRepository;
    }


    @Override
    public UserDetails loadUserByUsername(String cpf) throws UsernameNotFoundException {
        Client client = clientRepository.findByCpf(cpf);
        if(client == null){
            throw new UsernameNotFoundException(cpf);
        }
        return new UserSpringSecurity(client.getId(), client.getCpf(), client.getPassword(), client.getProfiles());
    }
}
