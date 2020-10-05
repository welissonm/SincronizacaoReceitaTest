package br.com.sicredi.sincronizacontas;

import org.junit.jupiter.api.Test;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.domain.EntityScan;

import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@SpringBootTest
@EnableAutoConfiguration
@EntityScan(basePackages = {"br.com.sicredi.sincronizacontas"})
@EnableJpaRepositories(value = {"br.com.sicredi.sincronizacontas"})
class SincronizacontasApplicationTests {

	// @Autowired
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
