package com.cursomc.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.stereotype.Service;

import com.cursomc.domain.Cliente;
import com.cursomc.dto.ClienteDTO;
import com.cursomc.repository.ClienteRepository;
import com.cursomc.service.exceptions.DataIntegrationException;
import com.cursomc.service.exceptions.ObjectNotFoundException;

@Service
public class ClienteService {

	@Autowired
	private ClienteRepository clienteRepository;
	
	public Cliente find(Integer id){
		Optional<Cliente> cliente = clienteRepository.findById(id);
		return cliente.orElseThrow(() -> new ObjectNotFoundException(
				"Objeto não encontrado! Id: " + id + " Tipo: " + Cliente.class.getName()));	
	}

	public List<Cliente> findAll() {
		return clienteRepository.findAll();
	}

	public Cliente fromDto(ClienteDTO clienteDTO) {
		return new Cliente(clienteDTO.getId(), clienteDTO.getNome(), clienteDTO.getEmail(), null, null);
	}

	public Cliente inserir(Cliente cliente) {
		cliente.setId(null);
		clienteRepository.save(cliente);
		return cliente;
	}

	public Cliente update(Cliente cliente) {
		Cliente clienteSalvo = find(cliente.getId());
		updateDate(clienteSalvo, cliente);
		return clienteRepository.save(clienteSalvo);
	}

	private void updateDate(Cliente clienteSalvo, Cliente cliente) {
		clienteSalvo.setNome(cliente.getNome());
		clienteSalvo.setEmail(cliente.getEmail());
	}

	public void delete(Integer id) {
		find(id);
			try {
				clienteRepository.deleteById(id);
			} catch (DataIntegrityViolationException e) {
				throw new DataIntegrationException("Não é possível excluir um cliente porque há entidades relacionadas");
			}
	}

	public Page<Cliente> findPage(Integer page, Integer linesPage, String orderBy, String direction) {
		PageRequest pageRequest = PageRequest.of(page, linesPage, Direction.valueOf(direction), orderBy);
		return clienteRepository.findAll(pageRequest);
	}
	
	
}
