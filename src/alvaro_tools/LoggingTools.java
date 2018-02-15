/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package alvaro_tools;

/**
 * Auxiliar log functions
 *
 * @author alvaro9650
 */
public class LoggingTools {

    /**
     * Shows a value and returns it. This is usefull for logging in very long
     * lines
     *
     * @param variable variable you want to see
     * @param varname variable name
     * @return the variable you passed without any changes
     * @author alvaro9650
     */
    static public Integer showValueInline(Integer variable, String varname) {
        System.out.println(varname + " value is " + variable);
        return variable;
    }

    /**
     * Shows a value and returns it. This is usefull for logging in very long
     * lines
     *
     * @param variable variable you want to see
     * @param varname variable name
     * @return the variable you passed without any changes
     * @author alvaro9650
     */
    static public Boolean showValueInline(Boolean variable, String varname) {
        System.out.println(varname + " value is " + variable);
        return variable;
    }

    /**
     * Shows a value and returns it. This is usefull for logging in very long
     * lines
     *
     * @param variable variable you want to see
     * @param varname variable name
     * @return the variable you passed without any changes
     * @author alvaro9650
     */
    static public Float showValueInline(Float variable, String varname) {
        System.out.println(varname + " value is " + variable);
        return variable;
    }

    /**
     * Shows a value and returns it. This is usefull for logging in very long
     * lines
     *
     * @param variable variable you want to see
     * @param varname variable name
     * @return the variable you passed without any changes
     * @author alvaro9650
     */
    static public Double showValueInline(Double variable, String varname) {
        System.out.println(varname + " value is " + variable);
        return variable;
    }

    /**
     * Shows a value and returns it. This is usefull for logging in very long
     * lines
     *
     * @param variable variable you want to see
     * @param varname variable name
     * @return the variable you passed without any changes
     * @author alvaro9650
     */
    static public String showValueInline(String variable, String varname) {
        System.out.println(varname + " value is " + variable);
        return variable;
    }
}
