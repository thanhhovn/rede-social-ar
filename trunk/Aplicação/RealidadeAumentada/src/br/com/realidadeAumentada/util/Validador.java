package br.com.realidadeAumentada.util;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Validador {

	public static boolean validarEmail(String email){
		boolean isEmailValido = false;
		if(email != null && email.length() > 0){
			String expressao = "^[\\w\\.-]+@([\\w\\-]+\\.)+[a-z]{2,4}$";
			Pattern pattern = Pattern.compile(expressao,Pattern.CASE_INSENSITIVE);
			Matcher matcher = pattern.matcher(email);
			if(matcher.matches()){
				isEmailValido = true;
			}
		}
		return isEmailValido;
	}
}
