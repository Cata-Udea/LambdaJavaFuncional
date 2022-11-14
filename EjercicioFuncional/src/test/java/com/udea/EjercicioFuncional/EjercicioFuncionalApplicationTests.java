package com.udea.EjercicioFuncional;

import com.udea.EjercicioFuncional.dao.ClienteDao;
import com.udea.EjercicioFuncional.dao.PedidoDao;
import com.udea.EjercicioFuncional.dao.ProductoDao;
import com.udea.EjercicioFuncional.entidad.Pedido;
import com.udea.EjercicioFuncional.entidad.Producto;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDate;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Slf4j
@DataJpaTest
class EjercicioFuncionalApplicationTests {

	@Autowired
	private ClienteDao clienteDao;

	@Autowired
	private PedidoDao pedidoDao;

	@Autowired
	private ProductoDao productoDao;

	@Test
	@DisplayName("Obtener una lista de productos con categoría = \"Libros\" y precio < 50")
	public void exerciseEj() {
		long startTime = System.currentTimeMillis();
		List<Producto> result = productoDao.findAll()
				.stream()
				.filter(p -> p.getCategoria().equalsIgnoreCase("Libros"))
				.filter(p -> p.getPrecio() < 50 )
				.collect(Collectors.toList());
		long endTime = System.currentTimeMillis();

		log.info(String.format("ejercicio de prueba - tiempo de ejecución: %1$d ms", (endTime - startTime)));
		result.forEach(p -> log.info(p.toString()));
	}

	@Test
	@DisplayName("Obtener una lista de 5 pedidos mas recientes")
	public void exercise1() {
		long startTime = System.currentTimeMillis();
		List<Pedido> pedidos = pedidoDao.findAll()
				.stream()
				.sorted(Comparator.comparing(Pedido::getPedidoFecha).reversed())
				.collect(Collectors.toList());
		for (int i = 0; i < 5; i++) {
			log.info(pedidos.get(i).toString());
		}
		long endTime = System.currentTimeMillis();

		log.info(String.format("ejercicio 1 - tiempo de ejecución: %1$d ms", (endTime - startTime)));
		//pedidos.forEach(p -> log.info(p.toString()));
	}

	@Test
	@DisplayName("Obtener una lista de productos juguetes y 15% dscto")
	public void exercise2() {
		long startTime = System.currentTimeMillis();
		List<Producto> productos = productoDao.findAll()
				.stream()
				.filter(p -> p.getCategoria().equalsIgnoreCase("Juguetes"))
				.map(p -> {
					p.setPrecio(p.getPrecio() * 0.85);
					return p;
				})
				.collect(Collectors.toList());
		long endTime = System.currentTimeMillis();

		log.info(String.format("ejercicio 2 - tiempo de ejecución: %1$d ms", (endTime - startTime)));
		productos.forEach(p -> log.info(p.toString()));
	}

	@Test
	@DisplayName("Obtener una lista de pedidos con productos de categoria \"bebe\" ")
	public void exercise3() {
		long startTime = System.currentTimeMillis();
		List<Pedido> pedidos = pedidoDao.findAll()
				.stream()
				.filter(p -> p.getProductos().stream().anyMatch(pr -> pr.getCategoria().equalsIgnoreCase("bebe")))
				.collect(Collectors.toList());
		long endTime = System.currentTimeMillis();

		log.info(String.format("ejercicio 3 - tiempo de ejecución: %1$d ms", (endTime - startTime)));
		pedidos.forEach(p -> log.info(p.toString()));
	}

	@Test
	@DisplayName("Obtener una lista de productos con productos de categoria \"Libros\" y precio >100 ")
	public void exercise4() {
		long startTime = System.currentTimeMillis();
		List<Producto> productos = productoDao.findAll()
				.stream()
				.filter(p -> p.getCategoria().equalsIgnoreCase("Libros"))
				.filter(p -> p.getPrecio() >100 )
				.collect(Collectors.toList());
		long endTime = System.currentTimeMillis();

		log.info(String.format("ejercicio 3 - tiempo de ejecución: %1$d ms", (endTime - startTime)));
		productos.forEach(p -> log.info(p.toString()));
	}

}
