package br.com.realidadeAumentada.util;


public class DataConverter {

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
	
	public static void main(String[] args){
		DataConverter data = new DataConverter();
		int mes = data.mes("mar");
		System.out.println(mes);
	}
	
	
}

