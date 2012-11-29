package br.com.realidadeAumentada.validador;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class ValidadorCPF {

	private static final long serialVersionUID = 1L;

	public void validate(String cpf)
	{ 
		String cpfValor = (String) cpf;
		cpfValor = cpfValor.replaceAll("\\.", "");
		cpfValor = cpfValor.replaceAll("-", "");
		cpfValor = cpfValor.replaceAll("_", "");
		Pattern p = Pattern.compile("^[0-9]{11}$");
		Matcher m = p.matcher(cpfValor);
		if ( !m.matches() || !validarCpf(cpfValor) ) {
			
		}
	}

	private boolean validarCpf(String cpf) {	
		int soma = 0;
		for (int i = 0; i < 9; i++) { 
			soma = soma + parseInt(cpf.charAt(i)) * (10 - i);
		}
		int dv1 = 11 - (soma % 11);
		if (dv1 == 10 || dv1 == 11) {
			dv1 = 0;
		}
		if (dv1 != parseInt(cpf.charAt(9))) {
			return false;
		}
		soma = 0;
		for (int i = 0; i < 9; i++) {
			soma = soma + parseInt(cpf.charAt(i)) * (11 - i);
		}
		soma = soma + dv1 * 2;
		int dv2 = 11 - (soma % 11);
		if (dv2 == 10 || dv2 == 11) {
			dv2 = 0;
		}
		if (dv2 != parseInt(cpf.charAt(10))) {
			return false;
		}
		return true;
	}
	
	private int parseInt(Character c) {
		return Integer.parseInt(c.toString());
	}
	
}
