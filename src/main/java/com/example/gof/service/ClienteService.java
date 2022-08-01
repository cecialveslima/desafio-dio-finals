package com.example.gof.service;


import org.springframework.beans.factory.annotation.Autowired;

import com.example.gof.model.Cliente;


/***
 * Interface que define o padrão Strategy no domínio de Cliente. Com isso, se necessário, podemos ter múltiplas
 * implementações dessa mesma interface
 * @author Cecilia
 *
 */

public interface ClienteService {
	@Autowired //(required=true)
	
	Iterable<Cliente> buscarTodos();
	
	Cliente buscarPorId(Long id);
	
	void inserir(Cliente cliente);
	
	void atualizar(Long id, Cliente cliente);
	
	void deletar(Long id);

}
