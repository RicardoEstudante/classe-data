public class TesteData {
    public static void main(String[] args) {
        Data data = new Data("20/10/2010");
        Data data1 = new Data("01/06/2000");
        Data datac = new Data(data1);
        
        System.out.println(data.dataString());
        
        data.mudaFormato(4);
        
        System.out.println(data.dataString());
        
        System.out.println(data1.dataString());
        
        data1.mudaFormato(2);
        
        System.out.println(data1.dataString());
        
        System.out.println(data.dataString());
        
        System.out.println("Data copia: " + datac.dataString());
    }
}
