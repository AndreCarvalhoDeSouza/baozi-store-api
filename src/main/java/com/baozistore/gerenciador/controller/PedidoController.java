package com.baozistore.gerenciador.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.DeleteMapping;
import jakarta.validation.Valid;

import com.baozistore.gerenciador.repository.PedidoRepository;
import com.baozistore.gerenciador.repository.ClienteRepository;
import com.baozistore.gerenciador.repository.ProdutoRepository;
import com.baozistore.gerenciador.model.Pedido;
import com.baozistore.gerenciador.model.Cliente;
import com.baozistore.gerenciador.model.Produto;

@RestController
@RequestMapping("/pedidos")
public class PedidoController {
	
	@Autowired
	private PedidoRepository pedidoRepository;
	
	@Autowired
	private ClienteRepository clienteRepository;
	
	@Autowired
	private ProdutoRepository produtoRepository;
	
	@PostMapping
	public ResponseEntity <Pedido> create(@Valid @RequestBody Pedido pedido) {
		if(pedido.getCliente().getId() == null) {
			throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "ID do cliente deve ser informado");
		}
		
		if(pedido.getProduto().getId() == null) {
			throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "ID do produto deve ser informado");
		}
		
		Cliente cliente = clienteRepository.findById(
			pedido.getCliente().getId()
		).orElseThrow(() ->
			new ResponseStatusException(HttpStatus.NOT_FOUND, "Cliente não encontrado")
		);
		
		Produto produto = produtoRepository.findById(
				pedido.getProduto().getId()
			).orElseThrow(() ->
				new ResponseStatusException(HttpStatus.NOT_FOUND, "Produto não encontrado")
		);
		
		pedido.setCliente(cliente);
		pedido.setProduto(produto);
		
		return ResponseEntity.status(HttpStatus.CREATED).body(pedidoRepository.save(pedido));
	}
	
	@GetMapping
	public List<Pedido> findAll() {
		return pedidoRepository.findAll();
	}
	
	@GetMapping("/{id}")
	public ResponseEntity<Pedido> findById(@PathVariable Long id) {
		return pedidoRepository.findById(id).map(ResponseEntity::ok).orElse(ResponseEntity.notFound().build());
	}
	
	@PutMapping("/{id}")
	public ResponseEntity<Pedido> update(@PathVariable Long id, @Valid @RequestBody Pedido pedido) {
		
	    return pedidoRepository.findById(id).map(record -> {
			
			if(pedido.getCliente().getId() == null) {
				throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "ID do cliente deve ser informado");
			}
		
			if(pedido.getProduto().getId() == null) {
				throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "ID do produto deve ser informado");
			}

	        Cliente cliente = clienteRepository.findById(
	            pedido.getCliente().getId()
	        ).orElseThrow(() ->
	            new ResponseStatusException(HttpStatus.NOT_FOUND, "Cliente não encontrado")
	        );

	        Produto produto = produtoRepository.findById(
	            pedido.getProduto().getId()
	        ).orElseThrow(() ->
	            new ResponseStatusException(HttpStatus.NOT_FOUND, "Produto não encontrado")
	        );

	        record.setCliente(cliente);
	        record.setProduto(produto);
	        record.setQuantidade(pedido.getQuantidade());

	        return ResponseEntity.ok(pedidoRepository.save(record));

	    }).orElse(ResponseEntity.notFound().build());
	}
	
	@DeleteMapping("/{id}")
	public ResponseEntity<Void> delete(@PathVariable Long id) {
		 if (!pedidoRepository.existsById(id)) {
			 return ResponseEntity.notFound().build();
		 }
		
		pedidoRepository.deleteById(id);
		
		return ResponseEntity.noContent().build();
	}
	
}
