
import java.text.DecimalFormat;

public class Data {

    /*
        componentes[0] = dia;
        componentes[1] = mes;
        componentes[2] = ano;
    */
    int componentes [] = new int [3];
    
    /*
        0 Britânico: DD/MM/AAAA
	1 Americano: MM/DD/AAAA
    	2 Italiano: DD-MM-AAAA
	3 Germânico: DD.MM.AAAA
	4 ANSI: AAAA.MM.DD
    */
    static int formato = 0;

    /*
        termos[] = é para a ordem da data de acordo com a classificação;
        por padrão dessa classe é utilizado o padrão britânico.
    */
    int termos[] = {0,1,2};
    
    static String separador = "/";

    public Data(String sd) {
        this.stringData(sd);
    }
    
    public Data(int dia, int mes, int ano){
        if (diasMes(mes, ano) >= dia) {
            this.componentes[this.termos[0]] = dia;
            this.componentes[this.termos[1]] = mes;
            this.componentes[this.termos[2]] = ano;
        }
    }

    public boolean stringData(String sd){
        int pos[] = new int[2];
        String sub1,sub2,sub3;
        int j = 0;
        for (int i = 0; i < sd.length(); i++) {
            if (separador.equals(sd.charAt(i))) {
                pos[j] = i;
                j++;
            }
        }
        
        sub1 = sd.substring(0, pos[0]);
        sub2 = sd.substring(pos[0] + 1, pos[1]);
        sub3 = sd.substring(pos[1]+ 1);
        
        int aux = Integer.parseInt(sub2);
        
        if (aux > diasMes(aux, Integer.parseInt(sub3))) {
            return false;
        }
        
        this.componentes[this.termos[0]] = Integer.parseInt(sub1);
        this.componentes[this.termos[1]] = Integer.parseInt(sub2);
        this.componentes[this.termos[2]] = Integer.parseInt(sub3);
        
        return true;
    }
    
    public String dataString(){
        String data;
        data = new DecimalFormat("00").format(this.componentes[this.termos[0]]) + this.separador;
        data += new DecimalFormat("00").format(this.componentes[this.termos[1]]) + this.separador;
        data += String.valueOf(this.componentes[this.termos[2]]);
        return data;
    }
    
    static boolean bissexto(int ano){
        return ((ano%4) == 0 && (ano%100) != 0) || ano%400 == 0;
    }
    
    static int diasMes(int m, int a){
        if (m == 4 || m == 6 || m == 9 || m == 11) {
            return 30;
        }
        else{
            if (m == 2) {
                return 28;
            }
            else{
                if (m == 2 && bissexto(a)) {
                    return 29;
                }
            }
        }
        return 31;
    }
    
    static boolean mudaFormato(int f){
        if (f < 0 || f > 4) {
            return false;
        }
        Data.formato = f;
        return true;
    }
    
}
