/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package alvaro_tools;

/**
 *
 * @author alumno1718_2
 */
public class Comparators {
    public boolean between(Integer number_tocheck,Integer low_number,Integer high_number){
        return low_number<number_tocheck && number_tocheck<high_number;
    }
    public boolean between(Integer number_tocheck,Integer low_number,Long high_number){
        return low_number.longValue()<number_tocheck.longValue() && number_tocheck.longValue()<high_number;
    }
    public boolean between(Integer number_tocheck,Integer low_number,Float high_number){
        return low_number.floatValue()<number_tocheck.floatValue() && number_tocheck.floatValue()<high_number;
    }
    public boolean between(Integer number_tocheck,Integer low_number,Double high_number){
        return low_number.doubleValue()<number_tocheck.doubleValue() && number_tocheck.doubleValue()<high_number;
    }
    public boolean between(Integer number_tocheck,Long low_number,Integer high_number){
        return low_number<number_tocheck.longValue() && number_tocheck.longValue()<high_number.longValue();
    }
    public boolean between(Integer number_tocheck,Long low_number,Long high_number){
        return low_number<number_tocheck.longValue() && number_tocheck.longValue()<high_number;
    }
    public boolean between(Integer number_tocheck,Long low_number,Float high_number){
        return low_number.floatValue()<number_tocheck.floatValue() && number_tocheck.floatValue()<high_number;
    }
    public boolean between(Integer number_tocheck,Long low_number,Double high_number){
        return low_number.doubleValue()<number_tocheck.doubleValue() && number_tocheck.doubleValue()<high_number;
    }
    public boolean between(Integer number_tocheck,Float low_number,Integer high_number){
        return low_number<number_tocheck.floatValue() && number_tocheck.floatValue()<high_number.floatValue();
    }
    public boolean between(Integer number_tocheck,Float low_number,Long high_number){
        return low_number<number_tocheck.floatValue() && number_tocheck.floatValue()<high_number.floatValue();
    }
    public boolean between(Integer number_tocheck,Float low_number,Float high_number){
        return low_number<number_tocheck.floatValue() && number_tocheck.floatValue()<high_number;
    }
    public boolean between(Integer number_tocheck,Float low_number,Double high_number){
        return low_number.doubleValue()<number_tocheck.doubleValue() && number_tocheck.doubleValue()<high_number;
    }
    public boolean between(Integer number_tocheck,Double low_number,Integer high_number){
        return low_number<number_tocheck.doubleValue() && number_tocheck.doubleValue()<high_number.doubleValue();
    }
    public boolean between(Integer number_tocheck,Double low_number,Long high_number){
        return low_number<number_tocheck.doubleValue() && number_tocheck.doubleValue()<high_number.doubleValue();
    }
    public boolean between(Integer number_tocheck,Double low_number,Float high_number){
        return low_number<number_tocheck.doubleValue() && number_tocheck.doubleValue()<high_number.doubleValue();
    }
    public boolean between(Integer number_tocheck,Double low_number,Double high_number){
        return low_number<number_tocheck.doubleValue() && number_tocheck.doubleValue()<high_number;
    }
    public boolean between(Long number_tocheck,Integer low_number,Integer high_number){
        return low_number.longValue()<number_tocheck && number_tocheck<high_number.longValue();
    }
    public boolean between(Long number_tocheck,Integer low_number,Long high_number){
        return low_number.longValue()<number_tocheck && number_tocheck<high_number;
    }
    public boolean between(Long number_tocheck,Integer low_number,Float high_number){
        return low_number.floatValue()<number_tocheck.floatValue() && number_tocheck.floatValue()<high_number;
    }
    public boolean between(Long number_tocheck,Integer low_number,Double high_number){
        return low_number.doubleValue()<number_tocheck.doubleValue() && number_tocheck.doubleValue()<high_number;
    }
    public boolean between(Long number_tocheck,Long low_number,Integer high_number){
        return low_number<number_tocheck && number_tocheck<high_number.longValue();
    }
    public boolean between(Long number_tocheck,Long low_number,Long high_number){
        return low_number<number_tocheck && number_tocheck<high_number;
    }
    public boolean between(Long number_tocheck,Long low_number,Float high_number){
        return low_number.floatValue()<number_tocheck.floatValue() && number_tocheck.floatValue()<high_number;
    }
    public boolean between(Long number_tocheck,Long low_number,Double high_number){
        return low_number.doubleValue()<number_tocheck.doubleValue() && number_tocheck.doubleValue()<high_number;
    }
    public boolean between(Long number_tocheck,Float low_number,Integer high_number){
        return low_number<number_tocheck.floatValue() && number_tocheck.floatValue()<high_number.floatValue();
    }
    public boolean between(Long number_tocheck,Float low_number,Long high_number){
        return low_number<number_tocheck.floatValue() && number_tocheck.floatValue()<high_number.floatValue();
    }
    public boolean between(Long number_tocheck,Float low_number,Float high_number){
        return low_number<number_tocheck.floatValue() && number_tocheck.floatValue()<high_number;
    }
    public boolean between(Long number_tocheck,Float low_number,Double high_number){
        return low_number.doubleValue()<number_tocheck.doubleValue() && number_tocheck.doubleValue()<high_number;
    }
    public boolean between(Long number_tocheck,Double low_number,Integer high_number){
        return low_number<number_tocheck.doubleValue() && number_tocheck.doubleValue()<high_number.doubleValue();
    }
    public boolean between(Long number_tocheck,Double low_number,Long high_number){
        return low_number<number_tocheck.doubleValue() && number_tocheck.doubleValue()<high_number.doubleValue();
    }
    public boolean between(Long number_tocheck,Double low_number,Float high_number){
        return low_number<number_tocheck.doubleValue() && number_tocheck.doubleValue()<high_number.doubleValue();
    }
    public boolean between(Long number_tocheck,Double low_number,Double high_number){
        return low_number<number_tocheck.doubleValue() && number_tocheck.doubleValue()<high_number;
    }
    public boolean between(Float number_tocheck,Integer low_number,Integer high_number){
        return low_number.floatValue()<number_tocheck && number_tocheck<high_number.floatValue();
    }
    public boolean between(Float number_tocheck,Integer low_number,Long high_number){
        return low_number.floatValue()<number_tocheck && number_tocheck<high_number.floatValue();
    }
    public boolean between(Float number_tocheck,Integer low_number,Float high_number){
        return low_number.floatValue()<number_tocheck && number_tocheck<high_number;
    }
    public boolean between(Float number_tocheck,Integer low_number,Double high_number){
        return low_number.doubleValue()<number_tocheck.doubleValue() && number_tocheck.doubleValue()<high_number;
    }
    public boolean between(Float number_tocheck,Long low_number,Integer high_number){
        return low_number.floatValue()<number_tocheck && number_tocheck<high_number.floatValue();
    }
    public boolean between(Float number_tocheck,Long low_number,Long high_number){
        return low_number.floatValue()<number_tocheck && number_tocheck<high_number.floatValue();
    }
    public boolean between(Float number_tocheck,Long low_number,Float high_number){
        return low_number.floatValue()<number_tocheck && number_tocheck<high_number;
    }
    public boolean between(Float number_tocheck,Long low_number,Double high_number){
        return low_number.doubleValue()<number_tocheck.doubleValue() && number_tocheck.doubleValue()<high_number;
    }
    public boolean between(Float number_tocheck,Float low_number,Integer high_number){
        return low_number<number_tocheck && number_tocheck<high_number.floatValue();
    }
    public boolean between(Float number_tocheck,Float low_number,Long high_number){
        return low_number<number_tocheck && number_tocheck<high_number.floatValue();
    }
    public boolean between(Float number_tocheck,Float low_number,Float high_number){
        return low_number<number_tocheck && number_tocheck<high_number;
    }
    public boolean between(Float number_tocheck,Float low_number,Double high_number){
        return low_number.doubleValue()<number_tocheck.doubleValue() && number_tocheck.doubleValue()<high_number;
    }
    public boolean between(Float number_tocheck,Double low_number,Integer high_number){
        return low_number<number_tocheck.doubleValue() && number_tocheck.doubleValue()<high_number.doubleValue();
    }
    public boolean between(Float number_tocheck,Double low_number,Long high_number){
        return low_number<number_tocheck.doubleValue() && number_tocheck.doubleValue()<high_number.doubleValue();
    }
    public boolean between(Float number_tocheck,Double low_number,Float high_number){
        return low_number<number_tocheck.doubleValue() && number_tocheck.doubleValue()<high_number.doubleValue();
    }
    public boolean between(Float number_tocheck,Double low_number,Double high_number){
        return low_number<number_tocheck.doubleValue() && number_tocheck.doubleValue()<high_number;
    }
    public boolean between(Double number_tocheck,Integer low_number,Integer high_number){
        return low_number.doubleValue()<number_tocheck && number_tocheck<high_number.doubleValue();
    }
    public boolean between(Double number_tocheck,Integer low_number,Long high_number){
        return low_number.doubleValue()<number_tocheck && number_tocheck<high_number.doubleValue();
    }
    public boolean between(Double number_tocheck,Integer low_number,Float high_number){
        return low_number.doubleValue()<number_tocheck && number_tocheck<high_number.doubleValue();
    }
    public boolean between(Double number_tocheck,Integer low_number,Double high_number){
        return low_number.doubleValue()<number_tocheck && number_tocheck<high_number;
    }
    public boolean between(Double number_tocheck,Long low_number,Integer high_number){
        return low_number.doubleValue()<number_tocheck && number_tocheck<high_number.doubleValue();
    }
    public boolean between(Double number_tocheck,Long low_number,Long high_number){
        return low_number.doubleValue()<number_tocheck && number_tocheck<high_number.doubleValue();
    }
    public boolean between(Double number_tocheck,Long low_number,Float high_number){
        return low_number.doubleValue()<number_tocheck && number_tocheck<high_number.doubleValue();
    }
    public boolean between(Double number_tocheck,Long low_number,Double high_number){
        return low_number.doubleValue()<number_tocheck && number_tocheck<high_number;
    }
    public boolean between(Double number_tocheck,Float low_number,Integer high_number){
        return low_number.doubleValue()<number_tocheck && number_tocheck<high_number.doubleValue();
    }
    public boolean between(Double number_tocheck,Float low_number,Long high_number){
        return low_number.doubleValue()<number_tocheck && number_tocheck<high_number.doubleValue();
    }
    public boolean between(Double number_tocheck,Float low_number,Float high_number){
        return low_number.doubleValue()<number_tocheck && number_tocheck<high_number.doubleValue();
    }
    public boolean between(Double number_tocheck,Float low_number,Double high_number){
        return low_number.doubleValue()<number_tocheck && number_tocheck<high_number;
    }
    public boolean between(Double number_tocheck,Double low_number,Integer high_number){
        return low_number<number_tocheck && number_tocheck<high_number.doubleValue();
    }
    public boolean between(Double number_tocheck,Double low_number,Long high_number){
        return low_number<number_tocheck && number_tocheck<high_number.doubleValue();
    }
    public boolean between(Double number_tocheck,Double low_number,Float high_number){
        return low_number<number_tocheck && number_tocheck<high_number.doubleValue();
    }
    public boolean between(Double number_tocheck,Double low_number,Double high_number){
        return low_number<number_tocheck && number_tocheck<high_number;
    }
}
