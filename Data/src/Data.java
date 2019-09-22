package date;

import java.text.DecimalFormat;
import java.time.LocalDate;

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
    static int termos[] = {0,1,2};

    private static char separador = '/';

    public Data(int dia, int mes, int ano){
        if (diasMes(mes, ano) >= dia) {
            this.componentes[this.termos[0]] = dia;
            this.componentes[this.termos[1]] = mes;
            this.componentes[this.termos[2]] = ano;
        }
    }

    public Data(String sd) {
        this.stringData(sd);
    }

    public Data(Data d){
        this.componentes[this.termos[0]] = d.componentes[d.termos[0]];
        this.componentes[this.termos[1]] = d.componentes[d.termos[1]];
        this.componentes[this.termos[2]] = d.componentes[d.termos[2]];
    }

    public boolean stringData(String sd){
        int pos[] = new int[2];
        String sub1,sub2,sub3;
        int j = 0;
        for (int i = 0; i < sd.length(); i++) {
            if (sd.charAt(i) == separador) {
                pos[j] = i;
                j++;
            }
        }

        sub1 = sd.substring(0, pos[0]);
        sub2 = sd.substring(pos[0] + 1, pos[1]);
        sub3 = sd.substring(pos[1]+ 1);

        int aux = Integer.parseInt(sub2);

        if (Integer.parseInt(sub1) > diasMes(aux, Integer.parseInt(sub3))) {
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
        data += new DecimalFormat("00").format(this.componentes[this.termos[2]]);
        return data;
    }

    public static boolean bissexto(int ano){
        return ((ano%4) == 0 && (ano%100) != 0) || ano%400 == 0;
    }

    public static int diasMes(int mes, int ano){
        if (mes == 4 || mes == 6 || mes == 9 || mes == 11) {
            return 30;
        }
        else{ if (mes == 2 && bissexto(ano)) {
            return 29;
        }
        else{
            if (mes == 2) {
                return 28;
            }
        }
        }
        return 31;
    }

    private long dataDias(){
        int mes = this.componentes[1];
        int dias = this.componentes[0];

        for (int i = 1900; i < this.componentes[2]; i++) {
            if (bissexto(i)) {
                dias += 366;
            }
            else{
                dias += 365;
            }
            if (i == this.componentes[2] - 1) {
                while(mes > 1){
                    dias += diasMes(mes, i);
                    mes--;
                }
            }
        }
        return dias - 1;
    }

    private void diasData(long d){

    }

    public Data soma(int dias){
        Data obj = new Data(this.componentes[0], this.componentes[1], this.componentes[2]);
        for (int i = dias; i > 0; i--) {
            obj.componentes[0]++;
            if (obj.componentes[0] == diasMes(obj.componentes[1],obj.componentes[2])) {
                obj.componentes[1]++;
                obj.componentes[0] = 0;
            }
            if (obj.componentes[1] == 12) {
                obj.componentes[1] = 0;
                obj.componentes[2]++;
            }
        }
        return obj;
    }

    public Data sub(int dias){
        Data obj = new Data(this.componentes[0], this.componentes[1], this.componentes[2]);
        for (int i = dias; i > 0; i--) {
            obj.componentes[0]--;
            if (obj.componentes[0] == 0) {
                obj.componentes[1]--;
                obj.componentes[0] = diasMes(obj.componentes[1],obj.componentes[2]);
            }
            if (obj.componentes[1] == 0) {
                obj.componentes[1] = 12;
                obj.componentes[2]--;
            }
        }
        return obj;
    }

    private boolean anoValido (int ano){
        if (ano > 0 && ano < 5000){
            return true;
        }
        return false;
    }

    public long dataSubtracao(int ano1, int ano2){
        if (anoValido(ano1) && anoValido(ano2)) {
            if ((bissexto(ano1) && bissexto(ano2)) || (!bissexto(ano1) && (!bissexto(ano2)))) {
                if (ano2 > ano1) {
                    int anos = ano2 - ano1;
                    int bissextos = (anos / 4);
                    int dias = (anos * 365) + bissextos;
                    return dias;
                }
                if (ano1 > ano2) {
                    int anos = ano1 - ano2;
                    int bissextos = (anos / 4);
                    int dias = (anos * 365) + bissextos;
                    return dias;
                }
            }
            if (bissexto(ano1) || bissexto(ano2)) {
                if (ano2 > ano1) {
                    int anos = ano2 - ano1;
                    int bissextos = (anos / 4) + 1;
                    int dias = (anos * 365) + bissextos;
                    return dias;
                }
                if (ano1 > ano2) {
                    int anos = ano1 - ano2;
                    int bissextos = (anos / 4) + 1;
                    int dias = (anos * 365) + bissextos;
                    return dias;
                }
            }
        }
        return 0 ;
    }

    static boolean mudaFormato(int f){
        if (formato < 0 || formato > 4) {
            return false;
        }
        formato = f;
        switch(f){
            case 0:
                separador = '/';
                termos[0] = 0;
                termos[1] = 1;
                termos[2] = 2;
                break;
            case 1:
                separador = '/';
                termos[0] = 1;
                termos[1] = 0;
                termos[2] = 2;
                break;
            case 2:
                separador = '-';
                termos[0] = 0;
                termos[1] = 1;
                termos[2] = 2;
                break;
            case 3:
                separador = '.';
                termos[0] = 0;
                termos[1] = 1;
                termos[2] = 2;
                break;
            case 4:
                separador = '.';
                termos[0] = 2;
                termos[1] = 1;
                termos[2] = 0;
                break;
        }
        return true;
    }

}

