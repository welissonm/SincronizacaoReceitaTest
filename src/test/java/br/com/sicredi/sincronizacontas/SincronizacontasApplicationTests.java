package br.com.sicredi.sincronizacontas;

import org.junit.jupiter.api.Test;
// import org.junit.jupiter.api.DisplayName;
// import org.junit.jupiter.api.Nested;

import org.springframework.beans.factory.annotation.Autowired;
// import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.context.SpringBootTest;

// import br.com.sicredi.sincronizacontas.models.Conta;
// import br.com.sicredi.sincronizacontas.repositories.ContaRepository;

// import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
// @DataJpaTest
class SincronizacontasApplicationTests {

	@Autowired
	// ContaRepository contaRepository;


	@Test
	void contextLoads() {
	}

	// @Test
	// @DisplayName("Testando save do ContaRepository")
	// public void Save(){
	// 	Conta contaA = new Conta();
	// 	contaA.setAgencia(756);
	// 	contaA.setNumero("123456-7");
	// 	contaA.setSaldo(0.0F);
	// 	contaA.setStatus('I');
	// 	Conta result = contaRepository.save(contaA);
	// 	assertThat(result).isNotNull();
	// }



}
