package com.example.gof.service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;

import com.example.gof.model.Cliente;
import com.example.gof.model.ClienteRepository;
import com.example.gof.model.Endereco;
import com.example.gof.model.EnderecoRepository;

public class ClienteServiceImpl implements ClienteService {

	@Autowired
	private ClienteRepository clienteRepository;

	@Autowired
	private EnderecoRepository enderecoRepository;

	@Autowired
	private ViaCepService viacep;

	@Override
	public Iterable<Cliente> buscarTodos() {

		return clienteRepository.findAll();
	}

	@Override
	public Cliente buscarPorId(Long id) {
		Optional<Cliente> cliente = clienteRepository.findById(id);
		return cliente.get();
	}

	@Override
	public void inserir(Cliente cliente) {
		salvarClienteComCep(cliente);
	}


	@Override
	public void atualizar(Long id, Cliente cliente) {
		Optional<Cliente> clienteBd = clienteRepository.findById(id);
		if(clienteBd.isPresent()) {
			salvarClienteComCep(cliente);
		}

	}

	@Override
	public void deletar(Long id) {
		clienteRepository.deleteById(id);

	}
	
	private void salvarClienteComCep(Cliente cliente) {
		String cep = cliente.getEndereco().getCep();
		Endereco endereco = enderecoRepository.findById(cep).orElseGet(() -> {

			// Caso nao exista , integrar com via CEP e persistir o retorno
			Endereco novoEndereco = viacep.consultarCep(cep);
			enderecoRepository.save(novoEndereco);
			return null;
		});
		cliente.setEndereco(endereco);
		clienteRepository.save(cliente);
	}

}
