package com.fatec.API3.Security;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Repository;

import com.fatec.API3.model.Alunos;
import com.fatec.API3.repository.AlunosRepository;

@Repository
@Transactional
public class ImplementsAlunosDetailsService implements UserDetailsService {
	
	@Autowired(required=true)
	private AlunosRepository AR;
	
	@Override
	public UserDetails loadUserByUsername(String login) throws UsernameNotFoundException {
		Alunos aluno = AR.findBylogin(login);
		
		if(aluno == null){
			throw new UsernameNotFoundException("Usuario não encontrado!");
		}
		return new User(aluno.getUsername(), aluno.getPassword(), true, true, true, true, aluno.getAuthorities());
	}

}
