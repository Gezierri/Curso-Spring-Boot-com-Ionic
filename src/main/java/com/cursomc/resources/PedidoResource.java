package com.cursomc.resources;

import java.net.URI;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import com.cursomc.domain.Pedido;
import com.cursomc.service.PedidoService;

@RestController
@RequestMapping("/pedidos")
public class PedidoResource {

	@Autowired
	private PedidoService pedidoService;

	@GetMapping("/{id}")
	public ResponseEntity<Pedido> find(@PathVariable Integer id) {
		Pedido pedidoSalvo = pedidoService.find(id);
		return ResponseEntity.status(HttpStatus.OK).body(pedidoSalvo);
	}

//	@GetMapping
//	public ResponseEntity<List<Pedido>> list() {
//		List<Pedido> list = pedidoService.findAll();
//		return ResponseEntity.ok().body(list);
//	}

	@PostMapping
	public ResponseEntity<Pedido> inser(@Valid @RequestBody Pedido pedido) {
		pedido = pedidoService.insert(pedido);
		URI uri = ServletUriComponentsBuilder.fromCurrentRequestUri().path("/{id}").buildAndExpand(pedido.getId())
				.toUri();
		return ResponseEntity.created(uri).build();
	}

	@GetMapping
	public ResponseEntity<Page<Pedido>> findPage(@RequestParam(value = "page", defaultValue = "0") Integer page,
			@RequestParam(value = "linesPerPage", defaultValue = "24") Integer linesPerPage,
			@RequestParam(value = "orderBy", defaultValue = "instante") String direction,
			@RequestParam(value = "direction", defaultValue = "DESC") String orderBy) {
		Page<Pedido> list = pedidoService.findPage(page, linesPerPage, direction, orderBy);
		return ResponseEntity.ok(list);
	}
}
