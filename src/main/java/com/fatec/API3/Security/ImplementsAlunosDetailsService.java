package com.fatec.API3.Security;

import org.springframework.security.core.userdetails.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import com.fatec.API3.model.Alunos;
import com.fatec.API3.repository.AlunosRepository;

public class ImplementsAlunosDetailsService implements UserDetailsService {
	
	@Autowired
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
