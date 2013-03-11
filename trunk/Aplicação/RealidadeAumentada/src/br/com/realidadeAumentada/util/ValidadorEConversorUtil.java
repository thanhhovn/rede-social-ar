package br.com.realidadeAumentada.util;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class ValidadorEConversorUtil {

	private static String[] meses = {"jan","fev","mar","abr","mai","jun","jul","ago","set","out","nov","dez"};
	
	public static int mes(String mes){
		Integer retorno = null;
		for (int i = 0; i < meses.length; i++) {
			if(mes.equals(meses[i])){
				retorno = i;
			}
		}
		return retorno;
	}
	
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
	
//	public static void main(String[] args){
//		DataConverter data = new DataConverter();
//		int mes = data.mes("mar");
//		System.out.println(mes);
//	}
}
