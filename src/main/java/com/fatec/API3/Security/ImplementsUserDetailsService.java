package com.fatec.API3.Security;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Repository;

import com.fatec.API3.model.Alunos;
import com.fatec.API3.model.Professor;
import com.fatec.API3.model.Usuario;
import com.fatec.API3.repository.AlunosRepository;
import com.fatec.API3.repository.ProfessorRepository;

@Repository
@Transactional
public class ImplementsUserDetailsService implements UserDetailsService {
	
	@Autowired(required=true)
	private AlunosRepository AR;
	private ProfessorRepository PR;
	
	@Override
	public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
		
		
		Alunos aluno = AR.findByemail(email);
		Professor professor = PR.findByemail(email);
		
		if(professor == null && aluno == null){
			throw new UsernameNotFoundException("Usuario não encontrado!");
		}
		
		if (aluno != null && aluno.getEmail().equals(email)) {
				Usuario.tipoUsu = "aluno";
				Usuario.idUsu = aluno.getId();
				return (UserDetails) aluno;
		}
		
		if (professor != null && professor.getEmail().equals(email)) {
				Usuario.tipoUsu = "professor";
				Usuario.idUsu = professor.getId();
				return (UserDetails) professor;
	}
		
		
		
	return null;
		

}
}