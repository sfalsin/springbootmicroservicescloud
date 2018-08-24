package br.com.meupag.sbac3;

import org.springframework.stereotype.Component;

@Component 
class Servico1Fallback implements Servico1 {

	@Override
	public String servico1() {
		return "DEU RUIM! FAIÔ!";
	}
}