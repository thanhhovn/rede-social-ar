package br.com.AR.conexao;

import static org.junit.Assert.*;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.fail;

import java.util.List;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import br.com.AR.webService.FilmeBean;
import br.com.AR.webService.Locadora;

public class Teste {

	private static FilmeBean filme;
	private static Locadora implementacao;
	
	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
	}

	@AfterClass
	public static void tearDownAfterClass() throws Exception {
	}

	@Before
	public void setUp() throws Exception {
		filme = new FilmeBean();
		implementacao = new Locadora();
	}

	@After
	public void tearDown() throws Exception {
	}

	@Test
	public void testFilmeImpl() {
		fail("Not yet implemented");
	}

	@Test
	public void testVerificarDisponibilidade() {
		fail("Not yet implemented");
	}

	@Test
	public void testLocarFilme() {
		fail("Not yet implemented");
	}

	@Test
	public void testDevolverFilme() {
		fail("Not yet implemented");
	}

	@Test
	public void testConsultarFilme() {
		FilmeBean filmeBean = implementacao.consultarFilme("Até que termine o dia");
//		List lista = implementacao.listarFilmes();
	//	assertNotNull(filmeBean); 
	}

	@Test
	public void testListarFilmes() {
		fail("Not yet implemented");
	}

	@Test
	public void testSubmitJob() {
		fail("Not yet implemented");
	}

	@Test
	public void testMain() {
		fail("Not yet implemented");
	}

}
